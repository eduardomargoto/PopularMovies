package br.com.etm.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;


/**
 * Created by EDUARDO_MARGOTO on 12/28/2016.
 */

public class PopularMovieContract {

    public static final String PATH_MOVIE = "movie";
    public static final String CONTENT_AUTHORITY = "br.com.etm.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_PATH_POSTER = "path_poster";
        public static final String COLUMN_PATH_BACKDROP = "path_backdrop";
        public static final String COLUMN_RELEASE_DATE = "release_date";
//        public static final String COLUMN_FAVORITE = "favorite";

        public static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
