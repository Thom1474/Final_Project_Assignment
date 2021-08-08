package com.example.final_project_assignment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
/*
 * Soccer games Api
 * Submitted by Alan Thomas
 * CST2335_022
 * Final Project
 * */
public class Empty_data extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.empty_layout);
        Bundle Getover = getIntent().getExtras();

        Fragment_Soccer SoccerFragment = new Fragment_Soccer();
        SoccerFragment.setArguments( Getover );
        getSupportFragmentManager().beginTransaction().replace(R.id.SoccerfragmentLocation, SoccerFragment).commit();
    }
}
