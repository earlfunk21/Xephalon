package com.morax.xephalon.model;

import java.io.Serializable;

public class Language implements Serializable {
    private int logo;
    private String jsonString;
    private String name;

    public Language(int logo, String jsonString, String name) {
        this.logo = logo;
        this.jsonString = jsonString;
        this.name = name;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
