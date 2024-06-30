package com.example.s_store.common.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@Entity(tableName = "Cart_Items")
@SuperBuilder
public class CartItemEntity implements Serializable {
    @PrimaryKey
    @NonNull
    public String id; // id of optional product
    private String name; // name of optional product
    private Integer quantity; // quantity to add to cart
    private Double price; // price of optional product
    private String image; // image of optional product
    private String productName; // name of product
    private Long createdAt = System.currentTimeMillis();
}
