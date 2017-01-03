package br.com.etm.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import br.com.etm.popularmovies.domains.Movie;
import br.com.etm.popularmovies.utils.TheMoviesDBAPI;

/**
 * Created by EDUARDO_MARGOTO on 11/29/2016.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    private ArrayList<Movie> mMovies;
    private MainActivity activity;

    public MovieAdapter(Activity activity, ArrayList<Movie> movies) {
        this.mMovies = movies;
        this.activity = (MainActivity) activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
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
        final int positionAdapter = holder.getAdapterPosition();
        holder.image_banner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMovie fm = (FragmentMovie) activity.getSupportFragmentManager().findFragmentById(R.id.fragment_movie);
                fm.setPosition(positionAdapter);

                if (!activity.isTwoPane()) {
                    Intent intent = new Intent(activity, DetailMovieActivity.class);
                    intent.putExtra(Intent.EXTRA_TEXT, movie);
                    activity.startActivity(intent);
                } else {
                    activity.onMovieChanged(movie);
                }
            }
        });

    }

    public ArrayList<Movie> getMovies() {
        return mMovies;
    }


    public void setMovies(ArrayList<Movie> mMovies) {
        this.mMovies = mMovies;
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public Movie getFirstMovie() {
        try {
            return mMovies.get(0);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }

    public void clear() {
        // clear a list of mMovies
        mMovies.clear();
        notifyDataSetChanged();
    }

    // adding elements in list.
    public void add(Movie m) {
        if (m != null) {
            mMovies.add(m);
            notifyItemInserted(mMovies.size() - 1);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image_banner;
//        FrameLayout card_view;

        public MyViewHolder(View itemView) {
            super(itemView);
            image_banner = (ImageView) itemView.findViewById(R.id.poster_movie);
//            card_view = (FrameLayout) itemView.findViewById(R.id.card_view);
        }

    }
}
