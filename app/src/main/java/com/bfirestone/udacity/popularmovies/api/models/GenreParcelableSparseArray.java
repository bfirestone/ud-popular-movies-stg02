package com.bfirestone.udacity.popularmovies.api.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

@SuppressWarnings({"unchecked", "unused"})
public class GenreParcelableSparseArray implements Parcelable {
    private SparseArray<String> genres = new SparseArray<>();

    public GenreParcelableSparseArray() {}

    public void add(int id, String name) {
        genres.append(id, name);
    }

    public String get(int id) {
        return genres.get(id);
    }

    private GenreParcelableSparseArray(Parcel in) {
        genres = in.readSparseArray(String.class.getClassLoader());
    }

    public static final Creator<GenreParcelableSparseArray> CREATOR = new Creator<GenreParcelableSparseArray>() {
        @Override
        public GenreParcelableSparseArray createFromParcel(Parcel in) {
            return new GenreParcelableSparseArray(in);
        }

        @Override
        public GenreParcelableSparseArray[] newArray(int size) {
            return new GenreParcelableSparseArray[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSparseArray((SparseArray) genres);
    }
}
