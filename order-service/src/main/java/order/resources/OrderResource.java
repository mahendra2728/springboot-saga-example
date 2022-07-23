package order.resources;

import dto.OrderRequestDTO;
import order.entity.Order;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import order.service.OrderService;

@RestController
@RequestMapping("/v1/api/")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @PostMapping(value = "createOrder")
    public Order createOrder(@RequestBody OrderRequestDTO orderRequestDTO){
        return orderService.createOrder(orderRequestDTO);

    }

    @GetMapping(value = "orders")
    public List<Order> getAllOrders(){
        return orderService.getAllOrders();
    }

}
