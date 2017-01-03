package br.com.etm.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.etm.popularmovies.domains.Trailer;


/**
 * Created by EDUARDO_MARGOTO on 12/28/2016.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder> {

    public static final String PATH_YOUTUBE = "https://www.youtube.com/watch?v=";
    private static final String LOG_TAG = TrailerAdapter.class.getSimpleName();

    ArrayList<Trailer> mTrailers;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public TrailerAdapter(Context context, ArrayList<Trailer> trailers) {
        this.mTrailers = trailers;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public TrailerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.trailer, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final TrailerAdapter.MyViewHolder holder, final int position) {
        holder.title.setText(mTrailers.get(position).getName());

        final int positionAdapter = holder.getAdapterPosition();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + mTrailers.get(positionAdapter).getKey())));
                } catch (ActivityNotFoundException ex) {
                    mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(PATH_YOUTUBE + mTrailers.get(positionAdapter).getKey())));
                }
            }
        });
    }


    public Trailer getFirstTrailer() {
        if (mTrailers != null && !mTrailers.isEmpty())
            return mTrailers.get(0);
        else return null;

    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public void clear() {
        // clear a list of movies
        mTrailers.clear();
        notifyDataSetChanged();
    }

    // adding elements in list.
    public void add(Trailer trailer) {
        if (trailer != null) {
            mTrailers.add(trailer);
            notifyItemInserted(mTrailers.size() - 1);
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            imageView = (ImageView) itemView.findViewById(R.id.iv_video);
        }

    }

}
