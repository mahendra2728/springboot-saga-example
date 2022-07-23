package order.service;

import dto.OrderRequestDTO;
import events.OrderEvent;
import events.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
public class OrderEventPublisher {

    @Autowired
    private Sinks.Many<OrderEvent> sinks;

    public void publishOrderEvent(OrderRequestDTO orderRequestDTO, OrderStatus orderStatus){
        OrderEvent orderEvent = new OrderEvent(orderRequestDTO, orderStatus);
        sinks.tryEmitNext(orderEvent);
        System.out.println("event pushed");
    }
}
