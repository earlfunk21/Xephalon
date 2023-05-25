package com.morax.xephalon.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class History {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String search;
    public long userId;

    public History(String search, long userId) {
        this.search = search;
        this.userId = userId;
    }

    @NonNull
    @Override
    public String toString() {
        return search;
    }
}
