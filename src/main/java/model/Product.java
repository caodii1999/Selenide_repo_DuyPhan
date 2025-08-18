package model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {

    private String name;
    private double price;
    private int quantity;
    private double subTotal;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(int qty, double subTotal) {
        this.quantity = qty;
        this.subTotal = subTotal;
    }
}
