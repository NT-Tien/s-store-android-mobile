package com.example.s_store.ui.checkout;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_store.auth.network.TokenManager;
import com.example.s_store.common.entities.CartItemEntity;
import com.example.s_store.common.util.DecodeJwt;
import com.example.s_store.databinding.ActivityCheckoutBinding;
import com.example.s_store.di.api.dto.OrderRequestDto;
import com.example.s_store.di.cart.CartService;
import com.example.s_store.di.order.OrderService;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CheckoutActivity extends AppCompatActivity implements View.OnClickListener {

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

        this.binding.btnCheckout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Editable phone = binding.inputPhone.getText();
        Editable email = binding.inputEmail.getText();
        Editable name = binding.inputName.getText();
        Editable addressLine = binding.inputAddressLine.getText();
        Editable city = binding.inputCity.getText();
        Editable province = binding.inputProvince.getText();
        String token = tokenManager.getToken();
        String uid = DecodeJwt.decode(token, "id");

        if(phone == null || email == null || name == null || addressLine == null || city == null || province == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//            this.binding.btnCheckout.setEnabled(false);
            return;
        }

        if(phone.toString().isEmpty() || email.toString().isEmpty() || name.toString().isEmpty() || addressLine.toString().isEmpty() || city.toString().isEmpty() || province.toString().isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//            this.binding.btnCheckout.setEnabled(false);
            return;
        }

//        if(email.toString().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
//            Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
////            this.binding.btnCheckout.setEnabled(false);
//            return;
//        }
//
//        if(phone.toString().matches("^(\\+62|0)8[0-9]{8,}$")) {
//            Toast.makeText(this, "Phone number must start with +84", Toast.LENGTH_SHORT).show();
////            this.binding.btnCheckout.setEnabled(false);
//            return;
//        }

//        this.binding.btnCheckout.setEnabled(true);


        new Thread(() -> {
            OrderRequestDto.CreateOrder.CreateOrderBuilder dto = OrderRequestDto.CreateOrder.builder()
                    .user(uid)
                    .phone(phone.toString())
                    .email(email.toString())
                    .username(name.toString())
                    .address(addressLine + ", " + city + ", " + province)
                    .voucher(null);
            List<CartItemEntity> items = this.cartService.getAll();
            dto.items(items);
            dto.total(items.stream().reduce(0d, (subtotal, item) -> subtotal + (item.getPrice() * item.getQuantity()), Double::sum));
            this.orderService.createOrder(dto.build(), tokenManager.getToken());
        }).start();
    }
}
