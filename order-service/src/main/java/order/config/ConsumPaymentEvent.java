package order.config;

import events.PaymentEvent;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumPaymentEvent {

    @Autowired
    private OrderUpdateHandler orderUpdateHandler;

    @Bean
    public Consumer<PaymentEvent> paymentEventConsumer(){
        return (payment) -> orderUpdateHandler.updateOrder(payment.getPaymentRequestDTO().getOrderId(),order -> {
            order.setPaymentStatus(payment.getPaymentStatus());
        });
    }
}
