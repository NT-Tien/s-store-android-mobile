package com.example.s_store.di.api.dto;

import com.example.s_store.common.entities.CartItemEntity;
import com.google.gson.JsonElement;

import java.util.List;

import lombok.Getter;

public class OrderResponseDto {

    @Getter
    public static class CreateOrder {
        private String id;
    }

    @Getter
    public static class GetResult {

        private String id;
        private String name;
        private OrderDataWrapper data;
        private Integer progress;
        private Integer delay;
        private Long timestamp;
        private Integer attemptsMade;
        private JsonElement returnvalue;

        @Getter
        public static class OrderReturnValueSuccess {
            private String email;
            private String phone;
            private String username;
            private String address;
            private double total;
            private String voucher;

            private CartItemEntity[] items;
            private String status;
            private String appTransId;

            private Object zpTransId;
            private Object note;
            private String id;
            private String createdAt;
            private String updatedAt;
            private Object deletedAt;
        }

        @Getter
        public static class OrderDataWrapper {
            private GetResult.OrderData order;
        }

        @Getter
        public static class OrderData {
            private String email;
            private String phone;
            private String username;
            private String address;
            private int total;
            private Object voucher; // Could be String or null, based on your JSON
            private List<Item> items;
            private String status;
            private Payment payment;

            @Getter
            public static class Item {
                private String id;
                private String image;
                private String name;
                private int price;
                private String productName;
                private int quantity;
            }

            @Getter
            public static class Payment {
                private PaymentDetails payment;
                private PaymentInfo info;

                @Getter
                public static class PaymentDetails {
                    private int return_code;
                    private String return_message;
                    private int sub_return_code;
                    private String sub_return_message;
                    private String zp_trans_token;
                    private String order_url;
                    private String order_token;
                    private String qr_code;
                }

                @Getter
                public static class PaymentInfo {
                    private String app_id;
                    private String app_trans_id;
                    private String app_user;
                    private long app_time;
                    private String item;
                    private String embed_data;
                    private int amount;
                    private String description;
                    private String bank_code;
                    private String mac;
                    private String callback_url;
                }
            }
        }
    }
}
