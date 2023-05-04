package com.morax.xephalon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.morax.xephalon.activity.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private static final String myPrefs = "myPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(myPrefs, Context.MODE_PRIVATE);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPreferences.getString("username", "anonymous").equals("anonymous")){

            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

    }

}