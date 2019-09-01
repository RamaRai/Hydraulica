package com.example.hydraulica;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class HydraulicaTableHandler {
    // Database table
    public static final String TABLE_ADDRESS = "notes";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DESIGNATION = "category";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_ADDRESS = "streetaddress";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_REMARKS = "remarks";
    public static final String COLUMN_PHONE = "contact";

    // Database Table creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_ADDRESS
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_DESIGNATION + " text not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_SUBJECT + " text not null, "
            + COLUMN_ADDRESS + " text not null, "
            + COLUMN_COUNTRY + " text not null, "
            + COLUMN_REMARKS + " text not null, "
            + COLUMN_PHONE + " text "
            + ");";

    public static final String LOGTAG = "Hydraulica2019_RamaRai";

    public static void onCreate(SQLiteDatabase database) {

        database.execSQL(DATABASE_CREATE);
        Log.v(LOGTAG, " Data table Created in the database");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(HydraulicaTableHandler.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        onCreate(database);
    }


} 