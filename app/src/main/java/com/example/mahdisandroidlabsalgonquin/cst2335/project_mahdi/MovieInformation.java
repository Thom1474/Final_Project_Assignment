package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;

import com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database.Movie;

import java.util.ArrayList;

public class MovieInformation extends AppCompatActivity {

    MovieInformationViewAdapter adapter;
    private ArrayList<Movie> data = new ArrayList<Movie>();
    int movieCount = 0;
    Button movieInformationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);

        MovieFragment fragment = new MovieFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainer, fragment);

        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
/*
        movieInformationButton = (Button) findViewById(R.id.movieInformationButton);
        movieInformationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(this, MovieInformation.class));
            }
        });*/
    }

}
