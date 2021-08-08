package com.example.final_project_assignment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
/*
 * Soccer games Api
 * Submitted by Alan Thomas
 * CST2335_022
 * Final Project
 * */
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button Clickhere  = findViewById(R.id.btn_First);
        Clickhere.setOnClickListener( c -> {

            Intent goToProfile  = new Intent(MainActivity.this, Activity_Soccer_games.class);

            startActivity( goToProfile);
        });
    }
}
