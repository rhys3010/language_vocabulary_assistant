package uk.ac.aber.dcs.cs31620.rhe24.lva.model.util;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Utility Class to handle all Date conversions between SQLite and Java
 * Code From FAA v6 https://github.com/chriswloftus/faa_version6
 * @author Rhys Evans
 * @version 29/11/2018
 */
public class DateTimeConverter {
    private static final String DATE_ERROR_TAG = "DATE FORMAT ERROR";

    /**
     * Convert SQLite timestamp to Java Date Object
     * @param timestamp
     * @return
     */
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    /**
     * Convert Java Date Object to SQLite timestamp
     * @param date
     * @return
     */
    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null ? null : date.getTime();
    }
}
