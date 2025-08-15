package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.enums.TimezoneType;
import org.unify4j.text.TimeFormatText;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.function.Function;

public class Time4jDecorator {
    protected static final Logger logger = LoggerFactory.getLogger(Time4jDecorator.class);

    private final Date baseDate;
    private ZoneId timezone;
    private String format;

    /**
     * Private constructor for decorator pattern implementation.
     *
     * @param base The base date to be decorated
     */
    private Time4jDecorator(Date base) {
        this.baseDate = base;
        this.timezone = ZoneId.systemDefault();
        this.format = TimeFormatText.BIBLIOGRAPHY_EPOCH_PATTERN;
    }

    /**
     * Creates a new TimeDecorator instance with the specified date.
     *
     * @param date The date to be decorated
     * @return A new TimeDecorator instance
     */
    public static Time4jDecorator of(Date date) {
        return new Time4jDecorator(date);
    }

    /**
     * Creates a new TimeDecorator instance with the current date.
     *
     * @return A new TimeDecorator instance with current date
     */
    public static Time4jDecorator now() {
        return new Time4jDecorator(new Date());
    }

    /**
     * Sets the time zone for this decorator.
     *
     * @param timezone The time zone to apply
     * @return This decorator instance for method chaining
     */
    public Time4jDecorator withTimezone(ZoneId timezone) {
        this.timezone = timezone;
        return this;
    }

    /**
     * Sets the time zone using TimezoneType enum.
     *
     * @param timezone The timezone type to apply
     * @return This decorator instance for method chaining
     */
    public Time4jDecorator withTimezone(TimezoneType timezone) {
        if (timezone != null) {
            this.timezone = ZoneId.of(timezone.getTimeZoneId());
        }
        return this;
    }

    /**
     * Sets the format pattern for string operations.
     *
     * @param format The format pattern to apply
     * @return This decorator instance for method chaining
     */
    public Time4jDecorator withFormat(String format) {
        if (String4j.isNotEmpty(format)) {
            this.format = format;
        }
        return this;
    }

    /**
     * Applies a custom transformation function to the base date.
     *
     * @param transformer The transformation function to apply
     * @return A new TimeDecorator with the transformed date
     */
    public Time4jDecorator transform(Function<Date, Date> transformer) {
        Date applied = transformer.apply(this.baseDate);
        return Time4jDecorator.of(applied).withTimezone(this.timezone).withFormat(this.format);
    }

    /**
     * Adds the specified number of days to the decorated date.
     *
     * @param days The number of days to add
     * @return A new TimeDecorator with the modified date
     */
    public Time4jDecorator addDays(int days) {
        return transform(date -> Time4j.addDays(date, days));
    }

    /**
     * Adds the specified number of hours to the decorated date.
     *
     * @param hours The number of hours to add
     * @return A new TimeDecorator with the modified date
     */
    public Time4jDecorator addHours(int hours) {
        return transform(date -> Time4j.addHours(date, hours));
    }

    /**
     * Adds the specified number of minutes to the decorated date.
     *
     * @param minutes The number of minutes to add
     * @return A new TimeDecorator with the modified date
     */
    public Time4jDecorator addMinutes(int minutes) {
        return transform(date -> Time4j.addMinutes(date, minutes));
    }

    /**
     * Rounds down the decorated date to the nearest hour.
     *
     * @return A new TimeDecorator with the rounded date
     */
    public Time4jDecorator roundDownToNearestHour() {
        return transform(Time4j::roundDownToNearestHour);
    }

    /**
     * Rounds up the decorated date to the next hour.
     *
     * @param hours The number of hours to round up
     * @return A new TimeDecorator with the rounded date
     */
    public Time4jDecorator roundUpToNextHour(int hours) {
        return transform(date -> Time4j.roundUpToNextHour(date, hours));
    }

    /**
     * Converts the decorated date to the beginning of the day.
     *
     * @return A new TimeDecorator with the date set to beginning of day
     */
    public Time4jDecorator toBeginOfDay() {
        return transform(Time4j::ofBeginDay);
    }

    /**
     * Converts the decorated date to the end of the day.
     *
     * @return A new TimeDecorator with the date set to end of day
     */
    public Time4jDecorator toEndOfDay() {
        return transform(Time4j::ofEndDay);
    }

    /**
     * Formats the decorated date using the configured format and timezone.
     *
     * @return A formatted string representation of the date
     */
    public String format() {
        if (this.timezone.equals(ZoneId.systemDefault())) {
            return Time4j.format(this.baseDate, this.format);
        }
        return Time4j.format(this.baseDate, TimeZone.getTimeZone(this.timezone), this.format);
    }

