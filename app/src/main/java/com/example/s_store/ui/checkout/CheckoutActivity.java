package com.example.s_store.ui.checkout;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_store.databinding.ActivityCheckoutBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CheckoutActivity extends AppCompatActivity {

    private ActivityCheckoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCheckoutBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());
    }
}
