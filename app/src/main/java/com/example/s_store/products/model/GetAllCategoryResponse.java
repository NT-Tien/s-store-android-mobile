package com.example.s_store.products.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetAllCategoryResponse {
    @SerializedName("data")
    private ArrayList<Category> categories;

    @SerializedName("message")
    private String message;

    @SerializedName("statusCode")
    private int statusCode;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
