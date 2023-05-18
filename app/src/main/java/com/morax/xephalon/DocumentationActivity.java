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

public class DocumentationActivity extends AppCompatActivity {

    private ImageView ivThumbnail;
    private TextView tvTitle, tvDocs, tvDate;
    private SharedPreferences userPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentation);

        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);

        ivThumbnail = findViewById(R.id.iv_thumbnail_details);
        tvTitle = findViewById(R.id.tv_title_details);
        tvDocs = findViewById(R.id.tv_docs_details);
        tvDate = findViewById(R.id.tv_date_details);

        Intent intent = getIntent();
        Documentation documentation = (Documentation) intent.getSerializableExtra("model");
        String title = documentation.getTitle();
        String docs = documentation.getDocs();
        int thumbnail = documentation.getThumbnail();
        String date = documentation.getDate();

        tvTitle.setText(title);
        tvDocs.setText(docs);
        ivThumbnail.setImageResource(thumbnail);
        tvDate.setText(date);
    }
    public void goBack(View view) {
        onBackPressed();
        finish();
    }
}