    /**
     * Formats the decorated date using a custom format pattern.
     *
     * @param customFormat The custom format pattern to use
     * @return A formatted string representation of the date
     */
    public String format(String customFormat) {
        return Time4j.format(this.baseDate, TimeZone.getTimeZone(this.timezone), customFormat);
    }

    /**
     * Checks if the decorated date is overdue compared to current time.
     *
     * @return true if the date is overdue, false otherwise
     */
    public boolean isOverdue() {
        return Time4j.isOverdue(this.baseDate);
    }

    /**
     * Checks if the decorated date is on time compared to current time.
     *
     * @return true if the date is on time, false otherwise
     */
    public boolean isOnTime() {
        return Time4j.isOnTime(this.baseDate);
    }

    /**
     * Gets a human-readable string representing time elapsed since the decorated date.
     *
     * @return A string describing the elapsed time
     */
    public String since() {
        return Time4j.since(this.baseDate);
    }

    /**
     * Gets the day of the week for the decorated date.
     *
     * @return The day of the week as a string
     */
    public String getDayOfWeek() {
        return Time4j.getDayOfWeek(this.baseDate);
    }

    /**
     * Gets the decorated date as a Date object.
     *
     * @return The decorated date
     */
    public Date getDate() {
        return this.baseDate;
    }

    /**
     * Gets the decorated date as a LocalDateTime object.
     *
     * @return The decorated date as LocalDateTime
     */
    public LocalDateTime getLocalDateTime() {
        return Time4j.transform(this.baseDate);
    }

    /**
     * Gets the decorated date as a LocalDate object.
     *
     * @return The decorated date as LocalDate
     */
    public LocalDate getLocalDate() {
        return Time4j.transformLocal(this.baseDate);
    }

    /**
     * Checks if the decorated date is before the specified date.
     *
     * @param other The date to compare against
     * @return true if this date is before the other date, false otherwise
     */
    public boolean isBefore(Date other) {
        if (other == null || this.baseDate == null) {
            return false;
        }
        return this.baseDate.before(other);
    }

    /**
     * Checks if the decorated date is after the specified date.
     *
     * @param other The date to compare against
     * @return true if this date is after the other date, false otherwise
     */
    public boolean isAfter(Date other) {
        if (other == null || this.baseDate == null) {
            return false;
        }
        return this.baseDate.after(other);
    }

    /**
     * Checks if the decorated date is between two specified dates (inclusive).
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return true if this date is between the specified dates, false otherwise
     */
    public boolean isBetween(Date startDate, Date endDate) {
        return Time4j.isWithinRange(this.baseDate, startDate, endDate);
    }

    /**
     * Converts the decorated date to a different time zone.
     *
     * @param targetTimezone The target time zone
     * @return A new TimeDecorator with the date converted to the target time zone
     */
    public Time4jDecorator convertToTimeZone(ZoneId targetTimezone) {
        if (targetTimezone == null) {
            return this;
        }
        return transform(date -> {
            TimeZone fromTz = TimeZone.getTimeZone(this.timezone);
            TimeZone toTz = TimeZone.getTimeZone(targetTimezone);
            return Time4jExtensions.convertTimezone(date, fromTz, toTz);
        }).withTimezone(targetTimezone);
    }

    /**
     * Converts the decorated date to a different time zone using TimezoneType.
     *
     * @param targetTimezone The target timezone type
     * @return A new TimeDecorator with the date converted to the target time zone
     */
    public Time4jDecorator convertToTimeZone(TimezoneType targetTimezone) {
        if (targetTimezone == null) {
            return this;
        }
        return convertToTimeZone(ZoneId.of(targetTimezone.getTimeZoneId()));
    }

    /**
     * Calculates the duration between the decorated date and another date.
     *
     * @param other The other date to calculate duration with
     * @return Duration object representing the time difference
     */
    public Duration getDurationTo(Date other) {
        if (other == null || this.baseDate == null) {
            return Duration.ZERO;
        }
        LocalDateTime thisDateTime = Time4j.transform(this.baseDate);
        LocalDateTime otherDateTime = Time4j.transform(other);
        return Duration.between(thisDateTime, otherDateTime);
    }

    /**
     * Calculates the duration from another date to the decorated date.
     *
     * @param other The other date to calculate duration from
     * @return Duration object representing the time difference
     */
    public Duration getDurationFrom(Date other) {
        if (other == null || this.baseDate == null) {
            return Duration.ZERO;
        }
        LocalDateTime thisDateTime = Time4j.transform(this.baseDate);
        LocalDateTime otherDateTime = Time4j.transform(other);
        return Duration.between(otherDateTime, thisDateTime);
    }

