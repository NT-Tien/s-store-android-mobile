package com.example.s_store.auth.models;

public class SignUpRequest {
    private String username;
    private String email;
    private String phone;
    private String password;

    public SignUpRequest(String username, String email, String phone, String password) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }
}
