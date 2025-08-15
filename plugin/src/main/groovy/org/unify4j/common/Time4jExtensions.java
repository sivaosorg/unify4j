package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.enums.TimePeriodType;
import org.unify4j.model.request.DateRangeRequest;
import org.unify4j.model.response.DateStatisticsResponse;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public class Time4jExtensions {
    protected static final Logger logger = LoggerFactory.getLogger(Time4jExtensions.class);

    /**
     * Creates a TimeDecorator for enhanced date operations.
     * This method serves as a factory method to create decorated date instances.
     *
     * @param date The date to decorate
     * @return A TimeDecorator instance for enhanced operations
     */
    public static Time4jDecorator decorate(Date date) {
        return Time4jDecorator.of(date);
    }

    /**
     * Creates a TimeBuilder for fluent date construction.
     * This method serves as a factory method to create builder instances.
     *
     * @return A TimeBuilder instance for fluent date construction
     */
    public static Time4jBuilder builder() {
        return Time4jBuilder.create();
    }

    /**
     * Calculates the age in years between two dates.
     * This function determines the number of complete years between the birthdate and the reference date.
     *
     * @param birthdate     The birthdate
     * @param referenceDate The reference date (typically current date)
     * @return The age in complete years
     */
    public static long calculateAge(Date birthdate, Date referenceDate) {
        if (birthdate == null || referenceDate == null) {
            return 0;
        }
        LocalDate birth = Time4j.transformLocal(birthdate);
        LocalDate reference = Time4j.transformLocal(referenceDate);
        return ChronoUnit.YEARS.between(birth, reference);
    }

    /**
     * Calculates the age in years from birthdate to current date.
     * This function determines the number of complete years from birthdate to now.
     *
     * @param birthdate The birthdate
     * @return The current age in complete years
     */
    public static long calculateAge(Date birthdate) {
        return calculateAge(birthdate, new Date());
    }

    /**
     * Checks if a given year is a leap year.
     * This function determines whether the specified year has 366 days (leap year) or 365 days.
     *
     * @param year The year to check
     * @return true if the year is a leap year, false otherwise
     */
    public static boolean isLeapYear(int year) {
        return Year.of(year).isLeap();
    }

    /**
     * Checks if a given date falls within a leap year.
     * This function determines whether the specified date's year is a leap year.
     *
     * @param date The date to check
     * @return true if the date's year is a leap year, false otherwise
     */
    public static boolean isLeapYear(Date date) {
        if (date == null) {
            return false;
        }
        LocalDate local = Time4j.transformLocal(date);
        return isLeapYear(local.getYear());
    }

    /**
     * Gets the number of days in a specific month and year.
     * This function returns the total number of days in the specified month and year,
     * accounting for leap years when calculating February.
     *
     * @param year  The year
     * @param month The month (1-12, where 1 is January)
     * @return The number of days in the specified month and year
     */
    public static int getDaysInMonth(int year, int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        return YearMonth.of(year, month).lengthOfMonth();
    }

    /**
     * Gets the number of days in the month of the specified date.
     * This function returns the total number of days in the month containing the specified date.
     *
     * @param date The date whose month to analyze
     * @return The number of days in the month of the specified date
     */
    public static int getDaysInMonth(Date date) {
        if (date == null) {
            return 0;
        }
        LocalDate local = Time4j.transformLocal(date);
        return local.lengthOfMonth();
    }

    /**
     * Gets all dates within a specified date range.
     * This function generates a list of all dates between the start and end dates, inclusive.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return A list of all dates within the specified range
     */
    public static List<Date> getDateRange(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new ArrayList<>();
        }

        LocalDate start = Time4j.transformLocal(startDate);
        LocalDate end = Time4j.transformLocal(endDate);

        return start.datesUntil(end.plusDays(1))
                .map(Time4j::transform)
                .collect(Collectors.toList());
    }

    /**
     * Gets all weekdays (Monday to Friday) within a specified date range.
     * This function filters the date range to include only business days.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return A list of all weekdays within the specified range
     */
    public static List<Date> getWeekdaysInRange(Date startDate, Date endDate) {
        return getDateRange(startDate, endDate).stream()
                .filter(Time4jExtensions::isWeekday)
                .collect(Collectors.toList());
    }

    /**
     * Gets all weekends (Saturday and Sunday) within a specified date range.
     * This function filters the date range to include only weekend days.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return A list of all weekend days within the specified range
     */
    public static List<Date> getWeekendsInRange(Date startDate, Date endDate) {
        return getDateRange(startDate, endDate).stream()
                .filter(Time4jExtensions::isWeekend)
                .collect(Collectors.toList());
    }

    /**
     * Checks if a given date is a weekday (Monday to Friday).
     * This function determines whether the specified date falls on a business day.
     *
     * @param date The date to check
     * @return true if the date is a weekday, false otherwise
     */
    public static boolean isWeekday(Date date) {
        if (date == null) {
            return false;
        }
        DayOfWeek dayOfWeek = Time4j.getDayOfWeek(Time4j.transformLocal(date));
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    /**
     * Checks if a given date is a weekend (Saturday or Sunday).
     * This function determines whether the specified date falls on a weekend.
     *
     * @param date The date to check
     * @return true if the date is a weekend, false otherwise
     */
    public static boolean isWeekend(Date date) {
        return !isWeekday(date) && date != null;
    }

    /**
     * Gets the next business day after the specified date.
     * This function finds the next weekday (Monday to Friday) following the given date.
     *
     * @param date The reference date
     * @return The next business day after the specified date
     */
    public static Date getNextBusinessDay(Date date) {
        if (date == null) {
            return null;
        }

        Date nextDay = Time4j.addDays(date, 1);
        while (isWeekend(nextDay)) {
            nextDay = Time4j.addDays(nextDay, 1);
        }
        return nextDay;
    }

    /**
     * Gets the previous business day before the specified date.
     * This function finds the previous weekday (Monday to Friday) before the given date.
     *
     * @param date The reference date
     * @return The previous business day before the specified date
     */
    public static Date getPreviousBusinessDay(Date date) {
        if (date == null) {
            return null;
        }

        Date previousDay = Time4j.minusDays(date, 1);
        while (isWeekend(previousDay)) {
            previousDay = Time4j.minusDays(previousDay, 1);
        }
        return previousDay;
    }

    /**
     * Calculates the number of business days between two dates.
     * This function counts only weekdays (Monday to Friday) in the specified range.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @return The number of business days between the dates
     */
    public static long getBusinessDaysBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return getWeekdaysInRange(startDate, endDate).size();
    }

    /**
     * Gets the quarter of the year for the specified date.
     * This function determines which quarter (1-4) the date falls into.
     *
     * @param date The date to analyze
     * @return The quarter number (1-4) of the specified date
     */
    public static int getQuarter(Date date) {
        if (date == null) {
            return 0;
        }
        LocalDate local = Time4j.transformLocal(date);
        return (local.getMonthValue() - 1) / 3 + 1;
    }

    /**
     * Gets the first day of the quarter for the specified date.
     * This function returns the first date of the quarter containing the specified date.
     *
     * @param date The reference date
     * @return The first day of the quarter containing the specified date
     */
    public static Date getFirstDayOfQuarter(Date date) {
        if (date == null) {
            return null;
        }
        LocalDate local = Time4j.transformLocal(date);
        int quarter = getQuarter(date);
        int firstMonthOfQuarter = (quarter - 1) * 3 + 1;
        LocalDate firstDayOfQuarter = LocalDate.of(local.getYear(), firstMonthOfQuarter, 1);
        return Time4j.transform(firstDayOfQuarter);
    }

    /**
     * Gets the last day of the quarter for the specified date.
     * This function returns the last date of the quarter containing the specified date.
     *
     * @param date The reference date
     * @return The last day of the quarter containing the specified date
     */
    public static Date getLastDayOfQuarter(Date date) {
        if (date == null) {
            return null;
        }
        LocalDate local = Time4j.transformLocal(date);
        int quarter = getQuarter(date);
        int lastMonthOfQuarter = quarter * 3;
        LocalDate lastDayOfQuarter = LocalDate.of(local.getYear(), lastMonthOfQuarter, 1)
                .with(TemporalAdjusters.lastDayOfMonth());
        return Time4j.transform(lastDayOfQuarter);
    }

    /**
     * Calculates the time zone offset in hours for a given date and time zone.
     * This function determines how many hours ahead or behind UTC the specified time zone is.
     *
     * @param date     The reference date
     * @param timezone The time zone to analyze
     * @return The offset in hours from UTC (positive for ahead, negative for behind)
     */
    public static int getTimezoneOffset(Date date, TimeZone timezone) {
        if (date == null || timezone == null) {
            return 0;
        }
        return timezone.getOffset(date.getTime()) / (1000 * 60 * 60);
    }

    /**
     * Converts a date from one time zone to another.
     * This function adjusts the date to represent the same moment in a different time zone.
     *
     * @param date         The date to convert
     * @param fromTimezone The source time zone
     * @param toTimezone   The target time zone
     * @return The date adjusted to the target time zone
     */
    public static Date convertTimezone(Date date, TimeZone fromTimezone, TimeZone toTimezone) {
        if (date == null || fromTimezone == null || toTimezone == null) {
            return date;
        }

        // Convert to UTC first, then to target timezone
        long utcTime = date.getTime() - fromTimezone.getOffset(date.getTime());
        long targetTime = utcTime + toTimezone.getOffset(utcTime);
        return new Date(targetTime);
    }

    /**
     * Gets the ISO week number for the specified date.
     * This function returns the week number according to ISO 8601 standard.
     *
     * @param date The date to analyze
     * @return The ISO week number (1-53)
     */
    public static int getISOWeekNumber(Date date) {
        if (date == null) {
            return 0;
        }
        LocalDate local = Time4j.transformLocal(date);
        return local.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
    }

    /**
     * Gets the week year according to ISO 8601 for the specified date.
     * This function returns the year that contains the majority of days in the week.
     *
     * @param date The date to analyze
     * @return The ISO week year
     */
    public static int getISOWeekYear(Date date) {
        if (date == null) {
            return 0;
        }
        LocalDate local = Time4j.transformLocal(date);
        return local.get(java.time.temporal.IsoFields.WEEK_BASED_YEAR);
    }

    /**
     * Checks if two dates are in the same week according to ISO 8601.
     * This function determines if two dates fall within the same ISO week.
     *
     * @param date1 The first date to compare
     * @param date2 The second date to compare
     * @return true if the dates are in the same ISO week, false otherwise
     */
    public static boolean isSameISOWeek(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        return getISOWeekNumber(date1) == getISOWeekNumber(date2) &&
                getISOWeekYear(date1) == getISOWeekYear(date2);
    }

    /**
     * Gets all dates for a specific day of the week within a date range.
     * This function finds all occurrences of a specific day (e.g., all Mondays) in the range.
     *
     * @param startDate The start date of the range
     * @param endDate   The end date of the range
     * @param dayOfWeek The day of the week to find
     * @return A list of dates that fall on the specified day of the week
     */
    public static List<Date> getDatesForDayOfWeek(Date startDate, Date endDate, DayOfWeek dayOfWeek) {
        if (startDate == null || endDate == null || dayOfWeek == null) {
            return new ArrayList<>();
        }

        return getDateRange(startDate, endDate).stream()
                .filter(date -> Time4j.getDayOfWeek(Time4j.transformLocal(date)) == dayOfWeek)
                .collect(Collectors.toList());
    }

    /**
     * Calculates the number of working hours between two dates.
     * Assumes standard working hours (9 AM to 5 PM) on weekdays only.
     *
     * @param startDate The start date
     * @param endDate   The end date
     * @return The number of working hours between the dates
     */
    public static long getWorkingHoursBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return 0;
        }

        long workingHours = 0;
        List<Date> weekdays = getWeekdaysInRange(startDate, endDate);

        for (Date day : weekdays) {
            Date workStart = Time4jBuilder.from(day).hour(9).minute(0).second(0).build();
            Date workEnd = Time4jBuilder.from(day).hour(17).minute(0).second(0).build();

            Date effectiveStart = startDate.after(workStart) ? startDate : workStart;
            Date effectiveEnd = endDate.before(workEnd) ? endDate : workEnd;

            if (effectiveStart.before(effectiveEnd)) {
                long millisBetween = effectiveEnd.getTime() - effectiveStart.getTime();
                workingHours += millisBetween / (1000 * 60 * 60); // Convert to hours
            }
        }

        return workingHours;
    }

    /**
     * Gets all months between two dates.
     * Returns a list of the first day of each month in the range.
     *
     * @param startDate The start date
     * @param endDate   The end date
     * @return A list of first days of months in the range
     */
    public static List<Date> getMonthsBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new ArrayList<>();
        }

        List<Date> months = new ArrayList<>();
        LocalDate start = Time4j.transformLocal(startDate);
        LocalDate end = Time4j.transformLocal(endDate);

        LocalDate current = start.withDayOfMonth(1);
        while (!current.isAfter(end)) {
            months.add(Time4j.transform(current));
            current = current.plusMonths(1);
        }

        return months;
    }

    /**
     * Gets all years between two dates.
     * Returns a list of January 1st for each year in the range.
     *
     * @param startDate The start date
     * @param endDate   The end date
     * @return A list of first days of years in the range
     */
    public static List<Date> getYearsBetween(Date startDate, Date endDate) {
        if (startDate == null || endDate == null || startDate.after(endDate)) {
            return new ArrayList<>();
        }

        List<Date> years = new ArrayList<>();
        LocalDate start = Time4j.transformLocal(startDate);
        LocalDate end = Time4j.transformLocal(endDate);

        LocalDate current = LocalDate.of(start.getYear(), 1, 1);
        while (current.getYear() <= end.getYear()) {
            years.add(Time4j.transform(current));
            current = current.plusYears(1);
        }

        return years;
    }

    /**
     * Finds the closest date to a target date from a list of dates.
     *
     * @param targetDate The target date to find the closest match for
     * @param candidates The list of candidate dates
     * @return The closest date to the target, or null if no candidates
     */
    public static Date findClosestDate(Date targetDate, List<Date> candidates) {
        if (targetDate == null || Collection4j.isEmpty(candidates)) {
            return null;
        }

        Date closest = null;
        long minDifference = Long.MAX_VALUE;

        for (Date candidate : candidates) {
            if (candidate != null) {
                long difference = Math.abs(candidate.getTime() - targetDate.getTime());
                if (difference < minDifference) {
                    minDifference = difference;
                    closest = candidate;
                }
            }
        }

        return closest;
    }

    /**
     * Checks if a date matches a specific pattern (day of week, week of month, etc.).
     *
     * @param date        The date to check
     * @param dayOfWeek   The required day of week (null to ignore)
     * @param weekOfMonth The required week of month (null to ignore, 1-5)
     * @param month       The required month (null to ignore, 1-12)
     * @return true if the date matches all specified criteria
     */
    public static boolean matchesPattern(Date date, DayOfWeek dayOfWeek, Integer weekOfMonth, Integer month) {
        if (date == null) {
            return false;
        }

        LocalDate local = Time4j.transformLocal(date);

        // Check day of week
        if (dayOfWeek != null && local.getDayOfWeek() != dayOfWeek) {
            return false;
        }

        // Check month
        if (month != null && local.getMonthValue() != month) {
            return false;
        }

        // Check week of month
        if (weekOfMonth != null) {
            int actualWeekOfMonth = (local.getDayOfMonth() - 1) / 7 + 1;
            return actualWeekOfMonth == weekOfMonth;
        }

        return true;
    }

    /**
     * Gets all dates in a range that match a specific pattern.
     *
     * @param startDate   The start date of the range
     * @param endDate     The end date of the range
     * @param dayOfWeek   The required day of week (null to ignore)
     * @param weekOfMonth The required week of month (null to ignore)
     * @param month       The required month (null to ignore)
     * @return A list of dates matching the pattern
     */
    public static List<Date> findDatesMatchingPattern(Date startDate, Date endDate,
                                                      DayOfWeek dayOfWeek, Integer weekOfMonth, Integer month) {
        return getDateRange(startDate, endDate).stream()
                .filter(date -> matchesPattern(date, dayOfWeek, weekOfMonth, month))
                .collect(Collectors.toList());
    }

    /**
     * Calculates basic statistics for a collection of dates.
     *
     * @param dates The collection of dates to analyze
     * @return A DateStatistics object containing min, max, average, etc.
     */
    @SuppressWarnings({"SimplifyStreamApiCallChains"})
    public static DateStatisticsResponse calculateDateStatistics(Collection<Date> dates) {
        if (Collection4j.isEmpty(dates)) {
            return new DateStatisticsResponse();
        }

        List<Date> validDates = dates.stream()
                .filter(Objects::nonNull)
                .sorted()
                .collect(Collectors.toList());

        if (validDates.isEmpty()) {
            return new DateStatisticsResponse();
        }

        Date min = validDates.get(0);
        Date max = validDates.get(validDates.size() - 1);

        // Calculate average
        long totalMillis = validDates.stream()
                .mapToLong(Date::getTime)
                .sum();
        Date average = new Date(totalMillis / validDates.size());

        // Calculate median
        Date median;
        int size = validDates.size();
        if (size % 2 == 0) {
            long medianMillis = (validDates.get(size / 2 - 1).getTime() +
                    validDates.get(size / 2).getTime()) / 2;
            median = new Date(medianMillis);
        } else {
            median = validDates.get(size / 2);
        }

        return new DateStatisticsResponse(min, max, average, median, validDates.size());
    }

    /**
     * Gets the business day offset from a given date.
     * Positive offset moves forward, negative moves backward.
     *
     * @param startDate         The starting date
     * @param businessDayOffset The number of business days to offset
     * @return The date after applying the business day offset
     */
    public static Date getBusinessDayOffset(Date startDate, int businessDayOffset) {
        if (startDate == null || businessDayOffset == 0) {
            return startDate;
        }

        Date current = new Date(startDate.getTime());
        int remaining = Math.abs(businessDayOffset);
        boolean forward = businessDayOffset > 0;

        while (remaining > 0) {
            current = forward ? Time4j.addDays(current, 1) : Time4j.minusDays(current, 1);
            if (isWeekday(current)) {
                remaining--;
            }
        }

        return current;
    }

    /**
     * Checks if a date falls within a specific time period pattern.
     *
     * @param date          The date to check
     * @param pattern       The time period pattern (DAILY, WEEKLY, MONTHLY, YEARLY)
     * @param referenceDate The reference date for the pattern
     * @return true if the date matches the pattern relative to reference date
     */
    @SuppressWarnings({"EnhancedSwitchMigration"})
    public static boolean isInTimePeriod(Date date, TimePeriodType pattern, Date referenceDate) {
        if (date == null || pattern == null || referenceDate == null) {
            return false;
        }

        LocalDate localDate = Time4j.transformLocal(date);
        LocalDate refDate = Time4j.transformLocal(referenceDate);

        switch (pattern) {
            case DAILY:
                return localDate.equals(refDate);
            case WEEKLY:
                return isSameISOWeek(date, referenceDate);
            case MONTHLY:
                return localDate.getMonth() == refDate.getMonth() &&
                        localDate.getYear() == refDate.getYear();
            case YEARLY:
                return localDate.getYear() == refDate.getYear();
            default:
                return false;
        }
    }

    /**
     * Gets the overlap period between two date ranges.
     *
     * @param start1 Start of first range
     * @param end1   End of first range
     * @param start2 Start of second range
     * @param end2   End of second range
     * @return A DateRange object representing the overlap, or null if no overlap
     */
    public static DateRangeRequest getOverlapPeriod(Date start1, Date end1, Date start2, Date end2) {
        if (start1 == null || end1 == null || start2 == null || end2 == null) {
            return null;
        }

        Date overlapStart = start1.after(start2) ? start1 : start2;
        Date overlapEnd = end1.before(end2) ? end1 : end2;

        if (overlapStart.before(overlapEnd) || overlapStart.equals(overlapEnd)) {
            return new DateRangeRequest(overlapStart, overlapEnd);
        }

        return null;
    }
}