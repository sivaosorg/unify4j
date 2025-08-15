package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.enums.TimezoneType;
import org.unify4j.text.TimeFormatText;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;

public class Time4jBuilder {
    protected static final Logger logger = LoggerFactory.getLogger(Time4jBuilder.class);

    private int year;
    private int month = 1;
    private int day = 1;
    private int hour = 0;
    private int minute = 0;
    private int second = 0;
    private int millisecond = 0;
    private ZoneId timezone = ZoneId.systemDefault();
    private String formatPattern = TimeFormatText.BIBLIOGRAPHY_EPOCH_PATTERN;

    /**
     * Private constructor for builder pattern.
     */
    private Time4jBuilder() {
        // Initialize with current year as default
        this.year = LocalDate.now().getYear();
    }

    /**
     * Creates a new TimeBuilder instance.
     *
     * @return A new TimeBuilder instance
     */
    public static Time4jBuilder create() {
        return new Time4jBuilder();
    }

    /**
     * Creates a new TimeBuilder instance initialized with current date and time.
     *
     * @return A new TimeBuilder instance with current date/time
     */
    public static Time4jBuilder now() {
        Time4jBuilder builder = new Time4jBuilder();
        LocalDateTime now = LocalDateTime.now();
        return builder.year(now.getYear())
                .month(now.getMonthValue())
                .day(now.getDayOfMonth())
                .hour(now.getHour())
                .minute(now.getMinute())
                .second(now.getSecond())
                .millisecond(now.getNano() / 1_000_000);
    }

    /**
     * Creates a new TimeBuilder instance from an existing Date object.
     *
     * @param date The date to initialize the builder with
     * @return A new TimeBuilder instance initialized with the provided date
     */
    public static Time4jBuilder from(Date date) {
        Time4jBuilder builder = new Time4jBuilder();
        if (date != null) {
            LocalDateTime localDateTime = Time4j.transform(date);
            return builder.year(localDateTime.getYear())
                    .month(localDateTime.getMonthValue())
                    .day(localDateTime.getDayOfMonth())
                    .hour(localDateTime.getHour())
                    .minute(localDateTime.getMinute())
                    .second(localDateTime.getSecond())
                    .millisecond(localDateTime.getNano() / 1_000_000);
        }
        return builder;
    }

    /**
     * Creates a new TimeBuilder instance from an existing LocalDateTime object.
     *
     * @param local The LocalDateTime to initialize the builder with
     * @return A new TimeBuilder instance initialized with the provided LocalDateTime
     */
    public static Time4jBuilder from(LocalDateTime local) {
        Time4jBuilder builder = new Time4jBuilder();
        if (local != null) {
            return builder.year(local.getYear())
                    .month(local.getMonthValue())
                    .day(local.getDayOfMonth())
                    .hour(local.getHour())
                    .minute(local.getMinute())
                    .second(local.getSecond())
                    .millisecond(local.getNano() / 1_000_000);
        }
        return builder;
    }

    /**
     * Sets the year for the date being built.
     *
     * @param year The year to set
     * @return This builder instance for method chaining
     */
    public Time4jBuilder year(int year) {
        this.year = year;
        return this;
    }

    /**
     * Sets the month for the date being built.
     *
     * @param month The month to set (1-12, where 1 is January)
     * @return This builder instance for method chaining
     */
    public Time4jBuilder month(int month) {
        if (month >= 1 && month <= 12) {
            this.month = month;
        } else {
            logger.warn("Invalid month value: {}. Month should be between 1 and 12.", month);
        }
        return this;
    }

    /**
     * Sets the day for the date being built.
     *
     * @param day The day to set (1-31 depending on the month)
     * @return This builder instance for method chaining
     */
    public Time4jBuilder day(int day) {
        if (day >= 1 && day <= 31) {
            this.day = day;
        } else {
            logger.warn("Invalid day value: {}. Day should be between 1 and 31.", day);
        }
        return this;
    }

