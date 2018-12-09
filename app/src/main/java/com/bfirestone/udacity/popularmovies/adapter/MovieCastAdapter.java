package com.bfirestone.udacity.popularmovies.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bfirestone.udacity.popularmovies.R;
import com.bfirestone.udacity.popularmovies.api.model.Cast;
import com.bfirestone.udacity.popularmovies.glide.GlideApp;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieCastAdapter extends RecyclerView.Adapter<MovieCastAdapter.CreditsViewHolder> {
    private static final String LOG_TAG = MovieCastAdapter.class.getSimpleName();

    private Context context;
    private List<Cast> castList;

    public MovieCastAdapter(Context context) {
        this.context = context;
    }

    public void setCast(List<Cast> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CreditsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CreditsViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_cast, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CreditsViewHolder holder, int position) {
        final Cast cast = castList.get(position);
        holder.bindCast(cast);
    }

    @Override
    public int getItemCount() {
        return castList != null ? castList.size() : 0;
    }

    class CreditsViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_cast_thumbnail)
        ImageView imageView;
        @BindView(R.id.tv_cast_full_name)
        TextView castFullName;
        @BindString(R.string.TMDB_BASE_IMG_URL)
        String movieDbBaseImageUrl;

        CreditsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindCast(Cast cast) {
            if (cast.getCharacter() != null) {
                StringBuilder castName = new StringBuilder().append(cast.getName()).append(" (").append(cast.getCharacter()).append(")");
                castFullName.setText(castName);
            } else {
                castFullName.setText(cast.getName());
            }

            Uri.Builder uriBuilder = Uri.parse(movieDbBaseImageUrl).buildUpon();
            uriBuilder.appendEncodedPath(cast.getProfilePath());
            Uri castThumbUri = uriBuilder.build();
            Log.d(LOG_TAG, "cast_url: " + castThumbUri);

            GlideApp.with(context)
                    .load(castThumbUri)
                    .placeholder((R.drawable.gradient_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(imageView);
        }
    }
}
