package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi.database.Movie;

import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieInformationViewAdapter extends RecyclerView.Adapter<MovieInformationViewAdapter.ViewHolder> {

    private ArrayList<Movie> movieData;
    private LayoutInflater movieInflater;
    private ItemClickListener mOnClickListener;
    private Activity activity;

    public MovieInformationViewAdapter(ItemClickListener itemClickListener, ArrayList<Movie> data, Activity _activity) {
        this.mOnClickListener = itemClickListener;
        this.movieData = data;
        this.activity = _activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        movieInflater = LayoutInflater.from(context);

        View view = movieInflater.inflate(R.layout.activity_movie_information_row, parent, false);
        ViewHolder initRow = new ViewHolder(view);
        return initRow;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = movieData.get(position);

        holder.movieTitle.setText(movie.getTitle());
        holder.movieYear.setText(movie.getYear());
        holder.runTime.setText(movie.getRunTime());
        holder.director.setText(movie.getDirector());

        try {

            Executor newThread = Executors.newSingleThreadExecutor();
            newThread.execute(() -> {
               final Bitmap bitmap = movie.getMovieImage();
               activity.runOnUiThread(() -> {
                   holder.imageView.setImageBitmap(bitmap);
                });
            });
        }catch(Exception e){

        }

    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public Movie getItem(int position) {
        return movieData.get(position);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTitle;
        TextView movieYear;
        TextView runTime;
        TextView director;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movieTitle);
            movieYear = itemView.findViewById(R.id.movieYear);
            runTime = itemView.findViewById(R.id.runTime);
            director = itemView.findViewById(R.id.director);
            //try {
                imageView = itemView.findViewById(R.id.imageView);
            //}catch(Exception e){

            //}
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null){
                mOnClickListener.onItemClick(v, getAbsoluteAdapterPosition());
            }
        }
    }
/*
    void setClickListener(ItemClickListener itemClickListener) {
        this.mOnClickListener = itemClickListener;
    }
*/
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}