    /**
     * Sets the hour for the time being built.
     *
     * @param hour The hour to set (0-23)
     * @return This builder instance for method chaining
     */
    public Time4jBuilder hour(int hour) {
        if (hour >= 0 && hour <= 23) {
            this.hour = hour;
        } else {
            logger.warn("Invalid hour value: {}. Hour should be between 0 and 23.", hour);
        }
        return this;
    }

    /**
     * Sets the minute for the time being built.
     *
     * @param minute The minute to set (0-59)
     * @return This builder instance for method chaining
     */
    public Time4jBuilder minute(int minute) {
        if (minute >= 0 && minute <= 59) {
            this.minute = minute;
        } else {
            logger.warn("Invalid minute value: {}. Minute should be between 0 and 59.", minute);
        }
        return this;
    }

    /**
     * Sets the second for the time being built.
     *
     * @param second The second to set (0-59)
     * @return This builder instance for method chaining
     */
    public Time4jBuilder second(int second) {
        if (second >= 0 && second <= 59) {
            this.second = second;
        } else {
            logger.warn("Invalid second value: {}. Second should be between 0 and 59.", second);
        }
        return this;
    }

    /**
     * Sets the millisecond for the time being built.
     *
     * @param millisecond The millisecond to set (0-999)
     * @return This builder instance for method chaining
     */
    public Time4jBuilder millisecond(int millisecond) {
        if (millisecond >= 0 && millisecond <= 999) {
            this.millisecond = millisecond;
        } else {
            logger.warn("Invalid millisecond value: {}. Millisecond should be between 0 and 999.", millisecond);
        }
        return this;
    }

    /**
     * Sets the time zone for the date being built.
     *
     * @param timezone The time zone to set
     * @return This builder instance for method chaining
     */
    public Time4jBuilder timeZone(ZoneId timezone) {
        if (timezone != null) {
            this.timezone = timezone;
        }
        return this;
    }

    /**
     * Sets the time zone using a timezone string.
     *
     * @param timezoneID The time zone ID to set (e.g., "UTC", "America/New_York")
     * @return This builder instance for method chaining
     */
    public Time4jBuilder timeZone(String timezoneID) {
        if (String4j.isNotEmpty(timezoneID)) {
            this.timezone = Time4j.parseTimeZone(timezoneID);
        }
        return this;
    }

    /**
     * Sets the time zone using TimezoneType enum.
     *
     * @param timezone The timezone type to set
     * @return This builder instance for method chaining
     */
    public Time4jBuilder timeZone(TimezoneType timezone) {
        if (timezone != null) {
            this.timezone = ZoneId.of(timezone.getTimeZoneId());
        }
        return this;
    }

    /**
     * Sets the format pattern for string operations.
     *
     * @param formatPattern The format pattern to set
     * @return This builder instance for method chaining
     */
    public Time4jBuilder formatPattern(String formatPattern) {
        if (String4j.isNotEmpty(formatPattern)) {
            this.formatPattern = formatPattern;
        }
        return this;
    }

    /**
     * Sets the time to the beginning of the day (00:00:00).
     *
     * @return This builder instance for method chaining
     */
    public Time4jBuilder atStartOfDay() {
        this.hour = 0;
        this.minute = 0;
        this.second = 0;
        this.millisecond = 0;
        return this;
    }

    /**
     * Sets the time to the end of the day (23:59:59).
     *
     * @return This builder instance for method chaining
     */
    public Time4jBuilder atEndOfDay() {
        this.hour = 23;
        this.minute = 59;
        this.second = 59;
        this.millisecond = 999;
        return this;
    }

    /**
     * Sets the time to noon (12:00:00).
     *
     * @return This builder instance for method chaining
     */
    public Time4jBuilder atNoon() {
        this.hour = 12;
        this.minute = 0;
        this.second = 0;
        this.millisecond = 0;
        return this;
    }

