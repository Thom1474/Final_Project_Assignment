package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.MovieInformationViewAdapter;

import java.util.ArrayList;

public class MoviesDAO {

    private Context context;
    private ArrayList<Movie> movies = new ArrayList();
    private MovieInformationViewAdapter movieInformationViewAdapter;

    public MoviesDAO(Context _context) {
        context = _context;

    }

    /**
     * Inserts a movie object into the database
     * @param movie the movie object to be inserted
     */
    public void insertMovie(Movie movie) {

        MyOpenHelper opener = new MyOpenHelper(context);
        SQLiteDatabase db = opener.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("movieId", movie.movieId);
        contentValues.put("title", movie.title);
        contentValues.put("year", movie.year);
        contentValues.put("rated", movie.rated);
        contentValues.put("released", movie.released);
        contentValues.put("runTime", movie.runTime);
        contentValues.put("genre", movie.genre);
        contentValues.put("director", movie.director);
        contentValues.put("writer", movie.writer);
        contentValues.put("plot", movie.plot);
        contentValues.put("moviePosterUrl", movie.moviePosterUrl);

        long id = db.insert(MyOpenHelper.TABLE_NAME, null, contentValues);
        System.out.println("MovieDAO insert: " + id);
    }

    public ArrayList<Movie> getMovies() {
        MyOpenHelper opener = new MyOpenHelper(context);
        SQLiteDatabase db = opener.getWritableDatabase();

        movies.clear();
        Cursor results = db.rawQuery("Select * from " + MyOpenHelper.TABLE_NAME + ";", null);

        //  if(results.moveToFirst()) {
        while (results.moveToNext()) {
            int _idCol = results.getColumnIndex("_id");

            String movieId = results.getString(results.getColumnIndex(MyOpenHelper.movieId));
            String title = results.getString(results.getColumnIndex(MyOpenHelper.title));
            String year = results.getString(results.getColumnIndex(MyOpenHelper.year));
            String rated = results.getString(results.getColumnIndex(MyOpenHelper.rated));
            String released = results.getString(results.getColumnIndex(MyOpenHelper.released));
            String runTime = results.getString(results.getColumnIndex(MyOpenHelper.runTime));
            String genre = results.getString(results.getColumnIndex(MyOpenHelper.genre));
            String director = results.getString(results.getColumnIndex(MyOpenHelper.director));
            String writer = results.getString(results.getColumnIndex(MyOpenHelper.writer));
            String plot = results.getString(results.getColumnIndex(MyOpenHelper.plot));
            String moviePosterUrl = results.getString(results.getColumnIndex(MyOpenHelper.moviePosterUrl));

            Movie movie = new Movie(movieId, title, year, rated, runTime, genre, released, director, writer, plot, moviePosterUrl);
            movies.add(movie);

        }
        // }
        results.close();
        return movies;
    }

    public void deleteMovie(Movie movie) {
        MyOpenHelper opener = new MyOpenHelper(context);
        SQLiteDatabase db = opener.getWritableDatabase();

        db.delete(MyOpenHelper.TABLE_NAME, "title=?", new String[]{movie.getTitle()});

    }
}
