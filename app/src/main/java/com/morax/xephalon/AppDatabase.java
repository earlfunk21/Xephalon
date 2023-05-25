package com.morax.xephalon;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.morax.xephalon.converter.DocumentationTypeConverter;
import com.morax.xephalon.dao.BookmarkDao;
import com.morax.xephalon.dao.HistoryDao;
import com.morax.xephalon.dao.UserDao;
import com.morax.xephalon.model.Bookmark;
import com.morax.xephalon.model.History;
import com.morax.xephalon.model.User;

@Database(entities = {User.class, Bookmark.class, History.class}, version = 1)
@TypeConverters({DocumentationTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract BookmarkDao bookmarkDao();
    public abstract HistoryDao historyDao();
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class,
                    "test_db3").allowMainThreadQueries().build();
        }
        return instance;
    }
}