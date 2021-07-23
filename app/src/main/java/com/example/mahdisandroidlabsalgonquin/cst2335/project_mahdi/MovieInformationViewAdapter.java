package com.example.mahdisandroidlabsalgonquin.cst2335.project_mahdi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MovieInformationViewAdapter extends RecyclerView.Adapter<MovieInformationViewAdapter.ViewHolder> {

    private ArrayList<Movie> movieData;
    private LayoutInflater movieInflater;
    private ItemClickListener mClickListener;

    MovieInformationViewAdapter(Context context, ArrayList<Movie> data) {
        this.movieInflater = LayoutInflater.from(context);
        this.movieData = data;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = movieInflater.inflate(R.layout.activity_movie_information_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie m = movieData.get(position);
        holder.movieTitleTV.setText(m.title);
        holder.movieYearTV.setText(m.year);
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView movieTitleTV;
        TextView movieYearTV;

        public ViewHolder(View itemView) {
            super(itemView);
            movieTitleTV = itemView.findViewById(R.id.movieTitleTextView);
            movieYearTV = itemView.findViewById(R.id.movieYearTextView);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {};
        }

    }

    Movie getItem(int id) {
        return movieData.get(id);
    }

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
