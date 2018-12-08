package com.bfirestone.udacity.popularmovies.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
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

//public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MovieViewHolder> {
public class MovieListAdapter extends ListAdapter<MovieEntity, MovieListAdapter.MovieViewHolder> {
    private static final String LOG_TAG = MovieListAdapter.class.getSimpleName();

    private ArrayList<MovieEntity> movieEntityList = new ArrayList<>();
    private Context context;
    final private ItemClickListener mItemClickListener;

    private static final DiffUtil.ItemCallback<MovieEntity> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<MovieEntity>() {
                @Override
                public boolean areItemsTheSame(MovieEntity oldItem, MovieEntity newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull MovieEntity oldItem,
                                                  @NonNull MovieEntity newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public MovieListAdapter(Context context, ItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.mItemClickListener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_movie_list, parent, false);
        Log.d(LOG_TAG, String.format("method=onCreateViewHolder viewType=%d", viewType));
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, final int position) {
        Log.v(LOG_TAG, String.format("method=onBindViewHolder() size=%d position=%s",
                movieEntityList.size(), position));
        holder.bindMovie(getItem(position));
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

    public void clear() {
        if (movieEntityList != null) {
            movieEntityList.clear();
            notifyDataSetChanged();
        }
    }

    public void setMovieList(List<MovieEntity> movieEntityList) {
        Log.v(LOG_TAG, String.format("method=setMovieList() list_size=%d", movieEntityList.size()));
        this.movieEntityList.addAll(movieEntityList);
        submitList(movieEntityList);
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

            Log.d(LOG_TAG, "method=bindMovie() fetching poster for move=[" + movieEntity.getTitle()
                    + "] from=" + moviePosterUrl);

            Picasso.Builder builder = new Picasso.Builder(context);

            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(moviePosterUrl)
                    .placeholder((R.drawable.gradient_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(coverImage);

            // TODO: get glide to work here
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
