package com.example.s_store.ui.checkout;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_store.MainActivity;
import com.example.s_store.auth.network.TokenManager;
import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.common.exceptions.CreateOrderException;
import com.example.s_store.common.util.DecodeJwt;
import com.example.s_store.databinding.ActivityCheckoutBinding;
import com.example.s_store.di.api.dto.OrderRequestDto;
import com.example.s_store.di.api.dto.OrderResponseDto;
import com.example.s_store.di.cart.CartService;
import com.example.s_store.di.order.OrderService;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CheckoutActivity extends AppCompatActivity {

    @Inject
    OrderService orderService;
    @Inject
    CartService cartService;

    private ActivityCheckoutBinding binding;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCheckoutBinding.inflate(this.getLayoutInflater());
        this.setContentView(this.binding.getRoot());
        this.setTitle("Checkout");
        tokenManager = new TokenManager(this);

        this.binding.btnCheckout.setOnClickListener((v) -> {
            Editable phone = binding.inputPhone.getText();
            Editable name = binding.inputName.getText();
            Editable addressLine = binding.inputAddressLine.getText();
            Editable city = binding.inputCity.getText();
            Editable province = binding.inputProvince.getText();
            String token = tokenManager.getToken();
            String uid = DecodeJwt.decode(token, "id");
            String email = DecodeJwt.decode(token, "email");

            if(phone == null || email == null || name == null || addressLine == null || city == null || province == null) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if(phone.toString().isEmpty() || email.toString().isEmpty() || name.toString().isEmpty() || addressLine.toString().isEmpty() || city.toString().isEmpty() || province.toString().isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                OrderRequestDto.CreateOrder.CreateOrderBuilder dto = OrderRequestDto.CreateOrder.builder()
                        .user(uid)
                        .phone(phone.toString())
                        .email(email)
                        .username(name.toString())
                        .address(addressLine + ", " + city + ", " + province)
                        .voucher(null);
                List<CartItemEntity> items = this.cartService.getAll();
                dto.items(items);
                dto.total(items.stream().reduce(0d, (subtotal, item) -> subtotal + (item.getPrice() * item.getQuantity()), Double::sum));
                try {
                    OrderResponseDto.GetResult.OrderData data = this.orderService.createOrder(dto.build(), tokenManager.getToken());
                    this.cartService.clearAll();
                    runOnUiThread(() -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Order created");
                        builder.setMessage("Order created successfully. Your payment link is " + data.getPayment().getPayment().getOrder_url());
                        builder.setPositiveButton("Goto Payment", (dialog, which) -> {
                            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getPayment().getPayment().getOrder_url()));
                            startActivity(i);
                        });
                        builder.show();
                    });
                } catch(CreateOrderException e) {
                    cartService.clearAll();
                    runOnUiThread(() -> {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        Intent i = new Intent(this, MainActivity.class);
                        startActivity(i);
                    });
                }
            }).start();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Thread(() -> {
            if(cartService.count() == 0) {
                runOnUiThread(() -> {
                    Intent i = new Intent(this, MainActivity.class);
                    startActivity(i);
                });
            }
        }).start();
        new Thread(() -> {
            Double total = this.cartService.total();
            runOnUiThread(() -> {
                this.binding.total.setText(String.format("Total: $%.2f", total));
            });
        }).start();
    }
}