    /**
     * Formats the decorated date with locale support.
     *
     * @param pattern The format pattern
     * @param locale  The locale to use for formatting
     * @return A formatted string representation of the date
     */
    public String formatWithLocale(String pattern, Locale locale) {
        if (this.baseDate == null || String4j.isEmpty(pattern) || locale == null) {
            return "";
        }
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, locale);
            formatter.setTimeZone(TimeZone.getTimeZone(this.timezone));
            return formatter.format(this.baseDate);
        } catch (Exception e) {
            logger.error("Error formatting date with locale: {}", e.getMessage(), e);
            return "";
        }
    }

    /**
     * Formats the decorated date with default locale.
     *
     * @param pattern The format pattern
     * @return A formatted string representation of the date
     */
    public String formatWithDefaultLocale(String pattern) {
        return formatWithLocale(pattern, Locale.getDefault());
    }

    /**
     * Creates a deep copy of the decorated date.
     *
     * @return A new TimeDecorator with the same configuration and a copied date
     */
    public Time4jDecorator copy() {
        Date copiedDate = this.baseDate != null ? new Date(this.baseDate.getTime()) : null;
        return Time4jDecorator.of(copiedDate).withTimezone(this.timezone).withFormat(this.format);
    }

    /**
     * Creates a clone of the decorated date.
     *
     * @return A new TimeDecorator identical to this one
     */
    @SuppressWarnings({"MethodDoesntCallSuperMethod"})
    public Time4jDecorator clone() {
        return copy();
    }

    /**
     * Validates if the decorated date is a valid date.
     *
     * @return true if the date is valid and not null, false otherwise
     */
    public boolean isValid() {
        return this.baseDate != null;
    }

    /**
     * Validates if the decorated date falls within business hours (9 AM to 5 PM).
     *
     * @return true if the time is within business hours, false otherwise
     */
    public boolean isBusinessHours() {
        if (this.baseDate == null) {
            return false;
        }
        LocalDateTime local = Time4j.transform(this.baseDate);
        int hour = local.getHour();
        return hour >= 9 && hour < 17;
    }

    /**
     * Validates if the decorated date is a weekday.
     *
     * @return true if the date is a weekday, false otherwise
     */
    public boolean isWeekday() {
        return Time4jExtensions.isWeekday(this.baseDate);
    }

    /**
     * Validates if the decorated date is a weekend.
     *
     * @return true if the date is a weekend, false otherwise
     */
    public boolean isWeekend() {
        return Time4jExtensions.isWeekend(this.baseDate);
    }

    /**
     * Checks if the decorated date is in the current month.
     *
     * @return true if the date is in the current month, false otherwise
     */
    public boolean isCurrentMonth() {
        if (this.baseDate == null) {
            return false;
        }
        LocalDate thisDate = Time4j.transformLocal(this.baseDate);
        LocalDate currentDate = LocalDate.now();
        return thisDate.getMonth() == currentDate.getMonth() &&
                thisDate.getYear() == currentDate.getYear();
    }

    /**
     * Checks if the decorated date is in the current year.
     *
     * @return true if the date is in the current year, false otherwise
     */
    public boolean isCurrentYear() {
        if (this.baseDate == null) {
            return false;
        }
        LocalDate thisDate = Time4j.transformLocal(this.baseDate);
        LocalDate currentDate = LocalDate.now();
        return thisDate.getYear() == currentDate.getYear();
    }

    /**
     * Gets the epoch milliseconds of the decorated date.
     *
     * @return The epoch milliseconds, or 0 if date is null
     */
    public long getEpochMillis() {
        return this.baseDate != null ? this.baseDate.getTime() : 0L;
    }

    /**
     * Gets the epoch seconds of the decorated date.
     *
     * @return The epoch seconds, or 0 if date is null
     */
    public long getEpochSeconds() {
        return this.baseDate != null ? this.baseDate.getTime() / 1000L : 0L;
    }

    /**
     * Truncates the decorated date to the specified unit.
     *
     * @param unit The temporal unit to truncate to
     * @return A new TimeDecorator with the truncated date
     */
    public Time4jDecorator truncateTo(ChronoUnit unit) {
        if (this.baseDate == null || unit == null) {
            return this;
        }
        return transform(date -> {
            LocalDateTime dateTime = Time4j.transform(date);
            LocalDateTime truncated = dateTime.truncatedTo(unit);
            return Time4j.transform(truncated);
        });
    }
}