package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.request.CharacterFreqRequest;
import org.unify4j.model.response.StringStatsResponse;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.unify4j.text.ExpressionText.EMAIL_REGULAR_EXPRESSION;
import static org.unify4j.text.ExpressionText.PHONE_NUMBER_REGULAR_EXPRESS;

public class StringExtensions4j {
    protected static final Logger logger = LoggerFactory.getLogger(StringExtensions4j.class);

    /**
     * Creates a StringDecorator for enhanced string operations.
     *
     * @param str The string to decorate
     * @return A StringDecorator instance
     */
    public static StringDecorator4j decorate(String str) {
        return StringDecorator4j.of(str);
    }

    /**
     * Creates a StringBuilder4j for fluent string construction.
     *
     * @return A StringBuilder4j instance
     */
    public static StringBuilder4j builder() {
        return StringBuilder4j.create();
    }

    /**
     * Calculates the Levenshtein distance between two strings.
     * This function determines the minimum number of single-character edits
     * required to change one string into another.
     *
     * @param str1 The first string
     * @param str2 The second string
     * @return The Levenshtein distance between the strings
     */
    public static int levenshteinDistance(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return Math.max(String4j.length(str1), String4j.length(str2));
        }

        int len1 = str1.length();
        int len2 = str2.length();

        if (len1 == 0) return len2;
        if (len2 == 0) return len1;

        int[][] matrix = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            matrix[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                int cost = (str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1;
                matrix[i][j] = Math.min(Math.min(matrix[i - 1][j] + 1,      // deletion
                                matrix[i][j - 1] + 1),     // insertion
                        matrix[i - 1][j - 1] + cost // substitution
                );
            }
        }

