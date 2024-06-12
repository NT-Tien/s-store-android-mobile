package com.example.s_store.auth.network;

import com.example.s_store.auth.models.LoginRequest;
import com.example.s_store.auth.models.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}
