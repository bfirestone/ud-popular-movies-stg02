package com.bfirestone.udacity.popularmovies.Utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DisplayUtils {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    private static final String LOG_TAG = DisplayUtils.class.getSimpleName();

    private DisplayUtils() {}

    public static String getDisplayReleaseDate(String releaseDate) {
        if (TextUtils.isEmpty(releaseDate)) return "";
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DATE_FORMAT.parse(releaseDate));

            return calendar.getDisplayName(
                    Calendar.MONTH,
                    Calendar.LONG, Locale.ENGLISH) + " " + calendar.get(Calendar.YEAR);

        } catch (ParseException e) {
            Log.e(LOG_TAG, "date parse exception: " + e.getMessage());
            return "";
        }
    }
}
