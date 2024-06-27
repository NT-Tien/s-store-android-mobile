package com.example.s_store.products.model;

public class Card {
    private String name;
    private String description;
    private double price;
    private boolean inStock;
    private int imageResourceId;

    public Card(String name, String description, double price, boolean inStock, int imageResId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.inStock = inStock;
        this.imageResourceId = imageResId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }
}
