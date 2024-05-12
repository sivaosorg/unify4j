package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Time4j {
    protected static final Logger logger = LoggerFactory.getLogger(Time4j.class);

    /**
     * Converts milliseconds to seconds.
     *
     * @param milliseconds the number of milliseconds to convert
     * @return the equivalent number of seconds
     */
    public static long fromMillisToSecond(long milliseconds) {
        return TimeUnit.MILLISECONDS.toSeconds(milliseconds);
    }

    /**
     * Converts seconds to milliseconds.
     *
     * @param seconds the number of seconds to convert
     * @return the equivalent number of milliseconds
     */
    public static long fromSecondToMillis(long seconds) {
        return TimeUnit.SECONDS.toMillis(seconds);
    }

    /**
     * Converts seconds to minutes.
     *
     * @param seconds the number of seconds to convert
     * @return the equivalent number of minutes
     */
    public static long fromSecondToMinute(long seconds) {
        return TimeUnit.SECONDS.toMinutes(seconds);
    }

    /**
     * Converts minutes to seconds.
     *
     * @param minutes the number of minutes to convert
     * @return the equivalent number of seconds
     */
    public static long fromMinuteToSecond(long minutes) {
        return TimeUnit.MINUTES.toSeconds(minutes);
    }

    /**
     * Converts minutes to milliseconds.
     *
     * @param minutes the number of minutes to convert
     * @return the equivalent number of milliseconds
     */
    public static long fromMinuteToMillis(long minutes) {
        return TimeUnit.MINUTES.toMillis(minutes);
    }

    /**
     * Converts milliseconds to minutes.
     *
     * @param milliseconds the number of milliseconds to convert
     * @return the equivalent number of minutes
     */
    public static long fromMillisToMinute(long milliseconds) {
        return TimeUnit.MILLISECONDS.toMinutes(milliseconds);
    }

    /**
     * Converts hours to minutes.
     *
     * @param hours the number of hours to convert
     * @return the equivalent number of minutes
     */
    public static long fromHourToMinute(long hours) {
        return TimeUnit.HOURS.toMinutes(hours);
    }

    /**
     * Converts minutes to hours.
     *
     * @param minutes the number of minutes to convert
     * @return the equivalent number of hours
     */
    public static long fromMinuteToHour(long minutes) {
        return TimeUnit.MINUTES.toHours(minutes);
    }

    /**
     * Converts hours to seconds.
     *
     * @param hours the number of hours to convert
     * @return the equivalent number of seconds
     */
    public static long fromHourToSecond(long hours) {
        return TimeUnit.HOURS.toSeconds(hours);
    }

    /**
     * Converts days to hours.
     *
     * @param days the number of days to convert
     * @return the equivalent number of hours
     */
    public static long fromDayToHour(long days) {
        return TimeUnit.DAYS.toHours(days);
    }

    /**
     * Converts seconds to days.
     *
     * @param seconds the number of seconds to convert
     * @return the equivalent number of days
     */
    public static long fromSecondToDay(long seconds) {
        return TimeUnit.SECONDS.toDays(seconds);
    }

}
