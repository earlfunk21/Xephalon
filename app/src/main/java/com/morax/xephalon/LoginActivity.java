package com.morax.xephalon;

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

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private boolean nightMode;

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

    @Override
    protected void onStart() {
        super.onStart();
        if(!sharedPreferences.getString("user", "anonymous").equals("anonymous")){
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
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
        EditText etEmail = findViewById(R.id.et_email);
        EditText etPassword = findViewById(R.id.et_password);
        TextView tvErrorEmail = findViewById(R.id.tv_error_email);
        TextView tvErrorPassword = findViewById(R.id.tv_error_password);

        String strEmail = etEmail.getText().toString();
        String strPassword = etPassword.getText().toString();
        if(strEmail.equals("admin")){
            tvErrorEmail.setVisibility(View.GONE);
            if (strPassword.equals("123456")){
                tvErrorPassword.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                SharedPreferences sharedPreferences1 = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("user", "Admin");
                editor.apply();
            } else{
                tvErrorPassword.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Invalid Password", Toast.LENGTH_SHORT).show();
            }
        } else{
            tvErrorEmail.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
        }
    }

    public void logoutUser(View view) {
    }
}