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
import com.morax.xephalon.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean nightMode;

    private TextView tvErrorMessageReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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

    public void openLoginActivity(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void registerUser(View view) {
        EditText etUsernameReg = findViewById(R.id.et_username_reg);
        EditText etPasswordReg = findViewById(R.id.et_password_reg);
        EditText etConfirmPassword = findViewById(R.id.et_confirm_password);

        String strUsernameReg = etUsernameReg.getText().toString();
        String strPasswordReg = etPasswordReg.getText().toString();
        String strConfirmPassword = etConfirmPassword.getText().toString().toString();

        if(!strConfirmPassword.equals(strPasswordReg)){
            Toast.makeText(this, "Passwords not match", Toast.LENGTH_SHORT).show();
        } else {
            registerUserApi(strUsernameReg, strPasswordReg);
        }
    }

    public void registerUserApi(String username, String password){
        tvErrorMessageReg = findViewById(R.id.tv_error_message_reg);
        AuthApi authApi = ClientService.getAuthService();
        AuthRequest authRequest = new AuthRequest(username, password);
        Call<RegisterResponse> call = authApi.register(authRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(@NonNull Call<RegisterResponse> call, @NonNull Response<RegisterResponse> response) {
                if(response.isSuccessful()) {
                    tvErrorMessageReg.setVisibility(View.GONE);
                    RegisterResponse registerResponse = response.body();
                    String username = registerResponse.getUsername();
                    String msg = "User: " + username + " created successfully";
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<RegisterResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to Register", Toast.LENGTH_SHORT).show();
            }
        });
    }
}