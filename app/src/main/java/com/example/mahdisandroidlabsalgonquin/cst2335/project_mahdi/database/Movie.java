package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Movie {

    String title;
    String year;
    String rated;
    String released;
    String runTime;
    String genre;
    String director;
    String writer;
    String plot;
    String moviePosterUrl;
    Bitmap moviePoster;
    String movieId = "0";

    public Movie(String movieId, String title, String year,  String rated, String runTime,
                 String genre, String director, String writer, String plot, String moviePoster){

        this.movieId = movieId;
        this.title = title;
        this.year = year;
        this.rated = rated;
        this.runTime = runTime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.plot = plot;
        this.moviePosterUrl = moviePoster;
        this.moviePoster = this.getMovieImage(moviePoster);

    }
    public Movie(String movieString){

        try {

            String[] movieArr = movieString.split("#");

            this.movieId = movieArr[0];
            this.title = movieArr[1];
            this.year = movieArr[2];
            this.rated = movieArr[3];
            this.runTime = movieArr[4];
            this.genre = movieArr[5];
            this.director = movieArr[6];
            this.writer = movieArr[7];
            this.plot = movieArr[8];
            this.moviePosterUrl = movieArr[9];
            this.moviePoster = this.getMovieImage(this.moviePosterUrl);

        }catch(Exception ie)
        {
            System.out.println("Error with creating movie from JSON string");
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

    @Override
    public String toString() {
        return "{ Movie: {" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", rating=" + rated +
                ", runTime='" + runTime + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", writer='" + writer + '\'' +
                ", plot='" + plot + '\'' +
                ", moviePoster='" + moviePosterUrl + '\'' +
                '}';
    }

    public String toStorageString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(movieId + "#");
        buffer.append(title + "#");
        buffer.append(year + "#");
        buffer.append(rated + "#");
        buffer.append(runTime + "#");
        buffer.append(director + "#");
        buffer.append(genre + "#");
        buffer.append(writer + "#");
        buffer.append(plot + "#");
        buffer.append(moviePosterUrl);
        return buffer.toString();
    }

}
