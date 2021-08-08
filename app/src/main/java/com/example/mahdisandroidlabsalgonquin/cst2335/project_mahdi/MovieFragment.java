package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database.Movie;
import com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database.MovieInformationViewAdapter;
import com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database.MoviesDAO;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class MovieFragment extends Fragment implements MovieInformationViewAdapter.ItemClickListener{

    MovieInformationViewAdapter adapter;
    private ArrayList<Movie> data = new ArrayList<Movie>();
    int movieCount = 0;
    private RecyclerView recyclerView;
    private MoviesDAO moviesDAO;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        moviesDAO = new MoviesDAO(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        recyclerView = view.findViewById(R.id.movieListRecyclerView);
        EditText movieTitle = view.findViewById(R.id.movieTitle);
        movieTitle.setText(getDataFromPreferences());

        Button loadAPI =  view.findViewById(R.id.loadAPI);
        loadAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String movie = movieTitle.getText().toString();
                saveToPreferences(movie);

                InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(movieTitle.getWindowToken(), 0);

                Executor newThread = Executors.newSingleThreadExecutor();
                newThread.execute(() -> {
                    processJSON(null, view, movie);
                });
            }
        });
        Button loadDB =  view.findViewById(R.id.loadDB);
        loadDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = moviesDAO.getMovies();
                reloadRecyclerView();
            }
        });
        return view;
    }

    private void processJSON(AlertDialog dialog, View view, String movie) {

        try {
           //  String stringURL = "http://www.omdbapi.com/?i=tt3896198&apikey=e708e8bd";
            String stringURL = "http://www.omdbapi.com/?t=" + movie + "&apikey=e708e8bd";

            URL url = new URL(stringURL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String text = (new BufferedReader(
                    new InputStreamReader(in, StandardCharsets.UTF_8)))
                    .lines()
                    .collect(Collectors.joining("\n"));

            //http://omdbapi.com?t=exorcist&apikey=e708e8bd

            String title = null;
            String year = null;
            String rated = null;
            String runTime = null;
            String genre = null;
            String released = null;
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
            released = theDocument.getString("Released");
            director = theDocument.getString("Director");
            writer = theDocument.getString("Writer");
            plot = theDocument.getString("Plot");
            moviePoster = theDocument.getString("Poster");

            movieCount = movieCount + 1;
            String movieCountStr = String.valueOf(movieCount);
            Movie m = new Movie(movieCountStr, title, year, rated, runTime, genre, released, director, writer, plot, moviePoster);
            data.add(m);

            /// Store in shared preferences
            /*
            SharedPreferences sharedPreferences = getActivity().getPreferences((Context.MODE_PRIVATE));
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String movieStringForStorage = m.toStorageString();
            editor.putString(movieCountStr, movieStringForStorage);
            editor.apply();
             */
            ///

            // JSONArray weatherArray = theDocument.getJSONArray("weather");
            reloadRecyclerView();

        } catch (IOException | JSONException ioe) {
            Log.e("Connection error:", ioe.getMessage());

        }
    }

    private void saveToPreferences(String itemToSave)
    {
        SharedPreferences sharedPreferences = getActivity().getPreferences((Context.MODE_PRIVATE));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("movieEntry", itemToSave);
        editor.apply();
    }

    private String getDataFromPreferences()
    {
        SharedPreferences sharedPreferences = getActivity().getPreferences((Context.MODE_PRIVATE));
        String itemSaved = sharedPreferences.getString("movieEntry", "");
        return itemSaved;
    }

    private void reloadRecyclerView() {

        getActivity().runOnUiThread(() -> {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new MovieInformationViewAdapter(this, data, getActivity());
            // adapter.setClickListener(this);
            recyclerView.setAdapter(adapter);

        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("Click", "clicked on item at row number " + position);
        showDialog(position);
    }

    private void showDialog(int _position)
    {
        Movie mDelete = data.get(_position);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to save the movie: " + mDelete.getTitle())
                .setTitle("Save Movie")
                .setNegativeButton("No", (dialog, cl) -> {
                })
                .setPositiveButton("Yes", (dialog, cl) -> {

                    final int position = _position;
                    // editor.remove(m.getMovieId());
                   Movie movie = data.get(position);
                   moviesDAO.insertMovie(movie);

                    this.reloadRecyclerView();
                    // adt.notifyItemRemoved(position);

                    // db.delete(MyOpenHelper.TABLE_NAME, "_id=?", new String[] { String.valueOf(removedMessage.getId()) });
/*

                    Snackbar.make(messageText, "you deleted movie " + m.getTitle(), Snackbar.LENGTH_LONG)
                            .setAction("Undo", clk -> {
                                messages.add(position, removedMessage);
                                adt.notifyItemInserted(position);
                            })
                            .show();
                            */

                })
                .create().show();
    }
}