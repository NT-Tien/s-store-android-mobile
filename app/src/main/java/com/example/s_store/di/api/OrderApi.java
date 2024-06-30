package com.example.s_store.di.api;

import com.example.s_store.di.api.dto.OrderRequestDto;
import com.example.s_store.di.api.dto.OrderResponseDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrderApi {
    @POST("/user-order")
    Call<OrderResponseDto.CreateOrder> createOrder(@Header("Authorization") String token, OrderRequestDto.CreateOrder dto);

    @GET("/user-order/get-result/{id}")
    Call<OrderResponseDto.GetResult> getOrderResult(@Header("Authorization") String token, @Path("id") String id);

    @GET("/user-order")
    Call<?> getOrders(@Header("Authorization") String token);
}