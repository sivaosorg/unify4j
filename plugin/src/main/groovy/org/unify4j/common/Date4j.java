package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date utility for parsing String dates with optional times, especially when the input String formats
 * may be inconsistent.  This will parse the following formats:<br/>
 * <pre>
 * 12-31-2023, 12/31/2023, 12.31.2023     mm is 1-12 or 01-12, dd is 1-31 or 01-31, and yyyy can be 0000 to 9999.
 *
 * 2023-12-31, 2023/12/31, 2023.12.31     mm is 1-12 or 01-12, dd is 1-31 or 01-31, and yyyy can be 0000 to 9999.
 *
 * January 6th, 2024                Month (3-4 digit abbreviation or full English name), white-space and optional comma,
 *                                  day of month (1-31) with optional suffixes 1st, 3rd, 22nd, whitespace and
 *                                  optional comma, and yyyy (0000-9999)
 *
 * 17th January 2024                day of month (1-31) with optional suffixes (e.g. 1st, 3rd, 22nd),
 *                                  Month (3-4 digit abbreviation or full English name), whites space and optional comma,
 *                                  and yyyy (0000-9999)
 *
 * 2024 January 31st                4 digit year, white space and optional comma, Month (3-4 digit abbreviation or full
 *                                  English name), white space and optional command, and day of month with optional
 *                                  suffixes (1st, 3rd, 22nd)
 *
 * Sat Jan 6 11:06:10 EST 2024      Unix/Linux style.  Day of week (3-letter or full name), Month (3-4 digit or full
 *                                  English name), time hh:mm:ss, TimeZone (Java supported Timezone names), Year
 * </pre>
 * All dates can be followed by a Time, or the time can precede the Date. Whitespace or a single letter T must separate the
 * date and the time for the non-Unix time formats.  The Time formats supported:<br/>
 * <pre>
 * hh:mm                            hours (00-23), minutes (00-59).  24 hour format.
 *
 * hh:mm:ss                         hours (00-23), minutes (00-59), seconds (00-59).  24 hour format.
 *
 * hh:mm:ss.sssss                   hh:mm:ss and fractional seconds. Variable fractional seconds supported.
 *
 * hh:mm:offset -or-                offset can be specified as +HH:mm, +HHmm, +HH, -HH:mm, -HHmm, -HH, or Z (GMT)
 * hh:mm:ss.sss:offset              which will match: "12:34", "12:34:56", "12:34.789", "12:34:56.789", "12:34+01:00",
 *                                  "12:34:56+1:00", "12:34-01", "12:34:56-1", "12:34Z", "12:34:56Z"
 *
 * hh:mm:zone -or-                  Zone can be specified as Z (Zulu = UTC), older short forms: GMT, EST, CST, MST,
 * hh:mm:ss.sss:zone                PST, IST, JST, BST etc. as well as the long forms: "America/New_York", "Asia/Saigon",
 *                                  etc. See ZoneId.getAvailableZoneIds().
 * </pre>
 * DateUtilities will parse Epoch-based integer-based value. It is considered number of milliseconds since Jan, 1970 GMT.
 * <pre>
 * "0" to                           A string of numeric digits will be parsed and returned as the number of milliseconds
 * "999999999999999999"             the Unix Epoch, January 1st, 1970 00:00:00 UTC.
 * </pre>
 * On all patterns above (excluding the numeric epoch millis), if a day-of-week (e.g. Thu, Sunday, etc.) is included
 * (front, back, or between date and time), it will be ignored, allowing for even more formats than listed here.
 * The day-of-week is not be used to influence the Date calculation.
 */
