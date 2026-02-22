package com.ladajules.mobdevtasks;

import java.io.Serializable;

public class Product implements Serializable {
    String name;
    double price;
    int quantity;
    int imageId;

    public Product(String name, double price, int quantity, int imageId) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
