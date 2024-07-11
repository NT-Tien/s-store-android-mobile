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
    private String app_trans_id;
    private String zp_trans_id;
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
            private String app_id;
            private long app_time;
            private String app_user;
            private String bank_code;
            private String embed_data;
            private String description;
            private String app_trans_id;
            private String callback_url;
        }

        @Getter
        public static class PaymentDetails {
            private String qr_code;
            private String order_url;
            private String order_token;
            private int return_code;
            private String return_message;
            private String zp_trans_token;
            private int sub_return_code;
            private String sub_return_message;
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