@SuppressWarnings({"SpellCheckingInspection"})
public class Date4j {
    protected static final Logger logger = LoggerFactory.getLogger(Date4j.class);
    protected static final Pattern allDigits = Pattern.compile("^\\d+$");
    protected static final String days = "monday|mon|tuesday|tues|tue|wednesday|wed|thursday|thur|thu|friday|fri|saturday|sat|sunday|sun";
    protected static final String mos = "January|Jan|February|Feb|March|Mar|April|Apr|May|June|Jun|July|Jul|August|Aug|September|Sept|Sep|October|Oct|November|Nov|December|Dec";
    protected static final String yr = "[+-]?\\d{4,5}\\b";
    protected static final String d1or2 = "\\d{1,2}";
    protected static final String d2 = "\\d{2}";
    protected static final String ord = "st|nd|rd|th";
    protected static final String sep = "[./-]";
    protected static final String ws = "\\s+";
    protected static final String wsOp = "\\s*";
    protected static final String wsOrComma = "[ ,]+";
    protected static final String tzUnix = "[A-Z]{1,3}";
    protected static final String tz_Hh_MM = "[+-]\\d{1,2}:\\d{2}";
    protected static final String tz_Hh_MM_SS = "[+-]\\d{1,2}:\\d{2}:\\d{2}";
    protected static final String tz_HHMM = "[+-]\\d{4}";
    protected static final String tz_Hh = "[+-]\\d{1,2}";
    protected static final String tzNamed = wsOp + "\\[?[A-Za-z][A-Za-z0-9~/._+-]+]?";
    protected static final String nano = "\\.\\d+";

    // Patterns defined in BNF influenced style using above named elements
    protected static final Pattern isoDatePattern = Pattern.compile(                                 // Regex's using | (OR)
            "(" + yr + ")(" + sep + ")(" + d1or2 + ")" + "\\2" + "(" + d1or2 + ")|" +                // 2024/01/21 (yyyy/mm/dd -or- yyyy-mm-dd -or- yyyy.mm.dd)   [optional time, optional day of week]  \2 references 1st separator (ensures both same)
                    "(" + d1or2 + ")(" + sep + ")(" + d1or2 + ")" + "\\6(" + yr + ")");              // 01/21/2024 (mm/dd/yyyy -or- mm-dd-yyyy -or- mm.dd.yyyy)   [optional time, optional day of week]  \6 references 2nd 1st separator (ensures both same)
    protected static final Pattern alphaMonthPattern = Pattern.compile("\\b(" + mos + ")\\b" + wsOrComma + "(" + d1or2 + ")(" + ord + ")?" + wsOrComma + "(" + yr + ")|" +   // Jan 21st, 2024  (comma optional between all, day of week optional, time optional, ordinal text optional [st, nd, rd, th])
                    "(" + d1or2 + ")(" + ord + ")?" + wsOrComma + "\\b(" + mos + ")\\b" + wsOrComma + "(" + yr + ")|" +                                                             // 21st Jan, 2024
                    "(" + yr + ")" + wsOrComma + "\\b(" + mos + "\\b)" + wsOrComma + "(" + d1or2 + ")(" + ord + ")?",                                                               // 2024 Jan 21st
            Pattern.CASE_INSENSITIVE);
    protected static final Pattern unixDateTimePattern = Pattern.compile("\\b(" + days + ")\\b" + ws + "\\b(" + mos + ")\\b" + ws + "(" + d1or2 + ")" + ws + "(" + d2 + ":" + d2 + ":" + d2 + ")" + wsOp + "(" + tzUnix + ")?" + wsOp + "(" + yr + ")", Pattern.CASE_INSENSITIVE);
    protected static final Pattern timePattern = Pattern.compile("(" + d2 + "):(" + d2 + ")(?::(" + d2 + ")(" + nano + ")?)?(" + tz_Hh_MM_SS + "|" + tz_Hh_MM + "|" + tz_HHMM + "|" + tz_Hh + "|Z)?(" + tzNamed + ")?", Pattern.CASE_INSENSITIVE);
    protected static final Pattern dayPattern = Pattern.compile("\\b(" + days + ")\\b", Pattern.CASE_INSENSITIVE);
    protected static final Map<String, Integer> months = new ConcurrentHashMap<>();

    static {
        months.put("jan", 1);
        months.put("january", 1);
        months.put("feb", 2);
        months.put("february", 2);
        months.put("mar", 3);
        months.put("march", 3);
        months.put("apr", 4);
        months.put("april", 4);
        months.put("may", 5);
        months.put("jun", 6);
        months.put("june", 6);
        months.put("jul", 7);
        months.put("july", 7);
        months.put("aug", 8);
        months.put("august", 8);
        months.put("sep", 9);
        months.put("sept", 9);
        months.put("september", 9);
        months.put("oct", 10);
        months.put("october", 10);
        months.put("nov", 11);
        months.put("november", 11);
        months.put("dec", 12);
        months.put("december", 12);
    }

