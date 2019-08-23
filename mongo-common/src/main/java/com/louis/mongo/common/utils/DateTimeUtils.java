package com.louis.mongo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public static String getDateTime(Date date) {
        if (date == null) {
            return "";
        }
        return sdf.format(date);
    }
}
