package com.kayali_developer.popularmoviesstage1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.kayali_developer.popularmoviesstage1.model.Movie;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>>{
    private static final int LOADER_ID = 0;
    private static final String REQUEST_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String API_KEY = "dba53a04d2e1b8de6dcb8e6e47f19703";
    RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesRecyclerView = (RecyclerView)findViewById(R.id.movies_recycler_view);
        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2);
        moviesRecyclerView.setLayoutManager(layoutManager);
        moviesRecyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter();
        moviesRecyclerView.setAdapter(movieAdapter);
        loadMovieData();
    }

    private void loadMovieData(){
        // Checking state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // Call the Loader to get data when there is a connection
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(LOADER_ID, null, this);
        } else {


        }
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        String sortBy = "popularity.desc";
        String sortByAverage = "vote_average.desc";
        Uri baseUri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("api_key", API_KEY);
        uriBuilder.appendQueryParameter("sort_by", sortBy);
        return new MovieLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> movies) {
        // Add the new retrieved stories to the adapter
        if (movies != null && !movies.isEmpty()) {
            movieAdapter.setMoviesData(movies);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        movieAdapter = new MovieAdapter();
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
            loadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
