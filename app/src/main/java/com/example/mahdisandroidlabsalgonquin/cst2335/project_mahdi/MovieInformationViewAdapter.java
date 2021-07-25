package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

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
        //Log.d("MOVIE", Integer.toString(movieData.size()));
        //Log.d("MOVIE0", movieData.get(0).toString());
        //Log.d("MOVIE1", movieData.get(1).toString());
        //Log.d("MOVIE2", movieData.get(2).toString());
        //Log.d("MOVIE3", movieData.get(3).toString());
        //Log.d("MOVIe pos",Integer.toString(position));
        //holder.movieTitleTV.setText(m.title);
        //holder.movieYearTV.setText(m.year);
        holder.movieTitleTV.setText(m.getTitle());
        holder.movieYearTV.setText(Integer.toString(m.getYear()));
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public Movie getItem(int position) {
        return movieData.get(position);
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTitleTV;
        TextView movieYearTV;

        public ViewHolder(View itemView) {
            super(itemView);
            movieTitleTV = itemView.findViewById(R.id.movieTitleTextView);
            movieYearTV = itemView.findViewById(R.id.movieYearTextView);
            //movieTitleTV.setText("text from code");

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
