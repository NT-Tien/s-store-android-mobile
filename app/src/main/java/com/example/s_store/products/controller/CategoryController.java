package com.example.s_store.products.controller;

import com.example.s_store.products.model.Category;
import com.example.s_store.products.model.GetAllCategoryResponse;
import com.example.s_store.products.network.ApiService;
import com.example.s_store.products.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryController {

    public interface CategoryFetchListener {
        void onCategoriesFetched(List<Category> categories);
        void onFetchFailed(String errorMessage);
    }
    public static void getAllCategories(CategoryFetchListener listener) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);
        Call<GetAllCategoryResponse> call = apiService.getAllCategories();
        call.enqueue(new Callback<GetAllCategoryResponse>() {
            @Override
            public void onResponse(Call<GetAllCategoryResponse> call, Response<GetAllCategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body().getCategories();
                    listener.onCategoriesFetched(categories);
                } else {
                    listener.onFetchFailed("Failed to fetch categories");
                }
            }

            @Override
            public void onFailure(Call<GetAllCategoryResponse> call, Throwable t) {
                listener.onFetchFailed("Network Error: " + t.getMessage());
            }
        });
    }
}
