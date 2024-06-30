package com.example.s_store;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.di.cart.CartDAO;

@Database(entities = {CartItemEntity.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CartDAO cartDAO();
}
