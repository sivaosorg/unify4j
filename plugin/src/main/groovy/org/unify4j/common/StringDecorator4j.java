package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;

public class StringDecorator4j {
    protected static final Logger logger = LoggerFactory.getLogger(StringDecorator4j.class);

    private final String value;
    private Charset encoding;
    private Locale locale;
    private boolean trimByDefault;

    /**
     * Protected constructor for decorator pattern implementation.
     *
     * @param value The base string to be decorated
     */
    protected StringDecorator4j(String value) {
        this.value = value;
        this.encoding = Charset.defaultCharset();
        this.locale = Locale.getDefault();
        this.trimByDefault = false;
    }

    /**
     * Creates a new StringDecorator instance with the specified string.
     *
     * @param value The string to be decorated
     * @return A new StringDecorator instance
     */
    public static StringDecorator4j of(String value) {
        return new StringDecorator4j(value);
    }

    /**
     * Creates a new StringDecorator instance with an empty string.
     *
     * @return A new StringDecorator instance with empty string
     */
    public static StringDecorator4j empty() {
        return new StringDecorator4j("");
    }

    /**
     * Creates a new StringDecorator instance with null value.
     *
     * @return A new StringDecorator instance with null value
     */
    public static StringDecorator4j ofNullable() {
        return new StringDecorator4j(null);
    }

    /**
     * Sets the encoding for this decorator.
     *
     * @param encoding The encoding to apply
     * @return This decorator instance for method chaining
     */
    public StringDecorator4j withEncoding(Charset encoding) {
        StringDecorator4j newDecorator = new StringDecorator4j(this.value);
        newDecorator.encoding = encoding != null ? encoding : this.encoding;
        newDecorator.locale = this.locale;
        newDecorator.trimByDefault = this.trimByDefault;
        return newDecorator;
    }

    /**
     * Sets the locale for this decorator.
     *
     * @param locale The locale to apply
     * @return This decorator instance for method chaining
     */
    public StringDecorator4j withLocale(Locale locale) {
        StringDecorator4j newDecorator = new StringDecorator4j(this.value);
        newDecorator.encoding = this.encoding;
        newDecorator.locale = locale != null ? locale : this.locale;
        newDecorator.trimByDefault = this.trimByDefault;
        return newDecorator;
    }

    /**
     * Sets automatic trimming for all operations.
     *
     * @param trimByDefault Whether to trim by default
     * @return This decorator instance for method chaining
     */
    public StringDecorator4j withAutoTrim(boolean trimByDefault) {
        StringDecorator4j newDecorator = new StringDecorator4j(this.value);
        newDecorator.encoding = this.encoding;
        newDecorator.locale = this.locale;
        newDecorator.trimByDefault = trimByDefault;
        return newDecorator;
    }

    /**
     * Applies a custom transformation function to the decorated string.
     *
     * @param transformer The transformation function to apply
     * @return A new StringDecorator with the transformed string
     */
    public StringDecorator4j transform(Function<String, String> transformer) {
        String transformedValue = transformer.apply(this.value);
        return this.create(transformedValue);
    }

    /**
     * Applies a conditional transformation to the decorated string.
     *
     * @param condition   The condition to check
     * @param transformer The transformation function to apply if condition is true
     * @return A new StringDecorator with conditionally transformed string
     */
    public StringDecorator4j transformIf(Predicate<String> condition, Function<String, String> transformer) {
        if (condition.test(this.value)) {
            return this.transform(transformer);
        }
        return this;
    }

    /**
     * Trims whitespace from the decorated string.
     *
     * @return A new StringDecorator with trimmed string
     */
    public StringDecorator4j trim() {
        return this.transform(str -> str != null ? str.trim() : null);
    }

    /**
     * Trims all whitespace from the decorated string.
     *
     * @return A new StringDecorator with all whitespace removed
     */
    public StringDecorator4j trimAll() {
        return this.transform(String4j::trimWhitespaces);
    }

    /**
     * Trims whitespace and normalizes multiple spaces to single space.
     *
     * @return A new StringDecorator with normalized whitespace
     */
    public StringDecorator4j trimWhitespace() {
        return this.transform(String4j::trimWhitespace);
    }

    /**
     * Converts the decorated string to uppercase.
     *
     * @return A new StringDecorator with uppercase string
     */
    public StringDecorator4j toUpperCase() {
        return this.transform(str -> str != null ? str.toUpperCase(this.locale) : null);
    }

