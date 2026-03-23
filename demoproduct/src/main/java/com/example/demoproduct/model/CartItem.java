package com.example.demoproduct.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {

    private Long productId;
    private String name;
    private long price;
    private int quantity;
    private String image;
}
