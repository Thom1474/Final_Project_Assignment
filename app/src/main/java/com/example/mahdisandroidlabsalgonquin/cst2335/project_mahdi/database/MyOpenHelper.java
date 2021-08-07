package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

public class MyOpenHelper extends SQLiteOpenHelper {

    public static final String Name = "TheDatabase";
    public static final int version = 1;
    public static final String TABLE_NAME = "Movies";
    public static final String title = "title";
    public static final String year = "year";
    public static final String rated = "rated";
    public static final String released = "release";
    public static final String runTime = "runTime";
    public static final String genre = "genre";
    public static final String director = "director";
    public static final String writer = "writer";
    public static final String plot = "plot";
    public static final String moviePosterUrl = "moviePosterUrl";
    public static final String movieId = "movieId";
    public MyOpenHelper( Context context) {
        super(context, Name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table " + TABLE_NAME
                + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "movieId Text, "
                + "title Text, "
                + "year Text, "
                + "rated Text, "
                + "released Text, "
                + "runTime Text, "
                + "genre Text, "
                + "director Text, "
                + "writer Text, "
                + "plot Text, "
                + "moviePosterUrl TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
