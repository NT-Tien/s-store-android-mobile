package com.example.s_store;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s_store.di.cart.CartService;
import com.bumptech.glide.Glide;
import com.example.s_store.auth.controller.AuthController;
import com.example.s_store.auth.network.TokenManager;
import com.example.s_store.common.models.UserModel;
import com.example.s_store.products.controller.ProductController;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_store.databinding.ActivityMainBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private NavigationView navigationView;
    private TokenManager tokenManager;

    @Inject
    AuthController authController;
    private UserModel user;


    @Inject
    CartService cartService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        tokenManager = new TokenManager(this);
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_products, R.id.nav_cart, R.id.nav_history)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        new Thread(() -> {
            Integer count = cartService.count();
            if(count > 0) {
                runOnUiThread(() -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Cart");
                    builder.setMessage("You have " + count + " items in your cart");
                    builder.setPositiveButton("View Cart", (dialog, which) -> {
                        navController.navigate(R.id.nav_cart);
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    builder.show();
                });
            }
        }).start();
        // Call API and update header
        fetchUserData();

        // Logout button click listener
        Button btnLogout = navigationView.getHeaderView(0).findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> logout());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void updateDrawerHeader(String userName, String userEmail) {
        View headerView = navigationView.getHeaderView(0);

        ImageView imageView = headerView.findViewById(R.id.ivAvatar);
        TextView tvUsername = headerView.findViewById(R.id.tvUsername);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);

        // Set user name and email
        tvUsername.setText(userName);
        tvEmail.setText(userEmail);

        // Load user image using Glide
        String userImageUrl = "https://i.pravatar.cc/198?u=" + userEmail;
        Glide.with(this).load(userImageUrl).into(imageView);
    }

    private void fetchUserData() {
        authController.getMe(new AuthController.ProfileFetchListener() {
            @Override
            public void onProfileFetched(UserModel userData) {
                user = userData;
                updateDrawerHeader(user.getUsername(), user.getEmail());
            }

            @Override
            public void onFetchFailed(String errorMessage) {
                Toast.makeText(MainActivity.this, "Failed to fetch profile", Toast.LENGTH_SHORT).show();
            }
        }, "Bearer " + tokenManager.getToken());
    }

    private void logout() {
        // Clear token and navigate to login screen
        tokenManager.clearToken();
        startActivity(new Intent(this, LoginActivity.class));
        finish(); // Close MainActivity
    }
}