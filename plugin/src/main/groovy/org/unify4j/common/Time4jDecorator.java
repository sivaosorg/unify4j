package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.enums.TimezoneType;
import org.unify4j.text.TimeFormatText;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
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
}