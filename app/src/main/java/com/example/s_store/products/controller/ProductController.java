package com.example.s_store.products.controller;

import com.example.s_store.products.model.GetAllProductResponse;
import com.example.s_store.products.model.Product;
import com.example.s_store.products.network.ApiService;
import com.example.s_store.products.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductController {

    public interface ProductFetchListener {
        void onProductsFetched(List<Product> products);
        void onFetchFailed(String errorMessage);
    }
    public static void getAllProducts(ProductFetchListener listener) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<GetAllProductResponse> call = apiService.getAllProducts();
        call.enqueue(new Callback<GetAllProductResponse>() {
            @Override
            public void onResponse(Call<GetAllProductResponse> call, Response<GetAllProductResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> products = response.body().getProducts();
                    listener.onProductsFetched(products);
                } else {
                    listener.onFetchFailed("Failed to fetch categories");
                }
            }

            @Override
            public void onFailure(Call<GetAllProductResponse> call, Throwable t) {
                listener.onFetchFailed("Network Error: " + t.getMessage());
            }
        });
    }
}