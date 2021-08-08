package com.example.final_project_assignment;
/*
 * Soccer games Api
 * Submitted by Alan Thomas
 * CST2335_022
 * Final Project
 * */
public class Soccer_News {
    int Snews_id;
    String Snews_title;
    String Snews_date;
    String Snews_image;
    public Soccer_News(){

    }

    public Soccer_News(int SNews_id, String SNews_title, String SNews_date, String SNews_image){
        this.Snews_id = SNews_id;
        this.Snews_title= SNews_title;
        this.Snews_date= SNews_date;
        this.Snews_image= SNews_image;
    }

    public String getTitle(){
        return Snews_title;
    }

    public String getDate(){
        return Snews_date;
    }

    public String getImage(){
        return Snews_image;
    }

    public int getID(){
        return Snews_id;
    }
}
