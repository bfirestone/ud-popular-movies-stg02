
package com.bfirestone.udacity.popularmovies.api.model;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("unused")
public class ReviewList implements Parcelable {

    @Json(name = "page")
    private int page;
    @Json(name = "results")
    private List<Review> reviews = new ArrayList<>();
    @Json(name = "total_pages")
    private int totalPages;
    @Json(name = "total_results")
    private int totalResults;
    public final static Creator<ReviewList> CREATOR = new Creator<ReviewList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ReviewList createFromParcel(Parcel in) {
            return new ReviewList(in);
        }

        public ReviewList[] newArray(int size) {
            return (new ReviewList[size]);
        }

    };

    private ReviewList(Parcel in) {
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.reviews, (Review.class.getClassLoader()));
        this.totalPages = ((int) in.readValue((int.class.getClassLoader())));
        this.totalResults = ((int) in.readValue((int.class.getClassLoader())));
    }

    public ReviewList() {
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ReviewList withPage(int page) {
        this.page = page;
        return this;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public ReviewList withResults(List<Review> results) {
        this.reviews = results;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ReviewList withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ReviewList withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("page", page)
                .append("reviews", reviews)
                .append("totalPages", totalPages)
                .append("totalResults", totalResults).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(page);
        dest.writeList(reviews);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents() {
        return 0;
    }

}