    /**
     * Converts the decorated string to lowercase.
     *
     * @return A new StringDecorator with lowercase string
     */
    public StringDecorator4j toLowerCase() {
        return this.transform(str -> str != null ? str.toLowerCase(this.locale) : null);
    }

    /**
     * Capitalizes the first character of the decorated string.
     *
     * @return A new StringDecorator with first character capitalized
     */
    public StringDecorator4j capitalizeFirst() {
        return this.transform(String4j::uppercaseFirstChar);
    }

    /**
     * Capitalizes each word in the decorated string.
     *
     * @return A new StringDecorator with each word capitalized
     */
    public StringDecorator4j capitalizeWords() {
        return this.transform(String4j::capitalizeEachWords);
    }

    /**
     * Truncates the decorated string to the specified length.
     *
     * @param maxLength The maximum length
     * @return A new StringDecorator with truncated string
     */
    public StringDecorator4j truncate(int maxLength) {
        return this.transform(str -> String4j.truncate(str, maxLength));
    }

    /**
     * Masks a portion of the decorated string.
     *
     * @param start    The start index
     * @param end      The end index
     * @param maskChar The character to use for masking
     * @return A new StringDecorator with masked string
     */
    public StringDecorator4j mask(int start, int end, char maskChar) {
        return this.transform(str -> {
            try {
                return String4j.mask(str, start, end, maskChar);
            } catch (Exception e) {
                logger.warn("Error masking string: {}", e.getMessage());
                return str;
            }
        });
    }

    /**
     * Masks the email address in the decorated string.
     *
     * @param maskChar The character to use for masking
     * @return A new StringDecorator with masked email
     */
    public StringDecorator4j maskEmail(char maskChar) {
        return this.transform(str -> {
            try {
                return String4j.maskEmail(str, maskChar);
            } catch (Exception e) {
                logger.warn("Error masking email: {}", e.getMessage());
                return str;
            }
        });
    }

    /**
     * Removes accents from the decorated string.
     *
     * @return A new StringDecorator with accents removed
     */
    public StringDecorator4j stripAccents() {
        return this.transform(String4j::stripAccents);
    }

    /**
     * Removes non-ASCII characters from the decorated string.
     *
     * @return A new StringDecorator with only ASCII characters
     */
    public StringDecorator4j unAccents() {
        return this.transform(String4j::unAccents);
    }

    /**
     * Converts camelCase to snake_case.
     *
     * @param uppercase Whether to use uppercase
     * @return A new StringDecorator with snake_case string
     */
    public StringDecorator4j camelToSnake(boolean uppercase) {
        return this.transform(str -> String4j.camelToSnakeCase(str, uppercase));
    }

    /**
     * Removes prefix from the decorated string.
     *
     * @param prefix The prefix to remove
     * @return A new StringDecorator with prefix removed
     */
    public StringDecorator4j removePrefix(String prefix) {
        return this.transform(str -> String4j.removePrefix(str, prefix));
    }

    /**
     * Removes multiple prefixes from the decorated string.
     *
     * @param prefixes The prefixes to remove
     * @return A new StringDecorator with prefixes removed
     */
    public StringDecorator4j removePrefixes(String... prefixes) {
        return this.transform(str -> String4j.removePrefix(str, prefixes));
    }

    /**
     * Removes specific characters from the decorated string.
     *
     * @return A new StringDecorator with specific characters removed
     */
    public StringDecorator4j removeSpecialChars() {
        return this.transform(String4j::removeAllSpecificCharacters);
    }

    /**
     * Replaces whitespace with the specified symbol.
     *
     * @param symbol The replacement symbol
     * @return A new StringDecorator with whitespace replaced
     */
    public StringDecorator4j replaceWhitespace(String symbol) {
        return this.transform(str -> String4j.replaceWhitespace(str, symbol));
    }

    /**
     * Removes brackets from the decorated string.
     *
     * @return A new StringDecorator with brackets removed
     */
    public StringDecorator4j removeBrackets() {
        return this.transform(String4j::removeBrackets);
    }

    /**
     * Repeats the decorated string a specified number of times.
     *
     * @param count The number of repetitions
     * @return A new StringDecorator with repeated string
     */
    public StringDecorator4j repeat(int count) {
        return this.transform(str -> String4j.repeat(str, count));
    }

