package com.example.s_store.products.controller;

import com.example.s_store.common.models.ProductModel;
import com.example.s_store.di.api.ProductApi;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ProductController {
    private final ProductApi productApi;

    @Inject
    public ProductController(ProductApi productApi) {
        this.productApi = productApi;
    }

    public interface ProductFetchListener {
        void onProductsFetched(List<ProductModel> products);
        void onFetchFailed(String errorMessage);
    }

    public void getAllProducts(ProductFetchListener listener) {
        Call<List<ProductModel>> call = productApi.all();
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<ProductModel> products = response.body();
                    listener.onProductsFetched(products);
                } else {
                    listener.onFetchFailed("Failed to fetch products");
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                listener.onFetchFailed("Network Error: " + t.getMessage());
            }
        });
    }
}