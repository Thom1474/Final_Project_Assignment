package com.example.final_project_assignment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
/*
 * Soccer games Api
 * Submitted by Alan Thomas
 * CST2335_022
 * Final Project
 * */
public class Favourites_page extends AppCompatActivity {
    SQLiteDatabase db;
    public static ArrayList<Soccer_News> ListView = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Data();
        setContentView(R.layout.listbox);
        ListView listView = findViewById(R.id.SView1);

        SLAdapter Adt;
        listView.setAdapter(Adt = new SLAdapter());
        listView.setOnItemLongClickListener((parent, view, position, id)->{
            Soccer_News SoccerNew = ListView.get(position);
            AlertDialog.Builder b1 = new AlertDialog.Builder(this);
            b1.setTitle(getString(R.string.sDelete)).setMessage(getString(R.string.soccerDelete)+ position )
                    .setPositiveButton(getString(R.string.yes), (click, arg)->{

                        Removed(SoccerNew);
                        ListView.remove(position);
                        Snackbar snackbar = Snackbar
                                .make(listView,getString(R.string.instruction),Snackbar.LENGTH_LONG);
                        snackbar.show();

                        Button Remove = findViewById(R.id.btn2);
                        Remove.setOnClickListener(c->Remove.callOnClick());
                        Adt.notifyDataSetChanged();
                    }).setNegativeButton(getString(R.string.no), (click, arg)->{
                        Adt.notifyDataSetChanged();
                    })
                    .create().show();

            return  true;
        });
    }

    private void Data(){
        DataBase db1 = new DataBase(this);
        db = db1.getWritableDatabase();
        String [] columns = {DataBase.Col_id, DataBase.Col_title, DataBase.Col_date, DataBase.Col_image};
        Cursor final_db = db.query(false, DataBase.Table_Name, columns, null, null, null, null, null, null);
        ListView.clear();
        int idIndex = final_db.getColumnIndex(DataBase.Col_id);
        int TitleIndex = final_db.getColumnIndex(DataBase.Col_title);
        int DateIndex = final_db.getColumnIndex(DataBase.Col_date);
        int ImageIndex = final_db.getColumnIndex(DataBase.Col_image);
        while (final_db.moveToNext()){
            Soccer_News Sc = new Soccer_News( final_db.getInt(idIndex),final_db.getString(TitleIndex),final_db.getString(DateIndex),final_db.getString(ImageIndex));
            ListView.add(Sc);
        }
    }

    protected void Removed(Soccer_News news)    {
        String sq ="DELETE FROM " + DataBase.Table_Name + " WHERE "+ DataBase.Col_id +"='"+ news.getID() +"'";
        db.execSQL(sq);
    }

    class SLAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return ListView.size();
        }
        @Override
        public Soccer_News getItem(int position){
            return ListView.get(position);
        }
        @Override
        public long getItemId(int position) {
            return  position;
        }
        @Override
        public View getView(int position, View old, ViewGroup parent) {
            View getV;
            LayoutInflater inf = getLayoutInflater();

            getV = inf.inflate(R.layout.favourite_layout, parent, false);
            TextView Tiltle = getV.findViewById(R.id.Fav_title);
            TextView Date = getV.findViewById(R.id.Fav_date);


            Tiltle.setText(ListView.get(position).getTitle());
            Date.setText(ListView.get(position).getDate());
            return getV;
        }
    }


}
