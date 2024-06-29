package com.example.s_store.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.s_store.databinding.FragmentCartBinding;
import com.example.s_store.di.cart.CartService;
import com.example.s_store.ui.checkout.CheckoutActivity;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CartFragment extends Fragment {
    @Inject
    CartService cartService;

    private FragmentCartBinding binding;
    private Activity activity;
    private CartViewModel cartViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentCartBinding.inflate(inflater, container, false);
        this.activity = getActivity();

        this.cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        this.cartViewModel.getCartItemsLiveData().observe(this.getViewLifecycleOwner(), cartItems -> {
            CartAdapter cartAdapter = new CartAdapter(cartItems, cartItemEntity -> {
                this.cartViewModel.removeProduct(cartItemEntity);
                Toast.makeText(this.activity, "Product removed", Toast.LENGTH_SHORT).show();
                return null;
            });
            this.binding.cartItemList.setAdapter(cartAdapter);
            this.binding.cartItemList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        });

        this.binding.addTestProductToCart.setOnClickListener(v -> {
            this.cartViewModel.addProduct();
            Toast.makeText(this.activity, "Product added", Toast.LENGTH_SHORT).show();
        });
        this.binding.btnClear.setOnClickListener(v -> {
            this.cartViewModel.clearCart();
            Toast.makeText(this.activity, "Cart cleared", Toast.LENGTH_SHORT).show();
        });
        this.binding.btnCheckout.setOnClickListener(v -> {
            Intent i = new Intent(this.activity, CheckoutActivity.class);
            this.startActivity(i);
        });
        return this.binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        this.cartViewModel.reloadItems();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.binding = null;
    }
}
