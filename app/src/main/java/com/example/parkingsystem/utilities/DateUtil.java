package com.example.parkingsystem.utilities;

import java.util.Date;

public class DateUtil {
    public static boolean isDateInTheFuture(Date date) {
        return date.after(new Date());
    }

    public static boolean isDateInThePast(Date date) {
        return date.before(new Date());
    }
}