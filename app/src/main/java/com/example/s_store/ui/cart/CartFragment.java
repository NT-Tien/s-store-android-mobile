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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.s_store.R;
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
            if(cartItems.isEmpty()) {
                this.binding.cartItemList.setVisibility(View.GONE);
                this.binding.empty.setVisibility(View.VISIBLE);
                this.binding.btnCheckout.setText("Goto Product Catalogue");
                this.binding.btnCheckout.setOnClickListener(v -> {
                    NavController navController = Navigation.findNavController(this.activity, R.id.nav_host_fragment_content_main);
                    navController.navigate(R.id.nav_products);
                });
                return;
            }

            this.binding.cartItemList.setVisibility(View.VISIBLE);
            this.binding.empty.setVisibility(View.GONE);
            CartAdapter cartAdapter = new CartAdapter(cartItems, cartItemEntity -> {
                this.cartViewModel.removeProduct(cartItemEntity);
                Toast.makeText(this.activity, "Product removed", Toast.LENGTH_SHORT).show();
                return null;
            }, cartItemEntity -> {
                if(cartItemEntity.getQuantity() < 1) {
                    cartItemEntity.setQuantity(1);
                    this.cartViewModel.updateProduct(cartItemEntity);
                    Toast.makeText(this.activity, "Minimum quantity is 1", Toast.LENGTH_SHORT).show();
                } else if (cartItemEntity.getQuantity() > 5) {
                    cartItemEntity.setQuantity(5);
                    this.cartViewModel.updateProduct(cartItemEntity);
                    Toast.makeText(this.activity, "Maximum quantity is 5", Toast.LENGTH_SHORT).show();
                } else {
                    this.cartViewModel.updateProduct(cartItemEntity);
                    Toast.makeText(this.activity, "Quantity updated", Toast.LENGTH_SHORT).show();
                }
                return null;
            });
            this.binding.cartItemList.setAdapter(cartAdapter);
            this.binding.cartItemList.setLayoutManager(new LinearLayoutManager(this.getContext()));

            this.binding.btnCheckout.setText("Checkout");
            this.binding.btnCheckout.setOnClickListener(v -> {
                Intent i = new Intent(this.activity, CheckoutActivity.class);
                this.startActivity(i);
            });
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
