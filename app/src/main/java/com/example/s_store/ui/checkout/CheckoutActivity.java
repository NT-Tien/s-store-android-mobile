package com.example.s_store.ui.checkout;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.databinding.ActivityCheckoutBinding;
import com.example.s_store.di.api.dto.OrderRequestDto;
import com.example.s_store.di.cart.CartService;
import com.example.s_store.di.order.OrderService;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CheckoutActivity extends AppCompatActivity {

    @Inject OrderService orderService;
    @Inject CartService cartService;

    private ActivityCheckoutBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCheckoutBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());
        this.setTitle("Checkout");

        this.binding.btnCheckout.setOnClickListener(v -> {
            String phone = Objects.requireNonNull(binding.inputPhone.getText()).toString();
            String email = Objects.requireNonNull(binding.inputEmail.getText()).toString();
            String name = Objects.requireNonNull(binding.inputName.getText()).toString();
            String addressLine = Objects.requireNonNull(binding.inputAddressLine.getText()).toString();
            String city = Objects.requireNonNull(binding.inputCity.getText()).toString();
            String province = Objects.requireNonNull(binding.inputProvince.getText()).toString();

            OrderRequestDto.CreateOrder.CreateOrderBuilder dto = OrderRequestDto.CreateOrder.builder()
                    .user("") // TODO get user from context
                    .phone(phone)
                    .email(email)
                    .username(name)
                    .address(addressLine + ", " + city + ", " + province)
                    .voucher(null);

            new Thread(() -> {
                Double total = this.cartService.total();
                dto.total(total);
                List<CartItemEntity> items = this.cartService.getAll();
                dto.items(items);
                this.orderService.createOrder(dto.build());
            }).start();
        });
    }
}
