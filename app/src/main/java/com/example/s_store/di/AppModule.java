package com.example.s_store.di;

import android.content.Context;

import androidx.room.Room;

import com.example.s_store.AppDatabase;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(@ApplicationContext Context ctx) {
        return Room.databaseBuilder(ctx, AppDatabase.class, "app-main-db").setQueryExecutor(Executors.newSingleThreadExecutor()).build();
    }
}
