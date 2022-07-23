package dto;

import events.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDTO {

    private Integer paymentId;
    private Integer userId;
    private Integer orderId;

    private Integer productId;
    private double productAmount;

    private PaymentStatus paymentStatus;

}
