package com.kayali_developer.popularmoviesstage1;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.kayali_developer.popularmoviesstage1.model.Movie;
import com.kayali_developer.popularmoviesstage1.utilities.JSONUtils;

import java.util.List;

class MovieLoader extends AsyncTaskLoader<List<Movie>>{
    private String url;

    public MovieLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (url == null) {
            return null;
        }
        return JSONUtils.fetchMoviesData(url);
    }
}
