package order.config;

import dto.OrderRequestDTO;
import events.OrderStatus;
import events.PaymentStatus;
import java.util.function.Consumer;
import order.entity.Order;
import order.repository.OrderRepository;
import order.service.OrderEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class OrderUpdateHandler {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEventPublisher orderEventPublisher;

    @Transactional
    public void updateOrder(Integer orderId, Consumer<Order> orderConsumer){
        orderRepository.findById(orderId).ifPresent(orderConsumer.andThen(this::updateOrder));
    }

    private void updateOrder(Order order) {
        boolean isPaymentComplete = PaymentStatus.PAYMENT_SUCCESS.equals(order.getPaymentStatus());
        OrderStatus orderStatus = isPaymentComplete ? OrderStatus.ORDER_COMPLETED : OrderStatus.ORDER_CANCELLED;
        order.setOrderStatus(orderStatus);
        if (!isPaymentComplete) {
            // Updating failed status in order tbl
            updatedOrderStatus(order, orderStatus);
            orderEventPublisher.publishOrderEvent(convertEntityToDto(order),orderStatus);
        }
        else {
            // Updating order/payment status in Order table
            orderRepository.findById(order.getOrderId())
                .ifPresent(order1 -> {
                    order1.setOrderId(order.getOrderId());
                    order1.setPaymentStatus(PaymentStatus.PAYMENT_SUCCESS);
                    order1.setOrderStatus(orderStatus);
                    orderRepository.save(order1);
                });
        }
    }

    private void updatedOrderStatus(Order order, OrderStatus orderStatus) {
        Order order1 = new Order();
        order1.setOrderId(order.getOrderId());
        order1.setProductId(order.getProductId());
        order1.setUserId(order.getUserId());
        order1.setProductAmount(order.getProductAmount());
        order1.setPaymentStatus(PaymentStatus.PAYMENT_FAILED);
        order1.setOrderStatus(orderStatus);
        orderRepository.save(order1);
    }

    public static OrderRequestDTO convertEntityToDto(Order order){
        OrderRequestDTO orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setOrderId(order.getOrderId());
        orderRequestDTO.setUserId(order.getUserId());
        orderRequestDTO.setProductId(order.getProductId());
        orderRequestDTO.setProductName(order.getProductName());
        orderRequestDTO.setProductAmount(order.getProductAmount());
        return orderRequestDTO;

    }
}
