package com.bfirestone.udacity.popularmovies;

import android.util.SparseArray;

public enum MovieSortType {
    HIGHEST_RATING(1),
    MOST_POPULAR(2),
    FAVORITES(3);

    private int sortValue;

    MovieSortType(int sortValue) {
        this.sortValue = sortValue;
    }

    public int getSortValue() {
        return sortValue;
    }

    private static final SparseArray<MovieSortType> lookup = new SparseArray<>();

    static {
        for (MovieSortType movieSortType : MovieSortType.values()) {
            lookup.put(movieSortType.getSortValue(), movieSortType);
        }
    }

    public static MovieSortType getSortType(int value) {
        return lookup.get(value);
    }
}
