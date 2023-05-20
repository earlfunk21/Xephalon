package com.morax.xephalon.model;

import java.io.Serializable;

public class Documentation implements Serializable {
    private String lang;
    private String title;
    private String fullPath;
    private String markdown;
    private int thumbnail;

    public Documentation(String lang, String title, String fullPath, String markdown, int thumbnail) {
        this.lang = lang;
        this.title = title;
        this.fullPath = fullPath;
        this.markdown = markdown;
        this.thumbnail = thumbnail;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }

    public String getMarkdown() {
        return markdown;
    }

    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}
