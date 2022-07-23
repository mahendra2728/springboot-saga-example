package events;

import dto.OrderRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent{

    private OrderRequestDTO orderRequestDTO;
    private OrderStatus orderStatus;

}
