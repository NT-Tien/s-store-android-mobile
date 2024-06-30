package com.example.s_store.common.models;

import java.util.List;

import lombok.Getter;

@Getter
public class OrderModel {
    private String id;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;
    private String email;
    private String phone;
    private String username;
    private String address;
    private int total;
    private String status;
    private String appTransId;
    private String zpTransId;
    private Payment payment;
    private String note;
    private List<Item> items;

    @Getter
    public static class Payment {
        private Info info;
        private PaymentDetails payment;

        @Getter
        public static class Info {
            private String mac;
            private String item;
            private int amount;
            private String appId;
            private long appTime;
            private String appUser;
            private String bankCode;
            private String embedData;
            private String description;
            private String appTransId;
            private String callbackUrl;
        }

        @Getter
        public static class PaymentDetails {
            private String qrCode;
            private String orderUrl;
            private String orderToken;
            private int returnCode;
            private String returnMessage;
            private String zpTransToken;
            private int subReturnCode;
            private String subReturnMessage;
        }
    }

    @Getter
    public static class Item {
        private String id;
        private String createdAt;
        private String updatedAt;
        private String deletedAt;
        private String name;
        private int quantity;
        private int price;
        private Option opt;

        @Getter
        public static class Option {
            private String id;
            private String createdAt;
            private String updatedAt;
            private String deletedAt;
            private String name;
            private int price;
            private int quantity;
            private String image;
        }
    }
}
