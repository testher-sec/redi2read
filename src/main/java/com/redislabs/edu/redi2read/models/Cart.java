package com.redislabs.edu.redi2read.models;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.Set;

@Data
@Builder
public class Cart {
    private String id;
    private String userId;

    @Singular //Lombok generates for the field : One 'adder' method for adding single element to collection
    private Set<CartItem> cartItems;
    public Integer count() {
        return getCartItems().size();
    }
    public Double getTotal() {
        return cartItems
                .stream()
                .mapToDouble(ci -> ci.getPrice() * ci.getQuantity())
                .sum();
    }
}
