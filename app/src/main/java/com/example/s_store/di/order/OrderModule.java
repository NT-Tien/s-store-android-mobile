package com.example.s_store.di.order;

import com.example.s_store.di.api.ApiModule;
import com.example.s_store.di.api.OrderApi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module(includes = ApiModule.class)
@InstallIn(SingletonComponent.class)
public class OrderModule {
    @Provides
    public OrderService orderService(OrderApi orderApi) {
        return new OrderService(orderApi);
    }
}
