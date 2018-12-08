
package com.bfirestone.udacity.popularmovies.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("unused")
public class Review implements Parcelable {

    @Json(name = "author")
    private String author;
    @Json(name = "content")
    private String content;
    @Json(name = "id")
    private String id;
    @Json(name = "url")
    private String url;

    private boolean expanded = false;

    public final static Creator<Review> CREATOR = new Creator<Review>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return (new Review[size]);
        }

    };

    private Review(Parcel in) {
        this.author = ((String) in.readValue((String.class.getClassLoader())));
        this.content = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.url = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Review() {}

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Review withAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Review withContent(String content) {
        this.content = content;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Review withId(String id) {
        this.id = id;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Review withUrl(String url) {
        this.url = url;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("author", author)
                .append("content", content)
                .append("id", id)
                .append("expanded", expanded)
                .append("url", url).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(author);
        dest.writeValue(content);
        dest.writeValue(id);
        dest.writeValue(url);
    }

    public int describeContents() {
        return 0;
    }

}
