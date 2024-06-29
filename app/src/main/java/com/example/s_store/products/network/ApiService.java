package com.example.s_store.products.network;

import com.example.s_store.products.model.GetAllCategoryResponse;
import com.example.s_store.products.model.GetAllProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/category")
    Call<GetAllCategoryResponse> getAllCategories();

    @GET("/product/get-all-cache")
    Call<GetAllProductResponse> getAllProducts();
}
