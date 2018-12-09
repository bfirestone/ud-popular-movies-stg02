package com.bfirestone.udacity.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bfirestone.udacity.popularmovies.R;
import com.bfirestone.udacity.popularmovies.api.model.Trailer;
import com.bfirestone.udacity.popularmovies.glide.GlideApp;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.TrailerViewHolder> {
    private static final String LOG_TAG = MovieTrailerAdapter.class.getSimpleName();

    private Context context;
    private List<Trailer> trailers;

    public MovieTrailerAdapter(Context context) {
        this.context = context;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrailerViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        final Trailer trailer = trailers.get(position);

        Uri.Builder uriBuilder = Uri.parse(
                context.getResources().getString(R.string.YOUTUBE_BASE_IMAGE_URL)).buildUpon();

        uriBuilder.appendPath(trailer.getKey());
        uriBuilder.appendPath(context.getResources().getString(R.string.YOUTUBE_IMAGE_EXTENSION));

        Uri trailerThumbUri = uriBuilder.build();

        Log.v(LOG_TAG, "trailer_url: " + trailerThumbUri);

        if (trailer.getSite().equalsIgnoreCase("youtube")) {
            holder.trailerTitle.setText(trailer.getName());

            GlideApp.with(context)
                    .load(trailerThumbUri)
                    .placeholder((R.drawable.gradient_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.trailerThumbnail);
        }
    }


    @Override
    public int getItemCount() {
        return trailers != null ? trailers.size() : 0;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.tv_trailer_title)
        TextView trailerTitle;
        @BindView(R.id.ib_trailer_play_button)
        ImageButton trailerPlayButton;
        @BindView(R.id.iv_trailer_thumbnail)
        ImageView trailerThumbnail;
        @BindString(R.string.YOUTUBE_BASE_VIDEO_URL)
        String YoutubeBaseVideoUrl;

        TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            trailerPlayButton.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Trailer trailer = trailers.get(getAdapterPosition());
            Log.d(LOG_TAG, "playing trailer: " + trailer);

            if (trailer != null) {
                Uri.Builder uriBuilder = Uri.parse(YoutubeBaseVideoUrl).buildUpon();
                uriBuilder.appendQueryParameter("v", trailer.getKey());
                Uri trailerUri = uriBuilder.build();

                Log.v(LOG_TAG, "trailer_uri: " + trailerUri);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(trailerUri);

                if (intent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "error playing the trailer", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
