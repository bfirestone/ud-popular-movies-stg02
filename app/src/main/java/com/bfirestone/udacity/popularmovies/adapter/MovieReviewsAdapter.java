package com.bfirestone.udacity.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bfirestone.udacity.popularmovies.R;
import com.bfirestone.udacity.popularmovies.api.model.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieReviewsAdapter extends RecyclerView.Adapter<MovieReviewsAdapter.MovieReviewViewHolder> {
    private static final String LOG_TAG = MovieReviewsAdapter.class.getSimpleName();

    private Context context;
    private List<Review> reviews;

    public MovieReviewsAdapter(Context context) {
        this.context = context;
    }

    public MovieReviewsAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieReviewViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_reviews, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieReviewViewHolder holder, int position) {
        final Review review = reviews.get(position);

        holder.bindReview(review);

        holder.itemView.setOnClickListener(v -> {
            boolean expanded = review.isExpanded();
            review.setExpanded(!expanded);
            notifyItemChanged(position);
        });

    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }

    class MovieReviewViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_review_author)
        TextView author;
        @BindView(R.id.tv_review_content_body)
        TextView content;
        @BindView(R.id.review_content_layout)
        LinearLayout reviewContentLayout;

        MovieReviewViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindReview(Review review) {
            Log.i(LOG_TAG, "[bindReview] author=" + review.getAuthor() + " url=" + review.getUrl());
            reviewContentLayout.setVisibility(review.isExpanded() ? View.VISIBLE : View.GONE);
            author.setText(review.getAuthor());
            content.setText(review.getContent());
        }
    }
}
