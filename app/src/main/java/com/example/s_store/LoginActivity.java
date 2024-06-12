package com.example.s_store;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_store.auth.network.ApiService;
import com.example.s_store.auth.models.LoginRequest;
import com.example.s_store.auth.models.LoginResponse;
import com.example.s_store.auth.network.RetrofitClient;
import com.example.s_store.auth.network.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText etUsername;
    EditText etPassword;
    Button btnLogin;

    TextView tvSignUp;
    ApiService apiService;
    private TokenManager tokenManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);

        apiService = RetrofitClient.getClient().create(ApiService.class);
        tokenManager = new TokenManager(this);

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToSignUp();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                // Check if username and password are not empty
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create LoginRequest object
                LoginRequest loginRequest = new LoginRequest(username, password);

                // Call login API
                Call<LoginResponse> call = apiService.login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            // Handle successful login
                            Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                            // Store token
                            String accessToken = response.body().getToken();
                            tokenManager.saveToken(accessToken);
                            // Navigate to Main
                            navigateToMain();

                        } else {
                            // Handle failed login
                            Toast.makeText(LoginActivity.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Handle network errors
                        Toast.makeText(LoginActivity.this, "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

    }
    public void navigateToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void navigateToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
}