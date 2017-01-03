package br.com.etm.popularmovies;

import android.content.Intent;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import br.com.etm.popularmovies.data.PopularMovieContract;
import br.com.etm.popularmovies.domains.Movie;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_ARG_MOVIE = "MARG";
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    private String mCriteriaOrder;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCriteriaOrder = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.key_pref_order), getString(R.string.value_default_pref_order));

        if (findViewById(R.id.detail_movie_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                Bundle arguments = new Bundle();
                FragmentMovie fm = (FragmentMovie) getSupportFragmentManager().findFragmentById(R.id.fragment_movie);
                if (fm.getMovieAdapter().getFirstMovie() != null) {
                    arguments.putSerializable(KEY_ARG_MOVIE, fm.getMovieAdapter().getFirstMovie());

                    FragmentDetailMovie fdm = new FragmentDetailMovie();
                    fdm.setArguments(arguments);
                    getSupportFragmentManager().beginTransaction().add(R.id.detail_movie_container, fdm, KEY_ARG_MOVIE)
                            .commit();
                }
            }
        } else {
            mTwoPane = false;
        }
    }

    public void onMovieChanged(Movie movie) {
        if (movie != null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(KEY_ARG_MOVIE, movie);

            FragmentDetailMovie fdm = new FragmentDetailMovie();
            fdm.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_movie_container, fdm, KEY_ARG_MOVIE)
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_movie_container, new FragmentDetailMovie(), KEY_ARG_MOVIE)
                    .commit();
        }
    }

    public boolean isTwoPane() {
        return mTwoPane;
    }

    public String getCriteriaOrder() {
        return mCriteriaOrder;
    }


    @Override
    protected void onResume() {
        super.onResume();

        String criteria = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.key_pref_order), getString(R.string.value_default_pref_order));
        if (!mCriteriaOrder.equals(criteria)) {
            FragmentMovie fm = (FragmentMovie) getSupportFragmentManager().findFragmentById(R.id.fragment_movie);
            if (fm != null) {
                if (criteria.equals(getString(R.string.value_favorites_pref_order))) {
                    Cursor cursor = this.getContentResolver().query(PopularMovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
                    fm.updateMoviesTask(cursor);
                } else fm.updateMoviesTask(null);

                if (mTwoPane)
                    onMovieChanged(fm.getMovieAdapter().getFirstMovie());
            }
        }

        mCriteriaOrder = criteria;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_settings:
                // Do a intent for activity settings
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
