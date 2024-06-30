package com.example.s_store.auth.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("data")
    private String token;

    @SerializedName("message")
    private String message;

    @SerializedName("statusCode")
    private int statusCode;

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }
}

