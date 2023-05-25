package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.morax.xephalon.adapter.HistoryAdapter;
import com.morax.xephalon.dao.HistoryDao;
import com.morax.xephalon.dao.UserDao;
import com.morax.xephalon.databinding.ActivityBookmarkBinding;
import com.morax.xephalon.databinding.ActivitySearchHistoryBinding;
import com.morax.xephalon.model.Bookmark;
import com.morax.xephalon.model.History;

import java.util.ArrayList;
import java.util.List;

public class SearchHistoryActivity extends AppCompatActivity {

    private List<History> historyList;
    private ActivitySearchHistoryBinding binding;
    private HistoryDao historyDao;
    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchHistoryBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        historyDao = AppDatabase.getInstance(this).historyDao();
        initData();
        historyAdapter = new HistoryAdapter(this, historyList);
        binding.rvHistory.setAdapter(historyAdapter);
    }

    private void initData() {
        historyList = new ArrayList<>();
        SharedPreferences userPrefs = getSharedPreferences("userPrefs", MODE_PRIVATE);
        UserDao userDao = AppDatabase.getInstance(SearchHistoryActivity.this).userDao();
        long userId = userDao.getUserByUsername(userPrefs.getString("user", "")).id;
        List<History> historyList1 = historyDao.getListOfHistory(userId);
        if (historyList1 != null)
            historyList.addAll(historyList1);
    }

    public void deleteHistory(View view) {
        historyDao.deleteAll();
        finish();
    }

    public void goBack(View view) {
        onBackPressed();
        finish();
    }
}