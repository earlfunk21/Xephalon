package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.morax.xephalon.adapter.DocsAdapter;
import com.morax.xephalon.adapter.LangAdapter;
import com.morax.xephalon.databinding.ActivityConversionBinding;
import com.morax.xephalon.databinding.ActivityHomeBinding;
import com.morax.xephalon.model.Documentation;
import com.morax.xephalon.model.Language;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private SharedPreferences nightModePrefs;
    private SharedPreferences userPrefs;
    private boolean nightMode;
    private List<Language> langList;
    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initData();
        LangAdapter docsAdapter = new LangAdapter(this, langList);
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
        langList = new ArrayList<>();
        langList.add(new Language(R.drawable.go, "go_index.json", "GO Lang"));
        langList.add(new Language(R.drawable.js, "js_index.json", "JavaScript"));
        langList.add(new Language(R.drawable.rust, "rust_index.json", "Rust"));
    }

}