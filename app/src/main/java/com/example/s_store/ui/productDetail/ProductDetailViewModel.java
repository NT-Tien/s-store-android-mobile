package com.example.s_store.ui.productDetail;

import android.util.SparseBooleanArray;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.common.models.ProductModel;
import com.example.s_store.common.models.ProductOptionalModel;
import com.example.s_store.di.api.ProductApi;
import com.example.s_store.di.cart.CartService;
import com.example.s_store.products.controller.ProductController;

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class ProductDetailViewModel extends ViewModel {
    private final CartService cartService;
    private final ProductController productController;
    private final MutableLiveData<ProductModel> productDetailLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    @Inject
    public ProductDetailViewModel(ProductController productController, CartService cartService) {
        this.productController = productController;
        this.cartService = cartService;
    }

    public LiveData<ProductModel> getProduct(String productId) {
       return productDetailLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchProduct(String id) {
        productController.getProductById(new ProductController.ProductFetchListener() {
            @Override
            public void onProductFetched(ProductModel responseData) {
                productDetailLiveData.setValue(responseData);
            }

            @Override
            public void onFetchFailed(String error) {
                errorMessage.setValue(error);
            }
        }, id);
    }

    public void addProduct(ProductModel product, List<Integer> selectedOptions) {
        Double totalPrice = 0d;

        // Iterate through the selected options and sum their prices
        for (int i = 0; i < selectedOptions.size(); i++) {
            int key = selectedOptions.get(i);
            ProductOptionalModel option = product.getProductOpts().get(key);
            totalPrice += option.getPrice();
        }

//        CartItemEntity cartItemEntity = CartItemEntity.builder()
//                .id(product.getId())
//                .name(product.getName())
//                .price(totalPrice)
//                .quantity(selectedOptions.size())
//                .build();

        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .id(product.getProductOpts().get(0).getId())
                .name(product.getProductOpts().get(0).getName())
                .price(product.getProductOpts().get(0).getPrice())
                .quantity(1)
                .image(product.getProductOpts().get(0).getImage())
                .productName(product.getName())
                .build();

        new Thread(() -> {
            this.cartService.addProduct(cartItemEntity);
        }).start();
    }
}
