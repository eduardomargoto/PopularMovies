package br.com.etm.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.etm.popularmovies.domains.Movie;
import br.com.etm.popularmovies.utils.TheMoviesDBAPI;
import br.com.etm.popularmovies.utils.Utils;

/**
 * Created by EDUARDO_MARGOTO on 11/29/2016.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private ArrayList<Movie> movies;
    private MainActivity activity;

    public MovieAdapter(Activity activity, ArrayList<Movie> movies) {
        this.movies = movies;
        this.activity = (MainActivity) activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.movie, parent, false);
        MyViewHolder mvh = new MyViewHolder(view);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Movie movie = movies.get(position);
        Bitmap bitmap = null;
        try {
            bitmap = new TheMoviesDBAPI().getBitmap(movie.getPath_poster());
        } catch (InterruptedException e) {
            e.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.message_error), Toast.LENGTH_SHORT).show();
        } catch (ExecutionException e) {
            e.printStackTrace();
            Toast.makeText(activity, activity.getString(R.string.message_error), Toast.LENGTH_SHORT).show();
        }


        holder.image_banner.setImageBitmap(bitmap);
        // set a height image with a screen density
        holder.card_view.getLayoutParams().height = Utils.getHeightDensity(activity) - 60;

        // method click, opening a DetailMovieActivity
        holder.image_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, DetailMovieActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, movies.get(position));
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }


    public void clear() {
        // clear a list of movies
        movies.clear();
        notifyDataSetChanged();
    }

    // adding elements in list.
    public void add(Movie m) {
        if (m != null) {
            movies.add(m);
            notifyItemInserted(movies.size() - 1);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_banner;
        FrameLayout card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            image_banner = (ImageView) itemView.findViewById(R.id.poster_movie);
            card_view = (FrameLayout) itemView.findViewById(R.id.card_view);
        }

    }
}
