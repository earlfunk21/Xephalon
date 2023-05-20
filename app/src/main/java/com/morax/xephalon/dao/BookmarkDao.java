package com.morax.xephalon.dao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.morax.xephalon.model.Bookmark;
import com.morax.xephalon.model.Documentation;

import java.util.List;

@Dao
public interface BookmarkDao {
    @Insert
    void insert(Bookmark bookmark);

    @Query("SELECT * FROM Bookmark WHERE userId = :userId")
    List<Bookmark> getBookmarksByUserId(long userId);

    @Delete
    void delete(Bookmark bookmark);
}
