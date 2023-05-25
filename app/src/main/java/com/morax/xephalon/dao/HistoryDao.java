package com.morax.xephalon.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.morax.xephalon.model.History;

import java.util.List;


@Dao
public interface HistoryDao {
    @Insert
    void insert(History history);

    @Query("SELECT * FROM History WHERE userId = :userId")
    List<History> getListOfHistory(long userId);

    @Delete
    void delete(History history);

    @Query("DELETE FROM History")
    void deleteAll();
}