    /**
     * Checks if the decorated string is empty.
     *
     * @return true if the string is empty, false otherwise
     */
    public boolean isEmpty() {
        return String4j.isEmpty(this.value);
    }

    /**
     * Checks if the decorated string is not empty.
     *
     * @return true if the string is not empty, false otherwise
     */
    public boolean isNotEmpty() {
        return String4j.isNotEmpty(this.value);
    }

    /**
     * Checks if the decorated string is blank.
     *
     * @return true if the string is blank, false otherwise
     */
    public boolean isBlank() {
        return String4j.isBlank(this.value);
    }

    /**
     * Checks if the decorated string is not blank.
     *
     * @return true if the string is not blank, false otherwise
     */
    public boolean isNotBlank() {
        return String4j.isNotBlank(this.value);
    }

    /**
     * Checks if the decorated string is numeric.
     *
     * @return true if the string is numeric, false otherwise
     */
    public boolean isNumeric() {
        return String4j.isNumeric(this.value);
    }

    /**
     * Checks if the decorated string has content.
     *
     * @return true if the string has content, false otherwise
     */
    public boolean hasContent() {
        return String4j.hasContent(this.value);
    }

    /**
     * Checks if the decorated string has length.
     *
     * @return true if the string has length, false otherwise
     */
    public boolean hasLength() {
        return String4j.hasLength(this.value);
    }

    /**
     * Gets the length of the decorated string.
     *
     * @return The length of the string
     */
    public int length() {
        return String4j.length(this.value);
    }

    /**
     * Counts words in the decorated string.
     *
     * @return The number of words
     */
    public int countWords() {
        return String4j.countWords(this.value);
    }

    /**
     * Counts occurrences of a character in the decorated string.
     *
     * @param ch The character to count
     * @return The number of occurrences
     */
    public int count(char ch) {
        return String4j.count(this.value, ch);
    }

    /**
     * Counts occurrences of a substring in the decorated string.
     *
     * @param substring The substring to count
     * @return The number of occurrences
     */
    public int count(String substring) {
        return String4j.count(this.value, substring);
    }

    /**
     * Checks if the decorated string equals another string.
     *
     * @param other The other string
     * @return true if strings are equal, false otherwise
     */
    public boolean equals(String other) {
        return String4j.equals(this.value, other);
    }

    /**
     * Checks if the decorated string equals another string ignoring case.
     *
     * @param other The other string
     * @return true if strings are equal ignoring case, false otherwise
     */
    public boolean equalsIgnoreCase(String other) {
        return String4j.equalsIgnoreCase(this.value, other);
    }

    /**
     * Checks if the decorated string equals another string with trim.
     *
     * @param other The other string
     * @return true if trimmed strings are equal, false otherwise
     */
    public boolean equalsWithTrim(String other) {
        return String4j.equalsWithTrim(this.value, other);
    }

    /**
     * Gets the decorated string as bytes using the configured encoding.
     *
     * @return The byte array representation
     */
    public byte[] getBytes() {
        return String4j.getBytes(this.value, this.encoding.name());
    }

    /**
     * Gets the decorated string as UTF-8 bytes.
     *
     * @return The UTF-8 byte array representation
     */
    public byte[] getUTF8Bytes() {
        return String4j.getUTF8Bytes(this.value);
    }

    /**
     * Gets the decorated string value.
     *
     * @return The string value
     */
    public String getValue() {
        String result = this.value;
        if (this.trimByDefault && result != null) {
            result = result.trim();
        }
        return result;
    }

    /**
     * Gets the decorated string value or default if null/empty.
     *
     * @param defaultValue The default value
     * @return The string value or default
     */
    public String getValueOrDefault(String defaultValue) {
        String result = this.getValue();
        return String4j.isEmpty(result) ? defaultValue : result;
    }

    @Override
    public String toString() {
        return this.getValue();
    }

    /**
     * Creates a new StringDecorator instance with the same configuration.
     *
     * @param newValue The new string value
     * @return A new StringDecorator instance
     */
    private StringDecorator4j create(String newValue) {
        StringDecorator4j newDecorator = new StringDecorator4j(newValue);
        newDecorator.encoding = this.encoding;
        newDecorator.locale = this.locale;
        newDecorator.trimByDefault = this.trimByDefault;
        return newDecorator;
    }
}