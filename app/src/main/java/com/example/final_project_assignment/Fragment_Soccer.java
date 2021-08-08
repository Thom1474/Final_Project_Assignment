package com.example.final_project_assignment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
/*
 * Soccer games Api
 * Submitted by Alan Thomas
 * CST2335_022
 * Final Project
 * */
public class Fragment_Soccer extends Fragment {

    private static final String ARGUMENT1 = "param1";
    private static final String ARGUMENT2 = "param2";
    private String var1;
    private String var2;

    public Fragment_Soccer() {
    }
    public static Fragment_Soccer newInstance(String v1, String v2) {
        Fragment_Soccer fragment = new Fragment_Soccer();
        Bundle args = new Bundle();
        args.putString(ARGUMENT1, v1);
        args.putString(ARGUMENT2, v2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            var1 = getArguments().getString(ARGUMENT1);
            var2 = getArguments().getString(ARGUMENT2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Activity context= getActivity();
        Bundle ActivityLog;
        ActivityLog = getArguments();
        View result =  inflater.inflate(R.layout.fragment_layout, container, false);



        TextView titletxt = result.findViewById(R.id.Newslbl);
        TextView datetxt = result.findViewById(R.id.NewsDate);


        titletxt.setText(ActivityLog.getString("Title"));
        datetxt.setText(ActivityLog.getString("Date"));

        ImageView imageview = result.findViewById(R.id.soccerimageview);

        new SoccerImage(ActivityLog.getString("Image"), imageview).execute();



        Button Favoritetbtn = result.findViewById(R.id.favbtn);
        Favoritetbtn.setOnClickListener(c ->
        {
            SQLiteDatabase db;
            DataBase dbOpener = new DataBase(context);
            db = dbOpener.getWritableDatabase();

            ContentValues conVal = new ContentValues();
            conVal.put(DataBase.Col_title, ActivityLog.getString("Title"));
            conVal.put(DataBase.Col_date,ActivityLog.getString("Date"));
            conVal.put(DataBase.Col_image, ActivityLog.getString("Image"));

            long id = db.insert(DataBase.Table_Name,null , conVal);
            Toast.makeText(context,getString(R.string.dataSave),Toast.LENGTH_SHORT).show();
        });
        Button website = result.findViewById(R.id.button);

        website.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("http://www.daznservices.com/ns/rss/editorial/ArticleList"));
                startActivity(intent);
            } });

        return  result;
    }
    class SoccerImage  extends AsyncTask<Void, Void, Bitmap> {
        public String u;
        public ImageView img;

        public SoccerImage(String link, ImageView i_View) {
            this.u = link;
            this.img = i_View;
        }
        @Override
        protected Bitmap doInBackground(Void... params) {
            try {
                URL urlConnection = new URL(u);
                HttpURLConnection connection = (HttpURLConnection) urlConnection
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            img.setImageBitmap(result);
        }
    }
}
