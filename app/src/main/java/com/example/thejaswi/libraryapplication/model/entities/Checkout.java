package com.example.thejaswi.libraryapplication.model.entities;

import java.util.List;

public class Checkout {
    private String email;
    private List<Catalog> cart;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Catalog> getCart() {
        return cart;
    }

    public void setCart(List<Catalog> cart) {
        this.cart = cart;
    }
}
