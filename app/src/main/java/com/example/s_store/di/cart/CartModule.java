package com.example.s_store.di.cart;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class CartModule {

    @Provides
    @Singleton
    public CartService cartService() {
        return new CartService();
    }
}
