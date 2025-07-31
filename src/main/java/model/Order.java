package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
