package payment.service;

import dto.OrderRequestDTO;
import dto.PaymentRequestDTO;
import payment.entity.Payment;
import payment.entity.UserAccount;
import events.OrderEvent;
import events.OrderStatus;
import events.PaymentEvent;
import events.PaymentStatus;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payment.repository.PaymentRepository;
import payment.repository.UserAccountRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserAccountRepository userAccountRepository;

    @PostConstruct
    public void initUserAccountDB() {
        userAccountRepository.saveAll(Stream.of(new UserAccount(101, 5000),
            new UserAccount(102, 3000),
            new UserAccount(103, 4200),
            new UserAccount(104, 20000),
            new UserAccount(105, 999)).collect(Collectors.toList()));
    }

    @Transactional
    public PaymentEvent newOrderEvent(OrderEvent orderEvent) {
        OrderRequestDTO orderRequestDTO = orderEvent.getOrderRequestDTO();

        PaymentRequestDTO paymentRequestDTO = new PaymentRequestDTO(orderRequestDTO.getUserId(),
                        orderRequestDTO.getOrderId(),
                        orderRequestDTO.getProductId(),
                        orderRequestDTO.getProductAmount());

        return userAccountRepository.findById(paymentRequestDTO.getUserId())
              .filter(user -> user.getUserCredit() >= paymentRequestDTO.getProductAmount())
              .map(payment->{
                payment.setUserCredit(payment.getUserCredit() - paymentRequestDTO.getProductAmount());
                userAccountRepository.save(payment);

                // added entry in payment_tbl
                Payment payment1 =new Payment();
                payment1.setUserId(payment.getUserId());
                payment1.setOrderId(orderRequestDTO.getOrderId());
                payment1.setProductAmount(orderRequestDTO.getProductAmount());
                payment1.setOrderStatus(OrderStatus.ORDER_COMPLETED);
                payment1.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
                paymentRepository.save(payment1);

                return new PaymentEvent(paymentRequestDTO,PaymentStatus.PAYMENT_SUCCESS);

            }).orElse(new PaymentEvent(paymentRequestDTO, PaymentStatus.PAYMENT_FAILED));

    }

    @Transactional
    public void cancelOrderEvent(OrderEvent orderEvent) {
        userAccountRepository.findById(orderEvent.getOrderRequestDTO().getUserId())
            .ifPresent(userAccount -> {
               // userAccount.setUserCredit(userAccount.getUserCredit() + orderEvent.getOrderRequestDTO().getProductAmount());
                userAccountRepository.save(userAccount);

                // cancelling payment_tbl
                paymentRepository.findByOrderId(orderEvent.getOrderRequestDTO().getOrderId())
                    .ifPresent(payment -> {
                        paymentRepository.delete(payment);
                    });
            });
    }
}