        return matrix[len1][len2];
    }

    /**
     * Calculates the similarity percentage between two strings using Levenshtein distance.
     *
     * @param str1 The first string
     * @param str2 The second string
     * @return The similarity percentage (0.0 to 100.0)
     */
    public static double calculateSimilarity(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return 100.0;
        }
        if (str1 == null || str2 == null) {
            return 0.0;
        }

        int maxLength = Math.max(str1.length(), str2.length());
        if (maxLength == 0) {
            return 100.0;
        }

        int distance = levenshteinDistance(str1, str2);
        return ((double) (maxLength - distance) / maxLength) * 100.0;
    }

    /**
     * Finds the longest common subsequence between two strings.
     *
     * @param str1 The first string
     * @param str2 The second string
     * @return The longest common subsequence
     */
    public static String longestCommonSubsequence(String str1, String str2) {
        if (String4j.isEmpty(str1) || String4j.isEmpty(str2)) {
            return "";
        }

        int len1 = str1.length();
        int len2 = str2.length();
        int[][] dp = new int[len1 + 1][len2 + 1];

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // Reconstruct the LCS
        StringBuilder lcs = new StringBuilder();
        int i = len1, j = len2;
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.append(str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.reverse().toString();
    }

    /**
     * Extracts all matches of a regex pattern from the given string.
     *
     * @param str   The string to search in
     * @param regex The regex pattern
     * @return A list of matched strings
     */
    public static List<String> extractMatches(String str, String regex) {
        List<String> matches = new ArrayList<>();
        if (String4j.isEmpty(str) || String4j.isEmpty(regex)) {
            return matches;
        }

        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(str);
            while (matcher.find()) {
                matches.add(matcher.group());
            }
        } catch (Exception e) {
            logger.error("Error extracting matches with regex '{}': {}", regex, e.getMessage());
        }

        return matches;
    }

    /**
     * Extracts all email addresses from the given string.
     *
     * @param str The string to search in
     * @return A list of email addresses found
     */
    public static List<String> extractEmails(String str) {
        return extractMatches(str, EMAIL_REGULAR_EXPRESSION);
    }

    /**
     * Extracts all URL addresses from the given string.
     *
     * @param str The string to search in
     * @return A list of URLs found
     */
    public static List<String> extractUrls(String str) {
        String urlRegex = "https?://[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-\\.,@?^=%&:/~\\+#]*[\\w\\-\\@?^=%&/~\\+#])?";
        return extractMatches(str, urlRegex);
    }

    /**
     * Extracts all phone numbers from the given string.
     *
     * @param str The string to search in
     * @return A list of phone numbers found
     */
    public static List<String> extractPhoneNumbers(String str) {
        return extractMatches(str, PHONE_NUMBER_REGULAR_EXPRESS);
    }

    /**
     * Generates all possible permutations of characters in a string.
     *
     * @param str The input string
     * @return A set of all permutations
     */
    public static Set<String> generatePermutations(String str) {
        Set<String> permutations = new HashSet<>();
        if (String4j.isEmpty(str)) {
            return permutations;
        }
        generatePermutationsHelper(str.toCharArray(), 0, permutations);
        return permutations;
    }

    /**
     * Checks if two strings are anagrams of each other.
     *
     * @param str1 The first string
     * @param str2 The second string
     * @return true if the strings are anagrams, false otherwise
     */
    public static boolean areAnagrams(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return Objects.equals(str1, str2);
        }

        String clean1 = str1.replaceAll("\\s+", "").toLowerCase();
        String clean2 = str2.replaceAll("\\s+", "").toLowerCase();

        if (clean1.length() != clean2.length()) {
            return false;
        }

        char[] chars1 = clean1.toCharArray();
        char[] chars2 = clean2.toCharArray();
        Arrays.sort(chars1);
        Arrays.sort(chars2);

        return Arrays.equals(chars1, chars2);
    }

    /**
     * Checks if a string is a palindrome.
     *
     * @param str          The string to check
     * @param ignoreCase   Whether to ignore case differences
     * @param ignoreSpaces Whether to ignore spaces and punctuation
     * @return true if the string is a palindrome, false otherwise
     */
    public static boolean isPalindrome(String str, boolean ignoreCase, boolean ignoreSpaces) {
        if (String4j.isEmpty(str)) {
            return true;
        }

        String processed = str;
        if (ignoreCase) {
            processed = processed.toLowerCase();
        }
        if (ignoreSpaces) {
            processed = processed.replaceAll("[^a-zA-Z0-9]", "");
        }

        int left = 0;
        int right = processed.length() - 1;

        while (left < right) {
            if (processed.charAt(left) != processed.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * Converts a string to title case.
     *
     * @param str    The string to convert
     * @param locale The locale to use for conversion
     * @return The string in title case
     */
    public static String toTitleCase(String str, Locale locale) {
        if (String4j.isEmpty(str)) {
            return str;
        }

        if (locale == null) {
            locale = Locale.getDefault();
        }

        Locale localCopied = locale;
        return Arrays.stream(str.split("\\s+")).map(word -> word.isEmpty() ? word : word.substring(0, 1).toUpperCase(localCopied) + word.substring(1).toLowerCase(localCopied)).collect(Collectors.joining(" "));
    }

    /**
     * Converts a string to title case using default locale.
     *
     * @param str The string to convert
     * @return The string in title case
     */
    public static String toTitleCase(String str) {
        return toTitleCase(str, null);
    }

    /**
     * Converts snake_case to camelCase.
     *
     * @param snakeCase       The snake_case string
     * @param capitalizeFirst Whether to capitalize the first letter
     * @return The camelCase string
     */
    public static String snakeToCamelCase(String snakeCase, boolean capitalizeFirst) {
        if (String4j.isEmpty(snakeCase)) {
            return snakeCase;
        }

        String[] parts = snakeCase.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (String4j.isNotEmpty(part)) {
                if (i == 0 && !capitalizeFirst) {
                    result.append(part);
                } else {
                    result.append(String4j.uppercaseFirstChar(part));
                }
            }
        }

        return result.toString();
    }

    /**
     * Converts kebab-case to camelCase.
     *
     * @param kebabCase       The kebab-case string
     * @param capitalizeFirst Whether to capitalize the first letter
     * @return The camelCase string
     */
    public static String kebabToCamelCase(String kebabCase, boolean capitalizeFirst) {
        if (String4j.isEmpty(kebabCase)) {
            return kebabCase;
        }

        return snakeToCamelCase(kebabCase.replace("-", "_"), capitalizeFirst);
    }

    /**
     * Converts camelCase to kebab-case.
     *
     * @param camelCase The camelCase string
     * @return The kebab-case string
     */
    public static String camelToKebabCase(String camelCase) {
        if (String4j.isEmpty(camelCase)) {
            return camelCase;
        }

        return String4j.camelToSnakeCase(camelCase, false).replace("_", "-");
    }

    /**
     * Wraps text to fit within specified line length.
     *
     * @param text       The text to wrap
     * @param lineLength The maximum line length
     * @param breakWords Whether to break words if necessary
     * @return The wrapped text
     */
    public static String wrapText(String text, int lineLength, boolean breakWords) {
        if (String4j.isEmpty(text) || lineLength <= 0) {
            return text;
        }

        StringBuilder4j builder = StringBuilder4j.create().withSeparator(System.lineSeparator());

        String[] words = text.split("\\s+");
        StringBuilder lines = new StringBuilder();

        for (String word : words) {
            if (lines.length() + word.length() + 1 <= lineLength) {
                if (!lines.isEmpty()) {
                    lines.append(" ");
                }
                lines.append(word);
            } else {
                if (!lines.isEmpty()) {
                    builder.append(lines.toString());
                    lines.setLength(0);
                }

                if (word.length() > lineLength && breakWords) {
                    // Break long words
                    for (int i = 0; i < word.length(); i += lineLength) {
                        int end = Math.min(i + lineLength, word.length());
                        builder.append(word.substring(i, end));
                    }
                } else {
                    lines.append(word);
                }
            }
        }

        if (!lines.isEmpty()) {
            builder.append(lines.toString());
        }

        return builder.build();
    }

    /**
     * Calculates various statistics for a given string.
     *
     * @param str The string to analyze
     * @return A StringStatistics object containing various metrics
     */
    public static StringStatsResponse calculateStatistics(String str) {
        if (str == null) {
            return new StringStatsResponse();
        }

        int length = str.length();
        int wordCount = String4j.countWords(str);
        int lineCount = str.split("\\r?\\n").length;
        int charCount = str.replaceAll("\\s", "").length();
        int digitCount = (int) str.chars().filter(Character::isDigit).count();
        int letterCount = (int) str.chars().filter(Character::isLetter).count();
        int uppercaseCount = (int) str.chars().filter(Character::isUpperCase).count();
        int lowercaseCount = (int) str.chars().filter(Character::isLowerCase).count();
        int specialCharCount = length - letterCount - digitCount - (int) str.chars().filter(Character::isWhitespace).count();

        return new StringStatsResponse(length, wordCount, lineCount, charCount, digitCount, letterCount, uppercaseCount, lowercaseCount, specialCharCount);
    }

    /**
     * Finds the most frequent character in a string.
     *
     * @param str          The string to analyze
     * @param ignoreSpaces Whether to ignore whitespace characters
     * @return The most frequent character and its count
     */
    public static CharacterFreqRequest findMostFrequentChar(String str, boolean ignoreSpaces) {
        if (String4j.isEmpty(str)) {
            return new CharacterFreqRequest('\0', 0);
        }

        Map<Character, Integer> frequency = new HashMap<>();

        for (char c : str.toCharArray()) {
            if (ignoreSpaces && Character.isWhitespace(c)) {
                continue;
            }
            frequency.put(c, frequency.getOrDefault(c, 0) + 1);
        }

        char mostFrequentChar = '\0';
        int maxCount = 0;

        for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostFrequentChar = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        return new CharacterFreqRequest(mostFrequentChar, maxCount);
    }

    /**
     * Helper method to generate permutations recursively.
     *
     * @param chars  The character array to permute
     * @param index  The current index in the array
     * @param result The set to store unique permutations
     */
    protected static void generatePermutationsHelper(char[] chars, int index, Set<String> result) {
        if (index == chars.length) {
            result.add(new String(chars));
            return;
        }

        for (int i = index; i < chars.length; i++) {
            swap(chars, index, i);
            generatePermutationsHelper(chars, index + 1, result);
            swap(chars, index, i); // backtrack
        }
    }

    /**
     * Swaps two characters in a character array.
     *
     * @param chars The character array
     * @param i     The first index
     * @param j     The second index
     */
    protected static void swap(char[] chars, int i, int j) {
        char temp = chars[i];
        chars[i] = chars[j];
        chars[j] = temp;
    }
}