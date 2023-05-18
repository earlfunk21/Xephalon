package com.morax.xephalon;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.morax.xephalon.dao.UserDao;
import com.morax.xephalon.model.User;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;


@RunWith(AndroidJUnit4.class)
public class AppDatabaseTest extends TestCase {
    private UserDao userDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDao = db.userDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() throws Exception {
        userDao.insert(new User("hello", "world"));
        User user = userDao.getUserByUsername("hello");
        assertEquals(user.username, "hello");
    }
}