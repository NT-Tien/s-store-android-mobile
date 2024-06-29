package com.example.s_store.di.cart;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.s_store.common.entities.CartItemEntity;

import java.util.List;

@Dao
public interface CartDAO {
    @Query("SELECT * FROM Cart_Items")
    List<CartItemEntity> all();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartItemEntity cartItem);

    @Query("DELETE FROM Cart_Items WHERE id = :id")
    void delete(String id);

    @Query("DELETE FROM Cart_Items")
    void clear();

    @Query("SELECT * FROM Cart_Items WHERE id = :id")
    CartItemEntity one(String id);
}
