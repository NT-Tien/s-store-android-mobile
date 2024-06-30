package com.example.s_store.products.controller;

import com.example.s_store.common.models.CategoryModel;
import com.example.s_store.di.api.CategoryApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CategoryController {
    private final CategoryApi categoryApi;
    @Inject
    public CategoryController(CategoryApi categoryApi) {
        this.categoryApi = categoryApi;
    }

    public interface CategoryFetchListener {
        void onCategoriesFetched(List<CategoryModel> categories);
        void onFetchFailed(String errorMessage);
    }
    public void getAllCategories(CategoryFetchListener listener) {
        Call<List<CategoryModel>> call = categoryApi.all();
        call.enqueue(new Callback<List<CategoryModel>>() {
            @Override
            public void onResponse(Call<List<CategoryModel>> call, Response<List<CategoryModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<CategoryModel> categories = response.body();
                    listener.onCategoriesFetched(categories);
                } else {
                    listener.onFetchFailed("Failed to fetch categories");
                }
            }

            @Override
            public void onFailure(Call<List<CategoryModel>> call, Throwable t) {
                listener.onFetchFailed("Network Error: " + t.getMessage());
            }
        });
    }
}
