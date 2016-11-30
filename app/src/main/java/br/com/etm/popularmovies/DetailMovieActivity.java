package br.com.etm.popularmovies;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import br.com.etm.popularmovies.domains.Movie;
import br.com.etm.popularmovies.utils.TheMoviesDBAPI;
import br.com.etm.popularmovies.utils.Utils;

public class DetailMovieActivity extends AppCompatActivity {

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            movie = (Movie) intent.getSerializableExtra(Intent.EXTRA_TEXT);
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if(movie != null){
                getSupportActionBar().setTitle(movie.getTitle());
            }
        }

        bindViews();
    }



    private void bindViews() {
        ImageView iv_backdrop = ((ImageView) findViewById(R.id.iv_backdrop));
        try {
            iv_backdrop.setImageBitmap(
                    new TheMoviesDBAPI().getBitmap(movie.getBackdrop_path())
            );
        } catch (InterruptedException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        iv_backdrop.getLayoutParams().height = Utils.getHeightDensity(this);
        iv_backdrop.getLayoutParams().width = getResources().getDisplayMetrics().widthPixels;
        // Darkening the image, to make the text visible on top of it
        iv_backdrop.setColorFilter(Color.rgb(123, 123, 123), PorterDuff.Mode.MULTIPLY);

        ImageView iv_poster = ((ImageView) findViewById(R.id.iv_poster));
        try {
            iv_poster.setImageBitmap(
                    new TheMoviesDBAPI().getBitmap(movie.getPath_poster())
            );
        } catch (InterruptedException e) {
            Toast.makeText(this, getString(R.string.message_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ExecutionException e) {
            Toast.makeText(this, getString(R.string.message_error), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        iv_poster.getLayoutParams().height = Utils.getHeightDensity(this) - 60;
        // width proportional to 3 elements.
        iv_poster.getLayoutParams().width = Utils.getWidthDensity(this) / 3;

        ((TextView) findViewById(R.id.tv_overview)).setText(movie.getOverview());
        ((TextView) findViewById(R.id.tv_release_date)).setText(
                DateFormat.getDateInstance(DateFormat.YEAR_FIELD, Locale.getDefault()).format(movie.getReleaseDate())
        );
        ((TextView) findViewById(R.id.tv_rating)).setText(movie.getRating() + "/10 (" + movie.getVote_count() + ")");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
