package com.morax.xephalon.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.morax.xephalon.model.Documentation;

public class DocumentationTypeConverter {

    @TypeConverter
    public String fromDocumentation(Documentation documentation) {
        Gson gson = new Gson();
        return gson.toJson(documentation);
    }

    @TypeConverter
    public Documentation toDocumentation(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Documentation.class);
    }
}