package com.example.s_store.di.cart;

import com.example.s_store.AppDatabase;
import com.example.s_store.common.entities.CartItemEntity;

import java.util.List;

import javax.inject.Inject;

public class CartService {

    private final AppDatabase db;

    @Inject
    public CartService(AppDatabase db) {
        this.db = db;
    }

    public void addProduct(CartItemEntity cartItemEntity) {
        db.cartDAO().insert(cartItemEntity);
    }

    public void removeProduct(String id) {
        db.cartDAO().delete(id);
    }

    public List<CartItemEntity> getAll() {
        return db.cartDAO().all();
    }

    public void clearAll() {
        db.cartDAO().clear();
    }

    public CartItemEntity getOne(String id) {
        return db.cartDAO().one(id);
    }

    public Integer count() {
        return db.cartDAO().count();
    }

    public CartItemEntity get(Integer index) {
        return db.cartDAO().oneByIndex(index);
    }
}