    /**
     * If the date-time given does not include a timezone offset or name, then ZoneId.systemDefault()
     * will be used. We recommend using parse(String, ZoneId, boolean) version, so you can control the default
     * timezone used when one is not specified.
     *
     * @param s String containing a date.  If there is excess content, it will throw an IllegalArgumentException.
     * @return Date instance that represents the passed in date.  See comments at top of class for supported
     * formats.  This API is intended to be super flexible in terms of what it can parse. If a null or empty String is
     * passed in, null will be returned.
     */
    public static Date parse(String s) {
        if (String4j.isEmpty(s)) {
            return null;
        }
        ZonedDateTime time = parse(s, ZoneId.systemDefault(), true);
        if (time == null) {
            return null;
        }
        Instant instant = Instant.from(time);
        return Date.from(instant);
    }

    /**
     * Retrieve date-time from passed in String.  The boolean ensureDateTimeAlone, if set true, ensures that
     * no other non-date content existed in the String.
     *
     * @param s                   String containing a date.
     * @param defaultZoneId       ZoneId to use if no timezone offset or name is given.  Cannot be null.
     * @param ensureDateTimeAlone If true, if there is excess non-Date content, it will throw an IllegalArgument exception.
     * @return ZonedDateTime instance converted from the passed in date String.  See comments at top of class for supported
     * formats.  This function is intended to be super flexible in terms of what it can parse. If a null or empty String is
     * passed in, null will be returned.
     */
    public static ZonedDateTime parse(String s, ZoneId defaultZoneId, boolean ensureDateTimeAlone) {
        if (String4j.isEmpty(s)) {
            return null;
        }
        s = String4j.trimWhitespace(s);
        Vi4j.throwIfNull(defaultZoneId, "ZoneId cannot be null.  Use ZoneId.of(\"America/New_York\"), ZoneId.systemDefault(), etc.");
        if (allDigits.matcher(s).matches()) {
            return Instant.ofEpochMilli(Long.parseLong(s)).atZone(defaultZoneId);
        }
        String year, day, remains, tz = null;
        int month;
        // Determine which date pattern to use
        Matcher matcher = isoDatePattern.matcher(s);
        String remnant = matcher.replaceFirst("");
        if (remnant.length() < s.length()) {
            if (matcher.group(1) != null) {
                year = matcher.group(1);
                month = Integer.parseInt(matcher.group(3));
                day = matcher.group(4);
            } else {
                year = matcher.group(8);
                month = Integer.parseInt(matcher.group(5));
                day = matcher.group(7);
            }
            remains = remnant;
        } else {
            matcher = alphaMonthPattern.matcher(s);
            remnant = matcher.replaceFirst("");
            if (remnant.length() < s.length()) {
                String mon;
                if (matcher.group(1) != null) {
                    mon = matcher.group(1);
                    day = matcher.group(2);
                    year = matcher.group(4);
                    remains = remnant;
                } else if (matcher.group(7) != null) {
                    mon = matcher.group(7);
                    day = matcher.group(5);
                    year = matcher.group(8);
                    remains = remnant;
                } else {
                    year = matcher.group(9);
                    mon = matcher.group(10);
                    day = matcher.group(11);
                    remains = remnant;
                }
                month = months.get(mon.trim().toLowerCase());
            } else {
                matcher = unixDateTimePattern.matcher(s);
                if (matcher.replaceFirst("").length() == s.length()) {
                    throw new IllegalArgumentException(String.format("Unable to parse %s as a date-time", s));
                }
                year = matcher.group(6);
                String mon = matcher.group(2);
                month = months.get(mon.trim().toLowerCase());
                day = matcher.group(3);
                tz = matcher.group(5);
                remains = matcher.group(4);     // leave optional time portion remaining
            }
        }
        // For the remaining String, match the time portion (which could have appeared ahead of the date portion)
        String hour = null, min = null, sec = "00", fracSec = "0";
        remains = remains.trim();
        matcher = timePattern.matcher(remains);
        remnant = matcher.replaceFirst("");
        if (remnant.length() < remains.length()) {
            hour = matcher.group(1);
            min = matcher.group(2);
            if (matcher.group(3) != null) {
                sec = matcher.group(3);
            }
            if (matcher.group(4) != null) {
                fracSec = "0" + matcher.group(4);
            }
            if (matcher.group(5) != null) {
                tz = matcher.group(5).trim();
            }
            if (matcher.group(6) != null) {
                // to make round trip of ZonedDateTime equivalent we need to use the original Zone as ZoneId
                // is a much broader definition handling multiple possible dates, and we want this to
                // be equivalent to the original zone that was used if one was present.
                tz = String4j.removeBrackets(matcher.group(6).trim());
            }
        }
        if (ensureDateTimeAlone) {
            verifyNoExtraneous(remnant);
        }
        ZoneId zoneId = String4j.isEmpty(tz) ? defaultZoneId : Time4j.parseTimeZone(tz);
        return parseDateTime(s, zoneId, year, month, day, hour, min, sec, fracSec);
    }

