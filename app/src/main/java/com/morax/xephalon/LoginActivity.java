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

import com.morax.xephalon.dao.UserDao;
import com.morax.xephalon.databinding.ActivityLoginBinding;
import com.morax.xephalon.databinding.ActivityRegisterBinding;
import com.morax.xephalon.model.User;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences nightModePrefs;
    private SharedPreferences userPrefs;
    private boolean nightMode;


    private UserDao userDao;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Binding ID's
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        nightModePrefs = getSharedPreferences("MODE", MODE_PRIVATE);
        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);

        nightMode = nightModePrefs.getBoolean("nightMode", false);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        if (nightMode) {
            binding.switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        userDao = AppDatabase.getInstance(this).userDao();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!userPrefs.getString("user", "").equals("")) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public void switchMode(View view) {
        SharedPreferences.Editor editor;
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor = nightModePrefs.edit();
            editor.putBoolean("nightMode", false);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor = nightModePrefs.edit();
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
        String strEmail = Objects.requireNonNull(binding.etEmail.getText()).toString();
        String strPassword = Objects.requireNonNull(binding.etPassword.getText()).toString();

        User user = userDao.checkUser(strEmail, strPassword);
        if (user == null) {
            Toast.makeText(this, "Invalid Credentials!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putString("user", user.username);
        editor.apply();
    }

}