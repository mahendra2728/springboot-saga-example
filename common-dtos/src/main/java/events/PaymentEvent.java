package events;

import dto.PaymentRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEvent {

    private PaymentRequestDTO paymentRequestDTO;
    private PaymentStatus paymentStatus;

}
