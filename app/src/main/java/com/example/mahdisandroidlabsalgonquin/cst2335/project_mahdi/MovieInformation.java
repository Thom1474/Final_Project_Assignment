package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MovieInformation extends AppCompatActivity implements MovieInformationViewAdapter.ItemClickListener {

    MovieInformationViewAdapter adapter;
    private ArrayList<Movie> data = new ArrayList<Movie>();
    int movieCount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);


        Executor newThread = Executors.newSingleThreadExecutor();
        newThread.execute(() -> {

            processJSON(null);

        });
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this, "You clicked " + adapter.getItem(position).getTitle() + " on row number "  + position, Toast.LENGTH_SHORT).show();
        Log.d("Click", "clicked on item at row number " + position);
    }


    private void processJSON(AlertDialog dialog) {

        try {
            String stringURL = "http://www.omdbapi.com/?i=tt3896198&apikey=e708e8bd";

            URL url = new URL(stringURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String text = (new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)))
                    .lines()
                    .collect(Collectors.joining("\n"));

            String title = null;
            String year = null;
            String rated = null;
            String runTime = null;
            String genre = null;
            String director = null;
            String writer = null;
            String plot = null;
            String moviePoster = null;

            JSONObject theDocument = new JSONObject(text);

            title = theDocument.getString("Title");
            year = theDocument.getString("Year");
            rated = theDocument.getString("Rated");
            runTime = theDocument.getString("Runtime");
            genre = theDocument.getString("Genre");
            director = theDocument.getString("Director");
            writer = theDocument.getString("Writer");
            plot = theDocument.getString("Plot");
            moviePoster = theDocument.getString("Poster");
            Bitmap moviePosterBitmap = getMovieImage(moviePoster);

            movieCount = movieCount + 1;
            Movie m = new Movie(title, year, rated, runTime, genre, director, writer, plot, moviePosterBitmap, movieCount);
            // data.add(m);
            /// Store in shared preferences
            SharedPreferences sharedPreferences = getPreferences((Context.MODE_PRIVATE));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(String.valueOf(movieCount),m.toStorageString());
            editor.apply();
            ///
            try{
                for(int i=0; i<=movieCount; i++) {
                    String movie = sharedPreferences.getString(String.valueOf(i), "");

                    if (movie.equals("")) {
                        // movie has been deleted
                        continue;
                    }
                    JSONObject jsonObject = new JSONObject(movie);
                    data.add(null);
                }
            }catch(JSONException je)
            {
                je.printStackTrace();
            }

            // JSONArray weatherArray = theDocument.getJSONArray("weather");

            runOnUiThread(() -> {
                RecyclerView recyclerView = findViewById(R.id.movieListRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(MovieInformation.this));
                adapter = new MovieInformationViewAdapter(data);
                adapter.setClickListener(this);
                recyclerView.setAdapter(adapter);

            });

        } catch (IOException | JSONException ioe) {
            Log.e("Connection error:", ioe.getMessage());

        }
    }

    private Bitmap getMovieImage(String moviePoster) {
        Bitmap image = null;
        try {
            URL url = new URL(moviePoster);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                image = BitmapFactory.decodeStream(connection.getInputStream());

                try {
                    FileOutputStream fOut = new FileOutputStream(new File(moviePoster));
                    image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException | NullPointerException e) {
                    e.printStackTrace();

                }
            }
            final Bitmap imageIcon = image;

        } catch (IOException ioe) {
            Log.e("Connection error:", ioe.getMessage());

        }
        return image;
    }

}