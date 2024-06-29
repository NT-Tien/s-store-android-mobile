package com.example.s_store.di.api;

import com.example.s_store.common.models.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductApi {
    @GET("/product/get-all-cache")
    Call<List<ProductModel>> all();

    @GET("/product/{id}")
    Call<ProductModel> one(@Path("id") String id);
}