    /**
     * Adds the specified number of days to the current date configuration.
     *
     * @param days The number of days to add
     * @return This builder instance for method chaining
     */
    public Time4jBuilder addDays(int days) {
        LocalDate current = LocalDate.of(year, month, day);
        LocalDate newDate = current.plusDays(days);
        this.year = newDate.getYear();
        this.month = newDate.getMonthValue();
        this.day = newDate.getDayOfMonth();
        return this;
    }

    /**
     * Adds the specified number of months to the current date configuration.
     *
     * @param months The number of months to add
     * @return This builder instance for method chaining
     */
    public Time4jBuilder addMonths(int months) {
        LocalDate current = LocalDate.of(year, month, day);
        LocalDate newDate = current.plusMonths(months);
        this.year = newDate.getYear();
        this.month = newDate.getMonthValue();
        this.day = newDate.getDayOfMonth();
        return this;
    }

    /**
     * Adds the specified number of years to the current date configuration.
     *
     * @param years The number of years to add
     * @return This builder instance for method chaining
     */
    public Time4jBuilder addYears(int years) {
        this.year += years;
        return this;
    }

    /**
     * Sets the date to the first day of the current month.
     *
     * @return This builder instance for method chaining
     */
    public Time4jBuilder firstDayOfMonth() {
        this.day = 1;
        return this;
    }

    /**
     * Sets the date to the last day of the current month.
     *
     * @return This builder instance for method chaining
     */
    public Time4jBuilder lastDayOfMonth() {
        LocalDate date = LocalDate.of(year, month, 1);
        this.day = date.lengthOfMonth();
        return this;
    }

    /**
     * Builds and returns the Date object based on the current configuration.
     *
     * @return A Date object created from the builder configuration
     */
    public Date build() {
        try {
            ZonedDateTime zoned = ZonedDateTime.of(year, month, day, hour, minute, second,
                    millisecond * 1_000_000, timezone);
            return Date.from(zoned.toInstant());
        } catch (Exception e) {
            logger.error("Error building date with configuration: year={}, month={}, day={}, hour={}, minute={}, second={}, millisecond={}, timeZone={}",
                    year, month, day, hour, minute, second, millisecond, timezone, e);
            return null;
        }
    }

    /**
     * Builds and returns the LocalDateTime object based on the current configuration.
     *
     * @return A LocalDateTime object created from the builder configuration
     */
    public LocalDateTime buildLocalDateTime() {
        try {
            return LocalDateTime.of(year, month, day, hour, minute, second, millisecond * 1_000_000);
        } catch (Exception e) {
            logger.error("Error building LocalDateTime with configuration: year={}, month={}, day={}, hour={}, minute={}, second={}, millisecond={}",
                    year, month, day, hour, minute, second, millisecond, e);
            return null;
        }
    }

    /**
     * Builds and returns the LocalDate object based on the current date configuration.
     *
     * @return A LocalDate object created from the builder configuration
     */
    public LocalDate buildLocalDate() {
        try {
            return LocalDate.of(year, month, day);
        } catch (Exception e) {
            logger.error("Error building LocalDate with configuration: year={}, month={}, day={}",
                    year, month, day, e);
            return null;
        }
    }

    /**
     * Builds and returns a formatted string representation of the date.
     *
     * @return A formatted string representation of the built date
     */
    public String buildFormatted() {
        Date date = build();
        if (date != null) {
            return Time4j.format(date, TimeZone.getTimeZone(timezone), formatPattern);
        }
        return "";
    }

    /**
     * Builds and returns a formatted string representation using a custom format.
     *
     * @param customFormat The custom format pattern to use
     * @return A formatted string representation of the built date
     */
    public String buildFormatted(String customFormat) {
        Date date = build();
        if (date != null && String4j.isNotEmpty(customFormat)) {
            return Time4j.format(date, TimeZone.getTimeZone(timezone), customFormat);
        }
        return "";
    }
}