package com.kayali_developer.popularmoviesstage1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kayali_developer.popularmoviesstage1.model.Movie;
import com.kayali_developer.popularmoviesstage1.utilities.JSONUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getName();
    private List<Movie> movies;

    @NonNull
    @Override
    public MovieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        Log.w(LOG_TAG, "***************************" + "onCreateViewHolder started");
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapterViewHolder holder, int position) {
        Context context = holder.posterView.getContext();
        Movie currentMovie = movies.get(position);
        Log.w(LOG_TAG, "***************************" + currentMovie.getPosterPath());
        Picasso.with(context).load(currentMovie.getPosterPath()).into(holder.posterView);
        Log.w(LOG_TAG, "***************************" + "onBindViewHolder started");
    }

    @Override
    public int getItemCount() {
        if (movies == null){
            return 0;
        }
        return movies.size();
    }

    class MovieAdapterViewHolder extends RecyclerView.ViewHolder{
        final ImageView posterView;
        MovieAdapterViewHolder(View itemView) {
            super(itemView);
            posterView = itemView.findViewById(R.id.iv_poster_item);
            Log.w(LOG_TAG, "***************************" + "MovieAdapterViewHolder started");
        }
    }

    public void setMoviesData(List<Movie> moviesData) {
        movies = moviesData;
        notifyDataSetChanged();
        Log.w(LOG_TAG, "***************************" + "setMoviesData started");
    }
}
