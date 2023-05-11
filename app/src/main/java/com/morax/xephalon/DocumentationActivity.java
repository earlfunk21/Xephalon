package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.morax.xephalon.model.DocsModel;

public class DocumentationActivity extends AppCompatActivity {

    private ImageView ivThumbnail;
    private TextView tvTitle, tvDocs, tvDate;
    private boolean nightMode;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);
        SwitchCompat switchMode = findViewById(R.id.home_switch_mode);
        sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE);
        nightMode = sharedPreferences.getBoolean("nightMode", false);
        if (nightMode){
            switchMode.setChecked(true);
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        ivThumbnail = findViewById(R.id.iv_thumbnail_details);
        tvTitle = findViewById(R.id.tv_title_details);
        tvDocs = findViewById(R.id.tv_docs_details);
        tvDate = findViewById(R.id.tv_date_details);

        Intent intent = getIntent();
        DocsModel docsModel = (DocsModel) intent.getSerializableExtra("model");
        String title = docsModel.getTitle();
        String docs = docsModel.getDocs();
        int thumbnail = docsModel.getThumbnail();
        String date = docsModel.getDate();

        tvTitle.setText(title);
        tvDocs.setText(docs);
        ivThumbnail.setImageResource(thumbnail);
        tvDate.setText(date);
    }
    public void goBack(View view) {
        onBackPressed();
        finish();
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
}