package com.bfirestone.udacity.popularmovies.api.model.response;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.bfirestone.udacity.popularmovies.api.model.Cast;
import com.bfirestone.udacity.popularmovies.api.model.Crew;
import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@SuppressWarnings("unused")
public class CreditsResponse implements Parcelable {

    @Json(name = "id")
    private int id;
    @Json(name = "cast")
    private List<Cast> cast = null;
    @Json(name = "crew")
    private List<Crew> crew = null;
    public final static Parcelable.Creator<CreditsResponse> CREATOR = new Creator<CreditsResponse>() {

        @SuppressWarnings({"unchecked"})
        public CreditsResponse createFromParcel(Parcel in) {
            return new CreditsResponse(in);
        }

        public CreditsResponse[] newArray(int size) {
            return (new CreditsResponse[size]);
        }

    };

    private CreditsResponse(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.cast, (Cast.class.getClassLoader()));
        in.readList(this.crew, (Crew.class.getClassLoader()));
    }

    /**
     * No args constructor for use in serialization
     */
    public CreditsResponse() {
    }

    /**
     * @param id   cast id
     * @param cast cast list
     * @param crew crew list
     */
    public CreditsResponse(int id, List<Cast> cast, List<Crew> crew) {
        super();
        this.id = id;
        this.cast = cast;
        this.crew = crew;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CreditsResponse withId(int id) {
        this.id = id;
        return this;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    public CreditsResponse withCast(List<Cast> cast) {
        this.cast = cast;
        return this;
    }

    public List<Crew> getCrew() {
        return crew;
    }

    public void setCrew(List<Crew> crew) {
        this.crew = crew;
    }

    public CreditsResponse withCrew(List<Crew> crew) {
        this.crew = crew;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("cast", cast)
                .append("crew", crew).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(cast);
        dest.writeList(crew);
    }

    public int describeContents() {
        return 0;
    }

}