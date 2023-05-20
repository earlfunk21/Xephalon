package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.morax.xephalon.adapter.DocsAdapter;
import com.morax.xephalon.dao.BookmarkDao;
import com.morax.xephalon.dao.UserDao;
import com.morax.xephalon.databinding.ActivityBookmarkBinding;
import com.morax.xephalon.databinding.ActivityHomeBinding;
import com.morax.xephalon.databinding.ActivityLoginBinding;
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

public class BookmarkActivity extends AppCompatActivity {

    private BookmarkDao bookmarkDao;
    private ActivityBookmarkBinding binding;

    private SharedPreferences userPrefs;
    private List<Documentation> documentationList;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookmarkBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        userDao = AppDatabase.getInstance(this).userDao();
        bookmarkDao = AppDatabase.getInstance(this).bookmarkDao();

        initData();
        DocsAdapter docsAdapter = new DocsAdapter(this, documentationList);
        binding.rvDocs.setAdapter(docsAdapter);
    }
    public void goBack(View view) {
        onBackPressed();
        finish();
    }

    private void initData() {
        documentationList = new ArrayList<>();
        long userId = userDao.getUserByUsername(userPrefs.getString("user", "")).id;
        List<Documentation> docs = bookmarkDao.getBookmarksByUserId(userId);
        if(docs != null)
            documentationList.addAll(docs);
    }

}