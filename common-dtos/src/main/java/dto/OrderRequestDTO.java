package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private String productName;
    private double productAmount;

}
