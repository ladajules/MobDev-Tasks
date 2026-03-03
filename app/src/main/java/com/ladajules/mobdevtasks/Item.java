package com.ladajules.mobdevtasks;

public class Item {
    private String name;
    private double price;
    private String size;
    private String color;

    public Item(String name, double price, String size, String color) {
        this.name = name;
        this.price = price;
        this.size = size;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}
