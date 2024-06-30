package com.example.s_store.ui.productDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.common.models.ProductModel;
import com.example.s_store.common.models.ProductOptionalModel;
import com.example.s_store.di.cart.CartService;
import com.example.s_store.products.controller.ProductController;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

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

    public void addProduct(ProductModel product, int selectedPosition) {
        // Iterate through the selected options and sum their prices
        ProductOptionalModel option = product.getProductOpts().get(selectedPosition);

        CartItemEntity cartItemEntity = CartItemEntity.builder()
                .id(option.getId())
                .name(option.getName())
                .price(option.getPrice())
                .productName(product.getName())
                .image(option.getImage())
                .quantity(1)
                .build();

        new Thread(() -> {
            this.cartService.addProduct(cartItemEntity);
        }).start();
    }
}
