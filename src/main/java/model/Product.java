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
public class Product {

  private String name;
  private String quantity;
  private double price;

  public Product(String name) {
    this.name = name;
  }

  public Product(double price) {
    this.price = price;
  }
}
