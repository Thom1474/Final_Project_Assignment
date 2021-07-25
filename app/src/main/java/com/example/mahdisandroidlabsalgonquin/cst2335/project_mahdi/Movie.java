package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

public class Movie {

    String title;
    int year;
    double rating;
    String runTime;
    String mainActors;
    String plot;
    String posterUrl;

    public Movie(String title, int year, double rating, String runTime,
                 String mainActors, String plot, String posterUrl){

        this.title = title;
        this.year = year;
        this.rating = rating;
        this.runTime = runTime;
        this.mainActors = mainActors;
        this.plot = plot;
        this.posterUrl = posterUrl;

    }
    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public double getRating() {
        return rating;
    }

    public String getRunTime() {
        return runTime;
    }

    public String getMainActors() {
        return mainActors;
    }

    public String getPlot() {
        return plot;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setMainActors(String mainActors) {
        this.mainActors = mainActors;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setRunTime(String runTime) {
        this.runTime = runTime;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", year=" + year +
                ", rating=" + rating +
                ", runTime='" + runTime + '\'' +
                ", mainActors='" + mainActors + '\'' +
                ", plot='" + plot + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                '}';
    }
}
