package br.com.etm.popularmovies.utils;


/**
 * Created by EDUARDO_MARGOTO on 11/29/2016.
 */

public abstract class TheMoviesDBAPI {

    public static final String URL_DEFAULT = "https://api.themoviedb.org/3";
    public static final String MOVIES_POPULAR = "/movie/popular";
    public static final String MOVIES_TOP_RATED = "/movie/top_rated";
    public static final String PARAM_KEY_API = "api_key";
    public static final String URL_IMAGE = "https://image.tmdb.org/t/p/w500";
//    private static final String LOG_TAG = TheMoviesDBAPI.class.getSimpleName();
//
//    public ArrayList<Movie> getMovies(Context context) {
//        try {
//            // checking the preferences of user, for get movies
//            if (PreferenceManager.getDefaultSharedPreferences(context).getString(
//                    context.getString(R.string.key_pref_order),
//                    context.getString(R.string.value_default_pref_order)).equals(context.getString(R.string.value_default_pref_order))) {
//                String MOVIES_POPULAR = "/movie/popular";
//                return new DownloadDataMovies().execute(MOVIES_POPULAR).get();
//            } else {
//                String MOVIES_TOP_RATED = "/movie/top_rated";
//                return new DownloadDataMovies().execute(MOVIES_TOP_RATED).get();
//            }
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public ArrayList<Trailer> getTrailers(int movie_id) {
//        try {
//            // checking the preferences of user, for get movies
//            return new DownloadDataTrailers().execute(movie_id).get();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public ArrayList<Review> getReviews(int movie_id) {
//        try {
//            // checking the preferences of user, for get movies
//            return new DownloadDataReviews().execute(movie_id).get();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    /**
//     * Method for do download image, returning a bitmap.
//     *
//     * @param path
//     * @return Bitmap
//     */
//    public Bitmap getBitmap(String path) throws InterruptedException, ExecutionException {
//        return new DownloadImage().execute(path).get();
//    }
//
//
//    /**
//     * AsyncTask, for do download all movies, according to path
//     * Returning a ArrayList of Movies
//     */
//    class DownloadDataMovies extends AsyncTask<String, Void, ArrayList<Movie>> {
//
//        final String LOG_TAG = DownloadDataMovies.class.getSimpleName();
//
//        @Override
//        protected ArrayList<Movie> doInBackground(String... params) {
//            // These two need to be declared outside the try/catch
//            // so that they can be closed in the finally block.
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//
//            // Will contain the raw JSON response as a string.
//            String resultJsonStr;
//            try {
//                String strURL = URL_DEFAULT + params[0] + "?" + PARAM_KEY_API + "=" + BuildConfig.THE_MOVIEDB_API_KEY;
//                URL url = new URL(strURL);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line).append("\n");
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    return null;
//                }
//                resultJsonStr = buffer.toString();
////                Log.v(LOG_TAG, "resultJsonStr:" + resultJsonStr);
//                return parseJsonMovies(resultJsonStr);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            return null;
//        }
//
//
//        private ArrayList<Movie> parseJsonMovies(String json) throws JSONException, ParseException {
//            final String MOVIE_RESULTS = "results";
//
//            final String MOVIE_ID = "id";
//            final String MOVIE_TITLE = "original_title";
//            final String MOVIE_POSTER_PATH = "poster_path";
//            final String MOVIE_BACKDROP_PATH = "backdrop_path";
//            final String MOVIE_OVERVIEW = "overview";
//            final String MOVIE_RATING = "vote_average";
//            final String MOVIE_VOTE_COUNT = "vote_count";
//            final String MOVIE_RELEASEDATE = "release_date";
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//
//            ArrayList<Movie> movies = new ArrayList<>();
//
//            JSONObject jsonData = new JSONObject(json);
//            JSONArray results = jsonData.getJSONArray(MOVIE_RESULTS);
//
//            for (int i = 0; i < results.length(); i++) {
//                Movie movie = new Movie();
//                JSONObject jsonMovie = results.getJSONObject(i);
//
//                movie.setMovieId(jsonMovie.getLong(MOVIE_ID));
//                movie.setTitle(jsonMovie.getString(MOVIE_TITLE));
//                movie.setOverview(jsonMovie.getString(MOVIE_OVERVIEW));
//                movie.setRating( (float) jsonMovie.getDouble(MOVIE_RATING));
//                movie.setVote_count(jsonMovie.getInt(MOVIE_VOTE_COUNT));
//                movie.setPath_poster(jsonMovie.getString(MOVIE_POSTER_PATH));
//                movie.setBackdrop_path(jsonMovie.getString(MOVIE_BACKDROP_PATH));
//                movie.setReleaseDate(sdf.parse(jsonMovie.getString(MOVIE_RELEASEDATE)));
//
//                movies.add(movie);
//            }
//
//            return movies;
//        }
//
//
//    }
//
//
//    class DownloadDataTrailers extends AsyncTask<Integer, Void, ArrayList<Trailer>> {
//
//        @Override
//        protected ArrayList<Trailer> doInBackground(Integer... params) {
//            // These two need to be declared outside the try/catch
//            // so that they can be closed in the finally block.
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            int movie_id = (Integer) params[0];
//
//            // Will contain the raw JSON response as a string.
//            String resultJsonStr;
//            try {
//                String TRAILERS_MOVIE = "/movie/{movie_id}/videos";
//                String strURL = URL_DEFAULT + TRAILERS_MOVIE.replace("{movie_id}", movie_id + "") + "?" + PARAM_KEY_API + "=" + BuildConfig.THE_MOVIEDB_API_KEY;
//                URL url = new URL(strURL);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line).append("\n");
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    return null;
//                }
//                resultJsonStr = buffer.toString();
////                Log.v(LOG_TAG, "resultJsonStr:" + resultJsonStr);
//                return parseJsonTrailers(resultJsonStr);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            return null;
//        }
//
//        private ArrayList<Trailer> parseJsonTrailers(String json) throws JSONException, ParseException {
//            final String TRAILERS_RESULTS = "results";
//            final String TRAILER_NAME = "name";
//            final String TRAILER_SITE = "site";
//            final String TRAILER_KEY = "key";
//
//            ArrayList<Trailer> trailers = new ArrayList<>();
//
//            JSONObject jsonData = new JSONObject(json);
//            JSONArray results = jsonData.getJSONArray(TRAILERS_RESULTS);
//
//            for (int i = 0; i < results.length(); i++) {
//                Trailer trailer = new Trailer();
//                JSONObject jsonTrailer = results.getJSONObject(i);
//
//                trailer.setName(jsonTrailer.getString(TRAILER_NAME));
//                trailer.setSite(jsonTrailer.getString(TRAILER_SITE));
//                trailer.setKey(jsonTrailer.getString(TRAILER_KEY));
//
//                trailers.add(trailer);
//            }
//
//            return trailers;
//        }
//    }
//
//
//    class DownloadDataReviews extends AsyncTask<Integer, Void, ArrayList<Review>> {
//
//        @Override
//        protected ArrayList<Review> doInBackground(Integer... params) {
//            // These two need to be declared outside the try/catch
//            // so that they can be closed in the finally block.
//            HttpURLConnection urlConnection = null;
//            BufferedReader reader = null;
//            int movie_id = (Integer) params[0];
//
//            // Will contain the raw JSON response as a string.
//            String resultJsonStr ;
//            try {
//                String REVIEWS_MOVIE = "/movie/{movie_id}/reviews";
//                String strURL = URL_DEFAULT + REVIEWS_MOVIE.replace("{movie_id}", movie_id + "") + "?" + PARAM_KEY_API + "=" + BuildConfig.THE_MOVIEDB_API_KEY + "&page=1";
//                URL url = new URL(strURL);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                // Read the input stream into a String
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    buffer.append(line).append("\n");
//                }
//
//                if (buffer.length() == 0) {
//                    // Stream was empty.  No point in parsing.
//                    return null;
//                }
//                resultJsonStr = buffer.toString();
////                Log.v(LOG_TAG, "resultJsonStr:" + resultJsonStr);
//                return parseJsonReviews(resultJsonStr);
//            } catch (IOException e) {
//                e.printStackTrace();
//                return null;
//            } catch (JSONException e) {
//                e.printStackTrace();
//            } catch (ParseException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//
//            return null;
//        }
//
//        private ArrayList<Review> parseJsonReviews(String json) throws JSONException, ParseException {
//            final String REVIEWS_RESULTS = "results";
//            final String REVIEW_AUTHOR = "author";
//            final String REVIEW_CONTENT = "content";
//
//
//            ArrayList<Review> reviews = new ArrayList<>();
//
//            JSONObject jsonData = new JSONObject(json);
//            JSONArray results = jsonData.getJSONArray(REVIEWS_RESULTS);
//
//            for (int i = 0; i < results.length(); i++) {
//                Review review = new Review();
//                JSONObject jsonReview = results.getJSONObject(i);
//
//                review.setAuthor(jsonReview.getString(REVIEW_AUTHOR));
//                review.setContent(jsonReview.getString(REVIEW_CONTENT));
//
//                reviews.add(review);
//            }
//
//            return reviews;
//        }
//    }
//
//    /**
//     * AsyncTask, for do download images.
//     * Returning a bitmap
//     */
//    class DownloadImage extends AsyncTask<String, Void, Bitmap> {
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            HttpURLConnection urlConnection = null;
//            try {
//                String URL_IMAGE = "https://image.tmdb.org/t/p/w500";
//                String strURL = URL_IMAGE + params[0];
//                URL url = new URL(strURL);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                urlConnection.setRequestMethod("GET");
//                urlConnection.connect();
//
//                InputStream inputStream = urlConnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//                if (inputStream == null) {
//                    // Nothing to do.
//                    return null;
//                }
//
//                // making a bitmap
//                return BitmapFactory.decodeStream(inputStream);
//            } catch (IOException e) {
//                return null;
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//            }
//        }
//    }


}
