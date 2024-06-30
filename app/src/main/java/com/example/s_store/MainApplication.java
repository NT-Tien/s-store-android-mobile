package com.example.s_store;

import android.app.Application;

import java.io.File;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application {
    private static MainApplication instance;
    @Override
    public void onCreate() {
//        File databaseFile = getApplicationContext().getDatabasePath("app-main-db");
//        if (databaseFile.exists()) {
//            databaseFile.delete();
//        }
        super.onCreate();
    }
    public static MainApplication getInstance() {
        return instance;
    }
}
