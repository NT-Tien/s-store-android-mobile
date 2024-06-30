package com.example.s_store.di.api;

import com.example.s_store.common.models.UserModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AuthApi {
    @GET("/auth/user/get-account")
    Call<UserModel>  getMe(@Header("Authorization") String token);
}
