package br.com.etm.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EDUARDO_MARGOTO on 12/28/2016.
 */

public class PopularMovieDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "popularmovie.db";

    public PopularMovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = getSQLCreateMovieTable();
        db.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    private String getSQLCreateMovieTable() {
        return "CREATE TABLE " + PopularMovieContract.MovieEntry.TABLE_NAME + " (" +

                PopularMovieContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +

                PopularMovieContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                PopularMovieContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                PopularMovieContract.MovieEntry.COLUMN_PATH_BACKDROP + " TEXT NOT NULL, " +
                PopularMovieContract.MovieEntry.COLUMN_PATH_POSTER + " TEXT NOT NULL, " +

                PopularMovieContract.MovieEntry.COLUMN_RATING + " REAL NOT NULL, " +
                PopularMovieContract.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +

                PopularMovieContract.MovieEntry.COLUMN_RELEASE_DATE + " INTEGER NOT NULL );";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PopularMovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
