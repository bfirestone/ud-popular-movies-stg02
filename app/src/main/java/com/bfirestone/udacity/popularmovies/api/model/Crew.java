
package com.bfirestone.udacity.popularmovies.api.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.squareup.moshi.Json;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("unused")
public class Crew implements Parcelable
{

    @Json(name = "credit_id")
    private String creditId;
    @Json(name = "department")
    private String department;
    @Json(name = "gender")
    private int gender;
    @Json(name = "id")
    private int id;
    @Json(name = "job")
    private String job;
    @Json(name = "name")
    private String name;
    @Json(name = "profile_path")
    private String profilePath;
    public final static Creator<Crew> CREATOR = new Creator<Crew>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Crew createFromParcel(Parcel in) {
            return new Crew(in);
        }

        public Crew[] newArray(int size) {
            return (new Crew[size]);
        }

    }
    ;

    private Crew(Parcel in) {
        this.creditId = ((String) in.readValue((String.class.getClassLoader())));
        this.department = ((String) in.readValue((String.class.getClassLoader())));
        this.gender = ((int) in.readValue((int.class.getClassLoader())));
        this.id = ((int) in.readValue((int.class.getClassLoader())));
        this.job = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.profilePath = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Crew() {
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public Crew withCreditId(String creditId) {
        this.creditId = creditId;
        return this;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Crew withDepartment(String department) {
        this.department = department;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Crew withGender(int gender) {
        this.gender = gender;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Crew withId(int id) {
        this.id = id;
        return this;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Crew withJob(String job) {
        this.job = job;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Crew withName(String name) {
        this.name = name;
        return this;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public Crew withProfilePath(String profilePath) {
        this.profilePath = profilePath;
        return this;
    }

    @NonNull
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("creditId", creditId)
                .append("department", department)
                .append("gender", gender)
                .append("id", id)
                .append("job", job)
                .append("name", name)
                .append("profilePath", profilePath).toString();
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(creditId);
        dest.writeValue(department);
        dest.writeValue(gender);
        dest.writeValue(id);
        dest.writeValue(job);
        dest.writeValue(name);
        dest.writeValue(profilePath);
    }

    public int describeContents() {
        return  0;
    }

}
