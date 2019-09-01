package com.example.hydraulica;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hydraulicanotes.db";
    private static final int DATABASE_VERSION = 1;

    public static final String LOGTAG = "Hydraulica2019_RamaRai";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called once during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {

        HydraulicaTableHandler.onCreate(database);
        Log.v(LOGTAG, " Database Created");
    }
    // Method is called when there is an upgrade of the database, e.g. if you increase the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        HydraulicaTableHandler.onUpgrade(database, oldVersion, newVersion);
        Log.v(LOGTAG, " Database Upgraded to new version");
    }
} 