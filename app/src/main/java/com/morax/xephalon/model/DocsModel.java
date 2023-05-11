package com.morax.xephalon.model;

import java.io.Serializable;

public class DocsModel implements Serializable {
    private String title;
    private String docs;
    private String date;
    private int thumbnail;

    public DocsModel(String title, String docs, String date, int thumbnail) {
        this.title = title;
        this.docs = docs;
        this.date = date;
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
