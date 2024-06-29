package com.example.s_store.products.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAllProductResponse {
    @SerializedName("data")
    private ArrayList<Product> products;

    @SerializedName("message")
    private String message;

    @SerializedName("statusCode")
    private int statusCode;

    public ArrayList<Product> getProducts() {
        return products;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
