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

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    private List<MovieEntity> movieEntityList;
    private Context context;
    final private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void onMovieItemClick(int clickedItemIndex);
    }

    public MovieAdapter(Context context, ItemClickListener listener) {
        this.context = context;
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(
                parent.getContext()).inflate(R.layout.movie_list_grid,
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

    public List<MovieEntity> getMovieEntityList() {
        return movieEntityList;
    }

    public void clear() {
        if (movieEntityList != null) {
            movieEntityList.clear();
            notifyDataSetChanged();
        }
    }

    public void setMovieList(List<MovieEntity> movieEntityList) {
        this.movieEntityList = movieEntityList;
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

        void bindMovie(MovieEntity movie) {
            String moviePosterUrl = context.getResources().getString(R.string.TMDB_BASE_IMG_URL)
                    + movie.getPosterPath();

            Log.i(LOG_TAG, "fetching poster from: " + moviePosterUrl);

            Picasso.Builder builder = new Picasso.Builder(context);

            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(moviePosterUrl)
                    .placeholder((R.drawable.gradient_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(coverImage);

            // TODO: Why doesn't glide display properly?
//            GlideApp.with(context)
//                    .load(moviePosterUrl)
//                    .placeholder((R.drawable.gradient_background))
//                    .error(R.drawable.ic_launcher_background)
//                    .skipMemoryCache(true)
//                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                    .into(coverImage);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mItemClickListener.onMovieItemClick(clickedPosition);
        }
    }
}
