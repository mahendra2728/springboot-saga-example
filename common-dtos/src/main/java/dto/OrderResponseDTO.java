package dto;

import events.OrderStatus;

public class OrderResponseDTO {

    private Integer orderId;
    private Integer userId;
    private Integer productId;
    private String productName;
    private double productAmount;

    private OrderStatus orderStatus;


}
