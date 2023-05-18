package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.morax.xephalon.adapter.DocsAdapter;
import com.morax.xephalon.databinding.ActivityConversionBinding;
import com.morax.xephalon.databinding.ActivityHomeBinding;
import com.morax.xephalon.model.Documentation;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences nightModePrefs;
    private SharedPreferences userPrefs;
    private boolean nightMode;
    private List<Documentation> documentationList;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initData();
        DocsAdapter docsAdapter = new DocsAdapter(this, documentationList);
        binding.rvDocs.setAdapter(docsAdapter);

        nightModePrefs = getSharedPreferences("MODE", MODE_PRIVATE);
        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        nightMode = nightModePrefs.getBoolean("nightMode", false);
        if (nightMode) {
            binding.switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
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

    public void logoutUser(View view) {
        SharedPreferences.Editor editor = userPrefs.edit();
        editor.putString("user", "");
        editor.apply();
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        documentationList = new ArrayList<>();

        documentationList.add(
                new Documentation("Python", "Python's documentation, tutorials, and guides are constantly evolving.\n" +
                        "\n" +
                        "Get started here, or scroll down for documentation broken out by type and subject.", "01/02/2003", R.drawable.python_logo)
        );
        documentationList.add(
                new Documentation("Java", "Whether you are working on a new cutting edge app or simply ramping up on new technology, Java documentation has all the information you need to make your project a smashing success. Use the rich set of code samples, tutorials, developer guides, API documentation, and more to quickly develop your prototype and scale it up to a real world application.",
                        "01/02/2003",
                        R.drawable.java_logo)
        );
    }

    public void openConversion(View view) {
        Intent intent = new Intent(HomeActivity.this, ConversionActivity.class);
        startActivity(intent);
    }
}