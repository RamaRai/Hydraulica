package com.example.hydraulica;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ContactUs extends AppCompatActivity {

    public static final String TAG = "Hydraulica2019_RamaRai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.contactus);
        Log.i(TAG, " ContactUs Activity Created");

    }

    public void comeback1(View view) {

        finish();

        // onBackPressed();

    }
}
