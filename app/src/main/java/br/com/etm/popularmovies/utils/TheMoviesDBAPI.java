package br.com.etm.popularmovies.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import br.com.etm.popularmovies.R;
import br.com.etm.popularmovies.domains.Movie;

/**
 * Created by EDUARDO_MARGOTO on 11/29/2016.
 */

public class TheMoviesDBAPI {
    private final String KEY_API = "f9f7abfc7b11bc6efd77113ba22b8196";

    private final String URL_DEFAULT = "https://api.themoviedb.org/3";
    private final String URL_IMAGE = "https://image.tmdb.org/t/p/w500";

    private final String MOVIES_UPCOMING = "/movie/upcoming";
    private final String MOVIES_TOP_RATED = "/movie/top_rated";

    private final String PARAM_KEY_API = "api_key";


    public ArrayList<Movie> getMovies(Context context) {
        try {
            // checking the preferences of user, for get movies
            if (PreferenceManager.getDefaultSharedPreferences(context).getString(
                    context.getString(R.string.key_pref_order),
                    context.getString(R.string.value_default_pref_order)).equals(context.getString(R.string.value_default_pref_order))) {
                return new DownloadDataMovies().execute(MOVIES_UPCOMING).get();
            } else {
                return new DownloadDataMovies().execute(MOVIES_TOP_RATED).get();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Method for do download image, returning a bitmap.
     *
     * @param path
     * @return Bitmap
     */
    public Bitmap getBitmap(String path) throws InterruptedException, ExecutionException {
        return new DownloadImage().execute(path).get();
    }


    /**
     * AsyncTask, for do download all movies, according to path
     * Returning a ArrayList of Movies
     *
     */
    class DownloadDataMovies extends AsyncTask<String, Void, ArrayList<Movie>> {

        final String LOG_TAG = DownloadDataMovies.class.getSimpleName();

        @Override
        protected ArrayList<Movie> doInBackground(String... params) {
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String resultJsonStr = null;
            try {
                String strURL = URL_DEFAULT + params[0] + "?" + PARAM_KEY_API + "=" + KEY_API;
                URL url = new URL(strURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                resultJsonStr = buffer.toString();
//                Log.v(LOG_TAG, "resultJsonStr:" + resultJsonStr);
                return parseJsonMovies(resultJsonStr);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }


        private ArrayList<Movie> parseJsonMovies(String json) throws JSONException, ParseException {
            final String MOVIE_RESULTS = "results";
            final String MOVIE_TITLE = "original_title";
            final String MOVIE_POSTER_PATH = "poster_path";
            final String MOVIE_BACKDROP_PATH = "backdrop_path";
            final String MOVIE_OVERVIEW = "overview";
            final String MOVIE_RATING = "vote_average";
            final String MOVIE_VOTE_COUNT = "vote_count";
            final String MOVIE_RELEASEDATE = "release_date";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            ArrayList<Movie> movies = new ArrayList<>();

            JSONObject jsonData = new JSONObject(json);
            JSONArray results = jsonData.getJSONArray(MOVIE_RESULTS);

            for (int i = 0; i < results.length(); i++) {
                Movie movie = new Movie();
                JSONObject jsonMovie = results.getJSONObject(i);

                movie.setTitle(jsonMovie.getString(MOVIE_TITLE));
                movie.setOverview(jsonMovie.getString(MOVIE_OVERVIEW));
                movie.setRating(jsonMovie.getString(MOVIE_RATING));
                movie.setVote_count(jsonMovie.getString(MOVIE_VOTE_COUNT));
                movie.setPath_poster(jsonMovie.getString(MOVIE_POSTER_PATH));
                movie.setBackdrop_path(jsonMovie.getString(MOVIE_BACKDROP_PATH));
                movie.setReleaseDate(sdf.parse(jsonMovie.getString(MOVIE_RELEASEDATE)));

                movies.add(movie);
            }

            return movies;
        }
    }


    /**
     *  AsyncTask, for do download images.
     *  Returning a bitmap
     */
    class DownloadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            try {
                String strURL = URL_IMAGE + params[0];
                URL url = new URL(strURL);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }

                // making a bitmap
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }


}
