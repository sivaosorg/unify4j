package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class StringBuilder4j {
    protected static final Logger logger = LoggerFactory.getLogger(StringBuilder4j.class);

    private final StringBuilder buffer;
    private Charset encoding = Charset.defaultCharset();
    private Locale locale = Locale.getDefault();
    private String separator = "";
    private String prefix = "";
    private String suffix = "";
    private boolean trimElements = false;
    private boolean skipNulls = true;
    private boolean skipEmpty = false;
    private int maxLength = Integer.MAX_VALUE;

    /**
     * Protected constructor for builder pattern.
     */
    protected StringBuilder4j() {
        this.buffer = new StringBuilder();
    }

    /**
     * Protected constructor with initial capacity.
     */
    protected StringBuilder4j(int capacity) {
        this.buffer = new StringBuilder(capacity);
    }

    /**
     * Creates a new StringBuilder4j instance.
     *
     * @return A new StringBuilder4j instance
     */
    public static StringBuilder4j create() {
        return new StringBuilder4j();
    }

    /**
     * Creates a new StringBuilder4j instance with initial capacity.
     *
     * @param capacity The initial capacity
     * @return A new StringBuilder4j instance
     */
    public static StringBuilder4j create(int capacity) {
        return new StringBuilder4j(capacity);
    }

    /**
     * Creates a new StringBuilder4j instance from an existing string.
     *
     * @param initial The initial string
     * @return A new StringBuilder4j instance
     */
    public static StringBuilder4j from(String initial) {
        StringBuilder4j builder = new StringBuilder4j();
        if (String4j.isNotEmpty(initial)) {
            builder.buffer.append(initial);
        }
        return builder;
    }

    /**
     * Creates a new StringBuilder4j instance from multiple strings.
     *
     * @param strings The initial strings
     * @return A new StringBuilder4j instance
     */
    public static StringBuilder4j from(String... strings) {
        StringBuilder4j builder = new StringBuilder4j();
        if (!Array4j.isEmpty(strings)) {
            for (String str : strings) {
                builder.append(str);
            }
        }
        return builder;
    }

    /**
     * Builds a template string by replacing placeholders.
     *
     * @param template The template string with placeholders
     * @param values   The values to replace placeholders
     * @return This builder instance for method chaining
     */
    public StringBuilder4j from(String template, Map<String, String> values) {
        if (String4j.isNotEmpty(template)) {
            String processed = template;
            if (values != null) {
                for (Map.Entry<String, String> entry : values.entrySet()) {
                    String placeholder = "${" + entry.getKey() + "}";
                    String value = entry.getValue() != null ? entry.getValue() : "";
                    processed = processed.replace(placeholder, value);
                }
            }
            this.append(processed);
        }
        return this;
    }

    /**
     * Sets the encoding for this builder.
     *
     * @param encoding The encoding to use
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withEncoding(Charset encoding) {
        if (encoding != null) {
            this.encoding = encoding;
        }
        return this;
    }

    /**
     * Sets the locale for this builder.
     *
     * @param locale The locale to use
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withLocale(Locale locale) {
        if (locale != null) {
            this.locale = locale;
        }
        return this;
    }

    /**
     * Sets the separator for joining elements.
     *
     * @param separator The separator string
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withSeparator(String separator) {
        this.separator = String4j.isNotEmpty(separator) ? separator : "";
        return this;
    }

    /**
     * Sets the prefix to add before the built string.
     *
     * @param prefix The prefix string
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withPrefix(String prefix) {
        this.prefix = String4j.isNotEmpty(prefix) ? prefix : "";
        return this;
    }

    /**
     * Sets the suffix to add after the built string.
     *
     * @param suffix The suffix string
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withSuffix(String suffix) {
        this.suffix = String4j.isNotEmpty(suffix) ? suffix : "";
        return this;
    }

    /**
     * Sets whether to trim elements before adding.
     *
     * @param trimElements Whether to trim elements
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withTrimElements(boolean trimElements) {
        this.trimElements = trimElements;
        return this;
    }

    /**
     * Sets whether to skip null values.
     *
     * @param skipNulls Whether to skip nulls
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withSkipNulls(boolean skipNulls) {
        this.skipNulls = skipNulls;
        return this;
    }

    /**
     * Sets whether to skip empty values.
     *
     * @param skipEmpty Whether to skip empty strings
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withSkipEmpty(boolean skipEmpty) {
        this.skipEmpty = skipEmpty;
        return this;
    }

    /**
     * Sets the maximum length for the built string.
     *
     * @param maxLength The maximum length
     * @return This builder instance for method chaining
     */
    public StringBuilder4j withMaxLength(int maxLength) {
        this.maxLength = maxLength > 0 ? maxLength : Integer.MAX_VALUE;
        return this;
    }

    /**
     * Appends a string to the builder.
     *
     * @param str The string to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j append(String str) {
        if (this.shouldSkip(str)) {
            return this;
        }

        String processed = this.processString(str);
        if (String4j.isNotEmpty(processed) && this.willExceedMaxLength(processed)) {
            logger.warn("Appending string would exceed max length {}, truncating", maxLength);
            int remainingSpace = maxLength - buffer.length();
            if (remainingSpace > 0) {
                processed = processed.substring(0, Math.min(processed.length(), remainingSpace));
            } else {
                return this;
            }
        }

        if (String4j.isNotEmpty(buffer) && String4j.isNotEmpty(separator)) {
            buffer.append(separator);
        }

        if (processed != null) {
            buffer.append(processed);
        }

        return this;
    }

    /**
     * Appends multiple strings to the builder.
     *
     * @param strings The strings to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j append(String... strings) {
        if (!Array4j.isEmpty(strings)) {
            for (String str : strings) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Appends a collection of strings to the builder.
     *
     * @param strings The collection of strings to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j append(Collection<String> strings) {
        if (Collection4j.isNotEmpty(strings)) {
            for (String str : strings) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Conditionally appends a string to the builder.
     *
     * @param condition The condition to check
     * @param str       The string to append if condition is true
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIf(boolean condition, String str) {
        if (condition) {
            this.append(str);
        }
        return this;
    }

    /**
     * Conditionally appends an object to the builder.
     *
     * @param condition The condition to check
     * @param o         The object to append if condition is true
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIf(boolean condition, Object o) {
        if (condition && o != null) {
            this.append(o.toString());
        }
        return this;
    }

    /**
     * Conditionally appends a string using a predicate.
     *
     * @param predicate The predicate to test
     * @param str       The string to append if predicate is true
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIf(Predicate<String> predicate, String str) {
        if (predicate != null && predicate.test(str)) {
            this.append(str);
        }
        return this;
    }

    /**
     * Appends a string using a supplier if condition is true.
     *
     * @param condition The condition to check
     * @param supplier  The supplier for the string to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIf(boolean condition, Supplier<String> supplier) {
        if (condition && supplier != null) {
            this.append(supplier.get());
        }
        return this;
    }

    /**
     * Appends a formatted string to the builder.
     *
     * @param format The format string
     * @param args   The format arguments
     * @return This builder instance for method chaining
     */
    public StringBuilder4j append(String format, Object... args) {
        if (String4j.isNotEmpty(format)) {
            try {
                String formatted = String.format(locale, format, args);
                this.append(formatted);
            } catch (Exception e) {
                logger.warn("Error formatting string: {}", e.getMessage(), e);
                this.append(format);
            }
        }
        return this;
    }

    /**
     * Appends a number to the builder.
     *
     * @param number The number to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j append(Number number) {
        if (number != null) {
            this.append(number.toString());
        }
        return this;
    }

    /**
     * Appends a character to the builder.
     *
     * @param ch The character to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j append(char ch) {
        this.append(String.valueOf(ch));
        return this;
    }

    /**
     * Appends an object to the builder using its toString method.
     *
     * @param obj The object to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j append(Object obj) {
        if (obj != null) {
            this.append(obj.toString());
        }
        return this;
    }

    /**
     * Appends a line separator after the current content.
     *
     * @return This builder instance for method chaining
     */
    @SuppressWarnings({"UnusedReturnValue"})
    public StringBuilder4j appendLine() {
        buffer.append(System.lineSeparator());
        return this;
    }

    /**
     * Appends a string followed by a line separator.
     *
     * @param str The string to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendLine(String str) {
        this.append(str);
        this.appendLine();
        return this;
    }

    /**
     * Appends a space character to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendSpace() {
        return this.append(" ");
    }

    /**
     * Appends a tab character to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendTab() {
        return this.append("\t");
    }

    /**
     * Appends a comma to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendComma() {
        return this.append(",");
    }

    /**
     * Appends a semicolon to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendSemicolon() {
        return this.append(";");
    }

    /**
     * Appends a left parenthesis to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendLBrace() {
        return this.append("{");
    }

    /**
     * Appends a right parenthesis to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendRBrace() {
        return this.append("}");
    }

    /**
     * Appends a left square bracket to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendLBracket() {
        return this.append("[");
    }

    /**
     * Appends a right square bracket to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendRBracket() {
        return this.append("]");
    }

    /**
     * Appends a left parenthesis to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendLParen() {
        return this.append("(");
    }

    /**
     * Appends a right parenthesis to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendRParen() {
        return this.append(")");
    }

    /**
     * Appends a double quote to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendDoubleQuote() {
        return this.append("\"");
    }

    /**
     * Appends a single quote to the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendColon() {
        return this.append(":");
    }

    /**
     * Appends a string if a condition is met, otherwise appends a default value.
     *
     * @param condition    The condition to check
     * @param str          The string to append if condition is true
     * @param defaultValue The default value to append if condition is false
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIf(boolean condition, String str, String defaultValue) {
        if (condition) {
            this.append(str);
        } else {
            this.append(defaultValue);
        }
        return this;
    }

    /**
     * Appends a string if a predicate is true, otherwise appends a default value.
     *
     * @param predicate    The predicate to test
     * @param str          The string to append if predicate is true
     * @param defaultValue The default value to append if predicate is false
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIf(Predicate<String> predicate, String str, String defaultValue) {
        if (predicate != null && predicate.test(str)) {
            this.append(str);
        } else {
            this.append(defaultValue);
        }
        return this;
    }

    /**
     * Appends a string from a supplier if a condition is met, otherwise appends a default value.
     *
     * @param condition    The condition to check
     * @param supplier     The supplier for the string to append if condition is true
     * @param defaultValue The default value to append if condition is false
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIf(boolean condition, Supplier<String> supplier, String defaultValue) {
        if (condition && supplier != null) {
            this.append(supplier.get());
        } else {
            this.append(defaultValue);
        }
        return this;
    }

    /**
     * Appends a string if it will not exceed the maximum length.
     *
     * @param condition The condition to check
     * @param str       The string to append if condition is true and it won't exceed max length
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfWillExceedMaxLength(boolean condition, String str) {
        if (condition && !this.willExceedMaxLength(str)) {
            this.append(str);
        } else if (condition) {
            logger.warn("Appending string would exceed max length {}, skipping", maxLength);
        }
        return this;
    }

    /**
     * Appends a string if it will not exceed the maximum length based on a predicate.
     *
     * @param predicate The predicate to test
     * @param str       The string to append if predicate is true, and it won't exceed max length
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfWillExceedMaxLength(Predicate<String> predicate, String str) {
        if (predicate != null && predicate.test(str) && !this.willExceedMaxLength(str)) {
            this.append(str);
        } else if (predicate != null) {
            logger.warn("Appending string would exceed max length {}, skipping", maxLength);
        }
        return this;
    }

    /**
     * Appends a string from a supplier if it will not exceed the maximum length.
     *
     * @param condition The condition to check
     * @param supplier  The supplier for the string to append if condition is true and it won't exceed max length
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfWillExceedMaxLength(boolean condition, Supplier<String> supplier) {
        if (condition && supplier != null) {
            String str = supplier.get();
            if (!this.willExceedMaxLength(str)) {
                this.append(str);
            } else {
                logger.warn("Appending string from supplier would exceed max length {}, skipping", maxLength);
            }
        }
        return this;
    }

    /**
     * Appends a string if it is not empty.
     *
     * @param str The string to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(String str) {
        if (String4j.isNotEmpty(str)) {
            this.append(str);
        }
        return this;
    }

    /**
     * Appends a string from a supplier if it is not empty.
     *
     * @param supplier The supplier for the string to append
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(Supplier<String> supplier) {
        if (supplier != null) {
            String str = supplier.get();
            if (String4j.isNotEmpty(str)) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Appends a string if it is not empty based on a predicate.
     *
     * @param predicate The predicate to test
     * @param str       The string to append if predicate is true and string is not empty
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(Predicate<String> predicate, String str) {
        if (predicate != null && predicate.test(str) && String4j.isNotEmpty(str)) {
            this.append(str);
        }
        return this;
    }

    /**
     * Appends a string from a supplier if it is not empty based on a predicate.
     *
     * @param predicate The predicate to test
     * @param supplier  The supplier for the string to append if predicate is true and string is not empty
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(Predicate<String> predicate, Supplier<String> supplier) {
        if (predicate != null && supplier != null) {
            String str = supplier.get();
            if (predicate.test(str) && String4j.isNotEmpty(str)) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Appends a string if a condition is true and the string is not empty.
     *
     * @param condition The condition to check
     * @param str       The string to append if condition is true and string is not empty
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            this.append(str);
        }
        return this;
    }

    /**
     * Appends a string from a supplier if a condition is true and the string is not empty.
     *
     * @param condition The condition to check
     * @param supplier  The supplier for the string to append if condition is true and string is not empty
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(boolean condition, Supplier<String> supplier) {
        if (condition && supplier != null) {
            String str = supplier.get();
            if (String4j.isNotEmpty(str)) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Appends a string if a predicate is true and the string is not empty.
     *
     * @param predicate The predicate to test
     * @param condition The condition to check
     * @param str       The string to append if predicate is true, condition is true, and string is not empty
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(Predicate<String> predicate, boolean condition, String str) {
        if (predicate != null && condition && String4j.isNotEmpty(str) && predicate.test(str)) {
            this.append(str);
        }
        return this;
    }

    /**
     * Appends a string from a supplier if a predicate is true and the string is not empty.
     *
     * @param predicate The predicate to test
     * @param condition The condition to check
     * @param supplier  The supplier for the string to append if predicate is true, condition is true, and string is not empty
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(Predicate<String> predicate, boolean condition, Supplier<String> supplier) {
        if (predicate != null && condition && supplier != null) {
            String str = supplier.get();
            if (String4j.isNotEmpty(str) && predicate.test(str)) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Appends a string if a condition is true, the string is not empty, and it matches a predicate.
     *
     * @param condition The condition to check
     * @param predicate The predicate to test
     * @param str       The string to append if condition is true, string is not empty, and predicate is true
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(boolean condition, Predicate<String> predicate, String str) {
        if (condition && String4j.isNotEmpty(str) && (predicate == null || predicate.test(str))) {
            this.append(str);
        }
        return this;
    }

    /**
     * Appends a string from a supplier if a condition is true, the string is not empty, and it matches a predicate.
     *
     * @param condition The condition to check
     * @param predicate The predicate to test
     * @param supplier  The supplier for the string to append if condition is true, string is not empty, and predicate is true
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(boolean condition, Predicate<String> predicate, Supplier<String> supplier) {
        if (condition && supplier != null) {
            String str = supplier.get();
            if (String4j.isNotEmpty(str) && (predicate == null || predicate.test(str))) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Appends a string if a condition is true, the string is not empty, and it matches a predicate.
     *
     * @param condition The condition to check
     * @param supplier  The supplier for the string to append if condition is true, string is not empty, and predicate is true
     * @param predicate The predicate to test
     * @return This builder instance for method chaining
     */
    public StringBuilder4j appendIfNotEmpty(boolean condition, Supplier<String> supplier, Predicate<String> predicate) {
        if (condition && supplier != null) {
            String str = supplier.get();
            if (String4j.isNotEmpty(str) && (predicate == null || predicate.test(str))) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Prepends a string to the beginning of the builder.
     *
     * @param str The string to prepend
     * @return This builder instance for method chaining
     */
    public StringBuilder4j prepend(String str) {
        if (!this.shouldSkip(str)) {
            String processed = this.processString(str);
            if (String4j.isNotEmpty(processed)) {
                buffer.insert(0, processed);
                if (String4j.isNotEmpty(separator) && buffer.length() > processed.length()) {
                    buffer.insert(processed.length(), separator);
                }
            }
        }
        return this;
    }

    /**
     * Inserts a string at the specified position.
     *
     * @param index The position to insert at
     * @param str   The string to insert
     * @return This builder instance for method chaining
     */
    public StringBuilder4j insert(int index, String str) {
        if (!this.shouldSkip(str) && index >= 0 && index <= buffer.length()) {
            String processed = this.processString(str);
            if (String4j.isNotEmpty(processed)) {
                buffer.insert(index, processed);
            }
        }
        return this;
    }

    /**
     * Replaces all occurrences of a target string with a replacement.
     *
     * @param target      The string to replace
     * @param replacement The replacement string
     * @return This builder instance for method chaining
     */
    public StringBuilder4j replace(String target, String replacement) {
        if (String4j.isNotEmpty(target)) {
            String content = buffer.toString();
            String replaced = content.replace(target, String4j.isNotEmpty(replacement) ? replacement : "");
            buffer.setLength(0);
            buffer.append(replaced);
        }
        return this;
    }

    /**
     * Replaces all occurrences of a target string based on a predicate.
     *
     * @param predicate   The predicate to test each part
     * @param target      The string to replace
     * @param replacement The replacement string
     * @return This builder instance for method chaining
     */
    public StringBuilder4j replaceIf(Predicate<String> predicate, String target, String replacement) {
        if (predicate != null && predicate.test(target)) {
            this.replace(target, replacement);
        }
        return this;
    }

    /**
     * Replaces all occurrences of a target string with a replacement from a supplier.
     *
     * @param predicate           The predicate to test each part
     * @param target              The string to replace
     * @param replacementSupplier The supplier for the replacement string
     * @return This builder instance for method chaining
     */
    public StringBuilder4j replaceIf(Predicate<String> predicate, String target, Supplier<String> replacementSupplier) {
        if (predicate != null && predicate.test(target) && replacementSupplier != null) {
            String replacement = replacementSupplier.get();
            this.replace(target, replacement);
        }
        return this;
    }

    /**
     * Removes all occurrences of a specified string.
     *
     * @param str The string to remove
     * @return This builder instance for method chaining
     */
    public StringBuilder4j remove(String str) {
        return this.replace(str, "");
    }

    /**
     * Removes all occurrences of a specified string based on a predicate.
     *
     * @param predicate The predicate to test each part
     * @return This builder instance for method chaining
     */
    public StringBuilder4j removeIf(Predicate<String> predicate) {
        if (predicate != null) {
            String content = buffer.toString();
            String[] parts = content.split(separator);
            StringBuilder lines = new StringBuilder();

            for (String part : parts) {
                if (!predicate.test(part)) {
                    if (String4j.isNotEmpty(lines)) {
                        lines.append(separator);
                    }
                    lines.append(part);
                }
            }

            buffer.setLength(0);
            buffer.append(lines);
        }
        return this;
    }

    /**
     * Clears the builder content.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j clear() {
        buffer.setLength(0);
        return this;
    }

    /**
     * Reverses the content of the builder.
     *
     * @return This builder instance for method chaining
     */
    public StringBuilder4j reverse() {
        buffer.reverse();
        return this;
    }

    /**
     * Transforms the current content using a function.
     *
     * @param transformer The transformation function
     * @return This builder instance for method chaining
     */
    @SuppressWarnings({"UnusedReturnValue"})
    public StringBuilder4j transform(Function<String, String> transformer) {
        if (transformer != null) {
            String current = buffer.toString();
            String transformed = transformer.apply(current);
            buffer.setLength(0);
            if (String4j.isNotEmpty(transformed)) {
                buffer.append(transformed);
            }
        }
        return this;
    }

    /**
     * Applies a transformation if a condition is met.
     *
     * @param condition   The condition to check
     * @param transformer The transformation function
     * @return This builder instance for method chaining
     */
    public StringBuilder4j transformIf(boolean condition, Function<String, String> transformer) {
        if (condition) {
            this.transform(transformer);
        }
        return this;
    }

    /**
     * Generates repeated strings and appends them.
     *
     * @param str   The string to repeat
     * @param count The number of repetitions
     * @return This builder instance for method chaining
     */
    public StringBuilder4j repeat(String str, int count) {
        if (String4j.isNotEmpty(str) && count > 0) {
            for (int i = 0; i < count; i++) {
                this.append(str);
            }
        }
        return this;
    }

    /**
     * Gets the current length of the builder.
     *
     * @return The current length
     */
    public int length() {
        return buffer.length();
    }

    /**
     * Checks if the builder is empty.
     *
     * @return true if the builder is empty, false otherwise
     */
    public boolean isEmpty() {
        return String4j.isEmpty(buffer);
    }

    /**
     * Checks if the builder is not empty.
     *
     * @return true if the builder is not empty, false otherwise
     */
    public boolean isNotEmpty() {
        return !this.isEmpty();
    }

    /**
     * Builds and returns the final string.
     *
     * @return The built string with prefix and suffix applied
     */
    public String build() {
        StringBuilder result = new StringBuilder();

        if (String4j.isNotEmpty(prefix)) {
            result.append(prefix);
        }

        result.append(buffer);

        if (String4j.isNotEmpty(suffix)) {
            result.append(suffix);
        }

        String finalResult = result.toString();
        if (finalResult.length() > maxLength) {
            logger.warn("Built string exceeds max length {}, truncating", maxLength);
            finalResult = String4j.truncate(finalResult, maxLength);
        }

        return finalResult;
    }

    /**
     * Builds and returns the string as bytes using the configured encoding.
     *
     * @return The built string as byte array
     */
    public byte[] buildBytes() {
        return String4j.getBytes(this.build(), encoding.name());
    }

    /**
     * Creates a StringDecorator from the built string.
     *
     * @return A StringDecorator wrapping the built string
     */
    public StringDecorator4j buildDecorator() {
        return StringDecorator4j.of(this.build()).withEncoding(encoding).withLocale(locale);
    }

    @Override
    public String toString() {
        return this.build();
    }

    /**
     * Checks if a string should be skipped based on configuration.
     *
     * @param str The string to check
     * @return true if the string should be skipped, false otherwise
     */
    protected boolean shouldSkip(String str) {
        if (str == null && skipNulls) {
            return true;
        }
        return String4j.isEmpty(str) && skipEmpty;
    }

    /**
     * Processes a string based on configuration (e.g., trimming).
     *
     * @param str The string to process
     * @return The processed string
     */
    protected String processString(String str) {
        if (str == null) {
            return null;
        }
        String processed = str;
        if (trimElements) {
            processed = processed.trim();
        }
        return processed;
    }

    /**
     * Checks if appending a string would exceed the maximum length.
     *
     * @param str The string to check
     * @return true if appending would exceed max length, false otherwise
     */
    protected boolean willExceedMaxLength(String str) {
        if (str == null) {
            return false;
        }
        int additionalLength = str.length();
        if (this.isNotEmpty() && String4j.isNotEmpty(separator)) {
            additionalLength += separator.length();
        }
        return buffer.length() + additionalLength > maxLength;
    }
}