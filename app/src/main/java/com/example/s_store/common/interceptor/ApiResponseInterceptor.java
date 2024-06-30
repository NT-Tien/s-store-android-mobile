package com.example.s_store.common.interceptor;

import androidx.annotation.NonNull;

import com.example.s_store.common.models.ApiSuccessResponseModel;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class ApiResponseInterceptor implements Interceptor {
    private final Gson gson = new Gson();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        try (Response originalResponse = chain.proceed(chain.request()); ResponseBody body = originalResponse.body()) {
            if (body != null && body.contentType() != null && Objects.requireNonNull(body.contentType()).subtype().equalsIgnoreCase(("json"))) {
                String bodyString = body.string();
                ApiSuccessResponseModel<?> apiResponse = gson.fromJson(bodyString, new TypeToken<ApiSuccessResponseModel<?>>() {
                }.getType());
                return originalResponse.newBuilder()
                        .body(ResponseBody.create(body.contentType(), gson.toJson(apiResponse.getData())))
                        .build();
            }
        } catch (NullPointerException | JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        }
        return chain.proceed(chain.request());
    }
}
