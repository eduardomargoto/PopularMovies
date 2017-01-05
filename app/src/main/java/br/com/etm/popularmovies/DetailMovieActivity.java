package br.com.etm.popularmovies;


import android.content.Intent;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import br.com.etm.popularmovies.domains.Movie;
import br.com.etm.popularmovies.utils.Utils;

import static br.com.etm.popularmovies.MainActivity.KEY_ARG_MOVIE;
public class DetailMovieActivity extends AppCompatActivity {


    FragmentDetailMovie fragmentDetailMovie;
    private static final String LOG_TAG = DetailMovieActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Intent intent = getIntent();
        Movie movie;
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            movie = (Movie) intent.getSerializableExtra(Intent.EXTRA_TEXT);

            Bundle arguments = new Bundle();
            arguments.putSerializable(KEY_ARG_MOVIE, movie);

            fragmentDetailMovie = new FragmentDetailMovie();
            fragmentDetailMovie.setArguments(arguments);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.detail_movie_container, fragmentDetailMovie)
                        .commit();
            }

        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        if (Utils.checkConnection(this) && fragmentDetailMovie != null) {
            fragmentDetailMovie.updateTrailers();
            fragmentDetailMovie.updateReviews();
        }
    }

}
