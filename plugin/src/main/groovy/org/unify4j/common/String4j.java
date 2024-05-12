package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class String4j {

    protected static final Logger logger = LoggerFactory.getLogger(String4j.class);

    /**
     * Checks if the provided CharSequence is null or empty.
     *
     * @param cs the CharSequence to check
     * @return true if the CharSequence is null or empty, false otherwise
     */
    @SuppressWarnings({"SizeReplaceableByIsEmpty"})
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Checks if the provided CharSequence is not null and not empty.
     *
     * @param cs the CharSequence to check
     * @return true if the CharSequence is not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * Trims leading and trailing whitespace from the given string and replaces multiple consecutive whitespace characters with a single space.
     * If the input string is null or empty, returns an empty string.
     *
     * @param str the string to be trimmed
     * @return the trimmed string with single whitespace between words
     */
    public static String trimWhitespace(String str) {
        if (isEmpty(str)) {
            return "";
        }
        return str.trim().replaceAll("\\s{2,}", " ");
    }

    /**
     * Trim <i>all</i> whitespace from the given {@code String}:
     * leading, trailing, and in between characters.
     *
     * @param str the {@code String} to check
     * @return the trimmed {@code String}
     * @see java.lang.Character#isWhitespace
     */
    public static String trimWhitespaces(String str) {
        if (isEmpty(str)) {
            return str;
        }
        int len = str.length();
        StringBuilder sb = new StringBuilder(str.length());
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (!Character.isWhitespace(c)) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Masks a portion of the given string with the specified character.
     *
     * @param string      the string to be masked
     * @param start       the start index (inclusive) of the portion to be masked
     * @param end         the end index (exclusive) of the portion to be masked
     * @param characterBy the character used for masking
     * @return the string with the specified portion masked
     * @throws Exception if the end index is greater than the length of the string or if the start index is greater than the end index
     */
    @SuppressWarnings({"StringRepeatCanBeUsed"})
    public static String mask(String string, int start, int end, char characterBy) throws Exception {
        if (isEmpty(string)) {
            return string;
        }
        if (start < 0) {
            start = 0;
        }
        if (end > string.length()) {
            end = string.length();
        }
        if (start > end) {
            throw new Exception("End index cannot be greater than start index");
        }
        int mask = end - start;
        if (mask == 0) {
            return string;
        }
        StringBuilder builder = new StringBuilder(mask);
        for (int i = 0; i < mask; i++) {
            builder.append(characterBy);
        }
        return string.substring(0, start) + builder.toString() + string.substring(start + mask);
    }

    /**
     * Masks the local part of an email address with the specified character, leaving the domain part unchanged.
     *
     * @param email    the email address to be masked
     * @param maskChar the character used for masking
     * @return the masked email address
     * @throws Exception if the email address does not contain a "@" symbol or if an exception occurs while masking the local part
     */
    public static String maskEmail(String email, char maskChar) throws Exception {
        String[] parts = email.split("@");
        if (parts.length != 2) {
            throw new Exception("Invalid email address format");
        }
        String maskValue;
        if (parts[0].length() < 4) {
            maskValue = mask(parts[0], 0, parts[0].length(), maskChar);
        } else {
            maskValue = mask(parts[0], 1, parts[0].length() - 1, maskChar);
        }
        return String.format("%s@%s", maskValue, parts[1]);
    }
}
