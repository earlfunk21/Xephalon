package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.morax.xephalon.dao.UserDao;
import com.morax.xephalon.databinding.ActivityRegisterBinding;
import com.morax.xephalon.model.User;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private boolean nightMode;

    private UserDao userDao;
    private ActivityRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Binding ID's
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Get the shared preferences of dark mode
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);
        if (nightMode){
            binding.switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        userDao = AppDatabase.getInstance(this).userDao();
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

    public void createAccount(View view) {
        String password = Objects.requireNonNull(binding.etPassword.getText()).toString();
        String confirm_password = Objects.requireNonNull(binding.etPassword1.getText()).toString();
        String username = Objects.requireNonNull(binding.etEmail.getText()).toString();
        if(password.equals("") || confirm_password.equals("") || username.equals("")){
            Toast.makeText(this, "Fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(confirm_password)){
            Toast.makeText(this, "Password must match!", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = userDao.getUserByUsername(username);
        if(user != null){
            Toast.makeText(this, "Username is already exists!", Toast.LENGTH_SHORT).show();
            return;
        }
        userDao.insert(new User(username, password));
        Toast.makeText(this, "Successfully Created!", Toast.LENGTH_SHORT).show();
        openLoginActivity(view);
    }
}