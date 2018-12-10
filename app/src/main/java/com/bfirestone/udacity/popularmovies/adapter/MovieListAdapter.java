package com.bfirestone.udacity.popularmovies.adapter;

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
import com.bfirestone.udacity.popularmovies.glide.GlideApp;
import com.bfirestone.udacity.popularmovies.listener.ItemClickListener;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    private ArrayList<MovieEntity> movieEntityList = new ArrayList<>();
    private Context context;
    final private ItemClickListener mItemClickListener;

    public MovieListAdapter(Context context, ItemClickListener listener) {
        this.context = context;
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_movie_list, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        Log.v(LOG_TAG, String.format("method=onBindViewHolder() size=%d position=%s",
                movieEntityList.size(), position));
        holder.bindMovie(movieEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        if (movieEntityList != null) {
            return  movieEntityList.size();
        }

        return 0;
    }

    public ArrayList<MovieEntity> getMovieEntityList() {
        return movieEntityList;
    }

    public ArrayList<String> getMovieNameList() {
        if (movieEntityList == null) {
            return new ArrayList<>();
        }

        ArrayList<String> movieTitles = new ArrayList<>();

        movieEntityList.forEach(movieEntity -> movieTitles.add(movieEntity.getTitle()));

        return movieTitles;
    }

    public void addMovieList(List<MovieEntity> movieEntityList) {
        this.movieEntityList.addAll(movieEntityList);
        notifyDataSetChanged();
    }

    public void addMovie(MovieEntity movieEntity) {
        movieEntityList.add(movieEntity);
        notifyDataSetChanged();
    }

    public void setMovieList(List<MovieEntity> movieEntityList) {
        Log.v(LOG_TAG, String.format("method=setMovieList() list_size=%d",
                movieEntityList.size()));

//        StackTraceElement[] stackTrace = new Throwable().getStackTrace();
//        for (StackTraceElement stackTraceElement : stackTrace) {
//            Log.v(LOG_TAG, String.format("[stack] %s", stackTraceElement));
//        }

        this.movieEntityList = new ArrayList<>(movieEntityList);
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView coverImage;

        MovieViewHolder(View itemView) {
            super(itemView);

            coverImage = itemView.findViewById(R.id.iv_movie_poster_image);
            itemView.setOnClickListener(this);
        }

        void bindMovie(MovieEntity movieEntity) {
            String moviePosterUrl = context.getResources().getString(R.string.TMDB_BASE_IMG_URL)
                    + movieEntity.getPosterPath();

            Log.d(LOG_TAG, String.format("method=bindMovie() fetching poster for move=[%s] from=%s",
                    movieEntity.getTitle(), moviePosterUrl));

            GlideApp.with(context)
                    .load(moviePosterUrl)
                    .apply(new RequestOptions()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(Target.SIZE_ORIGINAL))
                    .placeholder(R.drawable.gradient_background)
                    .error(R.drawable.ic_launcher_background)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(coverImage);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mItemClickListener.onMovieItemClick(clickedPosition);
        }
    }
}
