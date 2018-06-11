package com.kayali_developer.popularmoviesstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.kayali_developer.popularmoviesstage1.model.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<Movie>>, MovieAdapter.MovieAdapterOnClickHandler {

    // API Key - You can change it using gradle.properties file
    private static final String API_KEY = BuildConfig.API_KEY;

    private static final int LOADER_ID = 0;
    private static final String REQUEST_URL = "https://api.themoviedb.org/3";
    private RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;
    // Empty View to display when there is no data to display
    private View emptyView;
    // Loading data Indicator
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emptyView = findViewById(R.id.empty_view);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        moviesRecyclerView = findViewById(R.id.movies_recycler_view);
        int posterWidth = 700;
        GridLayoutManager layoutManager
                = new GridLayoutManager(this,  calculateBestSpanCount(posterWidth));
        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        moviesRecyclerView.setAdapter(movieAdapter);
        // Check connection and then initialize the loader manager
        loadMovieData();
    }

    // Helper Method to make the layout more responsive by controlling poster width
    private int calculateBestSpanCount(int posterWidth) {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float screenWidth = outMetrics.widthPixels;
        return Math.round(screenWidth / posterWidth);
    }

    // Helper Method to Check connection and then initialize the loader manager
    private void loadMovieData() {
        // Checking state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // Call the Loader to get data when there is a connection
        if (networkInfo != null && networkInfo.isConnected()) {
            showMoviesDataView();
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {
            showErrorMessage();
        }
    }
        // Helper Method to hide emptyView and show recyclerView to display retrieved data
    private void showMoviesDataView() {
        emptyView.setVisibility(View.INVISIBLE);
        moviesRecyclerView.setVisibility(View.VISIBLE);
    }
    // Helper Method to hide recyclerView and show emptyView
    private void showErrorMessage() {
        moviesRecyclerView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
    }
    // If an Item clicked - start DetailsActivity and pass bundle as extra using an Intent
    @Override
    public void onClick(Movie currentMovie) {
        // Convert Movie object to arrayList
        String[] currentMovieStrings = {currentMovie.getOriginalTitle(), currentMovie.getPosterPath(), currentMovie.getOverview(),
                currentMovie.getRating(), currentMovie.getReleaseDate()};
        Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
        intent.putExtra("current_movie", currentMovieStrings);
        startActivity(intent);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        mLoadingIndicator.setVisibility(View.VISIBLE);
        // Sort by preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortBy = sharedPrefs.getString(
                getString(R.string.settings_sort_by_key),
                getString(R.string.settings_sort_by_default));
        // Parse Url
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendEncodedPath(sortBy);
        uriBuilder.appendQueryParameter("api_key", API_KEY);
        return new MovieLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        // Add the new retrieved movies to the adapter
        if (movies != null && !movies.isEmpty()) {
            showMoviesDataView();
            movieAdapter.setMoviesData(movies);
        } else {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        movieAdapter = new MovieAdapter(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.main, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            movieAdapter.setMoviesData(null);
            loadMovieData();
            return true;
        }
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
