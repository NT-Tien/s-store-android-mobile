package com.example.s_store.di.cart;

import com.example.s_store.AppDatabase;
import com.example.s_store.common.entities.CartItemEntity;

import java.util.List;

import javax.inject.Inject;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CartService {
    @Inject
    AppDatabase db;

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
}
