
package com.bfirestone.udacity.popularmovies.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


@SuppressWarnings("unused")
public class ProductionCompany implements Parcelable {

    @Json(name = "id")
    private int id;
    @Json(name = "logo_path")
    private String logoPath;
    @Json(name = "name")
    private String name;
    @Json(name = "origin_country")
    private String originCountry;
    public final static Creator<ProductionCompany> CREATOR = new Creator<ProductionCompany>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ProductionCompany createFromParcel(Parcel in) {
            return new ProductionCompany(in);
        }

        public ProductionCompany[] newArray(int size) {
            return (new ProductionCompany[size]);
        }

    };

    private ProductionCompany(Parcel in) {
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.logoPath = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.originCountry = ((String) in.readValue((String.class.getClassLoader())));
    }

    public ProductionCompany() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductionCompany withId(int id) {
        this.id = id;
        return this;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public ProductionCompany withLogoPath(String logoPath) {
        this.logoPath = logoPath;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductionCompany withName(String name) {
        this.name = name;
        return this;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public ProductionCompany withOriginCountry(String originCountry) {
        this.originCountry = originCountry;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("logoPath", logoPath)
                .append("name", name)
                .append("originCountry", originCountry).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(logoPath);
        dest.writeValue(name);
        dest.writeValue(originCountry);
    }

    public int describeContents() {
        return 0;
    }

}
