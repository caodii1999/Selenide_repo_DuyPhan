package model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    private int orderNumber;
    private String orderDate;
    private double totalPrice;

    public Order(int orderNumber, String orderDate) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
    }

}