    /**
     * Constructs a ZonedDateTime object from the provided date and time components.
     * Parses the year, month, day, hour, minute, second, and fractional second components to create
     * a ZonedDateTime instance in the specified time zone (zoneId).
     * If any component is invalid or out of range, throws an IllegalArgumentException with an appropriate message.
     *
     * @param dateTime             The original date-time string for error reporting purposes.
     * @param zoneId               The ZoneId representing the time zone of the resulting ZonedDateTime.
     * @param year                 The year component of the date.
     * @param month                The month component of the date (1-12).
     * @param day                  The day component of the date (1-31).
     * @param hour                 The hour component of the time (0-23).
     * @param min                  The minute component of the time (0-59).
     * @param sec                  The second component of the time (0-59).
     * @param fractionalSecondText The fractional second component of the time as a string representation.
     * @return A ZonedDateTime object representing the specified date and time.
     * @throws IllegalArgumentException if any component is invalid or out of range.
     */
    private static ZonedDateTime parseDateTime(String dateTime, ZoneId zoneId, String year, int month, String day, String hour, String min, String sec, String fractionalSecondText) {
        int y = Integer.parseInt(year);
        int d = Integer.parseInt(day);
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException(String.format("Month must be between 1 and 12 inclusive, date: %s", dateTime));
        }
        if (d < 1 || d > 31) {
            throw new IllegalArgumentException(String.format("Day must be between 1 and 31 inclusive, date: %s", dateTime));
        }
        // Check if time portion is present.
        if (hour == null) { // no [valid] time portion
            // Construct ZonedDateTime without time components.
            return ZonedDateTime.of(y, month, d, 0, 0, 0, 0, zoneId);
        } else {
            // Parse time components to integers.
            int h = Integer.parseInt(hour);
            int mn = Integer.parseInt(min);
            int s = Integer.parseInt(sec);
            // Convert fractional second text to nanoseconds.
            long nanoOfSec = Time4j.fromFractionToNanos(fractionalSecondText);
            // Validate hour component.
            if (h > 23) {
                throw new IllegalArgumentException(String.format("Hour must be between 0 and 23 inclusive, time: %s", dateTime));
            }
            // Validate minute component.
            if (mn > 59) {
                throw new IllegalArgumentException(String.format("Minute must be between 0 and 59 inclusive, time: %s", dateTime));
            }
            // Validate second component.
            if (s > 59) {
                throw new IllegalArgumentException(String.format("Second must be between 0 and 59 inclusive, time: %s", dateTime));
            }
            return ZonedDateTime.of(y, month, d, h, mn, s, (int) nanoOfSec, zoneId);
        }
    }

    /**
     * Verifies that the provided string contains no extraneous non-date-related content.
     * This function removes any day-of-week substrings and verifies that the remaining content
     * is either empty or consists solely of the characters 'T' or ','.
     * If any other characters are found, an IllegalArgumentException is thrown.
     *
     * @param remnant The string to verify for non-date-related content.
     * @throws IllegalArgumentException if extraneous non-date content is found in the string.
     */
    private static void verifyNoExtraneous(String remnant) {
        if (String4j.isEmpty(remnant)) {
            return;
        }
        // Remove any day-of-week substrings (e.g., mon, tue, wed, ...).
        if (String4j.length(remnant) > 0) {
            Matcher day = dayPattern.matcher(remnant);
            remnant = day.replaceFirst("").trim();
        }
        // Ensure that the remaining content is either empty, "T", or ",".
        if (String4j.length(remnant) > 0) {
            remnant = remnant.replaceAll("[T,]", "").trim();
            Vi4j.throwIfNullOrEmpty(remnant, String.format("Issue parsing date-time, other characters present: %s", remnant));
        }
    }
}
