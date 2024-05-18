package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
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

    /**
     * Retrieves the current date.
     * <p>
     * This method retrieves the current date as a LocalDate object representing the system clock's current date.
     *
     * @return the current date
     * <p>
     * This method simply returns the result of calling the now() method on the LocalDate class,
     * which retrieves the current date based on the system clock.
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Retrieves the next day after the given date.
     * <p>
     * This method calculates the date that comes after the specified date by adding one day to it.
     *
     * @param value the reference date, must not be null
     * @return the date representing the next day after the reference date
     * <p>
     * This method adds one day to the specified LocalDate object using the plusDays() method,
     * and returns the resulting date.
     */
    public static LocalDate getNextDay(LocalDate value) {
        return value.plusDays(1);
    }

    /**
     * Retrieves the previous day before the given date.
     * <p>
     * This method calculates the date that comes before the specified date by subtracting one day from it.
     *
     * @param value the reference date, must not be null
     * @return the date representing the previous day before the reference date
     * <p>
     * This method subtracts one day from the specified LocalDate object using the minus() method
     */
    public static LocalDate getPreviousDay(LocalDate value) {
        return value.minusDays(1);
    }

    /**
     * Retrieves the day of the week for the given date.
     * <p>
     * This method returns the day of the week for the specified date.
     *
     * @param value the date for which to retrieve the day of the week, must not be null
     * @return the day of the week for the specified date
     * <p>
     * This method simply calls the getDayOfWeek() method on the LocalDate object,
     * which returns the DayOfWeek enum representing the day of the week.
     */
    public static DayOfWeek getDayOfWeek(LocalDate value) {
        return value.getDayOfWeek();
    }

    /**
     * Retrieves the first day of the current month.
     * <p>
     * This method calculates and retrieves the first day of the current month based on the system clock's current date.
     *
     * @return the LocalDate representing the first day of the current month
     * <p>
     * This method first retrieves the current date using LocalDate.now().
     * Then, it applies the TemporalAdjusters.firstDayOfMonth() adjustment to obtain the first day of the current month.
     */
    public static LocalDate getFirstDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * Retrieves the first day of the month for the given date.
     * <p>
     * This method calculates and retrieves the first day of the month for the specified date.
     *
     * @param value the reference date, must not be null
     * @return the LocalDate representing the first day of the month for the specified date
     * <p>
     * This method applies the TemporalAdjusters.firstDayOfMonth() adjustment to the specified LocalDate object
     * to obtain the first day of the month.
     */
    public static LocalDate getFirstDayOfMonth(LocalDate value) {
        return value.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * Retrieves the last day of the current month.
     * <p>
     * This method calculates and retrieves the last day of the current month based on the system clock's current date.
     *
     * @return the LocalDate representing the last day of the current month
     * <p>
     * This method first retrieves the current date using LocalDate.now().
     * Then, it applies the TemporalAdjusters.lastDayOfMonth() adjustment to obtain the last day of the current month.
     */
    public static LocalDate getLastDayOfMonth() {
        return LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * Retrieves the last day of the month for the given date.
     * <p>
     * This method calculates and retrieves the last day of the month for the specified date.
     *
     * @param value the reference date, must not be null
     * @return the LocalDate representing the last day of the month for the specified date
     * <p>
     * This method applies the TemporalAdjusters.lastDayOfMonth() adjustment to the specified LocalDate object
     * to obtain the last day of the month.
     */
    public static LocalDate getLastDayOfMonth(LocalDate value) {
        return value.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * Retrieves the first day of the current week.
     * <p>
     * This method calculates and retrieves the first day of the current week based on the system clock's current date.
     *
     * @return the LocalDate representing the first day of the current week
     * <p>
     * This method first retrieves the current date using LocalDate.now().
     * Then, it applies the TemporalAdjusters.previousOrSame(MONDAY) adjustment to obtain the first day of the current week.
     * If today is Monday, it will return today's date. Otherwise, it will return the previous Monday's date.
     */
    public static LocalDate getFirstDayOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    /**
     * Retrieves the first day of the week for the given date.
     * <p>
     * This method calculates and retrieves the first day of the week for the specified date.
     *
     * @param value the reference date, must not be null
     * @return the LocalDate representing the first day of the week for the specified date
     * <p>
     * This method applies the TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY) adjustment to the specified LocalDate object
     * to obtain the first day of the week.
     * If the given date is already a Monday or later in the week, it returns the same date.
     * Otherwise, it returns the previous Monday's date.
     */
    public static LocalDate getFirstDayOfWeek(LocalDate value) {
        return value.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }

    /**
     * Retrieves the last day of the current week.
     * <p>
     * This method calculates and retrieves the last day of the current week based on the system clock's current date.
     *
     * @return the LocalDate representing the last day of the current week
     * <p>
     * This method first retrieves the current date using LocalDate.now().
     * Then, it applies the TemporalAdjusters.nextOrSame(SUNDAY) adjustment to obtain the last day of the current week.
     * If today is Sunday, it will return today's date. Otherwise, it will return the next Sunday's date.
     */
    public static LocalDate getLastDayOfWeek() {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    /**
     * Retrieves the last day of the week for the given date.
     * <p>
     * This method calculates and retrieves the last day of the week for the specified date.
     *
     * @param value the reference date, must not be null
     * @return the LocalDate representing the last day of the week for the specified date
     * <p>
     * This method applies the TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY) adjustment to the specified LocalDate object
     * to obtain the last day of the week.
     * If the given date is already a Sunday or later in the week, it returns the same date.
     * Otherwise, it returns the next Sunday's date.
     */
    public static LocalDate getLastDayOfWeek(LocalDate value) {
        return value.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    /**
     * Retrieves the start of the day for the given date.
     * <p>
     * This method calculates and retrieves the start of the day for the specified date.
     *
     * @param value the reference date, must not be null
     * @return the LocalDateTime representing the start of the day for the specified date
     * <p>
     * This method combines the specified LocalDate object with the time of midnight (00:00)
     * to obtain the LocalDateTime representing the start of the day.
     */
    public static LocalDateTime getStartOfDay(LocalDate value) {
        return value.atStartOfDay();
    }

    /**
     * Checks if two LocalDate objects represent the same source.
     * <p>
     * This method compares two LocalDate objects to determine if they represent the same source.
     *
     * @param source the first LocalDate object to compare, must not be null
     * @param target the second LocalDate object to compare, must not be null
     * @return true if the two LocalDate objects represent the same source, false otherwise
     * <p>
     * This method first checks if both LocalDate objects are not null using ObjectUtils.allNotNull().
     * If either of them is null, it returns false indicating they are not equal.
     * Otherwise, it compares the LocalDate objects using the equals() method,
     * returning true if they represent the same source, and false otherwise.
     */
    public static boolean areEquals(LocalDate source, LocalDate target) {
        if (!Object4j.allNotNull(source, target)) {
            return false;
        }
        return source.equals(target);
    }

    /**
     * Retrieves the current time.
     * <p>
     * This method retrieves the current time based on the system clock.
     *
     * @return the LocalTime representing the current time
     * <p>
     * This method simply returns the result of calling the now() method on the LocalTime class,
     * which retrieves the current time based on the system clock.
     */
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    /**
     * Adds the specified number of hours to the current time.
     * <p>
     * This method calculates and returns a LocalTime object representing the time after adding the specified number of hours
     * to the current time based on the system clock.
     *
     * @param hours the number of hours to add
     * @return the LocalTime representing the time after adding the specified hours to the current time
     * <p>
     * This method first retrieves the current time using LocalTime.now().
     * Then, it adds the specified number of hours to the current time using the plusHours() method,
     * returning the resulting LocalTime object.
     */
    public static LocalTime addHours(int hours) {
        LocalTime now = LocalTime.now();
        return now.plusHours(hours);
    }

    /**
     * Adds the specified number of hours to the given time.
     * <p>
     * This method calculates and returns a LocalTime object representing the time after adding the specified number of hours
     * to the given time.
     *
     * @param time  the reference time to which to add hours, must not be null
     * @param hours the number of hours to add
     * @return the LocalTime representing the time after adding the specified hours to the given time
     * <p>
     * This method adds the specified number of hours to the given time using the plusHours() method,
     * returning the resulting LocalTime object.
     */
    public static LocalTime addHours(LocalTime time, int hours) {
        return time.plusHours(hours);
    }
}
