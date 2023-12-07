package com.example.dailymotivator;

import java.util.Calendar;

public class Utils {
    static double secondsInAmonth = 2592000;

    static public double calculatePercentage() {
        Calendar currentTime = Calendar.getInstance();
        long dayS = (currentTime.get(Calendar.DAY_OF_MONTH) - 1) * 24 * 60 * 60;
        long hourS = currentTime.get(Calendar.HOUR_OF_DAY) * 60 * 60;
        long minuteS = currentTime.get(Calendar.MINUTE) * 60;
        long seconds = currentTime.get(Calendar.SECOND);
        double sum = dayS + hourS + minuteS + seconds;

        return (((double) ((int) ((sum / secondsInAmonth * 100) * 10000))) / 10000);
    }
}
