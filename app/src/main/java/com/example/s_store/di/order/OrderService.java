package com.example.s_store.di.order;

import com.example.s_store.common.models.OrderModel;
import com.example.s_store.di.api.OrderApi;
import com.example.s_store.di.api.dto.OrderRequestDto;
import com.example.s_store.di.api.dto.OrderResponseDto;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderService {
    private final OrderApi orderApi;

    @Inject
    public OrderService(OrderApi orderApi) {
        this.orderApi = orderApi;
    }

    public void createOrder(OrderRequestDto.CreateOrder dto, String token) {
        System.out.println("RESULT" + dto.getAddress() + dto.getAddress() + dto.getPhone() + dto.getEmail() + dto.getUser() + dto.getItems() + dto.getUsername() + dto.getTotal());
        try {
            Call<OrderResponseDto.CreateOrder> createOrder = this.orderApi.createOrder("Bearer " + token, dto);
            // create order
            Response<OrderResponseDto.CreateOrder> v = createOrder.execute();
            if (!v.isSuccessful()) {
                throw new IOException("Failed to create order");
            }
            OrderResponseDto.CreateOrder body = v.body();

            if (body == null) {
                throw new IOException("Failed to create order");
            }

            String orderId = body.getId();
            if (orderId == null) {
                throw new IOException("Failed to create order");
            }

            System.out.println("FIRST REQUEST DONE");

            Thread.sleep(3000);

            // get order result
            Call<OrderResponseDto.GetResult> getOrderResult = this.orderApi.getOrderResult("Bearer " + token, orderId);
            Response<OrderResponseDto.GetResult> v2 = getOrderResult.execute();

            if (!v2.isSuccessful()) {
                throw new IOException("Failed to get order result");
            }

            OrderResponseDto.GetResult body2 = v2.body();

            if (body2 == null) {
                throw new IOException("Failed to get order result");
            }

            if ((Object) body2.getReturnvalue() instanceof String) {
                throw new IOException("Failed to get order result");
            }

            JsonElement returnvalue = body2.getReturnvalue();
            if(returnvalue.isJsonObject()) {
                System.out.println("RETURNVSALYUE");
                System.out.println(returnvalue.getAsString());
                OrderResponseDto.GetResult.OrderData value = new Gson().fromJson(returnvalue, OrderResponseDto.GetResult.OrderData.class);
                System.out.println("ORDER SUCCESS: " + value.getUser());
            } else if (returnvalue.isJsonPrimitive()) {
                String value = returnvalue.getAsString();
                System.out.println("ORDER FAILED: " + value);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<OrderModel> getOrders(String token) {
        Call<List<OrderModel>> orders = this.orderApi.getOrders("Bearer " + token);
        try {
            Response<List<OrderModel>> v = orders.execute();
            if (!v.isSuccessful()) {
                throw new IOException("Failed to get orders");
            }
            return v.body();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
