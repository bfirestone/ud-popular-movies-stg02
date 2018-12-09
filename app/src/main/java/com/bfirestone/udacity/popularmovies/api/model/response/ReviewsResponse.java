
package com.bfirestone.udacity.popularmovies.api.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.bfirestone.udacity.popularmovies.api.model.Review;
import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@SuppressWarnings("unused")
public class ReviewsResponse implements Parcelable
{

    @Json(name = "id")
    private int id;
    @Json(name = "page")
    private int page;
    @Json(name = "results")
    private List<Review> reviewList = null;
    @Json(name = "total_pages")
    private int totalPages;
    @Json(name = "total_results")
    private int totalResults;
    public final static Creator<ReviewsResponse> CREATOR = new Creator<ReviewsResponse>() {

        @SuppressWarnings({"unchecked"})
        public ReviewsResponse createFromParcel(Parcel in) {
            return new ReviewsResponse(in);
        }

        public ReviewsResponse[] newArray(int size) {
            return (new ReviewsResponse[size]);
        }

    }
    ;

    private ReviewsResponse(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.page = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.reviewList, (Review.class.getClassLoader()));
        this.totalPages = ((int) in.readValue((int.class.getClassLoader())));
        this.totalResults = ((int) in.readValue((int.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ReviewsResponse() {
    }

    /**
     * 
     * @param id review id
     * @param reviewList list of reviews
     * @param totalResults number of reviews returned
     * @param page page number
     * @param totalPages total pages
     */
    public ReviewsResponse(int id, int page, List<Review> reviewList, int totalPages, int totalResults) {
        super();
        this.id = id;
        this.page = page;
        this.reviewList = reviewList;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ReviewsResponse withId(int id) {
        this.id = id;
        return this;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ReviewsResponse withPage(int page) {
        this.page = page;
        return this;
    }

    public List<Review> getReviewList() {
        return reviewList;
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    public ReviewsResponse withReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public ReviewsResponse withTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public ReviewsResponse withTotalResults(int totalResults) {
        this.totalResults = totalResults;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("page", page)
                .append("reviewList", reviewList)
                .append("totalPages", totalPages)
                .append("totalResults", totalResults).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(page);
        dest.writeList(reviewList);
        dest.writeValue(totalPages);
        dest.writeValue(totalResults);
    }

    public int describeContents() {
        return  0;
    }

}
