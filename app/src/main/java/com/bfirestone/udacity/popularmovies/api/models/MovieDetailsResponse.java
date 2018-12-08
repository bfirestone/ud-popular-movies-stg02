
package com.bfirestone.udacity.popularmovies.api.models;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@SuppressWarnings("unused")
public class MovieDetailsResponse implements Parcelable {

    @Json(name = "adult")
    private boolean adult;
    @Json(name = "backdrop_path")
    private String backdropPath;
    @Json(name = "belongs_to_collection")
    private MovieCollection movieCollection;
    @Json(name = "budget")
    private int budget;
    @Json(name = "genres")
    private List<Genre> genres = null;
    @Json(name = "homepage")
    private String homepage;
    @Json(name = "id")
    private int id;
    @Json(name = "imdb_id")
    private String imdbId;
    @Json(name = "original_language")
    private String originalLanguage;
    @Json(name = "original_title")
    private String originalTitle;
    @Json(name = "overview")
    private String overview;
    @Json(name = "popularity")
    private float popularity;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "production_companies")
    private List<ProductionCompany> productionCompanies = null;
    @Json(name = "production_countries")
    private List<ProductionCountry> productionCountries = null;
    @Json(name = "release_date")
    private String releaseDate;
    @Json(name = "revenue")
    private int revenue;
    @Json(name = "runtime")
    private int runtime;
    @Json(name = "spoken_languages")
    private List<SpokenLanguage> spokenLanguages = null;
    @Json(name = "status")
    private String status;
    @Json(name = "tagline")
    private String tagline;
    @Json(name = "title")
    private String title;
    @Json(name = "video")
    private boolean video;
    @Json(name = "vote_average")
    private float voteAverage;
    @Json(name = "vote_count")
    private int voteCount;
    @Json(name = "videos")
    private TrailerList trailerList;
    @Json(name = "reviews")
    private ReviewList reviews;
    @Json(name = "credits")
    private Credits credits;

    public final static Parcelable.Creator<MovieDetailsResponse> CREATOR = new Creator<MovieDetailsResponse>() {

        @SuppressWarnings({
                "unchecked"
        })
        public MovieDetailsResponse createFromParcel(Parcel in) {
            return new MovieDetailsResponse(in);
        }

        public MovieDetailsResponse[] newArray(int size) {
            return (new MovieDetailsResponse[size]);
        }

    };

    private MovieDetailsResponse(Parcel in) {
        this.adult = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.backdropPath = ((String) in.readValue((String.class.getClassLoader())));
        this.movieCollection = ((MovieCollection) in.readValue((MovieCollection.class.getClassLoader())));
        this.budget = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.genres, (Genre.class.getClassLoader()));
        this.homepage = ((String) in.readValue((String.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.imdbId = ((String) in.readValue((String.class.getClassLoader())));
        this.originalLanguage = ((String) in.readValue((String.class.getClassLoader())));
        this.originalTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.overview = ((String) in.readValue((String.class.getClassLoader())));
        this.popularity = ((float) in.readValue((float.class.getClassLoader())));
        this.posterPath = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.productionCompanies, (ProductionCompany.class.getClassLoader()));
        in.readList(this.productionCountries, (ProductionCountry.class.getClassLoader()));
        this.releaseDate = ((String) in.readValue((String.class.getClassLoader())));
        this.revenue = ((int) in.readValue((int.class.getClassLoader())));
        this.runtime = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.spokenLanguages, (SpokenLanguage.class.getClassLoader()));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.tagline = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.video = ((boolean) in.readValue((boolean.class.getClassLoader())));
        this.voteAverage = ((float) in.readValue((float.class.getClassLoader())));
        this.voteCount = ((int) in.readValue((int.class.getClassLoader())));
        this.trailerList = ((TrailerList) in.readValue((TrailerList.class.getClassLoader())));
        this.reviews = ((ReviewList) in.readValue((ReviewList.class.getClassLoader())));
        this.credits = ((Credits) in.readValue((Credits.class.getClassLoader())));
    }

    public MovieDetailsResponse() {
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public MovieDetailsResponse withAdult(boolean adult) {
        this.adult = adult;
        return this;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public MovieDetailsResponse withBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
        return this;
    }

    public MovieCollection getMovieCollection() {
        return movieCollection;
    }

    public void setMovieCollection(MovieCollection movieCollection) {
        this.movieCollection = movieCollection;
    }

    public MovieDetailsResponse withBelongsToCollection(MovieCollection belongsToCollection) {
        this.movieCollection = belongsToCollection;
        return this;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public MovieDetailsResponse withBudget(int budget) {
        this.budget = budget;
        return this;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public MovieDetailsResponse withGenres(List<Genre> genres) {
        this.genres = genres;
        return this;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public MovieDetailsResponse withHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MovieDetailsResponse withId(int id) {
        this.id = id;
        return this;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public MovieDetailsResponse withImdbId(String imdbId) {
        this.imdbId = imdbId;
        return this;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public MovieDetailsResponse withOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
        return this;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public MovieDetailsResponse withOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public MovieDetailsResponse withOverview(String overview) {
        this.overview = overview;
        return this;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public MovieDetailsResponse withPopularity(float popularity) {
        this.popularity = popularity;
        return this;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public MovieDetailsResponse withPosterPath(String posterPath) {
        this.posterPath = posterPath;
        return this;
    }

    public List<ProductionCompany> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }

    public MovieDetailsResponse withProductionCompanies(List<ProductionCompany> productionCompanies) {
        this.productionCompanies = productionCompanies;
        return this;
    }

    public List<ProductionCountry> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public MovieDetailsResponse withProductionCountries(List<ProductionCountry> productionCountries) {
        this.productionCountries = productionCountries;
        return this;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public MovieDetailsResponse withReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
        return this;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public MovieDetailsResponse withRevenue(int revenue) {
        this.revenue = revenue;
        return this;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public MovieDetailsResponse withRuntime(int runtime) {
        this.runtime = runtime;
        return this;
    }

    public List<SpokenLanguage> getSpokenLanguages() {
        return spokenLanguages;
    }

    public void setSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
    }

    public MovieDetailsResponse withSpokenLanguages(List<SpokenLanguage> spokenLanguages) {
        this.spokenLanguages = spokenLanguages;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MovieDetailsResponse withStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public MovieDetailsResponse withTagline(String tagline) {
        this.tagline = tagline;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MovieDetailsResponse withTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public MovieDetailsResponse withVideo(boolean video) {
        this.video = video;
        return this;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public MovieDetailsResponse withVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
        return this;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public MovieDetailsResponse withVoteCount(int voteCount) {
        this.voteCount = voteCount;
        return this;
    }

    public TrailerList getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(TrailerList trailerList) {
        this.trailerList = trailerList;
    }

    public MovieDetailsResponse withVideos(TrailerList videos) {
        this.trailerList = videos;
        return this;
    }

    public ReviewList getReviewList() {
        return reviews;
    }

    public void setReviewList(ReviewList reviews) {
        this.reviews = reviews;
    }

    public MovieDetailsResponse withReviewList(ReviewList reviews) {
        this.reviews = reviews;
        return this;
    }

    public Credits getCredits() {
        return credits;
    }

    public void setCredits(Credits credits) {
        this.credits = credits;
    }

    public MovieDetailsResponse withCredits(Credits credits) {
        this.credits = credits;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("adult", adult)
                .append("backdropPath", backdropPath)
                .append("movieCollection", movieCollection)
                .append("budget", budget)
                .append("genres", genres)
                .append("homepage", homepage)
                .append("id", id)
                .append("imdbId", imdbId)
                .append("originalLanguage", originalLanguage)
                .append("originalTitle", originalTitle)
                .append("overview", overview)
                .append("popularity", popularity)
                .append("posterPath", posterPath)
                .append("productionCompanies", productionCompanies)
                .append("productionCountries", productionCountries)
                .append("releaseDate", releaseDate)
                .append("revenue", revenue)
                .append("runtime", runtime)
                .append("spokenLanguages", spokenLanguages)
                .append("status", status)
                .append("tagline", tagline)
                .append("title", title)
                .append("video", video)
                .append("voteAverage", voteAverage)
                .append("voteCount", voteCount)
                .append("trailerList", trailerList)
                .append("reviews", reviews)
                .append("credits", credits).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(adult);
        dest.writeValue(backdropPath);
        dest.writeValue(movieCollection);
        dest.writeValue(budget);
        dest.writeList(genres);
        dest.writeValue(homepage);
        dest.writeValue(id);
        dest.writeValue(imdbId);
        dest.writeValue(originalLanguage);
        dest.writeValue(originalTitle);
        dest.writeValue(overview);
        dest.writeValue(popularity);
        dest.writeValue(posterPath);
        dest.writeList(productionCompanies);
        dest.writeList(productionCountries);
        dest.writeValue(releaseDate);
        dest.writeValue(revenue);
        dest.writeValue(runtime);
        dest.writeList(spokenLanguages);
        dest.writeValue(status);
        dest.writeValue(tagline);
        dest.writeValue(title);
        dest.writeValue(video);
        dest.writeValue(voteAverage);
        dest.writeValue(voteCount);
        dest.writeValue(trailerList);
        dest.writeValue(reviews);
        dest.writeValue(credits);
    }

    public int describeContents() {
        return 0;
    }

}