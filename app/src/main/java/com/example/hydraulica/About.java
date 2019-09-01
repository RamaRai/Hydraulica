package com.example.hydraulica;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class About extends AppCompatActivity {

    public static final String TAG = "Hydraulica2019_RamaRai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.about);
        Log.i(TAG, " About Activity Created");

    }
}
