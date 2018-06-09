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
    // JSON response Keys
    private static final String RESULTS_KEY = "results";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String OVERVIEW_KEY = "overview";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String RELEASE_DATE_KEY = "release_date";

    // Fetch a JSON response using API Url
    public static List<Movie> fetchMoviesData(String stringUrl) {
        URL url = NetworkUtils.createUrl(stringUrl);
        String jsonResponse = null;
        try {
            jsonResponse = NetworkUtils.getResponseFromHttpUrl(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        return extractResultFromJson(jsonResponse);
    }
    // Extract data from JSON Response to Strings and return a list of "Movie"
    private static List<Movie> extractResultFromJson(String jsonResponse) {
        if (jsonResponse == null) {
            return null;
        }
        List<Movie> movies = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray resultsJSONArray = baseJsonResponse.getJSONArray(RESULTS_KEY);
            for (int i = 0; i < resultsJSONArray.length(); i++) {
                JSONObject currentMovie = resultsJSONArray.getJSONObject(i);
                String originalTitle = currentMovie.getString(ORIGINAL_TITLE_KEY);
                String posterPath = currentMovie.getString(POSTER_PATH_KEY);
                String overview = currentMovie.getString(OVERVIEW_KEY);
                String userRating = String.valueOf(currentMovie.getDouble(VOTE_AVERAGE_KEY));
                String releaseDate = currentMovie.getString(RELEASE_DATE_KEY);
                Movie movie = new Movie(originalTitle, posterPath, overview, userRating, releaseDate);
                movies.add(movie);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the Story JSON results", e);
        }
        return movies;
    }
}
