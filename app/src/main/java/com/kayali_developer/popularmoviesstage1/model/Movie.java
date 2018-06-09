package com.kayali_developer.popularmoviesstage1.model;

public class Movie {
    final private String originalTitle, posterPath, overview, rating, releaseDate;
    // Base images Url to append with image path
    private static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";
    // Constructor
    public Movie(String originalTitle, String posterPath, String overview, String rating, String releaseDate) {
        this.originalTitle = originalTitle;
        this.posterPath = POSTER_BASE_URL + posterPath;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
    }
    // Getters
    public String getOriginalTitle() {
        return originalTitle;
    }
    public String getPosterPath() {
        return this.posterPath;
    }
    public String getOverview() {
        return overview;
    }
    public String getRating() {
        return rating;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
}
