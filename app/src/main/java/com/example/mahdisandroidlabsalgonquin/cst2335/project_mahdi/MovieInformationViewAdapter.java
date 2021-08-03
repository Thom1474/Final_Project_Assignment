package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;

public class MovieInformationViewAdapter extends RecyclerView.Adapter<MovieInformationViewAdapter.ViewHolder> {

    private ArrayList<Movie> movieData;
    private LayoutInflater movieInflater;
    private ItemClickListener mOnClickListener;

    MovieInformationViewAdapter(ArrayList<Movie> data) {
        this.movieData = data;
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
        Movie m = movieData.get(position);

        holder.movieTitle.setText(m.getTitle());
        holder.movieYear.setText(m.getYear());
        holder.runTime.setText(m.getRunTime());
        holder.director.setText(m.getDirector());
        holder.imageView.setImageBitmap(m.getMoviePoster());

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
            imageView = itemView.findViewById(R.id.imageView);

        }

        @Override
        public void onClick(View v) {
            if (mOnClickListener != null) mOnClickListener.onItemClick(v, getAbsoluteAdapterPosition());
        }
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mOnClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
