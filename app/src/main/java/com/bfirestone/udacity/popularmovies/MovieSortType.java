package com.bfirestone.udacity.popularmovies;

public enum MovieSortType {
    HIGHEST_RATING(1),
    MOST_POPULAR(2),
    FAVORITES(3);

    private int value;

    MovieSortType(int value) {
        this.value = value;
    }
}
