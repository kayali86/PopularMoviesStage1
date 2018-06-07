package com.kayali_developer.popularmoviesstage1.utilities;

import android.util.Log;

import com.kayali_developer.popularmoviesstage1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class JSONUtils {
    private static final String LOG_TAG = JSONUtils.class.getName();

    public static List<Movie> fetchMoviesData (String stringUrl){
        URL url = NetworkUtils.createUrl(stringUrl);
        String jsonResponse = null;
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractResultFromJson(jsonResponse);
    }

    private static List<Movie> extractResultFromJson(String jsonResponse){
        if (jsonResponse == null){
            return null;
        }
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray resultsJSONArray = baseJsonResponse.getJSONArray("results");
            for (int i=0; i < resultsJSONArray.length(); i++){
                JSONObject currentMovie = resultsJSONArray.getJSONObject(i);
                String originalTitle = currentMovie.getString("original_title");
                String posterPath = currentMovie.getString("poster_path");
                String overview = currentMovie.getString("overview");
                String userRating = String.valueOf(currentMovie.getDouble("vote_average"));
                String releaseDate = currentMovie.getString("release_date");
                Movie movie = new Movie(originalTitle, posterPath, overview, userRating, releaseDate);
                movies.add(movie);
            }
        }catch (JSONException e){
            Log.e(LOG_TAG, "Problem parsing the Story JSON results", e);
        }
        return movies;

    }
}
