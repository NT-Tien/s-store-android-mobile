package com.example.s_store.di.api;

import com.example.s_store.common.models.CategoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CategoryApi {
    @GET("/category/get-all-cache")
    Call<List<CategoryModel>> all();

    @GET("/category/{id}")
    Call<CategoryModel> one(@Path("id") String id);
}