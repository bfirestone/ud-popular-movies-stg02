
package com.bfirestone.udacity.popularmovies.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("unused")
public class Cast implements Parcelable
{

    @Json(name = "cast_id")
    private int castId;
    @Json(name = "character")
    private String character;
    @Json(name = "credit_id")
    private String creditId;
    @Json(name = "gender")
    private int gender;
    @Json(name = "id")
    private int id;
    @Json(name = "name")
    private String name;
    @Json(name = "order")
    private int order;
    @Json(name = "profile_path")
    private String profilePath;
    public final static Creator<Cast> CREATOR = new Creator<Cast>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Cast createFromParcel(Parcel in) {
            return new Cast(in);
        }

        public Cast[] newArray(int size) {
            return (new Cast[size]);
        }

    }
    ;

    private Cast(Parcel in) {
        this.castId = ((int) in.readValue((int.class.getClassLoader())));
        this.character = ((String) in.readValue((String.class.getClassLoader())));
        this.creditId = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((int) in.readValue((int.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((int) in.readValue((int.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Cast() {
    }

    public int getCastId() {
        return castId;
    }

    public void setCastId(int castId) {
        this.castId = castId;
    }

    public Cast withCastId(int castId) {
        this.castId = castId;
        return this;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Cast withCharacter(String character) {
        this.character = character;
        return this;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public Cast withCreditId(String creditId) {
        this.creditId = creditId;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Cast withGender(int gender) {
        this.gender = gender;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cast withId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Cast withName(String name) {
        this.name = name;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Cast withOrder(int order) {
        this.order = order;
        return this;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Cast withProfilePath(String profilePath) {
        this.profilePath = profilePath;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("castId", castId)
                .append("character", character)
                .append("creditId", creditId)
                .append("gender", gender)
                .append("id", id)
                .append("name", name)
                .append("order", order)
                .append("profilePath", profilePath).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(castId);
        dest.writeValue(character);
        dest.writeValue(creditId);
        dest.writeValue(gender);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(order);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return  0;
    }

}
