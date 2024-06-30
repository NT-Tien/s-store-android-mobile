package com.example.s_store.di.auth;

import android.content.Context;

import com.example.s_store.auth.network.TokenManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AuthModule {
    @Provides
    @Singleton
    public TokenManager tokenManager(@ApplicationContext Context ctx) {
        return new TokenManager(ctx);
    }
}
