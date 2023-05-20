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

import com.morax.xephalon.model.Documentation;

import io.noties.markwon.Markwon;

public class DocumentationActivity extends AppCompatActivity {

    private ImageView ivThumbnail;
    private TextView tvTitle, tvMarkdown, tvLang;
    private SharedPreferences userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);

        ivThumbnail = findViewById(R.id.iv_thumbnail_details);
        tvTitle = findViewById(R.id.tv_title_details);
        tvMarkdown = findViewById(R.id.tv_markdown_details);
        tvLang = findViewById(R.id.tv_lang_details);

        Intent intent = getIntent();
        Documentation documentation = (Documentation) intent.getSerializableExtra("model");
        String title = documentation.getTitle();
        String strMarkdown = documentation.getMarkdown();
        int thumbnail = documentation.getThumbnail();
        String lang = documentation.getLang();
        final Markwon markwon = Markwon.create(this);
        markwon.setMarkdown(tvMarkdown, strMarkdown);
        tvTitle.setText(title);
        ivThumbnail.setImageResource(thumbnail);
        tvLang.setText(lang);
    }
    public void goBack(View view) {
        onBackPressed();
        finish();
    }
}