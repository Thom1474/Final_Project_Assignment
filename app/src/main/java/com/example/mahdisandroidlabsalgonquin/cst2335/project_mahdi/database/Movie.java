package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Movie {

    String title;
    String year;
    String rated;
    String runTime;
    String genre;
    String released;
    String director;
    String writer;
    String plot;
    String moviePosterUrl;
    Bitmap moviePoster;
    String movieId = "0";

    public Movie(String movieId, String title, String year,  String rated, String runTime,
                 String genre, String released, String director, String writer, String plot, String moviePoster) {

        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.runTime = runTime;
        this.genre = genre;
        this.released = released;
        this.director = director;
        this.writer = writer;
        this.plot = plot;
        this.moviePosterUrl = moviePoster;

        try {
            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {
                this.moviePoster = this.getMovieImage();
            });
        }
        catch(Exception ie){
            ie.printStackTrace();
        }

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String rated) {
        this.rated = rated;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getRunTime() {
        return runTime;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Bitmap getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(Bitmap moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    public Bitmap getMovieImage() {

        if(this.moviePoster != null)
        {
            return this.moviePoster;
        }
        Bitmap image = null;
        try {
            URL url = new URL(this.moviePosterUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                image = BitmapFactory.decodeStream(connection.getInputStream());

                /*
                try {
                    String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/image_path";
                    // FileOutputStream fOut = new FileOutputStream(new File(filePath + "/" + this.moviePosterUrl));
                    FileOutputStream fOut = new FileOutputStream(new File(this.moviePosterUrl));
                    image.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException | NullPointerException e) {
                    e.printStackTrace();

                }
                */
            }

        } catch (IOException ioe) {
            Log.e("Connection error:", ioe.getMessage());

        }//catch (Exception ioe) {
        // Log.e("Connection error:", ioe.getMessage());

        // }
        return image;
    }

    @Override
    public String toString() {
        return "{ Movie: {" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", rating=" + rated +
                ", runTime='" + runTime + '\'' +
                ", released='" + released + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", writer='" + writer + '\'' +
                ", plot='" + plot + '\'' +
                ", moviePoster='" + moviePosterUrl + '\'' +
                '}';
    }

}
