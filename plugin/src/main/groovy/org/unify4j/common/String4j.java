package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class String4j {
    protected static final Logger logger = LoggerFactory.getLogger(String4j.class);
    protected static char[] hexes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected static String EMPTY = "";

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

    /**
     * Checks if any of the given CharSequence elements are empty.
     *
     * @param css an array of CharSequence elements to be checked.
     * @return {@code true} if any element in the array is null or has a length of 0, {@code false} otherwise.
     * <p>
     * This method first checks if the provided array is not null and contains elements.
     * If the array is empty, it returns {@code false}.
     * Then, it iterates over each element in the array and checks if it is empty using the {@code isEmpty} method.
     * If any element is found to be empty, the method returns {@code true}.
     * If all elements are non-empty, it returns {@code false}.
     * <p>
     * Note: This method assumes the existence of an {@code isEmpty(CharSequence cs)} utility method
     * that checks if a CharSequence is null or has a length of 0.
     * <p>
     */
    public static boolean isAnyEmpty(final CharSequence... css) {
        if (css == null) {
            return true;
        }
        for (CharSequence cs : css) {
            if (isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyNotEmpty(final CharSequence... css) {
        return !isAnyEmpty(css);
    }

    /**
     * Checks if a CharSequence is blank.
     * <p>
     * A CharSequence is considered blank if it is null, has a length of 0, or contains only whitespace characters.
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty, or contains only whitespace characters; {@code false} otherwise
     * <p>
     * This method first checks if the input CharSequence is null or has a length of 0.
     * If either condition is true, it returns {@code true}.
     * If the CharSequence is non-null and non-empty, it iterates over each character in the CharSequence.
     * It uses {@code Character.isWhitespace(char)} to check if each character is a whitespace character.
     * If a non-whitespace character is found, the method returns {@code false}.
     * If only whitespace characters are found, the method returns {@code true}.
     */
    public static boolean isBlank(final CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(final CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * Checks if any of the given CharSequence elements are blank.
     * <p>
     * A CharSequence is considered blank if it is null, has a length of 0, or contains only whitespace characters.
     *
     * @param css an array of CharSequence elements to be checked, may be null
     * @return {@code true} if any CharSequence in the array is null, empty, or contains only whitespace characters; {@code false} otherwise
     * <p>
     * This method first checks if the provided array is not null and contains elements.
     * If the array is empty, it returns {@code false} since there are no elements to be blank.
     * Then, it iterates over each element in the array and checks if it is blank using the {@code isBlank} method.
     * If any element is found to be blank, the method returns {@code true}.
     * If all elements are non-blank, it returns {@code false}.
     * <p>
     * Note: This method assumes the existence of an {@code isBlank(CharSequence cs)} utility method
     * that checks if a CharSequence is null, empty, or contains only whitespace characters.
     * <p>
     */
    public static boolean isAnyBlank(final CharSequence... css) {
        if (css == null) {
            return true;
        }
        for (CharSequence cs : css) {
            if (isBlank(cs)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isAnyNotBlank(final CharSequence... css) {
        return !isAnyBlank(css);
    }

    /**
     * Converts the first character of the given string to uppercase.
     *
     * @param in the input string, may be null
     * @return the modified string with the first character in uppercase, or the original string if it is null or empty
     * <p>
     * This method first checks if the input string is null or has a length of 0.
     * If either condition is true, it returns the input string as it is.
     * If the string is non-null and non-empty, it initializes a StringBuilder with the same length as the input string.
     * The first character of the input string is converted to uppercase and appended to the StringBuilder.
     * If the string has more than one character, the remaining part of the string is appended to the StringBuilder.
     * Finally, the StringBuilder is converted to a string and returned.
     */
    public static String uppercaseFirstChar(String in) {
        if (isEmpty(in)) {
            return in;
        }
        int length = in.length();
        StringBuilder sb = new StringBuilder(length);
        sb.append(Character.toUpperCase(in.charAt(0)));
        if (length > 1) {
            String remaining = in.substring(1);
            sb.append(remaining);
        }
        return sb.toString();
    }

    /**
     * Checks if the given CharSequence is numeric.
     * <p>
     * A CharSequence is considered numeric if it is not null, not empty, and contains only digit characters.
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is not null, not empty, and contains only digit characters; {@code false} otherwise
     * <p>
     * This method first checks if the input CharSequence is empty using the {@code isEmpty} method.
     * If it is empty, the method returns {@code false}.
     * Then, it iterates over each character in the CharSequence.
     * It uses {@code Character.isDigit(char)} to check if each character is a digit.
     * If any character is not a digit, the method returns {@code false}.
     * If all characters are digits, the method returns {@code true}.
     * <p>
     * Note: This method assumes the existence of an {@code isEmpty(CharSequence cs)} utility method
     * that checks if a CharSequence is null or has a length of 0.
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (isEmpty(cs)) {
            return false;
        }
        final int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Quote the given {@code String} with single quotes.
     *
     * @param str the input {@code String} (e.g. "myString")
     * @return the quoted {@code String} (e.g. "'myString'"),
     * or {@code null} if the input was {@code null}
     */

    public static String quote(String str) {
        return (str != null ? "'" + str + "'" : null);
    }

    /**
     * Checks if the given string has length.
     * <p>
     * A string is considered to have length if it is not null and its length is greater than 0.
     *
     * @param str the string to check, may be null
     * @return {@code true} if the string is not null and its length is greater than 0; {@code false} otherwise
     * <p>
     * This method first checks if the input string is not null. If the string is null, it returns {@code false}.
     * If the string is not null, it then checks if its length is greater than 0.
     * If both conditions are satisfied, the method returns {@code true}.
     * Otherwise, it returns {@code false}.
     */
    @SuppressWarnings({"SizeReplaceableByIsEmpty", "BooleanMethodIsAlwaysInverted"})
    public static boolean hasLength(String str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Trims trailing whitespace characters from the given string.
     * <p>
     * This method removes all trailing whitespace characters from the input string.
     *
     * @param str the string from which to trim trailing whitespace, may be null
     * @return the modified string with trailing whitespace removed, or the original string if it is null or has no length
     * <p>
     * This method first checks if the input string has length using the {@code hasLength} method.
     * If the string is null or has no length, it returns the input string as it is.
     * If the string has length, it initializes a StringBuilder with the input string.
     * It then iterates backwards from the end of the StringBuilder, removing characters as long as they are whitespace.
     * Once all trailing whitespace characters are removed, the StringBuilder is converted to a string and returned.
     * <p>
     * Note: This method assumes the existence of a {@code hasLength(String str)} utility method
     * that checks if a string is not null and has a length greater than 0.
     */
    public static String trimTrailingWhitespace(String str) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (isNotEmpty(sb) && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Trim all occurrences of the supplied leading character from the given
     * {@code String}.
     *
     * @param str              the {@code String} to check
     * @param leadingCharacter the leading character to be trimmed
     * @return the trimmed {@code String}
     */
    public static String trimLeadingCharacter(String str, char leadingCharacter) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (isNotEmpty(sb) && sb.charAt(0) == leadingCharacter) {
            sb.deleteCharAt(0);
        }
        return sb.toString();
    }

    /**
     * Trim all occurrences of the supplied trailing character from the given
     * {@code String}.
     *
     * @param str               the {@code String} to check
     * @param trailingCharacter the trailing character to be trimmed
     * @return the trimmed {@code String}
     */
    public static String trimTrailingCharacter(String str, char trailingCharacter) {
        if (!hasLength(str)) {
            return str;
        }
        StringBuilder sb = new StringBuilder(str);
        while (isNotEmpty(sb) && sb.charAt(sb.length() - 1) == trailingCharacter) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * Truncates the given string to the specified maximum length.
     * <p>
     * This method truncates the input string to the specified maximum length,
     * retaining only the characters from the beginning of the string up to the maximum length.
     *
     * @param str       the string to be truncated, may be null
     * @param maxLength the maximum length of the truncated string
     * @return the truncated string, or the original string if it is null or shorter than the maximum length,
     * or an empty string if the maximum length is less than 1
     * <p>
     * This method first checks if the input string is null or shorter than or equal to the maximum length.
     * If any of these conditions are true, it returns the input string as it is.
     * If the input string is longer than the maximum length and the maximum length is greater than or equal to 1,
     * it extracts a substring from the beginning of the input string up to the maximum length (exclusive).
     * The extracted substring is then returned as the truncated string.
     */
    public static String truncate(String str, int maxLength) {
        if (isEmpty(str) || str.length() <= maxLength || maxLength < 1) {
            return str;
        }
        return str.substring(0, maxLength);
    }

    /**
     * Capitalizes each word in the given string.
     * <p>
     * This method capitalizes the first character of each word in the input string and converts
     * the remaining characters to lowercase. Words are separated by whitespace characters.
     *
     * @param cs the string to be capitalized, may be null
     * @return the modified string with each word capitalized, or an empty string if the input string is null or empty
     * <p>
     * This method first checks if the input string is empty using the {@code isEmpty} method.
     * If the string is null or empty, it returns an empty string.
     * Otherwise, it trims leading and trailing whitespace from the string using the {@code trimAsWhitespace} method.
     * It then iterates over each character in the string, capitalizing the first character of each word
     * and converting the rest to lowercase.
     * A word is considered to be a sequence of alphabetic characters separated by non-alphabetic characters.
     * Characters following an apostrophe ('), such as in contractions, are not considered the start of a new word.
     * The modified character array is converted back to a string and returned.
     * <p>
     * Note: This method assumes the existence of an {@code isEmpty(String str)} utility method
     * that checks if a string is null or has a length of 0, and a {@code trimAsWhitespace(String str)} utility method
     * that trims leading and trailing whitespace characters from a string.
     */
    public static String capitalizeEachWords(String cs) {
        if (isEmpty(cs)) {
            return "";
        }
        cs = trimWhitespace(cs);
        boolean first = true;
        char[] str = cs.toCharArray();
        for (int i = 0; i < str.length; ++i) {
            char character = str[i];
            str[i] = first && (i == 0 || str[i - 1] != '\'') ? Character.toUpperCase(character) : Character.toLowerCase(character);
            first = !Character.isAlphabetic(character);
        }
        return new String(str);
    }

    /**
     * Removes the last 'n' characters from the given string.
     * <p>
     * This method removes the last 'n' characters from the input string 's'.
     *
     * @param s the string from which to remove characters, may be null
     * @param n the number of characters to remove from the end of the string
     * @return the modified string with the last 'n' characters removed, or the original string if it is null or empty,
     * or an empty string if 'n' is greater than or equal to the length of the string
     * <p>
     * This method first checks if the input string is not empty using the {@code isEmpty} method.
     * If the string is not empty, it calculates the new length after removing 'n' characters from the end of the string.
     * Then, it extracts a substring from the beginning of the input string up to the new length.
     * The extracted substring is returned as the modified string.
     */
    public static String removeLastChar(String s, int n) {
        if (!isEmpty(s) && n > 0 && s.length() > n) {
            return s.substring(0, s.length() - n);
        }
        return "";
    }

    /**
     * <p>
     * Returns the first value in the array which is not empty (""),
     * {@code null} or whitespace only.
     * </p>
     *
     * <p>
     * Whitespace is defined by {@link Character#isWhitespace(char)}.
     * </p>
     *
     * <p>
     * If all values are blank or the array is {@code null} or empty then
     * {@code null} is returned.
     * </p>
     *
     * @param <T>    the specific kind of CharSequence
     * @param values the values to test, may be {@code null} or empty
     * @return the first value from {@code values} which is not blank, or
     * {@code null} if there are no non-blank values
     * @since 3.8
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonBlank(final T... values) {
        if (values != null) {
            for (final T val : values) {
                if (isNotBlank(val)) {
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * <p>
     * Returns the first value in the array which is not empty.
     * </p>
     *
     * <p>
     * If all values are empty or the array is {@code null} or empty then
     * {@code null} is returned.
     * </p>
     *
     * @param <T>    the specific kind of CharSequence
     * @param values the values to test, may be {@code null} or empty
     * @return the first value from {@code values} which is not empty, or
     * {@code null} if there are no non-empty values
     * @since 3.8
     */
    @SafeVarargs
    public static <T extends CharSequence> T firstNonEmpty(final T... values) {
        if (values != null) {
            for (final T val : values) {
                if (isNotEmpty(val)) {
                    return val;
                }
            }
        }
        return null;
    }

    /**
     * Counts the number of words in the given string.
     * <p>
     * This method counts the number of words in the input string by splitting it into
     * individual words based on whitespace characters and then counting the resulting array.
     *
     * @param str the string in which to count words, may be null
     * @return the number of words in the string, or 0 if the string is null or empty
     * <p>
     * This method first trims any leading and trailing whitespace characters from the input string
     * using the {@code trimSingleWhitespace} method.
     * Then, it splits the trimmed string into individual words using the regular expression "\\s+".
     * Finally, it returns the length of the resulting array, which represents the number of words in the string.
     */
    public static int countWords(String str) {
        str = trimWhitespace(str);
        if (!isEmpty(str)) {
            return str.split("\\s+").length;
        }
        return 0;
    }

    /**
     * Converts a camelCase string to snake_case.
     * <p>
     * This method converts the input camelCase string to snake_case format.
     *
     * @param camel       the camelCase string to be converted, may be null or empty
     * @param isUpperCase a boolean indicating whether to convert to uppercase snake_case, default is false
     * @return the string converted to snake_case, or an empty string if the input is null or empty
     * <p>
     * This method first checks if the input camelCase string is not empty.
     * If the string is empty, it returns an empty string.
     * Then, it checks if the 'isUpperCase' parameter is null or not.
     * If the parameter is null, it sets the default value to false.
     * The method then performs the conversion from camelCase to snake_case using regular expressions.
     * If 'isUpperCase' is true, it converts the result to uppercase; otherwise, it converts to lowercase.
     */
    public static String camelToSnakeCase(String camel, boolean isUpperCase) {
        if (isEmpty(camel)) {
            return "";
        }
        String format = camel.replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2").replaceAll("([a-z])([A-Z])", "$1_$2");
        return isUpperCase ? format.toUpperCase() : format.toLowerCase();
    }

    /**
     * Test if the given {@code String} starts with the specified prefix,
     * * ignoring upper/lower case.
     * * @param str the {@code String} to check
     * * @param prefix the prefix to look for
     * * @see java.lang.String#startsWith
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return (str != null && prefix != null && str.length() >= prefix.length() && str.regionMatches(true, 0, prefix, 0, prefix.length()));
    }

    /**
     * Removes a prefix from the beginning of the given string.
     * <p>
     * This method removes the specified prefix from the beginning of the input string,
     * if the string is not empty and starts with the given prefix (case insensitive).
     *
     * @param text   the string from which to remove the prefix, may be null or empty
     * @param prefix the prefix to be removed, may be null or empty
     * @return the modified string with the prefix removed, or the original string if the prefix is not found
     * <p>
     * This method first checks if the input string is not empty and if it starts with the given prefix
     * (ignoring case) using the {@code startsWithIgnoreCase} method.
     * If both conditions are satisfied, it splits the string into two parts using the prefix as the delimiter,
     * and returns the second part of the split (the substring after the prefix).
     * If the prefix is not found or the string is empty, it returns the original string as it is.
     */
    public static String removePrefix(String text, String prefix) {
        if (isNotEmpty(text) && startsWithIgnoreCase(text, prefix)) {
            return text.split(prefix, 2)[1];
        }
        return text;
    }

    /**
     * Removes a prefix of specified length from the beginning of the given string.
     * <p>
     * This method removes the specified number of characters as a prefix from the beginning of the input string,
     * if the string is not empty and the prefix length is valid.
     *
     * @param text   the string from which to remove the prefix, may be null or empty
     * @param prefix the length of the prefix to be removed, must be non-negative
     * @return the modified string with the prefix removed, or the original string if it is empty or the prefix length is invalid
     * <p>
     * This method first checks if the input string is not empty and if the specified prefix length is valid.
     * If both conditions are satisfied, it extracts a substring starting from the index 'prefix' to the end of the string,
     * effectively removing the prefix.
     * If the string is empty or the prefix length is invalid, it returns the original string as it is.
     */
    @SuppressWarnings({"StringOperationCanBeSimplified"})
    public static String removePrefix(String text, int prefix) {
        if (isNotEmpty(text) && prefix >= 0 && prefix <= text.length()) {
            return text.substring(prefix, text.length());
        }
        return text;
    }

    /**
     * Removes one or more prefixes from the beginning of the given string.
     * <p>
     * This method removes the specified prefixes from the beginning of the input string,
     * up to a specified number of rounds.
     *
     * @param text     the string from which to remove the prefixes, may be null or empty
     * @param prefixes the prefixes to be removed, may be null or empty
     * @return the modified string with the prefixes removed, or the original string if it is empty or the prefixes are not found
     * <p>
     * This method delegates to the overloaded method {@code removePrefix(String text, List<String> prefixes, int round)}
     * with a default round value of 1.
     */
    public static String removePrefix(String text, String... prefixes) {
        return removePrefix(text, Arrays.asList(prefixes));
    }

    /**
     * Removes one or more prefixes from the beginning of the given string.
     * <p>
     * This method removes the specified prefixes from the beginning of the input string,
     * up to a specified number of rounds.
     *
     * @param text     the string from which to remove the prefixes, may be null or empty
     * @param prefixes the prefixes to be removed, may be null or empty
     * @return the modified string with the prefixes removed, or the original string if it is empty or the prefixes are not found
     * <p>
     * This method delegates to the overloaded method {@code removePrefix(String text, List<String> prefixes, int round)}
     * with a default round value of 1.
     */
    public static String removePrefix(String text, List<String> prefixes) {
        return removePrefix(text, prefixes, 1);
    }

    /**
     * Removes one or more prefixes from the beginning of the given string.
     * <p>
     * This method removes the specified prefixes from the beginning of the input string,
     * up to a specified number of rounds.
     *
     * @param text     the string from which to remove the prefixes, may be null or empty
     * @param prefixes the prefixes to be removed, may be null or empty
     * @param round    the number of rounds to remove prefixes, must be non-negative
     * @return the modified string with the prefixes removed, or the original string if it is empty or the prefixes are not found
     * <p>
     * This method first checks if the input string and the list of prefixes are not empty.
     * If either of them is empty, it returns the original string as it is.
     * Then, it iterates over each prefix in the list, removing it from the input string.
     * The process repeats for the specified number of rounds.
     * The modified string with the prefixes removed is returned.
     */
    public static String removePrefix(String text, List<String> prefixes, int round) {
        if (Collection4j.isEmpty(prefixes) || round <= 0) {
            return text;
        }
        int count = 0;
        for (String prefix : prefixes) {
            if (count == round) {
                break;
            }
            text = removePrefix(text, prefix);
            count++;
        }
        return text;
    }

    /**
     * Removes all specific characters from the given string.
     * <p>
     * This method removes all occurrences of specific characters from the input string.
     *
     * @param text the string from which to remove characters, may be null or empty
     * @return the modified string with specific characters removed, or the original string if it is null or empty
     * <p>
     * This method first checks if the input string is not empty.
     * If the string is empty, it returns the original string as it is.
     * Then, it replaces all occurrences of the specified characters with a space character.
     * The modified string with specific characters removed is returned.
     */
    public static String removeAllSpecificCharacters(String text) {
        if (isEmpty(text)) {
            return text;
        }
        return text.replaceAll("[-+_.^:!?;$#%&@*|()={},]", " ");
    }

    /**
     * Replaces whitespace characters in the given string with the specified symbol.
     * <p>
     * This method replaces all whitespace characters in the input string with the specified symbol.
     *
     * @param string the string in which to replace whitespace characters, may be null or empty
     * @param symbol the symbol to use as replacement, may be null or empty
     * @return the modified string with whitespace replaced by the specified symbol,
     * or the original string if it is null, empty, or the symbol is null or empty
     * <p>
     * This method first checks if the input string or the symbol is not empty.
     * If either of them is empty, it returns the original string as it is.
     * Then, it trims any leading and trailing whitespace characters from the input string
     * using the {@code trimSingleWhitespace} method.
     * After trimming, it replaces all consecutive whitespace characters with the specified symbol
     * using the regular expression "\\s+".
     * The modified string with whitespace replaced by the symbol is returned.
     */
    public static String replaceWhitespace(String string, String symbol) {
        if (isAnyEmpty(string, symbol)) {
            return string;
        }
        string = trimWhitespace(string);
        return string.replaceAll("\\s+", symbol);
    }

    /**
     * Strips diacritical marks (accents) from the given string.
     * This function normalizes the input string to decomposed form using Normalizer,
     * then removes all combining diacritical marks.
     *
     * @param str The string from which diacritical marks are to be stripped.
     * @return The input string with diacritical marks removed, or null if the input string is null.
     */
    public static String stripAccents(String str) {
        return str == null ? null : Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    /**
     * Removes non-ASCII characters (including diacritical marks) from the given string.
     * This function normalizes the input string to decomposed form using Normalizer,
     * then removes all characters that are not in the ASCII character set.
     *
     * @param str The string from which non-ASCII characters are to be removed.
     * @return The input string with non-ASCII characters removed.
     */
    public static String unAccents(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    /**
     * Checks if a CharSequence is empty (""), null or whitespace only.
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     */
    public static boolean isWhitespace(CharSequence cs) {
        int strLen = length(cs);
        if (strLen == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a String is not empty (""), not null and not whitespace only.
     *
     * @param s the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace only
     */
    public static boolean hasContent(String s) {
        return !isWhitespace(s);
    }

    /**
     * Gets a CharSequence length or {@code 0} if the CharSequence is {@code null}.
     *
     * @param cs a CharSequence or {@code null}
     * @return CharSequence length or {@code 0} if the CharSequence is {@code null}.
     */
    public static int length(CharSequence cs) {
        return cs == null ? 0 : cs.length();
    }

    /**
     * Removes the leading and trailing brackets from a string, if present.
     * This function checks if the input string is empty or null using the String4j utility,
     * and then uses a regular expression to replace any brackets at the start or end of the string.
     *
     * @param input the string from which brackets are to be removed
     * @return the input string with leading and trailing brackets removed, or the original string if it is empty or null
     */
    public static String removeBrackets(String input) {
        if (String4j.isEmpty(input)) {
            return input;
        }
        return input.replaceAll("^\\[|]$", "");
    }

    /**
     * Green implementation of regionMatches.
     *
     * @param cs         the {@link CharSequence} to be processed
     * @param ignoreCase whether to be case-insensitive
     * @param thisStart  the index to start on the {@code cs} CharSequence
     * @param substring  the {@link CharSequence} to be looked for
     * @param start      the index to start on the {@code substring} CharSequence
     * @param length     character length of the region
     * @return whether the region matched
     */
    @SuppressWarnings({"SameParameterValue"})
    static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
        Vi4j.throwIfNull(cs, "cs to be processed cannot be null");
        Vi4j.throwIfNull(substring, "substring cannot be null");
        if (cs instanceof String && substring instanceof String) {
            return ((String) cs).regionMatches(ignoreCase, thisStart, (String) substring, start, length);
        }
        int index1 = thisStart;
        int index2 = start;
        int tmp = length;
        // Extract these first, so we detect NPEs the same as the java.lang.String version
        int srcLen = cs.length() - thisStart;
        int otherLen = substring.length() - start;
        if (thisStart < 0 || start < 0 || length < 0) {
            return false;
        }
        // Check that the regions are long enough
        if (srcLen < length || otherLen < length) {
            return false;
        }
        while (tmp-- > 0) {
            char c1 = cs.charAt(index1++);
            char c2 = substring.charAt(index2++);
            if (c1 == c2) {
                continue;
            }
            if (!ignoreCase) {
                return false;
            }
            char u1 = Character.toUpperCase(c1);
            char u2 = Character.toUpperCase(c2);
            if (u1 != u2 && Character.toLowerCase(u1) != Character.toLowerCase(u2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters.
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered to be equal. The comparison is <strong>case-sensitive</strong>.</p>
     *
     * @param cs1 the first CharSequence, may be {@code null}
     * @param cs2 the second CharSequence, may be {@code null}
     * @return {@code true} if the CharSequences are equal (case-sensitive), or both {@code null}
     * @see #equalsIgnoreCase(CharSequence, CharSequence)
     */
    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return cs1.equals(cs2);
        }
        // Step-wise comparison
        int length = cs1.length();
        for (int i = 0; i < length; i++) {
            if (cs1.charAt(i) != cs2.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @see String4j#equals(CharSequence, CharSequence)
     */
    public static boolean equals(String s1, String s2) {
        return equals((CharSequence) s1, (CharSequence) s2);
    }

    /**
     * Compares two CharSequences, returning {@code true} if they represent
     * equal sequences of characters, ignoring case.
     *
     * <p>{@code null}s are handled without exceptions. Two {@code null}
     * references are considered equal. The comparison is <strong>case insensitive</strong>.</p>
     *
     * @param cs1 the first CharSequence, may be {@code null}
     * @param cs2 the second CharSequence, may be {@code null}
     * @return {@code true} if the CharSequences are equal (case-insensitive), or both {@code null}
     * @see #equals(CharSequence, CharSequence)
     */
    public static boolean equalsIgnoreCase(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        }
        if (cs1 == null || cs2 == null) {
            return false;
        }
        if (cs1.length() != cs2.length()) {
            return false;
        }
        return regionMatches(cs1, true, 0, cs2, 0, cs1.length());
    }

    /**
     * @see String4j#equalsIgnoreCase(CharSequence, CharSequence)
     */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        return equalsIgnoreCase((CharSequence) s1, (CharSequence) s2);
    }

    /**
     * Compares two strings after removing leading and trailing whitespace from both.
     *
     * @param s1 The first string to compare. It can be null.
     * @param s2 The second string to compare. It can be null.
     * @return true if the trimmed versions of the strings are equal, false otherwise. If both strings are null, true is returned.
     */
    public static boolean equalsWithTrim(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return Objects.equals(s1, s2);
        }
        return s1.trim().equals(s2.trim());
    }

    /**
     * Compares two strings after removing leading and trailing whitespace from both, ignoring case differences.
     *
     * @param s1 The first string to compare. It can be null.
     * @param s2 The second string to compare. It can be null.
     * @return true if the trimmed versions of the strings are equal ignoring case differences, false otherwise.
     * If both strings are null, true is returned.
     */
    public static boolean equalsIgnoreCaseWithTrim(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return Objects.equals(s1, s2);
        }
        return s1.trim().equalsIgnoreCase(s2.trim());
    }

    /**
     * Decodes a hexadecimal string into a byte array.
     *
     * @param s The hexadecimal string to decode. It should contain only hexadecimal characters [0-9a-fA-F].
     *          If the length of the string is odd, the function returns null.
     * @return A byte array representing the decoded hexadecimal string.
     * Each pair of hexadecimal characters in the input string corresponds to one byte in the output array.
     * If the input string is null or empty, the function returns an empty byte array.
     */
    public static byte[] decode(String s) {
        int len = s.length();
        // If the length of the string is odd, return null as it cannot be properly decoded.
        if (len % 2 != 0) {
            return null;
        }
        // Initialize a byte array to hold the decoded bytes.
        byte[] bytes = new byte[len / 2];
        int position = 0; // Position to insert decoded bytes into the byte array.
        // Iterate over the characters of the input string in pairs, decoding them into bytes.
        for (int i = 0; i < len; i += 2) {
            // Decode the current pair of characters into bytes.
            byte hi = (byte) Character.digit(s.charAt(i), 16);
            byte lo = (byte) Character.digit(s.charAt(i + 1), 16);
            // Combine the hi and lo bytes to form the decoded byte.
            bytes[position++] = (byte) (hi * 16 + lo);
        }
        return bytes;
    }

    /**
     * Convert a byte array into a printable format containing a
     * String of hex digit characters (two per byte).
     *
     * @param bytes array representation
     */
    public static String encode(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length << 1);
        for (byte b : bytes) {
            sb.append(toDigit(b >> 4));
            sb.append(toDigit(b & 0x0f));
        }
        return sb.toString();
    }

    /**
     * Counts the number of occurrences of the specified 'token' within the given 'content'.
     *
     * @param content The CharSequence in which to search for occurrences of the token.
     * @param token   The CharSequence representing the token whose occurrences are to be counted.
     * @return The number of times the token occurs within the content.
     * Returns 0 if either the content or the token is null, or if the content is an empty string,
     * or if the token is an empty string.
     */
    public static int count(CharSequence content, CharSequence token) {
        // If either the content or the token is null, return 0.
        if (content == null || token == null) {
            return 0;
        }
        String source = content.toString();
        // If the source string is empty, return 0.
        if (isEmpty(source)) {
            return 0;
        }
        String sub = token.toString();
        // If the token string is empty, return 0.
        if (isEmpty(sub)) {
            return 0;
        }
        int answer = 0; // Initialize the counter for token occurrences.
        int idx = 0; // Initialize the index for searching within the source string.
        // Loop through the source string to find occurrences of the token.
        while (true) {
            idx = source.indexOf(sub, idx); // Find the next occurrence of the token.
            // If no further occurrences are found, return the count of token occurrences.
            if (idx < answer) {
                return answer;
            }
            ++answer; // Increment the count of token occurrences.
            ++idx; // Move the search index to the next position after the current occurrence.
        }
    }

    public static int count(String s, char c) {
        return count(s, EMPTY + c);
    }

    /**
     * Convert the specified value (0 ... 15) to the corresponding hex digit.
     *
     * @param value to be converted
     * @return '0'...'F' in char format.
     */
    public static char toDigit(int value) {
        return hexes[value & 0x0f];
    }

    /**
     * Convert a String into a byte[] with a particular encoding.
     * Preferable used when the encoding is one of the guaranteed Java types,
     * and you don't want to have to catch the UnsupportedEncodingException
     * required by Java
     *
     * @param s        string to encode into bytes
     * @param encoding encoding to use
     */
    public static byte[] getBytes(String s, String encoding) {
        try {
            return isEmpty(s) ? null : s.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(String.format("Encoding (%s) is not supported by your JVM", encoding), e);
        }
    }

    /**
     * Converts a byte array into a String using a specified encoding.
     * This method is preferable when the encoding is one of the guaranteed Java types,
     * and you want to avoid handling the UnsupportedEncodingException manually.
     *
     * @param bytes    The byte array to encode into a string.
     * @param encoding The character encoding to use for the conversion.
     * @return The resulting String decoded from the byte array using the specified encoding.
     * Returns null if the input byte array is null.
     * @throws IllegalArgumentException If the specified encoding is not supported by the JVM.
     */
    public static String create(byte[] bytes, String encoding) {
        try {
            return bytes == null ? null : new String(bytes, encoding);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(String.format("Encoding (%s) is not supported by your JVM", encoding), e);
        }
    }

    /**
     * Converts a byte array into a UTF-8 encoded String.
     * This method is preferable when the encoding is one of the guaranteed Java types,
     * and you want to avoid handling the UnsupportedEncodingException manually.
     *
     * @param bytes The byte array to be decoded into a UTF-8 string.
     * @return The resulting UTF-8 encoded String decoded from the byte array.
     * Returns null if the input byte array is null.
     */
    public static String createUTF8(byte[] bytes) {
        return create(bytes, "UTF-8");
    }

    /**
     * Converts a String into a byte array encoded by UTF-8.
     *
     * @param s The string to be encoded into bytes using UTF-8 encoding.
     * @return The byte array representing the UTF-8 encoded bytes of the input string.
     * Returns null if the input string is null.
     */
    public static byte[] getUTF8Bytes(String s) {
        return getBytes(s, "UTF-8");
    }

    /**
     * Repeats the given string a specified number of times.
     *
     * @param s   the string to be repeated
     * @param cnt the number of times to repeat the string
     * @return a new string consisting of the original string repeated the specified number of times,
     * or the original string if it is empty
     * @throws IllegalArgumentException if the repeat count is negative
     *                                  <p>
     *                                  This method suppresses the "StringRepeatCanBeUsed" warning as it manually implements the repeat logic
     *                                  to maintain compatibility with Java versions prior to 11 where the String::repeat method is not available.
     */
    @SuppressWarnings({"StringRepeatCanBeUsed"})
    public static String repeat(String s, int cnt) {
        if (isEmpty(s)) {
            return s;
        }
        if (cnt < 0) {
            throw new IllegalArgumentException("Repeat count must be non-negative");
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cnt; i++) {
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * Repeats the given string a specified number of times, with an optional separator.
     *
     * @param s         the string to be repeated
     * @param cnt       the number of times to repeat the string
     * @param separator the separator to be placed between repetitions, may be null or empty
     * @return a new string consisting of the original string repeated the specified number of times,
     * separated by the specified separator, or the original string if it is empty
     * @throws IllegalArgumentException if the repeat count is negative
     *                                  <p>
     *                                  This method suppresses the "StringRepeatCanBeUsed" warning as it manually implements the repeat logic
     *                                  to maintain compatibility with Java versions prior to 11 where the String::repeat method is not available.
     */
    public static String repeat(String s, int cnt, String separator) {
        if (isEmpty(s)) {
            return s;
        }
        if (cnt < 0) {
            throw new IllegalArgumentException("Repeat count must be non-negative");
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < cnt; i++) {
            if (i > 0 && isNotEmpty(separator)) {
                builder.append(separator);
            }
            builder.append(s);
        }
        return builder.toString();
    }

    /**
     * Repeats the given object a specified number of times.
     * <p>
     * This method converts the object to a string representation and repeats it the specified number of times.
     *
     * @param o   the object to be repeated, may be null
     * @param cnt the number of times to repeat the object, must be non-negative
     * @return a new string consisting of the object's string representation repeated the specified number of times,
     * or null if the object is null
     * @throws IllegalArgumentException if the repeat count is negative
     */
    public static String repeat(Object o, int cnt) {
        if (o == null) {
            return null;
        }
        if (cnt < 0) {
            throw new IllegalArgumentException("Repeat count must be non-negative");
        }
        String str = Json4j.marshall(o);
        return repeat(str, cnt);
    }

    /**
     * Repeats the given object a specified number of times, with an optional separator.
     * <p>
     * This method converts the object to a string representation and repeats it the specified number of times,
     * inserting the specified separator between repetitions.
     *
     * @param o         the object to be repeated, may be null
     * @param cnt       the number of times to repeat the object, must be non-negative
     * @param separator the separator to be placed between repetitions, may be null or empty
     * @return a new string consisting of the object's string representation repeated the specified number of times,
     * separated by the specified separator, or null if the object is null
     * @throws IllegalArgumentException if the repeat count is negative
     */
    public static String repeat(Object o, int cnt, String separator) {
        if (o == null) {
            return null;
        }
        if (cnt < 0) {
            throw new IllegalArgumentException("Repeat count must be non-negative");
        }
        String str = Json4j.marshall(o);
        return repeat(str, cnt, separator);
    }
}
