package com.bfirestone.udacity.popularmovies.database.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.bfirestone.udacity.popularmovies.Utils.DisplayUtils;
import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.List;

import static com.bfirestone.udacity.popularmovies.AppConstants.DB_TABLE_MOVIES;

@Entity(tableName = DB_TABLE_MOVIES)
public class MovieEntity implements Parcelable {

    @Json(name ="id")
    @ColumnInfo(name ="id")
    @PrimaryKey
    private int id;

    @Json(name = "poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterPath;

    @Json(name ="adult")
    @ColumnInfo(name ="adult")
    private boolean adult;

    @Json(name ="overview")
    @ColumnInfo(name ="overview")
    private String overview;

    @Json(name ="release_date")
    @ColumnInfo(name ="release_date")
    private String releaseDate;

    @Json(name ="genre_ids")
    @ColumnInfo(name ="genre_ids")
    private List<Integer> genreIds;

    @Json(name ="original_title")
    @ColumnInfo(name ="original_title")
    private String originalTitle;

    @Json(name ="original_language")
    @ColumnInfo(name ="original_language")
    private String originalLanguage;

    @Json(name ="title")
    @ColumnInfo(name ="title")
    private String title;

    @Json(name ="backdrop_path")
    @ColumnInfo(name ="backdrop_path")
    private String backdropPath;

    @Json(name ="popularity")
    @ColumnInfo(name ="popularity")
    private double popularity;

    @Json(name ="vote_count")
    @ColumnInfo(name ="vote_count")
    private int voteCount;

    @Json(name ="video")
    @ColumnInfo(name ="video")
    private boolean video;

    @Json(name ="vote_average")
    @ColumnInfo(name ="vote_average")
    private double voteAverage;

    public MovieEntity(String posterPath, boolean adult, String overview, String releaseDate,
                       List<Integer> genreIds, int id, String originalTitle, String originalLanguage,
                       String title, String backdropPath, double popularity, int voteCount, boolean video,
                       double voteAverage) {

        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

    private MovieEntity(Parcel in) {
        posterPath = in.readString();
        adult = in.readByte() != 0;
        overview = in.readString();
        releaseDate = in.readString();
        genreIds = new ArrayList<>();
        in.readList(genreIds, List.class.getClassLoader());
        id = in.readInt();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        title = in.readString();
        backdropPath = in.readString();
        popularity = in.readDouble();
        voteCount = in.readInt();
        video = in.readByte() != 0;
        voteAverage = in.readDouble();
    }

    public static final Parcelable.Creator<MovieEntity> CREATOR = new Parcelable.Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel in) {
            return new MovieEntity(in);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };

    public String getPosterPath() {
        return posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getFormattedReleaseDate() {
        return DisplayUtils.getDisplayReleaseDate(releaseDate);
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(posterPath);
        parcel.writeByte((byte) (adult ? 1 : 0));
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeList(genreIds);
        parcel.writeInt(id);
        parcel.writeString(originalTitle);
        parcel.writeString(originalLanguage);
        parcel.writeString(title);
        parcel.writeString(backdropPath);
        parcel.writeDouble(popularity);
        parcel.writeInt(voteCount);
        parcel.writeByte((byte) (video ? 1 : 0));
        parcel.writeDouble(voteAverage);
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("title", title)
                .append("posterPath", posterPath)
                .append("adult", adult)
                .append("overview", overview)
                .append("releaseDate", releaseDate)
                .append("genreIds", genreIds)
                .append("backdropPath", backdropPath)
                .append("id", id)
                .append("originalLanguage", originalLanguage)
                .append("originalTitle", originalTitle)
                .append("popularity", popularity)
                .append("video", video)
                .append("voteAverage", voteAverage)
                .append("voteCount", voteCount).toString();
    }
}
