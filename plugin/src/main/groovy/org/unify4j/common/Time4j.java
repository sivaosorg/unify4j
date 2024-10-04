package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.enums.TimezoneType;
import org.unify4j.text.TimeFormatText;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Time4j {
    protected static final Logger logger = LoggerFactory.getLogger(Time4j.class);

    /**
     * Converts a fractional second string representation to nanoseconds.
     * Parses the given fractionalSecondText to a double, representing a fraction of a second.
     * Multiplies the fractional value by 1,000,000,000 (nanoseconds in a second) and returns the result
     * as a long integer, representing the equivalent nanoseconds.
     *
     * @param fractionalSecondText The string representation of the fractional part of a second.
     * @return The equivalent nanoseconds of the given fractional second.
     */
    public static long fromFractionToNanos(String fractionalSecondText) {
        double fractionalSecond = Double.parseDouble(fractionalSecondText);
        // Multiply the fractional value by 1,000,000,000 (nanoseconds in a second) and cast it to a long.
        return (long) (fractionalSecond * 1_000_000_000);
    }

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

    /**
     * Adds the specified number of minutes to the given time.
     * This function creates a new LocalTime object by adding the specified number of minutes to the provided time.
     *
     * @param time    The original time to which minutes are added.
     * @param minutes The number of minutes to add to the time. Can be positive or negative.
     * @return A new LocalTime object representing the original time plus the specified number of minutes.
     */
    public static LocalTime addMinutes(LocalTime time, int minutes) {
        return time.plusMinutes(minutes);
    }

    /**
     * Adds the specified number of seconds to the given time.
     * This function creates a new LocalTime object by adding the specified number of seconds to the provided time.
     *
     * @param time    The original time to which seconds are added.
     * @param seconds The number of seconds to add to the time. Can be positive or negative.
     * @return A new LocalTime object representing the original time plus the specified number of seconds.
     */
    public static LocalTime addSeconds(LocalTime time, int seconds) {
        return time.plusSeconds(seconds);
    }

    /**
     * Adds the specified number of seconds to the given date.
     * This function creates a new Date object by adding the specified number of seconds to the provided date.
     *
     * @param date    The original date to which seconds are added.
     * @param seconds The number of seconds to add to the date. Can be positive or negative.
     * @return A new Date object representing the original date plus the specified number of seconds,
     * or the original date if the input date is null or if the number of seconds is zero.
     */
    public static Date addSeconds(Date date, int seconds) {
        if (seconds == 0 || date == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * Subtracts the specified number of seconds from the given date.
     * This function creates a new Date object by subtracting the specified number of seconds from the provided date.
     *
     * @param date    The original date from which seconds are subtracted.
     * @param seconds The number of seconds to subtract from the date. Must be a non-positive integer.
     * @return A new Date object representing the original date minus the specified number of seconds,
     * or the original date if the input date is null or if the number of seconds is zero.
     */
    public static Date minusSeconds(Date date, int seconds) {
        if (seconds > 0) {
            seconds *= -1;
        }
        return addSeconds(date, seconds);
    }

    /**
     * Adds the specified number of minutes to the given date.
     * This function creates a new Date object by adding the specified number of minutes to the provided date.
     *
     * @param date    The original date to which minutes are added.
     * @param minutes The number of minutes to add to the date. Can be positive or negative.
     * @return A new Date object representing the original date plus the specified number of minutes,
     * or the original date if the input date is null or if the number of minutes is zero.
     */
    public static Date addMinutes(Date date, int minutes) {
        if (minutes == 0 || date == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * Subtracts the specified number of minutes from the given date.
     * This function creates a new Date object by subtracting the specified number of minutes from the provided date.
     *
     * @param date    The original date from which minutes are subtracted.
     * @param minutes The number of minutes to subtract from the date. Must be a non-positive integer.
     * @return A new Date object representing the original date minus the specified number of minutes,
     * or the original date if the input date is null or if the number of minutes is zero.
     */
    public static Date minusMinutes(Date date, int minutes) {
        if (minutes > 0) {
            minutes *= -1;
        }
        return addMinutes(date, minutes);
    }

    /**
     * Adds the specified number of hours to the given date.
     * This function creates a new Date object by adding the specified number of hours to the provided date.
     *
     * @param date  The original date to which hours are added.
     * @param hours The number of hours to add to the date. Can be positive or negative.
     * @return A new Date object representing the original date plus the specified number of hours,
     * or the original date if the input date is null or if the number of hours is zero.
     */
    public static Date addHours(Date date, int hours) {
        if (hours == 0 || date == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * Subtracts the specified number of hours from the given date.
     * This function creates a new Date object by subtracting the specified number of hours from the provided date.
     *
     * @param date  The original date from which hours are subtracted.
     * @param hours The number of hours to subtract from the date. Must be a non-positive integer.
     * @return A new Date object representing the original date minus the specified number of hours,
     * or the original date if the input date is null or if the number of hours is zero.
     */
    public static Date minusHours(Date date, int hours) {
        if (hours > 0) {
            hours *= -1;
        }
        return addHours(date, hours);
    }

    /**
     * Adds the specified number of days to the given date.
     * This function creates a new Date object by adding the specified number of days to the provided date.
     *
     * @param date The original date to which days are added.
     * @param days The number of days to add to the date. Can be positive or negative.
     * @return A new Date object representing the original date plus the specified number of days,
     * or the original date if the input date is null or if the number of days is zero.
     */
    public static Date addDays(Date date, int days) {
        if (days == 0 || date == null) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * Subtracts the specified number of days from the given date.
     * This function creates a new Date object by subtracting the specified number of days from the provided date.
     *
     * @param date The original date from which days are subtracted.
     * @param days The number of days to subtract from the date. Must be a non-positive integer.
     * @return A new Date object representing the original date minus the specified number of days,
     * or the original date if the input date is null or if the number of days is zero.
     */
    public static Date minusDays(Date date, int days) {
        if (days > 0) {
            days *= -1;
        }
        return addDays(date, days);
    }

    /**
     * Converts the given date to a string representation in UTC time zone.
     * This function formats the provided date using the RFC 1123 format with UTC time zone,
     * and returns the formatted string.
     *
     * @param date The date to be converted to a string representation in UTC time zone.
     * @return A string representing the provided date in UTC time zone,
     * or an empty string if the input date is null.
     */
    public static String getUTCText(Date date) {
        if (date == null) {
            return "";
        }
        DateFormat rfc1123Format = new SimpleDateFormat(TimeFormatText.BIBLIOGRAPHY_LEGACY_EPOCH_PATTERN, Locale.getDefault());
        rfc1123Format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return rfc1123Format.format(date);
    }

    /**
     * Converts the given LocalDateTime object to UTC (Coordinated Universal Time).
     * This function adjusts the provided LocalDateTime object to the UTC time zone.
     *
     * @param local The LocalDateTime object to be converted to UTC.
     * @return A new LocalDateTime object representing the same date and time in UTC,
     * or the original LocalDateTime object if it is null.
     */
    public static LocalDateTime getUTC(LocalDateTime local) {
        return local.with(ZoneOffset.UTC);
    }

    /**
     * Retrieves the current date and time in UTC (Coordinated Universal Time).
     * This function returns a LocalDateTime object representing the current date and time
     * adjusted to the UTC time zone.
     *
     * @return A LocalDateTime object representing the current date and time in UTC.
     */
    public static LocalDateTime getCurrentUTC() {
        return getUTC(LocalDateTime.now());
    }

    /**
     * Converts a LocalDateTime object to a Date object.
     * This function converts the provided LocalDateTime object to a Date object,
     * preserving the date and time information.
     *
     * @param local The LocalDateTime object to be converted to a Date object.
     * @return A Date object representing the same date and time as the provided LocalDateTime object,
     * converted to the default time zone of the system.
     */
    public static Date transform(LocalDateTime local) {
        return Date.from(local.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Converts a LocalDate object to a Date object.
     * This function creates a new Date object by converting the provided LocalDate object to a Date object,
     * using the start of the day in the default time zone of the system.
     *
     * @param local The LocalDate object to be converted to a Date object.
     * @return A Date object representing the start of the day of the provided LocalDate,
     * converted to the default time zone of the system.
     */
    public static Date transform(LocalDate local) {
        if (local == null) {
            return null;
        }
        return Date.from(local.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    /**
     * Converts a java.util.Date object to a java.sql.Date object.
     * This function creates a new java.sql.Date object from the provided java.util.Date object,
     * preserving only the date information (day, month, year) and discarding the time information.
     *
     * @param date The java.util.Date object to be converted to a java.sql.Date object.
     * @return A java.sql.Date object representing the same date as the provided java.util.Date object,
     * with the time portion set to midnight (00:00:00).
     * Returns null if the input date is null.
     */
    public static java.sql.Date transformDateSql(Date date) {
        if (date == null) {
            return null;
        }
        return new java.sql.Date(date.getTime());
    }

    /**
     * Converts a Calendar object to a LocalDateTime object.
     * This function creates a new LocalDateTime object from the provided Calendar object,
     * preserving the date and time information.
     *
     * @param calendar The Calendar object to be converted to a LocalDateTime object.
     * @return A LocalDateTime object representing the same date and time as the provided Calendar object,
     * in the default time zone of the system.
     */
    public static LocalDateTime transform(Calendar calendar) {
        return LocalDateTime.ofInstant(calendar.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Converts a java.util.Date object to a LocalDate object.
     * This function creates a new LocalDate object from the provided java.util.Date object,
     * preserving only the date information (day, month, year) and discarding the time information.
     *
     * @param date The java.util.Date object to be converted to a LocalDate object.
     * @return A LocalDate object representing the same date as the provided java.util.Date object,
     * Returns null if the input date is null.
     */
    public static LocalDate transformLocal(Date date) {
        if (date == null) {
            return null;
        }
        return transform(date).toLocalDate();
    }

    /**
     * Converts a java.util.Date object to a LocalDateTime object.
     * This function creates a new LocalDateTime object from the provided java.util.Date object,
     * preserving both the date and time information.
     *
     * @param date The java.util.Date object to be converted to a LocalDateTime object.
     * @return A LocalDateTime object representing the same date and time as the provided java.util.Date object,
     * in the default time zone of the system.
     */
    public static LocalDateTime transform(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Converts a LocalDate object to a Date object with a specified time zone.
     * This function creates a new Date object from the provided LocalDate object,
     * preserving only the date information (day, month, year) and discarding the time information.
     * The time zone for the conversion is specified by the given ZoneId.
     *
     * @param local The LocalDate object to be converted to a Date object.
     * @param zone  The ZoneId representing the time zone to use for the conversion.
     * @return A Date object representing the same date as the provided LocalDate object,
     * converted to the specified time zone.
     */
    public static Date transform(LocalDate local, ZoneId zone) {
        return Date.from(local.atStartOfDay().atZone(zone).toInstant());
    }

    /**
     * Converts a java.util.Date object to a Timestamp object.
     * This function creates a new Timestamp object from the provided java.util.Date object,
     * preserving both the date and time information.
     *
     * @param date The java.util.Date object to be converted to a Timestamp object.
     * @return A Timestamp object representing the same date and time as the provided java.util.Date object,
     * Returns null if the input date is null.
     */
    public static Timestamp transformTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return new Timestamp(date.getTime());
    }

    /**
     * Checks if a given LocalDateTime is overdue, compared to the current date and time.
     * This function determines if the provided LocalDateTime is earlier than the current date and time,
     * indicating that it is overdue.
     *
     * @param due The LocalDateTime to be checked for being overdue.
     * @return true if the provided LocalDateTime is earlier than the current date and time (overdue),
     * false otherwise. Returns false if the input due date is null.
     */
    public static boolean isOverdue(LocalDateTime due) {
        if (due == null) {
            return false;
        }
        return due.isBefore(LocalDateTime.now());
    }

    /**
     * Checks if a given Date is overdue, compared to the current date and time.
     * This function determines if the provided LocalDateTime is earlier than the current date and time,
     * indicating that it is overdue.
     *
     * @param due The LocalDateTime to be checked for being overdue.
     * @return true if the provided Date is earlier than the current date and time (overdue),
     * false otherwise. Returns false if the input due date is null.
     */
    public static boolean isOverdue(Date due) {
        return isOverdue(transform(due));
    }

    /**
     * Checks if a given LocalDateTime represents a time that has not passed yet, compared to the current date and time.
     * This function determines if the provided LocalDateTime is equal to or later than the current date and time,
     * indicating that it is on time or has not passed yet.
     *
     * @param on The LocalDateTime to be checked for being on time.
     * @return true if the provided LocalDateTime is equal to or later than the current date and time (on time or not yet passed),
     * false otherwise. Returns false if the input LocalDateTime is null.
     */
    public static boolean isOnTime(LocalDateTime on) {
        if (on == null) {
            return false;
        }
        return LocalDateTime.now().isAfter(on) || LocalDateTime.now().isEqual(on);
    }

    /**
     * Checks if a given Date object represents a time that has not passed yet, compared to the current date and time.
     * This function determines if the provided Date object is equal to or later than the current date and time,
     * indicating that it is on time or has not passed yet.
     * The input Date object is first converted to a LocalDateTime object for comparison.
     *
     * @param on The Date object to be checked for being on time.
     * @return true if the provided Date object represents a time equal to or later than the current date and time (on time or not yet passed),
     * false otherwise. Returns false if the input Date object is null.
     */
    public static boolean isOnTime(Date on) {
        return isOnTime(transform(on));
    }

    /**
     * Adds a specified number of days to a given LocalDate object.
     * If the number of days is less than or equal to zero, or if the LocalDate object is null,
     * the original LocalDate object is returned.
     *
     * @param local The LocalDate object to which days will be added.
     * @param days  The number of days to add.
     * @return A new LocalDate object with the specified number of days added, or the original LocalDate if
     * the input number of days is less than or equal to zero or the LocalDate is null.
     */
    public static LocalDate addDays(LocalDate local, int days) {
        if (days <= 0 || local == null) {
            return local;
        }
        return local.plusDays(days);
    }

    /**
     * Subtracts a specified number of days from a given LocalDate object.
     * If the number of days is less than or equal to zero, or if the LocalDate object is null,
     * the original LocalDate object is returned.
     *
     * @param local The LocalDate object from which days will be subtracted.
     * @param days  The number of days to subtract.
     * @return A new LocalDate object with the specified number of days subtracted, or the original LocalDate if
     * the input number of days is less than or equal to zero or the LocalDate is null.
     */
    public static LocalDate minusDays(LocalDate local, int days) {
        if (days <= 0 || local == null) {
            return local;
        }
        return local.minusDays(days);
    }

    /**
     * Adds a specified number of years to a given LocalDate object.
     * If the number of years is less than or equal to zero, or if the LocalDate object is null,
     * the original LocalDate object is returned.
     *
     * @param local The LocalDate object to which years will be added.
     * @param years The number of years to add.
     * @return A new LocalDate object with the specified number of years added, or the original LocalDate if
     * the input number of years is less than or equal to zero or the LocalDate is null.
     */
    public static LocalDate addYears(LocalDate local, int years) {
        if (years <= 0 || local == null) {
            return local;
        }
        return local.plusYears(years);
    }

    /**
     * Subtracts a specified number of years from a given LocalDate object.
     * If the number of years is less than or equal to zero, or if the LocalDate object is null,
     * the original LocalDate object is returned.
     *
     * @param local The LocalDate object from which years will be subtracted.
     * @param years The number of years to subtract.
     * @return A new LocalDate object with the specified number of years subtracted, or the original LocalDate if
     * the input number of years is less than or equal to zero or the LocalDate is null.
     */
    public static LocalDate minusYears(LocalDate local, int years) {
        if (years <= 0 || local == null) {
            return local;
        }
        return local.minusYears(years);
    }

    /**
     * Adds a specified number of weeks to a given LocalDate object.
     * If the number of weeks is less than or equal to zero, or if the LocalDate object is null,
     * the original LocalDate object is returned.
     *
     * @param local The LocalDate object to which weeks will be added.
     * @param weeks The number of weeks to add.
     * @return A new LocalDate object with the specified number of weeks added, or the original LocalDate if
     * the input number of weeks is less than or equal to zero or the LocalDate is null.
     */
    public static LocalDate addWeeks(LocalDate local, int weeks) {
        if (weeks <= 0 || local == null) {
            return local;
        }
        return local.plusWeeks(weeks);
    }

    /**
     * Subtracts a specified number of weeks from a given LocalDate object.
     * If the number of weeks is less than or equal to zero, or if the LocalDate object is null,
     * the original LocalDate object is returned.
     *
     * @param local The LocalDate object from which weeks will be subtracted.
     * @param weeks The number of weeks to subtract.
     * @return A new LocalDate object with the specified number of weeks subtracted, or the original LocalDate if
     * the input number of weeks is less than or equal to zero or the LocalDate is null.
     */
    public static LocalDate minusWeeks(LocalDate local, int weeks) {
        if (weeks <= 0 || local == null) {
            return local;
        }
        return local.minusWeeks(weeks);
    }

    /**
     * Adds a specified number of months to a given LocalDate object.
     * If the number of months is less than or equal to zero, or if the LocalDate object is null,
     * the original LocalDate object is returned.
     *
     * @param local  The LocalDate object to which months will be added.
     * @param months The number of months to add.
     * @return A new LocalDate object with the specified number of months added, or the original LocalDate if
     * the input number of months is less than or equal to zero or the LocalDate is null.
     */
    public static LocalDate addMonths(LocalDate local, int months) {
        if (months <= 0 || local == null) {
            return local;
        }
        return local.plusMonths(months);
    }

    /**
     * Subtracts a specified number of months from a given LocalDate object.
     * If the number of months is less than or equal to zero, or if the LocalDate object is null,
     * the original LocalDate object is returned.
     *
     * @param local  The LocalDate object from which months will be subtracted.
     * @param months The number of months to subtract.
     * @return A new LocalDate object with the specified number of months subtracted, or the original LocalDate if
     * the input number of months is less than or equal to zero or the LocalDate is null.
     */
    public static LocalDate minusMonths(LocalDate local, int months) {
        if (months <= 0 || local == null) {
            return local;
        }
        return local.minusMonths(months);
    }

    /**
     * Converts a given Date object to the beginning of the day (00:00:00) of the same date.
     * The function first transforms the Date object into a LocalDate, then sets the time to the start of the day,
     * and finally converts it back to a Date object.
     *
     * @param date The Date object to be transformed.
     * @return A Date object representing the beginning of the day of the provided date, or the original date if null.
     */
    public static Date ofBeginDay(Date date) {
        LocalDate local = transformLocal(date);
        LocalDateTime time = local.atStartOfDay();
        return transform(time);
    }

    /**
     * Converts a given Date object to the end of the day (23:59:59) of the same date.
     * The function first transforms the Date object into a LocalDate, then sets the time to the end of the day,
     * and finally converts it back to a Date object.
     *
     * @param date The Date object to be transformed.
     * @return A Date object representing the end of the day of the provided date, or the original date if null.
     */
    public static Date ofEndDay(Date date) {
        LocalDate local = transformLocal(date);
        LocalDateTime startOfDay = local.atTime(23, 59, 59);
        return transform(startOfDay);
    }

    /**
     * Parses a date string into a Date object using the specified format. If the format is not provided,
     * it defaults to a predefined pattern. If parsing fails, it attempts to parse using a secondary format.
     *
     * @param date   The date string to be parsed.
     * @param format The format to be used for parsing the date string. If null or empty, a default pattern is used.
     * @return A Date object representing the parsed date string, or null if parsing fails.
     */
    public static Date format(String date, String format) {
        try {
            if (String4j.isEmpty(date)) {
                return null;
            }
            if (String4j.isEmpty(format)) {
                return new SimpleDateFormat(TimeFormatText.BIBLIOGRAPHY_EPOCH_PATTERN).parse(date);
            }
            return new SimpleDateFormat(format).parse(date);
        } catch (ParseException e) {
            try {
                return new SimpleDateFormat(TimeFormatText.SHORT_BIBLIOGRAPHY_EPOCH_PATTERN).parse(date);
            } catch (ParseException ee) {
                logger.error("Parsing time got an exception: {} by date: {} and format: {}", ee.getMessage(), date, format, ee);
                return null;
            }
        }
    }

    /**
     * Parses a date string into a Date object using the specified format and timezone.
     * If the format or timezone is not provided, it defaults to predefined values.
     * If parsing fails, it attempts to parse using a secondary format.
     *
     * @param date     The date string to be parsed.
     * @param format   The format to be used for parsing the date string. If null or empty, a default pattern is used.
     * @param timezone The timezone to be applied during parsing. If null or empty, it defaults to "UTC".
     * @return A Date object representing the parsed date string in the specified timezone, or null if parsing fails.
     */
    public static Date format(String date, String format, String timezone) {
        try {
            if (String4j.isEmpty(date)) {
                return null;
            }
            // Set default format if not provided
            if (String4j.isEmpty(format)) {
                format = TimeFormatText.BIBLIOGRAPHY_EPOCH_PATTERN;
            }
            // Set default timezone if not provided
            if (String4j.isEmpty(timezone)) {
                timezone = "UTC";
            }
            // Create a SimpleDateFormat object with the specified format
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            // Set the timezone for the SimpleDateFormat object
            formatter.setTimeZone(TimeZone.getTimeZone(timezone));
            // Parse the date string using the format and timezone
            return formatter.parse(date);
        } catch (ParseException e) {
            try {
                // Attempt to parse with secondary format if the first attempt fails
                SimpleDateFormat formatterSecondary = new SimpleDateFormat(TimeFormatText.SHORT_BIBLIOGRAPHY_EPOCH_PATTERN);
                formatterSecondary.setTimeZone(TimeZone.getTimeZone(timezone)); // Apply timezone
                return formatterSecondary.parse(date);
            } catch (ParseException ee) {
                logger.error("Parsing time got an exception: {} by date: {} and format: {}", ee.getMessage(), date, format, ee);
                return null;
            }
        }
    }

    /**
     * Parses a date string into a Date object using the specified format and a custom TimezoneType.
     * If the timezone type is null, it defaults to the string-based overload of the format function.
     *
     * @param date     The date string to be parsed.
     * @param format   The format to be used for parsing the date string.
     * @param timezone The TimezoneType object containing the desired timezone.
     * @return A Date object representing the parsed date in the specified timezone, or null if parsing fails.
     */
    public static Date format(String date, String format, TimezoneType timezone) {
        if (timezone == null) {
            return format(date, format);
        }
        return format(date, format, timezone.getTimeZoneId());
    }

    /**
     * Parses a date string into a Date object using the specified format and a TimeZone object.
     * If the TimeZone object is null, it defaults to the string-based overload of the format function.
     *
     * @param date     The date string to be parsed.
     * @param format   The format to be used for parsing the date string.
     * @param timezone The TimeZone object containing the desired timezone.
     * @return A Date object representing the parsed date in the specified timezone, or null if parsing fails.
     */
    public static Date format(String date, String format, TimeZone timezone) {
        if (timezone == null) {
            return format(date, format);
        }
        return format(date, format, timezone.getID());
    }

    /**
     * Formats a Date object into a string representation using the specified format.
     *
     * @param date   The Date object to be formatted.
     * @param format The format string used to format the date.
     * @return A string representation of the formatted date.
     * @throws IllegalArgumentException if the format is null or invalid.
     */
    public static String format(Date date, String format) {
        if (date == null || String4j.isEmpty(format)) {
            return "";
        }
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * Formats a Date object into a string representation using a default format pattern.
     * The default pattern used is "yyyy-MM-dd HH:mm:ss".
     *
     * @param date The Date object to be formatted.
     * @return A string representation of the formatted date using the default pattern.
     * @throws IllegalArgumentException if the date is null.
     */
    public static String format(Date date) {
        return format(date, TimeFormatText.BIBLIOGRAPHY_EPOCH_PATTERN);
    }

    /**
     * Formats a Date object into a string representation using the specified timezone.
     *
     * @param date     The Date object to be formatted.
     * @param timezone The timezone.
     * @param format   The format string used to format the date.
     * @return A string representation of the formatted date with the specified timezone.
     * @throws IllegalArgumentException if the format is null or invalid.
     */
    public static String format(Date date, TimeZone timezone, String format) {
        if (date == null || timezone == null || String4j.isEmpty(format)) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setTimeZone(timezone);
        return sdf.format(date);
    }

    /**
     * Formats a Date object into a string representation using the specified timezone.
     *
     * @param date     The Date object to be formatted.
     * @param timezone The timezone enum type from TimezoneText enum.
     * @param format   The format string used to format the date.
     * @return A string representation of the formatted date with the specified timezone.
     * @throws IllegalArgumentException if the format is null or invalid.
     */
    public static String format(Date date, TimezoneType timezone, String format) {
        if (date == null || timezone == null || String4j.isEmpty(format)) {
            return "";
        }
        return format(date, TimeZone.getTimeZone(timezone.getTimeZoneId()), format);
    }

    /**
     * Formats a Date object into a string representation using the specified timezone.
     *
     * @param date     The Date object to be formatted.
     * @param timezone The timezone.
     * @return A string representation of the formatted date with the specified timezone.
     * @throws IllegalArgumentException if the format is null or invalid.
     */
    public static String format(Date date, TimeZone timezone) {
        return format(date, timezone, TimeFormatText.BIBLIOGRAPHY_EPOCH_PATTERN);
    }

    /**
     * Formats a Date object into a string representation using the specified timezone.
     *
     * @param date     The Date object to be formatted.
     * @param timezone The timezone.
     * @return A string representation of the formatted date with the specified timezone.
     * @throws IllegalArgumentException if the format is null or invalid.
     */
    public static String format(Date date, TimezoneType timezone) {
        return format(date, timezone, TimeFormatText.BIBLIOGRAPHY_EPOCH_PATTERN);
    }

    /**
     * Converts a date-time string from a specific time zone to UTC and formats it.
     * This method tries multiple input formats to parse the date-time string.
     *
     * @param dateTimeStr  The date-time string to be converted.
     * @param sourceZoneId The time zone of the input date-time string (e.g., "Asia/Ho_Chi_Minh").
     * @param formats      List of possible date-time formats for parsing the input string.
     * @return The formatted date-time string in UTC with the format "yyyy-MM-dd'T'HH:mm:ss.SSSZ".
     */
    public static String formatToUTC(String dateTimeStr, String sourceZoneId, List<String> formats) {
        if (String4j.isEmpty(dateTimeStr) || Collection4j.isEmpty(formats)) {
            return "";
        }
        if (String4j.isEmpty(sourceZoneId)) {
            sourceZoneId = TimezoneType.DefaultTimezoneVietnam.getTimeZoneId();
        }
        ZoneId zoneId = ZoneId.of(sourceZoneId);
        LocalDateTime locality;

        // Try each format to parse the input date-time string
        for (String format : formats) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            try {
                locality = LocalDateTime.parse(dateTimeStr, formatter);
                // Convert LocalDateTime to ZonedDateTime in the source time zone
                ZonedDateTime sourceZone = locality.atZone(zoneId);
                // Convert to UTC
                ZonedDateTime utc = sourceZone.withZoneSameInstant(ZoneId.of("UTC"));
                return utc.format(DateTimeFormatter.ofPattern(TimeFormatText.ISO_DATE_TIME_WITH_TIMEZONE_OFFSET));
            } catch (DateTimeParseException e) {
                // Continue to the next format
                logger.error("Formatting date-time to UTC got en exception: {} by date-time: {}, source timezone: {} and format(s): {}",
                        e.getMessage(), dateTimeStr, sourceZoneId, formats, e);
            }
        }
        // If no format succeeded, throw an exception
        throw new IllegalArgumentException("Invalid date-time format or time zone");
    }

    /**
     * Converts a date-time string from a specific time zone to UTC and formats it.
     * This method tries multiple input formats to parse the date-time string.
     *
     * @param dateTimeStr  The date-time string to be converted.
     * @param sourceZoneId The time zone of the input date-time string (e.g., "Asia/Ho_Chi_Minh").
     * @param formats      List of possible date-time formats for parsing the input string.
     * @return The formatted date-time string in UTC with the format "yyyy-MM-dd'T'HH:mm:ss.SSSZ".
     */
    public static String formatToUTC(String dateTimeStr, String sourceZoneId, String... formats) {
        return formatToUTC(dateTimeStr, sourceZoneId, Arrays.asList(formats));
    }

    /**
     * Parses a date with the specified year, month, and day using the system default time zone.
     *
     * @param year  The year to set.
     * @param month The month to set (1-based, January is 1, December is 12).
     * @param date  The day of the month to set.
     * @return A Date object representing the specified date at the start of the day.
     */
    public static Date parse(int year, int month, int date) {
        return parse(year, month, date, ZoneId.systemDefault());
    }

    /**
     * Parses a date with the specified year, month, and day using the provided time zone.
     *
     * @param year  The year to set.
     * @param month The month to set (1-based, January is 1, December is 12).
     * @param date  The day of the month to set.
     * @param zone  The time zone to use for parsing.
     * @return A Date object representing the specified date at the start of the day in the given time zone.
     */
    public static Date parse(int year, int month, int date, ZoneId zone) {
        return parse(year, month, date, 0, 0, zone);
    }

    /**
     * Parses a date with the specified year, month, day, hour, and minute using the system default time zone.
     *
     * @param year  The year to set.
     * @param month The month to set (1-based, January is 1, December is 12).
     * @param date  The day of the month to set.
     * @param hour  The hour to set.
     * @param min   The minute to set.
     * @return A Date object representing the specified date and time.
     */
    public static Date parse(int year, int month, int date, int hour, int min) {
        return parse(year, month, date, hour, min, ZoneId.systemDefault());
    }

    /**
     * Parses a date with the specified year, month, day, hour, and minute using the provided time zone.
     *
     * @param year  The year to set.
     * @param month The month to set (1-based, January is 1, December is 12).
     * @param date  The day of the month to set.
     * @param hour  The hour to set.
     * @param min   The minute to set.
     * @param zone  The time zone to use for parsing.
     * @return A Date object representing the specified date and time in the given time zone.
     */
    public static Date parse(int year, int month, int date, int hour, int min, ZoneId zone) {
        return Date.from(ZonedDateTime.of(year, month, date, hour, min, 0, 0, zone).toInstant());
    }

    /**
     * Parses a date with the specified year, month, day, hour, minute, and second using the system default time zone.
     *
     * @param year   The year to set.
     * @param month  The month to set (0-based, January is 0, December is 11).
     * @param day    The day of the month to set.
     * @param hour   The hour to set.
     * @param minute The minute to set.
     * @param second The second to set.
     * @return A Date object representing the specified date and time.
     */
    public static Date parse(int year, int month, int day, int hour, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Checks if the given date is within the specified date range.
     * <p>
     * This function determines if the provided date falls within the range specified
     * by the 'from' and 'to' dates, inclusive.
     *
     * @param date The date to check.
     * @param from The start date of the range.
     * @param to   The end date of the range.
     * @return true if the date is within the range (inclusive), false otherwise.
     * Returns false if any of the input dates are null.
     */
    public static boolean isWithinRange(Date date, Date from, Date to) {
        if (date == null || from == null || to == null) {
            return false;
        }
        return !(date.before(from) || date.after(to));
    }

    /**
     * Provides a human-readable string representing the time elapsed since the given date.
     * <p>
     * This function calculates the duration between the provided date and the current date,
     * and returns a string describing this duration in a human-readable format such as "just now",
     * "X minutes ago", "X hours ago", etc.
     *
     * @param date The date from which the elapsed time is calculated.
     * @return A string describing the elapsed time in a human-readable format.
     */
    public static String since(Date date) {
        return since(new Date(), date);
    }

    /**
     * Provides a human-readable string representing the time elapsed since the given date.
     * <p>
     * This function calculates the duration between the provided date and the current date,
     * and returns a string describing this duration in a human-readable format such as "just now",
     * "X minutes ago", "X hours ago", etc.
     *
     * @param source The reference date to calculate the duration from. Typically, this would be the current date.
     * @param target The date from which the elapsed time is calculated.
     * @return A string describing the elapsed time in a human-readable format.
     */
    public static String since(Date source, Date target) {
        LocalDateTime now = transform(source);
        LocalDateTime then = LocalDateTime.ofInstant(target.toInstant(), ZoneId.systemDefault());
        Duration duration = Duration.between(then, now);
        long seconds = duration.getSeconds();
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();
        long weeks = days / 7;
        long months = days / 30;
        long years = days / 365;
        if (seconds < 60) {
            return "just now";
        } else if (minutes < 60) {
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        } else if (hours < 24) {
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        } else if (days < 7) {
            return days + " day" + (days > 1 ? "s" : "") + " ago";
        } else if (weeks < 4) {
            return weeks + " week" + (weeks > 1 ? "s" : "") + " ago";
        } else if (months < 12) {
            return months + " month" + (months > 1 ? "s" : "") + " ago";
        } else {
            return years + " year" + (years > 1 ? "s" : "") + " ago";
        }
    }

    /**
     * Formats the given epoch time into a human-readable "time ago" format.
     * Examples:
     * - "Just now" for times within a minute.
     * - "X seconds ago," "X minutes ago," "X hours ago," "X days ago."
     *
     * @param epochMilli The epoch time in milliseconds.
     * @return A string representing the time difference.
     */
    public static String since(long epochMilli) {
        Instant now = Instant.now();
        Instant instant = Instant.ofEpochMilli(epochMilli);
        LocalDate _now = LocalDate.now();
        LocalDate local = Instant.ofEpochMilli(epochMilli).atZone(ZoneId.systemDefault()).toLocalDate();
        // Calculate duration for seconds, minutes, hours, days
        Duration duration = Duration.between(instant, now);
        long seconds = duration.getSeconds();
        long minutes = duration.toMinutes();
        long hours = duration.toHours();
        long days = duration.toDays();

        // Calculate period for months and years
        Period period = Period.between(local, _now);
        int months = period.getMonths();
        int years = period.getYears();

        if (seconds < 60) {
            return seconds <= 1 ? "just now" : seconds + " seconds ago";
        } else if (minutes < 60) {
            return minutes == 1 ? "1 minute ago" : minutes + " minutes ago";
        } else if (hours < 24) {
            return hours == 1 ? "1 hour ago" : hours + " hours ago";
        } else if (days < 30) {
            return days == 1 ? "1 day ago" : days + " days ago";
        } else if (months < 12) {
            return months == 1 ? "1 month ago" : months + " months ago";
        } else {
            return years == 1 ? "1 year ago" : years + " years ago";
        }
    }

    /**
     * Provides the difference in milliseconds between the given dates.
     *
     * @param source The reference date to calculate the duration from. Typically, this would be the current date.
     * @param target The date from which the elapsed time is calculated.
     * @return The difference in milliseconds between the two dates.
     */
    public static long sinceMilliseconds(Date source, Date target) {
        if (source == null || target == null) {
            return 0;
        }
        LocalDateTime now = transform(source);
        LocalDateTime then = LocalDateTime.ofInstant(target.toInstant(), ZoneId.systemDefault());
        Duration duration = Duration.between(then, now);
        return duration.toMillis();
    }

    /**
     * Provides the difference in seconds between the given dates.
     *
     * @param source The reference date to calculate the duration from. Typically, this would be the current date.
     * @param target The date from which the elapsed time is calculated.
     * @return The difference in seconds between the two dates.
     */
    public static long sinceSeconds(Date source, Date target) {
        if (source == null || target == null) {
            return 0;
        }
        LocalDateTime now = transform(source);
        LocalDateTime then = LocalDateTime.ofInstant(target.toInstant(), ZoneId.systemDefault());
        Duration duration = Duration.between(then, now);
        return duration.getSeconds();
    }

    /**
     * Provides the difference in minutes between the given dates.
     *
     * @param source The reference date to calculate the duration from. Typically, this would be the current date.
     * @param target The date from which the elapsed time is calculated.
     * @return The difference in minutes between the two dates.
     */
    public static long sinceMinutes(Date source, Date target) {
        if (source == null || target == null) {
            return 0;
        }
        LocalDateTime now = transform(source);
        LocalDateTime then = LocalDateTime.ofInstant(target.toInstant(), ZoneId.systemDefault());
        Duration duration = Duration.between(then, now);
        return duration.toMinutes();
    }

    /**
     * Provides the difference in hours between the given dates.
     *
     * @param source The reference date to calculate the duration from. Typically, this would be the current date.
     * @param target The date from which the elapsed time is calculated.
     * @return The difference in hours between the two dates.
     */
    public static long sinceHours(Date source, Date target) {
        if (source == null || target == null) {
            return 0;
        }
        LocalDateTime now = transform(source);
        LocalDateTime then = LocalDateTime.ofInstant(target.toInstant(), ZoneId.systemDefault());
        Duration duration = Duration.between(then, now);
        return duration.toHours();
    }

    /**
     * Provides the difference in days between the given dates.
     *
     * @param source The reference date to calculate the duration from. Typically, this would be the current date.
     * @param target The date from which the elapsed time is calculated.
     * @return The difference in days between the two dates.
     */
    public static long sinceDays(Date source, Date target) {
        if (source == null || target == null) {
            return 0;
        }
        LocalDateTime now = transform(source);
        LocalDateTime then = LocalDateTime.ofInstant(target.toInstant(), ZoneId.systemDefault());
        Duration duration = Duration.between(then, now);
        return duration.toDays();
    }

    /**
     * Provides the difference human-readable between the given dates.
     *
     * @param source The reference date to calculate the duration from. Typically, this would be the current date.
     * @param target The date from which the elapsed time is calculated.
     * @return The difference in human-readable between the two dates.
     */
    public static String sinceSmallRecently(Date source, Date target) {
        if (source == null || target == null) {
            return "unknown";
        }
        long days = sinceDays(source, target);
        if (days > 0) {
            return String.format("%d (d)", days);
        }
        long hours = sinceHours(source, target);
        if (hours > 0) {
            return String.format("%d (h)", hours);
        }
        long minutes = sinceMinutes(source, target);
        if (minutes > 0) {
            return String.format("%d (m)", minutes);
        }
        long seconds = sinceSeconds(source, target);
        if (seconds > 0) {
            return String.format("%d (s)", seconds);
        }
        long mills = sinceMilliseconds(source, target);
        return String.format("%d (ms)", mills);
    }

    /**
     * Provides the difference human-readable between the given dates.
     *
     * @param target The date from which the elapsed time is calculated.
     * @return The difference in human-readable between the two dates.
     */
    public static String sinceSmallRecently(Date target) {
        return sinceSmallRecently(new Date(), target);
    }

    /**
     * Retrieves the ZoneId corresponding to the given time zone identifier.
     * If the provided time zone identifier is empty or null, the system default ZoneId is returned.
     * If the time zone identifier starts with a plus (+) or minus (-) sign, it is treated as an offset
     * and converted to a ZoneId using the GMT time zone.
     * If the time zone identifier is a valid time zone identifier, it is converted to a ZoneId directly.
     * If the time zone identifier is not valid or cannot be recognized, an attempt is made to retrieve
     * the corresponding TimeZone instance, and its ZoneId is returned. If the TimeZone instance has
     * a raw offset of 0 (indicating an unknown or invalid time zone), an exception is thrown.
     *
     * @param tz The time zone identifier string.
     * @return The corresponding ZoneId for the given time zone identifier.
     * @throws IllegalArgumentException if the time zone identifier is invalid or cannot be recognized.
     */
    public static ZoneId parseTimeZone(String tz) {
        if (String4j.isEmpty(tz)) {
            return ZoneId.systemDefault();
        }
        if (tz.startsWith("-") || tz.startsWith("+")) {
            ZoneOffset offset = ZoneOffset.of(tz); // If the time zone identifier starts with '+' or '-', treat it as an offset and convert it to a ZoneId.
            return ZoneId.ofOffset("GMT", offset);
        } else {
            try {
                return ZoneId.of(tz); // Attempt to parse the time zone identifier directly as a ZoneId.
            } catch (Exception e) {
                // If the time zone identifier is not recognized as a valid ZoneId, attempt to retrieve the corresponding TimeZone.
                TimeZone timezone = TimeZone.getTimeZone(tz);
                if (timezone.getRawOffset() == 0) {
                    // If the retrieved TimeZone has a raw offset of 0, indicating an unknown or invalid time zone, throw an exception.
                    throw e;
                }
                // Otherwise, convert the retrieved TimeZone to a ZoneId and return it.
                return timezone.toZoneId();
            }
        }
    }

    /**
     * Rounds down the given date string to the nearest hour.
     * <p>
     * This method takes a date string and a layout format as inputs. It first parses
     * the date string using the provided layout and converts it into a Date object.
     * Then, the date is transformed into a LocalDateTime object. The time is adjusted
     * by setting the minutes and seconds to zero, effectively rounding the time down
     * to the nearest hour. The transformed LocalDateTime is then converted back to a Date object.
     * </p>
     *
     * @param date   The input date string that needs to be rounded down.
     * @param layout The format pattern of the input date string (e.g., "yyyy-MM-dd HH:mm:ss").
     * @return A Date object representing the time rounded down to the nearest hour,
     * or null if the input date string cannot be parsed.
     */
    public static Date roundDownToNearestHour(String date, String layout) {
        Date format = Time4j.format(date, layout);
        if (format == null) {
            return null;
        }
        return roundDownToNearestHour(format);
    }

    /**
     * Rounds down the given date string to the nearest hour.
     * <p>
     * This method takes a date string and a layout format as inputs. It first parses
     * the date string using the provided layout and converts it into a Date object.
     * Then, the date is transformed into a LocalDateTime object. The time is adjusted
     * by setting the minutes and seconds to zero, effectively rounding the time down
     * to the nearest hour. The transformed LocalDateTime is then converted back to a Date object.
     * </p>
     *
     * @param date The input date  that needs to be rounded down.
     * @return A Date object representing the time rounded down to the nearest hour,
     * or null if the input date string cannot be parsed.
     */
    public static Date roundDownToNearestHour(Date date) {
        if (date == null) {
            return null;
        }
        LocalDateTime local = Time4j.transform(date);
        LocalDateTime roundedDownTime = local.withMinute(0).withSecond(0);
        return Time4j.transform(roundedDownTime);
    }

    /**
     * Rounds up the given date string to the next hour.
     * <p>
     * This method takes a date string and a layout format as inputs. It first parses
     * the date string using the provided layout and converts it into a Date object.
     * Then, the date is transformed into a LocalDateTime object. If the time has any
     * minutes or seconds, one hour is added, effectively rounding the time up to the next hour.
     * Finally, the minutes and seconds are set to zero to represent the full hour, and
     * the LocalDateTime is transformed back to a Date object.
     * </p>
     *
     * @param date   The input date string that needs to be rounded up.
     * @param layout The format pattern of the input date string (e.g., "yyyy-MM-dd HH:mm:ss").
     * @return A Date object representing the time rounded up to the next hour,
     * or null if the input date string cannot be parsed.
     */
    public static Date roundUpToNextHour(String date, int hour, String layout) {
        Date format = Time4j.format(date, layout);
        if (format == null || hour < 0) {
            return format;
        }
        return roundUpToNextHour(format, hour);
    }

    /**
     * Rounds up the given date string to the next hour.
     * <p>
     * This method takes a date string and a layout format as inputs. It first parses
     * the date string using the provided layout and converts it into a Date object.
     * Then, the date is transformed into a LocalDateTime object. If the time has any
     * minutes or seconds, one hour is added, effectively rounding the time up to the next hour.
     * Finally, the minutes and seconds are set to zero to represent the full hour, and
     * the LocalDateTime is transformed back to a Date object.
     * </p>
     *
     * @param date The input date that needs to be rounded up.
     * @return A Date object representing the time rounded up to the next hour,
     * or null if the input date string cannot be parsed.
     */
    public static Date roundUpToNextHour(Date date, int hour) {
        if (date == null || hour < 0) {
            return date;
        }
        LocalDateTime local = Time4j.transform(date);
        local = local.plusHours(hour);
        LocalDateTime roundedUpTime = local.withMinute(0).withSecond(0);
        return Time4j.transform(roundedUpTime);
    }

    /**
     * Gets the day of the week for a given {@link Calendar} instance.
     *
     * @param calendar The {@link Calendar} instance.
     * @return The full name of the day of the week (e.g., "Monday", "Tuesday").
     */
    public static String getDayOfWeek(Calendar calendar) {
        if (calendar == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(calendar.getTime());
    }

    /**
     * Gets the day of the week for a given {@link Date} instance.
     *
     * @param date The {@link Date} instance.
     * @return The full name of the day of the week (e.g., "Monday", "Tuesday").
     */
    public static String getDayOfWeek(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("EEEE");
        return format.format(date);
    }

    /**
     * Returns the day of the week for a given {@link LocalDate} object.
     *
     * @param date The {@link LocalDate} object.
     * @return The full name of the day of the week (e.g., "Monday", "Tuesday").
     */
    public static String getDayOfWeekStr(LocalDate date) {
        if (date == null) {
            return "";
        }
        return getDayOfWeek(date).getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    /**
     * Returns the day of the week for a given {@link LocalDateTime} object.
     *
     * @param date The {@link LocalDateTime} object.
     * @return The full name of the day of the week (e.g., "Monday", "Tuesday").
     */
    public static String getDayOfWeek(LocalDateTime date) {
        if (date == null) {
            return "";
        }
        return date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }
}
