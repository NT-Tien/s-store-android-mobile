package com.example.s_store.di.api.dto;

import com.example.s_store.common.entities.CartItemEntity;

import java.util.List;

import lombok.Getter;

public class OrderResponseDto {

    @Getter
    public static class CreateOrder {
        private String id;
        private String name;
        private Integer progress;
        private Integer delay;
        private Integer timestamp;
        private Integer attemptsMade;
        private OrderDataWrapper data;

        @Getter
        public static class OrderDataWrapper {
            private OrderData order;
        }

        @Getter
        public static class OrderData {
            private Integer total;
            private String phone;
            private String address;
            private String email;
            private String username;
            private String user;
            private String voucher;
            private List<CartItemEntity> items;
        }
    }

    @Getter
    public static class GetResult {

        private String id;
        private String name;
        private OrderDataWrapper data;
        private Integer progress;
        private Integer delay;
        private Integer timestamp;
        private Integer attemptsMade;
        private OrderReturnValue returnvalue;

        @Getter
        public static class OrderReturnValue {
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
            private CreateOrder.OrderData order;
        }

        @Getter
        public static class OrderData {
            private Integer total;
            private String phone;
            private String address;
            private String email;
            private String username;
            private String user;
            private String voucher;
            private List<CartItemEntity> items;
        }
    }
}
