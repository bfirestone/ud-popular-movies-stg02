
package com.bfirestone.udacity.popularmovies.api.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.bfirestone.udacity.popularmovies.api.model.Trailer;
import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@SuppressWarnings("unused")
public class VideosResponse implements Parcelable {

    @Json(name = "id")
    private int id;
    @Json(name = "results")
    private List<Trailer> trailerList = null;
    public final static Creator<VideosResponse> CREATOR = new Creator<VideosResponse>() {


        @SuppressWarnings({"unchecked"})
        public VideosResponse createFromParcel(Parcel in) {
            return new VideosResponse(in);
        }

        public VideosResponse[] newArray(int size) {
            return (new VideosResponse[size]);
        }

    };

    private VideosResponse(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.trailerList, (Trailer.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public VideosResponse() {
    }

    /**
     * @param id movie id
     * @param trailerList list of trailer movies
     */
    public VideosResponse(int id, List<Trailer> trailerList) {
        super();
        this.id = id;
        this.trailerList = trailerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VideosResponse withId(int id) {
        this.id = id;
        return this;
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
    }

    public VideosResponse withTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("trailerList", trailerList).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(trailerList);
    }

    public int describeContents() {
        return 0;
    }
}
