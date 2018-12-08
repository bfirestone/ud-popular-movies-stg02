package com.bfirestone.udacity.popularmovies.database.converter;


import android.arch.persistence.room.TypeConverter;

import java.util.ArrayList;
import java.util.List;

public class Converter {

    @TypeConverter
    public static String fromIntegerList(List<Integer> ids) {
        StringBuilder idsString = new StringBuilder();

        for(int id : ids) {
            idsString.append(id).append(",");
        }

        return idsString.toString();
    }

    @TypeConverter
    public static List<Integer> toIntegerList(String concatenatedStrings) {
        List<Integer> myIds = new ArrayList<>();

        for(String s : concatenatedStrings.split(",")) myIds.add(Integer.valueOf(s));

        return myIds;
    }
}