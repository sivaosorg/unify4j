package org.unify4j.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Numeric4j {
    public static final BigInteger BIG_INT_LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);
    public static final BigInteger BIG_INT_LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);
    public static final BigDecimal BIG_DEC_DOUBLE_MIN = BigDecimal.valueOf(-Double.MAX_VALUE);
    public static final BigDecimal BIG_DEC_DOUBLE_MAX = BigDecimal.valueOf(Double.MAX_VALUE);

    /**
     * Formats a given amount as currency according to the specified locale.
     *
     * @param amount The amount to be formatted.
     * @param locale The locale to be used for currency formatting.
     * @return A string representing the formatted currency.
     */
    public static String currency(double amount, Locale locale) {
        NumberFormat instance = NumberFormat.getCurrencyInstance(locale);
        return instance.format(amount);
    }

    /**
     * Formats a given amount as currency using specified language and country codes.
     *
     * @param amount   The amount to be formatted.
     * @param language The language code for the locale.
     * @param country  The country code for the locale.
     * @return A string representing the formatted currency.
     */
    public static String currency(double amount, String language, String country) {
        Locale locale;
        if (String4j.isAnyEmpty(language, country)) {
            locale = Locale.getDefault();
        } else {
            locale = new Locale(language, country);
        }
        return currency(amount, locale);
    }

    /**
     * Formats a given amount as currency according to the specified locale and grouping usage.
     *
     * @param amount  The amount to be formatted.
     * @param locale  The locale to be used for currency formatting.
     * @param isGroup Whether to use grouping separators.
     * @return A string representing the formatted currency.
     */
    public static String currency(Double amount, Locale locale, boolean isGroup) {
        String text = "";
        if (Object4j.allNotNull(amount, locale)) {
            NumberFormat instance = NumberFormat.getCurrencyInstance(locale);
            instance.setGroupingUsed(isGroup);
            text = instance.format(amount);
        }
        return text;
    }

    /**
     * Formats a given amount as currency according to the specified locale and grouping usage.
     *
     * @param amount  The amount to be formatted.
     * @param locale  The locale to be used for integer formatting.
     * @param isGroup Whether to use grouping separators.
     * @return A string representing the formatted currency.
     */
    public static String currency(Long amount, Locale locale, boolean isGroup) {
        String text = "";
        if (Object4j.allNotNull(amount, locale)) {
            NumberFormat instance = NumberFormat.getIntegerInstance(locale);
            instance.setGroupingUsed(isGroup);
            text = instance.format(amount);
        }
        return text;
    }

    /**
     * Formats a given amount as currency according to the specified precision, pattern, and locale.
     *
     * @param amount    The amount to be formatted.
     * @param precision The number of decimal places to display.
     * @param pattern   The pattern to be applied for formatting.
     * @param locale    The locale to be used for currency formatting.
     * @return A string representing the formatted currency.
     */
    public static String currency(double amount, int precision, String pattern, Locale locale) {
        NumberFormat instance = NumberFormat.getCurrencyInstance(locale);
        DecimalFormat format = (DecimalFormat) instance;
        format.setMinimumFractionDigits(precision);
        format.setMaximumFractionDigits(precision);
        format.setDecimalSeparatorAlwaysShown(true);
        format.applyPattern(pattern);
        return format.format(amount);
    }

    /**
     * Formats a given amount as currency according to the specified precision and locale.
     *
     * @param amount    The amount to be formatted.
     * @param precision The number of decimal places to display.
     * @param locale    The locale to be used for currency formatting.
     * @return A string representing the formatted currency.
     */
    public static String currency(double amount, int precision, Locale locale) {
        NumberFormat instance = NumberFormat.getCurrencyInstance(locale);
        instance.setMinimumFractionDigits(precision);
        instance.setMaximumFractionDigits(precision);
        return instance.format(amount);
    }

    /**
     * Calculate the minimum value from an array of values.
     *
     * @param values Array of values.
     * @return minimum value of the provided set.
     */
    public static long minimum(long... values) {
        final int len = values.length;
        long current = values[0];
        for (int i = 1; i < len; i++) {
            current = Math.min(values[i], current);
        }
        return current;
    }

    /**
     * Calculate the minimum value from an array of values.
     *
     * @param values Array of values.
     * @return minimum value of the provided set.
     */
    public static long maximum(long... values) {
        final int len = values.length;
        long current = values[0];
        for (int i = 1; i < len; i++) {
            current = Math.max(values[i], current);
        }
        return current;
    }

    /**
     * Calculate the minimum value from an array of values.
     *
     * @param values Array of values.
     * @return minimum value of the provided set.
     */
    public static double minimum(double... values) {
        final int len = values.length;
        double current = values[0];
        for (int i = 1; i < len; i++) {
            current = Math.min(values[i], current);
        }
        return current;
    }

    /**
     * Calculate the minimum value from an array of values.
     *
     * @param values Array of values.
     * @return minimum value of the provided set.
     */
    public static double maximum(double... values) {
        final int len = values.length;
        double current = values[0];
        for (int i = 1; i < len; i++) {
            current = Math.max(values[i], current);
        }
        return current;
    }

    /**
     * Calculate the minimum value from an array of values.
     *
     * @param values Array of values.
     * @return minimum value of the provided set.
     */
    public static BigInteger minimum(BigInteger... values) {
        final int len = values.length;
        if (len == 1) {
            if (values[0] == null) {
                throw new IllegalArgumentException("Cannot passed null BigInteger entry to minimum()");
            }
            return values[0];
        }
        BigInteger current = values[0];
        for (int i = 1; i < len; i++) {
            if (values[i] == null) {
                throw new IllegalArgumentException("Cannot passed null BigInteger entry to minimum()");
            }
            current = values[i].min(current);
        }
        return current;
    }

    /**
     * Calculate the minimum value from an array of values.
     *
     * @param values Array of values.
     * @return minimum value of the provided set.
     */
    public static BigInteger maximum(BigInteger... values) {
        final int len = values.length;
        if (len == 1) {
            if (values[0] == null) {
                throw new IllegalArgumentException("Cannot passed null BigInteger entry to maximum()");
            }
            return values[0];
        }
        BigInteger current = values[0];
        for (int i = 1; i < len; i++) {
            if (values[i] == null) {
                throw new IllegalArgumentException("Cannot passed null BigInteger entry to maximum()");
            }
            current = values[i].max(current);
        }
        return current;
    }

    /**
     * Calculate the minimum value from an array of values.
     *
     * @param values Array of values.
     * @return minimum value of the provided set.
     */
    public static BigDecimal minimum(BigDecimal... values) {
        final int len = values.length;
        if (len == 1) {
            if (values[0] == null) {
                throw new IllegalArgumentException("Cannot passed null BigDecimal entry to minimum()");
            }
            return values[0];
        }
        BigDecimal current = values[0];
        for (int i = 1; i < len; i++) {
            if (values[i] == null) {
                throw new IllegalArgumentException("Cannot passed null BigDecimal entry to minimum()");
            }
            current = values[i].min(current);
        }
        return current;
    }

    /**
     * Calculate the maximum value from an array of values.
     *
     * @param values Array of values.
     * @return maximum value of the provided set.
     */
    public static BigDecimal maximum(BigDecimal... values) {
        final int len = values.length;
        if (len == 1) {
            if (values[0] == null) {
                throw new IllegalArgumentException("Cannot pass null BigDecimal entry to maximum()");
            }
            return values[0];
        }
        BigDecimal current = values[0];
        for (int i = 1; i < len; i++) {
            if (values[i] == null) {
                throw new IllegalArgumentException("Cannot pass null BigDecimal entry to maximum()");
            }
            current = values[i].max(current);
        }
        return current;
    }

    /**
     * Parse the passed in String as a numeric value and return the minimal data type between Long, Double,
     * BigDecimal, or BigInteger.  Useful for processing values from JSON files.
     *
     * @param numStr String to parse.
     * @return Long, BigInteger, Double, or BigDecimal depending on the value.  If the value is and integer and
     * between the range of Long min/max, a Long is returned.  If the value is an integer and outside this range, a
     * BigInteger is returned.  If the value is a decimal but within the confines of a Double, then a Double is
     * returned, otherwise a BigDecimal is returned.
     */
    public static Number tryNumeric(String numStr) {
        boolean isNegative = numStr.startsWith("-");  // Handle and preserve negative signs correctly while removing leading zeros
        if (isNegative || numStr.startsWith("+")) {
            char sign = numStr.charAt(0);
            numStr = sign + numStr.substring(1).replaceFirst("^0+", "");
        } else {
            numStr = numStr.replaceFirst("^0+", "");
        }
        boolean hasDecimalPoint = false;
        boolean hasExponent = false;
        int mantissaSize = 0;
        StringBuilder exponentValue = new StringBuilder();
        int len = numStr.length();
        for (int i = 0; i < len; i++) {
            char c = numStr.charAt(i);
            if (c == '.') {
                hasDecimalPoint = true;
            } else if (c == 'e' || c == 'E') {
                hasExponent = true;
            } else if (c >= '0' && c <= '9') {
                if (!hasExponent) {
                    mantissaSize++; // Count digits in the mantissa only
                } else {
                    exponentValue.append(c);
                }
            }
        }
        if (hasDecimalPoint || hasExponent) {
            if (mantissaSize < 17 && (String4j.isEmpty(exponentValue) || Math.abs(Integer.parseInt(exponentValue.toString())) < 308)) {
                return Double.parseDouble(numStr);
            } else {
                return new BigDecimal(numStr);
            }
        } else {
            if (numStr.length() < 19) {
                return Long.parseLong(numStr);
            }
            BigInteger bigInt = new BigInteger(numStr);
            if (bigInt.compareTo(BIG_INT_LONG_MIN) >= 0 && bigInt.compareTo(BIG_INT_LONG_MAX) <= 0) {
                return bigInt.longValue(); // Correctly convert BigInteger back to Long if within range
            } else {
                return bigInt;
            }
        }
    }
}