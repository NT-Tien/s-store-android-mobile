package com.example.s_store.auth.controller;

import com.example.s_store.common.models.ProductModel;
import com.example.s_store.common.models.UserModel;
import com.example.s_store.di.api.AuthApi;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AuthController {
    private final AuthApi authApi;

    @Inject
    public AuthController(AuthApi authApi) {
        this.authApi = authApi;
    }

    public interface ProfileFetchListener {
        void onProfileFetched(UserModel user);
        void onFetchFailed(String errorMessage);
    }

    public void getMe(ProfileFetchListener listener, String token) {
        Call<UserModel> call = authApi.getMe(token);
        call.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserModel user = response.body();
                    listener.onProfileFetched(user);
                } else {
                    listener.onFetchFailed("Failed to fetch product");
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                listener.onFetchFailed("Network Error: " + t.getMessage());
            }
        });
    }
}
