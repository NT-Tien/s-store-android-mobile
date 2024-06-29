package com.example.s_store.common.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

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
    public String id;
    private String name;
    private Integer quantity;
    private Integer price;
}
