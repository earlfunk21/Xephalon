package com.morax.xephalon.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.morax.xephalon.ClientService;
import com.morax.xephalon.MainActivity;
import com.morax.xephalon.R;
import com.morax.xephalon.api.AuthApi;
import com.morax.xephalon.request.AuthRequest;
import com.morax.xephalon.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private boolean nightMode;
    private TextView tvErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SwitchCompat switchMode = findViewById(R.id.switchMode);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);
        if (nightMode){
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

    }

    public void switchMode(View view) {
        SharedPreferences.Editor editor;
        if (nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor = sharedPreferences.edit();
            editor.putBoolean("nightMode", false);
        } else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor = sharedPreferences.edit();
            editor.putBoolean("nightMode", true);
        }
        editor.apply();
    }

    public void openRegisterActivity(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void loginUser(View view) {
        EditText etUsername = findViewById(R.id.et_username);
        EditText etPassword = findViewById(R.id.et_password);

        String strEmail = etUsername.getText().toString();
        String strPassword = etPassword.getText().toString();
        loginUserApi(strEmail, strPassword);
    }

    private void loginUserApi(String username, String password){
        tvErrorMessage = findViewById(R.id.tv_error_message);
        AuthApi authApi = ClientService.getAuthService();
        AuthRequest authRequest = new AuthRequest(username, password);
        Call<LoginResponse> call = authApi.login(authRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    tvErrorMessage.setVisibility(View.GONE);
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getToken();
                    String user = loginResponse.getUser();
                    SharedPreferences sharedPreferences1 = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences1.edit();
                    editor.putString("token", token);
                    editor.putString("user", user);
                    editor.apply();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Successfully logged In", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                tvErrorMessage.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Failed to login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}