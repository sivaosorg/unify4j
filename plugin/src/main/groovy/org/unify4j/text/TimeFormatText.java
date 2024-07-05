package org.unify4j.text;

@SuppressWarnings({"SpellCheckingInspection"})
public class TimeFormatText {

    /**
     * Format: 6/15/19, 10:54 PM
     * Description: Short pattern for date and time including month, day, year, hour, minutes, and AM/PM indicator.
     */
    public static final String SHORT_EPOCH_PATTERN = "M/d/yy, h:mm a";

    /**
     * Format: Jun 15, 2019, 10:54:25 PM
     * Description: Medium pattern for date and time including abbreviated month, day, year, hour, minutes, seconds, and AM/PM indicator.
     */
    public static final String MEDIUM_EPOCH_PATTERN = "MMM d, y, h:mm:ss a";

    /**
     * Format: June 15, 2019, at 10:54:25 PM GMT+5
     * Description: Long pattern for date and time including full month name, day, year, hour, minutes, seconds, AM/PM indicator, and timezone.
     */
    public static final String LONG_EPOCH_PATTERN = "MMMM d, y, h:mm:ss a z";

    /**
     * Format: Saturday, June 15, 2019, at 10:54:25 PM GMT+05:30
     * Description: Completed pattern for date and time including day of the week, full month name, day, year, hour, minutes, seconds, AM/PM indicator, and timezone.
     */
    public static final String COMPLETED_EPOCH_PATTERN = "EEEE, MMMM d, y, h:mm:ss a zzzz";

    /**
     * Format: 6/15/19
     * Description: Short pattern for date only including month, day, and year.
     */
    public static final String SHORT_DATE_EPOCH_PATTERN = "M/d/yy";

    /**
     * Format: Jun 15, 2019
     * Description: Medium pattern for date only including abbreviated month, day, and year.
     */
    public static final String MEDIUM_DATE_EPOCH_PATTERN = "MMM d, y";

    /**
     * Format: June 15, 2019,
     * Description: Long pattern for date only including full month name, day, and year.
     */
    public static final String LONG_DATE_EPOCH_PATTERN = "MMMM d, y";

    /**
     * Format: Saturday, June 15, 2019,
     * Description: Completed pattern for date only including day of the week, full month name, day, and year.
     */
    public static final String COMPLETED_DATE_EPOCH_PATTERN = "EEEE, MMMM d, y";

    /**
     * Format: Saturday June 15 2019 15:33:42 GMT-0400
     * Description: Completed pattern for date and time including day of the week, full month name, day, year, hour, minutes, seconds, and timezone offset.
     */
    public static final String COMPLETED_DATE_EPOCH_PATTERN_01 = "EEEE MMMM d y h:mm:ss z";

    /**
     * Format: 10:54 PM
     * Description: Short pattern for time only including hour, minutes, and AM/PM indicator.
     */
    public static final String SHORT_TIME_EPOCH_PATTERN = "h:mm a";

    /**
     * Format: 16:15
     * Description: Short pattern for time only in 24-hour format including hour and minutes.
     */
    public static final String SHORT_TIME_EPOCH_PATTERN_24H = "H:mm";

    /**
     * Format: 10:54:25 PM
     * Description: Medium pattern for time only including hour, minutes, seconds, and AM/PM indicator.
     */
    public static final String MEDIUM_TIME_EPOCH_PATTERN = "h:mm:ss a";

    /**
     * Format: 10:54:25 PM GMT+5
     * Description: Long pattern for time only including hour, minutes, seconds, AM/PM indicator, and timezone.
     */
    public static final String LONG_TIME_EPOCH_PATTERN = "h:mm:ss a z";

    /**
     * Format: 10:54:25 PM GMT+05:30
     * Description: Completed pattern for time only including hour, minutes, seconds, AM/PM indicator, and timezone with offset.
     */
    public static final String COMPLETED_TIME_EPOCH_PATTERN = "h:mm:ss a zzzz";

