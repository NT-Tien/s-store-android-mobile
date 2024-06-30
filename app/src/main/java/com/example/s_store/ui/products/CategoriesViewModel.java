package com.example.s_store.ui.products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.s_store.R;
import com.example.s_store.common.models.CategoryModel;
import com.example.s_store.common.models.ProductModel;
import com.example.s_store.products.controller.CategoryController;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CategoriesViewModel extends ViewModel {

    private final CategoryController categoryController;
    private final MutableLiveData<List<CategoryModel>> categoriesLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    @Inject
    public CategoriesViewModel(CategoryController categoryController) {
        this.categoryController = categoryController;
        fetchCategories(); // Automatically fetch categories when ViewModel is created
    }

    public LiveData<List<CategoryModel>> getCategories() {
        return categoriesLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private void fetchCategories() {
        categoryController.getAllCategories(new CategoryController.CategoryFetchListener() {
            @Override
            public void onCategoriesFetched(List<CategoryModel> responseData) {
                categoriesLiveData.setValue(responseData);
            }

            @Override
            public void onFetchFailed(String error) {
                errorMessage.setValue(error);
            }
        });
    }


}
