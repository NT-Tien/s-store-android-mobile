package com.example.s_store.auth.models;

import com.google.gson.annotations.SerializedName;

public class SignUpResponse {
    @SerializedName("data")
    private UserData data;

    @SerializedName("message")
    private String message;

    @SerializedName("statusCode")
    private int statusCode;

    public UserData getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public class UserData {
        private String username;
        private String email;
        private String phone;
        private String id;
        private String createdAt;
        private String updatedAt;
        private String deletedAt;
        private String role;

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }

        public String getId() {
            return id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public String getDeletedAt() {
            return deletedAt;
        }

        public String getRole() {
            return role;
        }
    }
}