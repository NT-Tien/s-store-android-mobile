package com.example.s_store.di.cart;

import com.example.s_store.AppDatabase;
import com.example.s_store.di.AppModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module(includes = AppModule.class)
@InstallIn(SingletonComponent.class)
public class CartModule {

    @Provides
    @Singleton
    public CartService cartService(AppDatabase appDatabase) {
        return new CartService(appDatabase);
    }
}
