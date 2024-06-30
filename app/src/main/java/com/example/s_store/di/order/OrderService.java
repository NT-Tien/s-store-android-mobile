package com.example.s_store.di.order;

import com.example.s_store.di.api.OrderApi;
import com.example.s_store.di.api.dto.OrderRequestDto;
import com.example.s_store.di.api.dto.OrderResponseDto;

import java.io.IOException;

import javax.inject.Inject;

import lombok.var;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderService {
    private OrderApi orderApi;

    @Inject
    public OrderService(OrderApi orderApi) {
        this.orderApi = orderApi;
    }

    public void createOrder(OrderRequestDto.CreateOrder dto) {
        Call<OrderResponseDto.CreateOrder> createOrder = this.orderApi.createOrder("", dto);
        try {
            // create order
            Response<OrderResponseDto.CreateOrder> v = createOrder.execute();
            if(!v.isSuccessful()) {
                throw new IOException("Failed to create order");
            }
            OrderResponseDto.CreateOrder body = v.body();
            if(body == null) {
                throw new IOException("Failed to create order");
            }

            Thread.sleep(3000);

            String orderId = body.getId();
            if(orderId == null) {
                throw new IOException("Failed to create order");
            }

            // get order result
            Call<OrderResponseDto.GetResult> getOrderResult = this.orderApi.getOrderResult("", orderId);
            Response<OrderResponseDto.GetResult> v2 = getOrderResult.execute();

            if(!v2.isSuccessful()) {
                throw new IOException("Failed to get order result");
            }

            OrderResponseDto.GetResult body2 = v2.body();

            if(body2 == null) {
                throw new IOException("Failed to get order result");
            }

            if((Object) body2.getReturnvalue() instanceof String) {
                throw new IOException("Failed to get order result");
            }
        } catch(IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
