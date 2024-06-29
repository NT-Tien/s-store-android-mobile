package com.example.s_store.common.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(tableName = "Cart_Items")
public class CartItemEntity implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    private String name;
    private String quantity;
    private Integer price;
}
