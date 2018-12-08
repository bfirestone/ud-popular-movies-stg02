package com.bfirestone.udacity.popularmovies.adapters;

import android.support.v7.util.DiffUtil;

import com.bfirestone.udacity.popularmovies.database.entity.MovieEntity;

import java.util.List;

public class MovieListAdapterCallback extends DiffUtil.Callback {

    private List<MovieEntity> mOldList;
    private List<MovieEntity> mNewList;

    public MovieListAdapterCallback(List<MovieEntity> oldList, List<MovieEntity> newList) {
        this.mOldList = oldList;
        this.mNewList = newList;
    }

    @Override
    public int getOldListSize() {
        return mOldList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldList.get(oldItemPosition).getId() == mNewList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        MovieEntity oldMovieEntity = mOldList.get(oldItemPosition);
        MovieEntity newMovieEntity = mNewList.get(newItemPosition);

        return oldMovieEntity.getId() == newMovieEntity.getId()
                && oldMovieEntity.getTitle().equals(newMovieEntity.getTitle());
    }
}