package com.morax.xephalon.model;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bookmark {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public Documentation documentation;
    public long userId;

    public Bookmark(Documentation documentation, long userId) {
        this.documentation = documentation;
        this.userId = userId;
    }
}
