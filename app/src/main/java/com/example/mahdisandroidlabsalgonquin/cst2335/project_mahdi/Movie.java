package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import android.graphics.Bitmap;

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
    Bitmap moviePoster;
    int movieId = 0;

    public Movie(String title, String year,  String rated, String runTime,
                 String genre, String director, String writer, String plot, Bitmap moviePoster,
                 int movieId){

        this.title = title;
        this.year = year;
        this.rated = rated;
        this.runTime = runTime;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.plot = plot;
        this.moviePoster = moviePoster;
        this.movieId = movieId;

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

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
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
                ", moviePoster='" + moviePoster + '\'' +
                '}';
    }

    public String toStorageString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append(title + "|");
        buffer.append(year + "|");
        buffer.append(rated + "|");
        buffer.append(runTime + "|");
        buffer.append(director + "|");
        buffer.append(genre + "|");
        buffer.append(writer + "|");
        buffer.append(plot + "|");
        buffer.append(moviePoster);
        return buffer.toString();
    }

}
