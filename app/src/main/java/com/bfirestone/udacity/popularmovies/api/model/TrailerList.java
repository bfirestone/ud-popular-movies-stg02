
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
public class TrailerList implements Parcelable {

    @Json(name = "results")
    private List<Trailer> trailers = new ArrayList<>();
    public final static Creator<TrailerList> CREATOR = new Creator<TrailerList>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TrailerList createFromParcel(Parcel in) {
            return new TrailerList(in);
        }

        public TrailerList[] newArray(int size) {
            return (new TrailerList[size]);
        }

    };

    private TrailerList(Parcel in) {
        in.readList(this.trailers, (Trailer.class.getClassLoader()));
    }

    public TrailerList() {
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public TrailerList withResults(List<Trailer> trailers) {
        this.trailers = trailers;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("trailers", trailers).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(trailers);
    }

    public int describeContents() {
        return 0;
    }

}
