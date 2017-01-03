package br.com.etm.popularmovies;

import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.etm.popularmovies.data.PopularMovieContract;
import br.com.etm.popularmovies.domains.Movie;
import br.com.etm.popularmovies.utils.TheMoviesDBAPI;
import br.com.etm.popularmovies.utils.Utils;

import static android.view.View.GONE;

/**
 * Created by EDUARDO_MARGOTO on 12/31/2016.
 */

public class FragmentMovie extends Fragment {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private MovieAdapter mMovieAdapter;
    private int mPosition;
    public static final String SELECTED_KEY = "selected_position";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.v(LOG_TAG, "onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_movie, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager;
        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(gridLayoutManager);

        // create adapter with a list of movie empty
        // Feed the list with method onStart.
        mMovieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        // set adapter
        recyclerView.setAdapter(mMovieAdapter);


        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
            recyclerView.scrollToPosition(mPosition);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.key_pref_order), getString(R.string.value_default_pref_order))
                .equals(getString(R.string.value_favorites_pref_order))) {
            Cursor cursor = getContext().getContentResolver().query(PopularMovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
            updateMoviesTask(cursor);
        } else {
            updateMoviesTask(null);
        }

        super.onViewCreated(view, savedInstanceState);

    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int mPosition) {
        this.mPosition = mPosition;
    }

    public MovieAdapter getMovieAdapter() {
        return mMovieAdapter;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    /**
     * method for update recyclerView with movies.
     * Do a background UI for a internet connection.
     */
    public void updateMoviesTask(Cursor cursor) {
        ArrayList<Movie> movies = new ArrayList<>();
        mMovieAdapter.clear();
        if (cursor == null) {
            // get a movies
            if (Utils.checkConn(getContext()) && getView().findViewById(R.id.empty_view) != null) {
                getView().findViewById(R.id.empty_view).setVisibility(GONE);
                movies = new TheMoviesDBAPI().getMovies(getContext());
            } else if (getView().findViewById(R.id.empty_view) != null) {
                getView().findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            }
        } else {
            while (cursor.moveToNext()) {
                movies.add(new Movie(cursor));
            }
            if (!movies.isEmpty() && getView().findViewById(R.id.empty_view) != null)
                getView().findViewById(R.id.empty_view).setVisibility(GONE);
            else if (getView().findViewById(R.id.empty_view) != null) {
                TextView textView = (TextView) getView().findViewById(R.id.empty_view);
                textView.setText(getString(R.string.no_movie_favorite));
                textView.setVisibility(View.VISIBLE);
            }
        }
        // add movies in recycler view
        for (Movie m : movies)
            mMovieAdapter.add(m);
    }

}
