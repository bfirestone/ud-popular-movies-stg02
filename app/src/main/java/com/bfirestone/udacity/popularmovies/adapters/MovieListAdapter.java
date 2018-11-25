package com.bfirestone.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bfirestone.udacity.popularmovies.R;
import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    private ArrayList<MovieEntity> movieEntityList = new ArrayList<>();
    private Context context;
    final private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void onMovieItemClick(int clickedItemIndex);
    }

    public MovieListAdapter(Context context, ItemClickListener listener) {
        this.context = context;
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.layout_movie_list,
                parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bindMovie(movieEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        if (movieEntityList == null) {
            return 0;
        }
        return movieEntityList.size();
    }

    public ArrayList<MovieEntity> getMovieEntityList() {
        return movieEntityList;
    }

    public void clear() {
        if (movieEntityList != null) {
            movieEntityList.clear();
            notifyDataSetChanged();
        }
    }

    public void setMovieList(List<MovieEntity> movieEntityList) {
        this.movieEntityList.addAll(movieEntityList);
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Context mContext;
        ImageView coverImage;

        MovieViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
            coverImage = itemView.findViewById(R.id.iv_movie_poster_image);
            itemView.setOnClickListener(this);
        }

        void bindMovie(MovieEntity movieEntity) {
            String moviePosterUrl = context.getResources().getString(R.string.TMDB_BASE_IMG_URL)
                    + movieEntity.getPosterPath();

            Log.i(LOG_TAG, "fetching poster for move=[" + movieEntity.getTitle()
                    + "] from=" + moviePosterUrl);

            Picasso.Builder builder = new Picasso.Builder(context);

            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(moviePosterUrl)
                    .placeholder((R.drawable.gradient_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(coverImage);

//            GlideApp.with(context)
//                    .load(moviePosterUrl)
//                    .skipMemoryCache(true)
//                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    .placeholder((R.drawable.gradient_background))
//                    .error(R.drawable.ic_launcher_background)
//                    .into(coverImage);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mItemClickListener.onMovieItemClick(clickedPosition);
        }
    }
}
