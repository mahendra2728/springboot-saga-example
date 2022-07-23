package order.entity;

import events.OrderStatus;
import events.PaymentStatus;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer orderId;

    private Integer userId;
    private Integer productId;
    private String productName;
    private double productAmount;


    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Override
    public String toString() {
        return "Order{" +
            "orderId=" + orderId +
            ", userId=" + userId +
            ", productId=" + productId +
            ", productName='" + productName + '\'' +
            ", productAmount=" + productAmount +
            ", orderStatus=" + orderStatus +
            ", paymentStatus=" + paymentStatus +
            '}';
    }
}