    /**
     * Format: 2020-09-26 22:00:00
     * Description: Pattern for a bibliographic style date and time including year, month, day, hour, minutes, and seconds.
     */
    public static final String BIBLIOGRAPHY_EPOCH_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * Format: 2024-07-06 00:37:34,566
     * Description: Pattern for a bibliographic style date and time including year, month, day, hour, minutes, seconds and milliseconds.
     */
    public static final String BIBLIOGRAPHY_COMPLETE_EPOCH_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * Format: Sat, 2024-07-06 00:37:34,566
     * Description: Pattern for a bibliographic style date and time including the day of the week (EEE for abbreviated day name) year, month, day, hour, minutes, seconds and milliseconds.
     */
    public static final String EEE_BIBLIOGRAPHY_COMPLETE_EPOCH_PATTERN = "EEE, yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * Format: 2020-09-26
     * Description: Short pattern for a bibliographic style date only including year, month, and day.
     */
    public static final String SHORT_BIBLIOGRAPHY_EPOCH_PATTERN = "yyyy-MM-dd";

    /**
     * Format: 2020-09-26
     * Description: Another short pattern for a bibliographic style date only including year, month, day, hour, minutes, and seconds.
     */
    public static final String SHORT_BIBLIOGRAPHY_EPOCH_PATTERN_2 = "yyyy-MM-dd hh mm ss";

    /**
     * Format: Sat, 26 Sep 2020
     * Description: Pattern for a GMT style date including abbreviated day of the week, day, abbreviated month, and year.
     */
    public static final String GMT_EPOCH_PATTERN = "EEE, d MMM yyyy";

    /**
     * Format: 00:00:00
     * Description: Pattern for time in HH:mm:ss format, indicating midnight.
     */
    public static final String SHORT_TIME_NUMBER_SECOND = "00:00:00";

    /**
     * Format: yyyy-MM-dd'T'HH:mm:ss.SSSX
     * Description: Pattern for legacy bibliographic date and time including year, month, day, hour, minutes, seconds, and timezone.
     */
    public static final String BIBLIOGRAPHY_LEGACY_EPOCH_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    /**
     * Format: yyyyMMdd
     * Description: Pattern for a short bibliographic date without any whitespace, including year, month, and day.
     */
    public static final String SHORT_BIBLIOGRAPHY_EPOCH_NO_WHITESPACE_PATTERN = "yyyyMMdd";

    /**
     * Format: dd/MM/yyyy HH:mm:ss a
     * Description: Pattern for a spreadsheet style date and time including day, month, year, hour, minutes, seconds, and AM/PM indicator.
     */
    public static final String SPREADSHEET_BIBLIOGRAPHY_EPOCH_PATTERN = "dd/MM/yyyy HH:mm:ss a";

    /**
     * Format: dd/MM/yyyy HH:mm:ss
     * Description: Custom pattern for date and time including day, month, year, hour, minutes, and seconds.
     */
    public static final String CUSTOM_BIBLIOGRAPHY_EPOCH_PATTERN = "dd/MM/yyyy HH:mm:ss";

    /**
     * Format: dd/MM/yyyy HH:mm
     * Description: Custom short pattern for date and time including day, month, year, hour, and minutes.
     */
    public static final String CUSTOM_SHORT_BIBLIOGRAPHY_EPOCH_PATTERN = "dd/MM/yyyy HH:mm";

    /**
     * Format: dd-MM-yyyy HH:mm
     * Description: Custom short pattern for date and time including day, month, year, hour, and minutes with hyphen as separator.
     */
    public static final String CUSTOM_SHORT_BIBLIOGRAPHY_SLASH_EPOCH_PATTERN = "dd-MM-yyyy HH:mm";

    /**
     * Format: EEE, dd MMM yyyy HH:mm:ss z
     * Description: Pattern following RFC 1123 format for date and time including abbreviated day of the week, day, abbreviated month, year, hour, minutes, seconds, and timezone.
     */
    private static final String RFC1123_EPOCH_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    /**
     * Format: HH:mm:ss
     * Description: Pattern for time only in 24-hour format including hour, minutes, and seconds.
     */
    public static final String SHORT_TIME_BIBLIOGRAPHY_EPOCH_PATTERN = "HH:mm:ss";

}

