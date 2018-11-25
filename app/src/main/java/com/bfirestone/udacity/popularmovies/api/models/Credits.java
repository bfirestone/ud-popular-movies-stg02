
package com.bfirestone.udacity.popularmovies.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Credits implements Parcelable
{

    @Json(name = "cast")
    private List<Cast> cast = new ArrayList<>();
    @Json(name = "crew")
    private List<Crew> crew = new ArrayList<>();
    public final static Creator<Credits> CREATOR = new Creator<Credits>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Credits createFromParcel(Parcel in) {
            return new Credits(in);
        }

        public Credits[] newArray(int size) {
            return (new Credits[size]);
        }

    }
    ;

    private Credits(Parcel in) {
        in.readList(this.cast, (Cast.class.getClassLoader()));
        in.readList(this.crew, (com.bfirestone.udacity.popularmovies.api.models.Crew.class.getClassLoader()));
    }

    public Credits() {
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public Credits withCast(List<Cast> cast) {
        this.cast = cast;
        return this;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public Credits withCrew(List<Crew> crew) {
        this.crew = crew;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cast", cast)
                .append("crew", crew).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return  0;
    }

}
