package br.com.etm.popularmovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.etm.popularmovies.domains.Movie;
import br.com.etm.popularmovies.utils.TheMoviesDBAPI;
import br.com.etm.popularmovies.utils.Utils;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager;
        // Check a orientation
        // for create a grid with 3 or 5 columns.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            gridLayoutManager = new GridLayoutManager(this, 5);
        else
            gridLayoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(gridLayoutManager);

        // create adapter with a list of movie empty
        // Feed the list with method onStart.
        adapter = new MovieAdapter(this, new ArrayList<Movie>());

        // set adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check in internet connection, to prevent an exception
        // or can too, do a try catch, but i chose to do so.
        if (Utils.checkConn(this)) {
            findViewById(R.id.empty_view).setVisibility(View.INVISIBLE);
            updateMoviesTask();
        } else findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
    }


    /**
     * method for update recyclerView with movies.
     * Do a background UI for a internet connection.
     */
    private void updateMoviesTask() {
        adapter.clear();
        // get a movies
        ArrayList<Movie> movies = new TheMoviesDBAPI().getMovies(this);

        // add movies in recycler view
        for (Movie m : movies)
            adapter.add(m);
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
