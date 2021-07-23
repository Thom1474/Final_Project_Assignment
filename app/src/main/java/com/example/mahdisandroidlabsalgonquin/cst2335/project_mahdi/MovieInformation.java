package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MovieInformation extends AppCompatActivity implements MovieInformationViewAdapter.ItemClickListener {

    MovieInformationViewAdapter adapter;

    Movie m1 = new Movie("t1", 0001, 7.7, "100 min", "a1, b1", "dfdsjnfsjdnsnf", "fndnfosins" );
    Movie m2 = new Movie("t2", 0002, 7.7, "100 min", "a1, b1", "dfdsjnfsjdnsnf", "fndnfosins" );
    Movie m3 = new Movie("t3", 0003, 7.7, "100 min", "a1, b1", "dfdsjnfsjdnsnf", "fndnfosins" );
    Movie m4 = new Movie("t4", 0004, 7.7, "100 min", "a1, b1", "dfdsjnfsjdnsnf", "fndnfosins" );
    ArrayList<Movie> data = new ArrayList<Movie>(Arrays.asList(m1,m2,m3,m4));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        RecyclerView recyclerView = findViewById(R.id.movieListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MovieInformationViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked on row number: " + position, Toast.LENGTH_SHORT).show();
    }
}