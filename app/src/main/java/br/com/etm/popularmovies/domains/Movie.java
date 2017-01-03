package br.com.etm.popularmovies.domains;

import android.content.ContentValues;
import android.database.Cursor;
import android.text.format.Time;

import java.io.Serializable;
import java.util.Date;

import br.com.etm.popularmovies.data.PopularMovieContract;

/**
 * Created by EDUARDO_MARGOTO on 11/29/2016.
 */

public class Movie implements Serializable {

    private long movie_id;
    private String title;
    private String path_poster;
    private String backdrop_path;
    private String overview;
    private float rating;
    private int vote_count;
    private Date releaseDate;
    private boolean favorite;


    public Movie(Cursor cursor) {
        movie_id = cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry._ID));
        title = cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_TITLE));
        overview = cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_OVERVIEW));
        vote_count = cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_VOTE_COUNT));
        rating = cursor.getFloat(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_RATING));
        path_poster = cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_PATH_POSTER));
        backdrop_path = cursor.getString(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_PATH_BACKDROP));
        int julianDay = cursor.getInt(cursor.getColumnIndex(PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE));
        releaseDate = new Date(julianDay);
        favorite = true;
    }

    public Movie() {
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public long getMovieId() {
        return movie_id;
    }

    public void setMovieId(long id) {
        this.movie_id = id;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getPath_poster() {
        return path_poster;
    }

    public void setPath_poster(String path_poster) {
        this.path_poster = path_poster;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ContentValues getContentValues() {
        Time dayTime = new Time();
        dayTime.set(releaseDate.getTime());
        int julianDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        ContentValues values = new ContentValues();
        values.put(PopularMovieContract.MovieEntry._ID, movie_id);
        values.put(PopularMovieContract.MovieEntry.COLUMN_TITLE, title);
        values.put(PopularMovieContract.MovieEntry.COLUMN_PATH_BACKDROP, backdrop_path);
        values.put(PopularMovieContract.MovieEntry.COLUMN_PATH_POSTER, path_poster);
        values.put(PopularMovieContract.MovieEntry.COLUMN_RATING, rating);
        values.put(PopularMovieContract.MovieEntry.COLUMN_VOTE_COUNT, vote_count);
        values.put(PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE, julianDay);
        values.put(PopularMovieContract.MovieEntry.COLUMN_OVERVIEW, overview);

        return values;
    }
}
