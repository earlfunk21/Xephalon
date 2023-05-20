package com.morax.xephalon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.morax.xephalon.adapter.BookmarkAdapter;
import com.morax.xephalon.adapter.DocsAdapter;
import com.morax.xephalon.dao.BookmarkDao;
import com.morax.xephalon.dao.UserDao;
import com.morax.xephalon.databinding.ActivityBookmarkBinding;
import com.morax.xephalon.databinding.ActivityHomeBinding;
import com.morax.xephalon.databinding.ActivityLoginBinding;
import com.morax.xephalon.model.Bookmark;
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
    private List<Bookmark> bookmarkList;
    private UserDao userDao;

    private BookmarkAdapter bookmarkAdapter;

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
        bookmarkAdapter = new BookmarkAdapter(this, bookmarkList);
        binding.rvDocs.setAdapter(bookmarkAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                Bookmark bookmark = bookmarkAdapter.getBookMarkAt(position);

                confirmDelete(bookmark);
            }
        }).attachToRecyclerView(binding.rvDocs);
    }

    public void goBack(View view) {
        onBackPressed();
        finish();
    }

    private void initData() {
        bookmarkList = new ArrayList<>();
        long userId = userDao.getUserByUsername(userPrefs.getString("user", "")).id;
        List<Bookmark> docs = bookmarkDao.getBookmarksByUserId(userId);
        if (docs != null)
            bookmarkList.addAll(docs);
    }

    private void confirmDelete(Bookmark bookmark) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.popup_delete, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Retrieve the text from the EditText
                bookmarkDao.delete(bookmark);
                long userId = userDao.getUserByUsername(userPrefs.getString("user", "")).id;
                List<Bookmark> docs = bookmarkDao.getBookmarksByUserId(userId);
                bookmarkAdapter.setBookmarkList(docs);
            }
        });
        dialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                long userId = userDao.getUserByUsername(userPrefs.getString("user", "")).id;
                List<Bookmark> docs = bookmarkDao.getBookmarksByUserId(userId);
                bookmarkAdapter.setBookmarkList(docs);
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}