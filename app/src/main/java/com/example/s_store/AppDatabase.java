package com.example.s_store;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.di.cart.CartDAO;

@Database(entities = {CartItemEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDAO cartDAO();
}
