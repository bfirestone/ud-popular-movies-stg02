package com.bfirestone.udacity.popularmovies;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

public enum MovieSortType {
    HIGHEST_RATING(1),
    MOST_POPULAR(2),
    FAVORITES(3);

    private int value;

    MovieSortType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private static final SparseArray<MovieSortType> lookup = new SparseArray<>();

    static {
        for (MovieSortType movieSortType : MovieSortType.values()) {
            lookup.put(movieSortType.getValue(), movieSortType);
        }
    }

    public static MovieSortType get(int value) {
        return lookup.get(value);
    }
}
