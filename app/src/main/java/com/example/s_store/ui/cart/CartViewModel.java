package com.example.s_store.ui.cart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.di.cart.CartService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import lombok.Getter;

@HiltViewModel
public class CartViewModel extends ViewModel {
    private final CartService cartService;

    @Inject
    public CartViewModel(CartService cartService) {
        this.cartService = cartService;
    }

    @Getter
    private final MutableLiveData<List<CartItemEntity>> cartItemsLiveData = new MutableLiveData<>(new ArrayList<>());

    public void addProduct() {
        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .id(String.valueOf(new Random().nextInt()))
                .name(String.valueOf(new Random().nextInt()))
                .price(new Random().nextInt())
                .quantity(new Random().nextInt())
                .build();

        new Thread(() -> {
            this.cartService.addProduct(cartItemEntity);
            List<CartItemEntity> cartItems = this.cartItemsLiveData.getValue();
            if (cartItems != null) {
                cartItems.add(cartItemEntity);
                this.cartItemsLiveData.postValue(cartItems);
            }
        }).start();
    }

    public void reloadItems() {
        new Thread(() -> {
            this.cartItemsLiveData.postValue(this.cartService.getAll());
        }).start();
    }

    public void clearCart() {
        new Thread(() -> {
            this.cartService.clearAll();
            this.cartItemsLiveData.postValue(new ArrayList<>());
        }).start();
    }

    public void removeProduct(CartItemEntity cartItemEntity) {
        new Thread(() -> {
            this.cartService.removeProduct(cartItemEntity.getId());
            List<CartItemEntity> cartItems = this.cartItemsLiveData.getValue();
            if (cartItems != null) {
                cartItems.remove(cartItemEntity);
                this.cartItemsLiveData.postValue(cartItems);
            }
        }).start();
    }
}
