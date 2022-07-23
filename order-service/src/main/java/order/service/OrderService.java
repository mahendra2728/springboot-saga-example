package order.service;

import dto.OrderRequestDTO;
import events.OrderStatus;
import events.PaymentStatus;
import java.util.List;
import order.entity.Order;
import order.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) {

        Order order = orderRepository.save(convertDtoToEntity(orderRequestDTO));
        orderRequestDTO.setOrderId(order.getOrderId());
        // making call for Publishing orderevent into order-event topic
        orderEventPublisher.publishOrderEvent(orderRequestDTO,OrderStatus.ORDER_CREATED);

        return order;
    }

    private static Order convertDtoToEntity(OrderRequestDTO orderRequestDTO) {
        Order order =new Order();
        order.setUserId(orderRequestDTO.getUserId());
        order.setProductId(orderRequestDTO.getProductId());
        order.setProductName(orderRequestDTO.getProductName());
        order.setProductAmount(orderRequestDTO.getProductAmount());
        order.setOrderStatus(OrderStatus.ORDER_CREATED);
        order.setPaymentStatus(PaymentStatus.PAYMENT_INITIATED);
        return order;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
