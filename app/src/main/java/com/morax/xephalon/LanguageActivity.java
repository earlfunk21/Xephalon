package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.morax.xephalon.adapter.DocsAdapter;
import com.morax.xephalon.dao.HistoryDao;
import com.morax.xephalon.dao.UserDao;
import com.morax.xephalon.databinding.ActivityLanguageBinding;
import com.morax.xephalon.model.Documentation;
import com.morax.xephalon.model.History;
import com.morax.xephalon.model.Language;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LanguageActivity extends AppCompatActivity {

    private List<Documentation> documentationList;

    private ActivityLanguageBinding binding;
    private Language language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLanguageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        language = (Language) intent.getSerializableExtra("model");
        binding.tvLangLanguage.setText(language.getName());
        binding.ivLogoLang.setImageResource(language.getLogo());

        initData();
        DocsAdapter docsAdapter = new DocsAdapter(this, documentationList);
        binding.rvDocs.setAdapter(docsAdapter);

        List<Documentation> filteredList = new ArrayList<>(documentationList);

        HistoryDao historyDao = AppDatabase.getInstance(this).historyDao();

        binding.etSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SharedPreferences userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
                UserDao userDao = AppDatabase.getInstance(LanguageActivity.this).userDao();
                long userId = userDao.getUserByUsername(userPrefs.getString("user", "")).id;
                History history = new History(s, userId);
                historyDao.insert(history);


                filteredList.clear();
                for(Documentation documentation : documentationList){
                    if(documentation.getMarkdown().toLowerCase().contains(s.toLowerCase()))
                        filteredList.add(documentation);
                }

                docsAdapter.setDocumentationList(filteredList);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }


    public void goBack(View view) {
        onBackPressed();
        finish();
    }

    private void initData() {
        documentationList = new ArrayList<>();
        String jsonArray = getJSONStringFromFile("data/" + language.getJsonString());
        addDocumentationLang(jsonArray, language.getLogo());
    }

    private void addDocumentationLang(String file, int thumbnail) {
        try {
            JSONArray jsonArray = new JSONArray(file);
            for (int i = 0; i < jsonArray.length(); i++) {
                String filePath = "data/" + jsonArray.getString(i);
                String goDocs = getJSONStringFromFile(filePath);
                documentationList.add(getDocsFromJSONString(goDocs, thumbnail));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getJSONStringFromFile(String file) {
        String jsonString = null;
        try {
            InputStream inputStream = getAssets().open(file);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            jsonString = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    private Documentation getDocsFromJSONString(String jsonString, int thumbnail) {
        String lang = null;
        String title = null;
        String fullPath = null;
        String markdown = null;
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            // Access the data in the JSON object
            lang = jsonObject.getString("lang");
            title = jsonObject.getString("title");
            fullPath = jsonObject.getString("fullPath");
            markdown = jsonObject.getString("markdown");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new Documentation(lang, title, fullPath, markdown, thumbnail);
    }
}


