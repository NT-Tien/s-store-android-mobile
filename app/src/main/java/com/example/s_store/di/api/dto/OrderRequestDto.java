package com.example.s_store.di.api.dto;

import com.example.s_store.common.entities.CartItemEntity;

import java.util.List;

import javax.annotation.Nullable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

public class OrderRequestDto {
    @NoArgsConstructor
    @Getter
    @AllArgsConstructor
    @Builder
    public static class CreateOrder {
        private String user;
        private String email;
        private String phone;
        private String username;
        private String address;
        private Double total;
        private String voucher;
        private List<CartItemEntity> items;
    }
}
