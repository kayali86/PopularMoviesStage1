package com.kayali_developer.popularmoviesstage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        // Retrieve extras from intent
        Bundle extras = getIntent().getExtras();
        // Avoid NullPointerException by checking if extras is null
        if (extras != null && extras.size() != 0) {
            // Initialize views
            TextView titleView = findViewById(R.id.tv_original_title);
            ImageView posterView = findViewById(R.id.iv_poster_detail);
            TextView overviewView = findViewById(R.id.tv_overview);
            TextView ratingView = findViewById(R.id.tv_user_rating);
            TextView releaseDateView = findViewById(R.id.tv_release_date);
            // Extract required data from stringArray to set it to views
            String[] currentMovieStrings = extras.getStringArray("current_movie");
            String originalTitle = currentMovieStrings[0];
            String postPath = currentMovieStrings[1];
            String overview = currentMovieStrings[2];
            String rating = currentMovieStrings[3];
            String releaseDate = currentMovieStrings[4];
            // Populate data on UI and check data before to avoid empty views
            if (originalTitle.isEmpty()) {
                titleView.setText(getString(R.string.no_data_found_message));
            }
            titleView.setText(originalTitle);

            Picasso.with(this)
                    .load(postPath)
                    .placeholder(R.drawable.no_poster)
                    .error(R.drawable.no_poster)
                    .into(posterView);

            if (overview.isEmpty()) {
                overviewView.setText(getString(R.string.no_data_found_message));
            }
            overviewView.setText(overview);

            if (rating.isEmpty()) {
                ratingView.setText(getString(R.string.no_data_found_message));
            }
            ratingView.setText(rating);

            if (releaseDate.isEmpty()) {
                releaseDateView.setText(getString(R.string.no_data_found_message));
            }
            releaseDateView.setText(releaseDate);
        } else {
            // When retrieving the extras from Intent failed
            Toast.makeText(this, R.string.details_error_loading_data, Toast.LENGTH_SHORT).show();
        }
    }
}
