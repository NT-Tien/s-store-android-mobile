package com.example.s_store.di.api;

import com.example.s_store.common.interceptor.ApiResponseInterceptor;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class ApiModule {
    private static final String BASE_URL = "https://s-api.caucalamdev.io.vn";

    @Provides
    public Retrofit retrofit() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ApiResponseInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Provides
    public ProductApi productApi() {
        return retrofit().create(ProductApi.class);
    }

    @Provides
    public CategoryApi categoryApi() {
        return retrofit().create(CategoryApi.class);
    }

    @Provides
    public OrderApi orderApi() {
        return retrofit().create(OrderApi.class);
    }
}
