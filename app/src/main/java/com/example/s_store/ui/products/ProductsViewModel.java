package com.example.s_store.ui.products;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.s_store.common.models.ProductModel;
import com.example.s_store.products.controller.ProductController;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProductsViewModel extends ViewModel {

    private final ProductController productController;
    private final MutableLiveData<List<ProductModel>> productsLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    @Inject
    public ProductsViewModel(ProductController productController) {
        this.productController = productController;
        fetchProducts(); // Automatically fetch products when ViewModel is created
    }

    public LiveData<List<ProductModel>> getProducts() {
        return productsLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    private void fetchProducts() {
        productController.getAllProducts(new ProductController.ProductsFetchListener() {
            @Override
            public void onProductsFetched(List<ProductModel> responseData) {
                productsLiveData.setValue(responseData);
            }

            @Override
            public void onFetchFailed(String error) {
                errorMessage.setValue(error);
            }
        });
    }
}
