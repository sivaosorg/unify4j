package org.unify4j.common;

import org.unify4j.model.c.Ascii;
import org.unify4j.model.enums.TimezoneType;
import org.unify4j.text.TimeFormatText;

import java.util.Date;
import java.util.TimeZone;

@SuppressWarnings({"GrazieInspection"})
public class Text4j {
    protected final StringBuilder message;

    public Text4j() {
        message = new StringBuilder();
    }

    /**
     * Appends a non-empty string to the message.
     *
     * @param str The string to append.
     * @return The current instance of Text4j.
     */
    public Text4j appendCompact(String str) {
        if (String4j.isNotEmpty(str)) {
            message.append(str);
        }
        return this;
    }

    /**
     * Appends a formatted string using the specified format and arguments, followed by a space.
     *
     * @param format The format string.
     * @param args   The arguments to format.
     * @return The current instance of Text4j.
     */
    public Text4j appendCompact(String format, Object... args) {
        if (String4j.isEmpty(format) || Array4j.isEmpty(args)) {
            return this;
        }
        String formatted = String.format(format, args);
        return this.appendCompact(formatted);
    }

    /**
     * Appends a non-null object's string representation (or its JSON if not a primitive).
     *
     * @param o The object to append.
     * @return The current instance of Text4j.
     */
    public Text4j appendCompact(Object o) {
        if (o != null) {
            return this.appendCompact(Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o));
        }
        return this;
    }

    /**
     * Conditionally appends a non-empty string followed by a space to the message.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to append if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j appendCompactIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.appendCompact(str);
        }
        return this;
    }

    /**
     * Conditionally appends a formatted string using the specified format and arguments followed by a space.
     *
     * @param condition If true, the formatted string will be appended; otherwise, no action is taken.
     * @param format    The format string.
     * @param args      The arguments to format.
     * @return The current instance of Text4j.
     */
    public Text4j appendCompactIf(boolean condition, String format, Object... args) {
        if (condition && !String4j.isEmpty(format) && !Array4j.isEmpty(args)) {
            String formatted = String.format(format, args);
            return this.appendCompact(formatted);
        }
        return this;
    }

    /**
     * Appends a non-empty string followed by a space to the message.
     *
     * @param str The string to append.
     * @return The current instance of Text4j.
     */
    public Text4j append(String str) {
        if (String4j.isNotEmpty(str)) {
            message.append(str);
        }
        return this.space();
    }

    /**
     * Appends a string repeated a specified number of times followed by a space.
     *
     * @param str    The string to repeat and append.
     * @param repeat The number of times to repeat the string.
     * @return The current instance of Text4j.
     */
    public Text4j append(String str, int repeat) {
        return this.append(String4j.repeat(str, repeat));
    }

    /**
     * Appends a non-null object's string representation (or its JSON if not a primitive) followed by a space.
     *
     * @param o The object to append.
     * @return The current instance of Text4j.
     */
    public Text4j append(Object o) {
        if (o != null) {
            return this.append(Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o));
        }
        return this;
    }

    /**
     * Appends a non-null object's string representation (or its JSON if not a primitive) repeated a specified number of times.
     *
     * @param o      The object to append.
     * @param repeat The number of times to repeat the string representation of the object.
     * @return The current instance of Text4j.
     */
    public Text4j append(Object o, int repeat) {
        if (o != null) {
            String value = Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o);
            return this.append(String4j.repeat(value, repeat));
        }
        return this;
    }

    /**
     * Appends a formatted string using the specified format and arguments.
     *
     * @param format The format string.
     * @param args   The arguments to format.
     * @return The current instance of Text4j.
     */
    public Text4j append(String format, Object[] args) {
        if (String4j.isEmpty(format) || Array4j.isEmpty(args)) {
            return this;
        }
        return this.append(String.format(format, args));
    }

    /**
     * Conditionally appends a non-empty string to the message based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to append if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j appendIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.append(str);
        }
        return this;
    }

    /**
     * Conditionally appends a formatted string using the specified format and arguments to the message.
     *
     * @param condition If true, the formatted string will be appended; otherwise, no action is taken.
     * @param format    The format string.
     * @param args      The arguments to format.
     * @return The current instance of Text4j.
     */
    public Text4j appendIf(boolean condition, String format, Object... args) {
        if (condition && !String4j.isEmpty(format) && !Array4j.isEmpty(args)) {
            String formatted = String.format(format, args);
            return this.append(formatted);
        }
        return this;
    }

    /**
     * Conditionally appends a non-null object's string representation (or its JSON if not a primitive) to the message.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param o         The object to append if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j appendIf(boolean condition, Object o) {
        if (condition && o != null) {
            return this.append(Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o));
        }
        return this;
    }

    /**
     * Conditionally appends a non-null object's string representation (or its JSON if not a primitive) repeated a specified number of times to the message.
     *
     * @param condition If true, the object's string representation repeated will be appended; otherwise, no action is taken.
     * @param o         The object to append if the condition is true.
     * @param repeat    The number of times to repeat the object's string representation.
     * @return The current instance of Text4j.
     */
    public Text4j appendIf(boolean condition, Object o, int repeat) {
        if (condition && o != null) {
            String value = Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o);
            return this.append(String4j.repeat(value, repeat));
        }
        return this;
    }

    /**
     * Appends a formatted timestamp of the given date and timezone.
     *
     * @param date     The date to format.
     * @param timezone The timezone to use for formatting.
     * @return The current instance of Text4j.
     */
    public Text4j timestamp(Date date, TimeZone timezone) {
        return this.append(Time4j.format(date, timezone));
    }

    /**
     * Appends a formatted timestamp of the given date, timezone, and custom format.
     *
     * @param date     The date to format.
     * @param timezone The timezone to use for formatting.
     * @param format   The custom format string to use for formatting.
     * @return The current instance of Text4j.
     */
    public Text4j timestamp(Date date, TimeZone timezone, String format) {
        return this.append(Time4j.format(date, timezone, format));
    }

    /**
     * Appends a formatted timestamp of the given date and TimezoneType.
     *
     * @param date     The date to format.
     * @param timezone The TimezoneType to use for formatting.
     * @return The current instance of Text4j.
     */
    public Text4j timestamp(Date date, TimezoneType timezone) {
        return this.append(Time4j.format(date, timezone));
    }

    /**
     * Appends a formatted timestamp of the given date, TimezoneType, and custom format.
     *
     * @param date     The date to format.
     * @param timezone The TimezoneType to use for formatting.
     * @param format   The custom format string to use for formatting.
     * @return The current instance of Text4j.
     */
    public Text4j timestamp(Date date, TimezoneType timezone, String format) {
        return this.append(Time4j.format(date, timezone, format));
    }

    /**
     * Appends a formatted timestamp of the given date and custom format.
     *
     * @param date   The date to format.
     * @param format The custom format string to use for formatting.
     * @return The current instance of Text4j.
     */
    public Text4j timestamp(Date date, String format) {
        return this.append(Time4j.format(date, format));
    }

    /**
     * Appends a default formatted timestamp of the given date.
     *
     * @param date The date to format.
     * @return The current instance of Text4j.
     */
    public Text4j timestamp(Date date) {
        return this.append(Time4j.format(date, TimeFormatText.BIBLIOGRAPHY_COMPLETE_EPOCH_PATTERN));
    }

    /**
     * Appends a default formatted timestamp of the current date and time.
     *
     * @return The current instance of Text4j.
     */
    public Text4j timestamp() {
        return this.timestamp(new Date());
    }

    /**
     * Appends a timestamp of the current date and time enclosed in parentheses.
     *
     * @return The current instance of Text4j.
     */
    public Text4j timestampParenthesis() {
        String f = String.format("%s%s%s",
                Ascii.Punctuation.LEFT_PARENTHESIS,
                Time4j.format(new Date(), TimeFormatText.BIBLIOGRAPHY_COMPLETE_EPOCH_PATTERN),
                Ascii.Punctuation.RIGHT_PARENTHESIS);
        return this.append(f);
    }

    /**
     * Appends the given text enclosed in vertical bars.
     *
     * @param text The text to enclose in vertical bars.
     * @return The current instance of Text4j.
     */
    public Text4j vertical(String text) {
        message.append(String4j.repeat(Ascii.Symbol.VERTICAL_LINE, 1))
                .append(text)
                .append(String4j.repeat(Ascii.Symbol.VERTICAL_LINE, 1));
        return this.space();
    }

    /**
     * Appends the string representation of the given object enclosed in vertical bars.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param value The object to enclose in vertical bars.
     * @return The current instance of Text4j.
     */
    public Text4j vertical(Object value) {
        if (value == null) {
            return this;
        }
        return this.vertical(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
    }

    /**
     * Conditionally appends the given text enclosed in vertical bars based on the provided condition.
     *
     * @param condition If true, the text will be appended; otherwise, no action is taken.
     * @param text      The text to enclose in vertical bars if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j verticalIf(boolean condition, String text) {
        if (condition && String4j.isNotEmpty(text)) {
            return this.vertical(text);
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object enclosed in vertical bars based on the provided condition.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param value     The object to enclose in vertical bars if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j verticalIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.vertical(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
        }
        return this;
    }

    /**
     * Conditionally appends the given text enclosed in vertical bars repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the text will be appended; otherwise, no action is taken.
     * @param value     The text to enclose in vertical bars if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j verticalIf(boolean condition, Object value, int repeat) {
        if (condition && value != null) {
            String str = Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value);
            return this.vertical(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Appends the given string enclosed in parentheses.
     *
     * @param str The string to enclose in parentheses.
     * @return The current instance of Text4j.
     */
    public Text4j parenthesis(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s%s",
                Ascii.Punctuation.LEFT_PARENTHESIS,
                str,
                Ascii.Punctuation.RIGHT_PARENTHESIS);
        return this.append(f);
    }

    /**
     * Appends the string representation of the given object enclosed in parentheses.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param value The object to enclose in parentheses.
     * @return The current instance of Text4j.
     */
    public Text4j parenthesis(Object value) {
        if (value == null) {
            return this;
        }
        return this.parenthesis(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
    }

    /**
     * Conditionally appends the given string enclosed in parentheses based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to enclose in parentheses if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j parenthesisIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.parenthesis(str);
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object enclosed in parentheses based on the provided condition.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param value     The object to enclose in parentheses if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j parenthesisIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.parenthesis(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
        }
        return this;
    }

    /**
     * Conditionally appends the given string enclosed in parentheses repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param value     The text to enclose in parentheses if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j parenthesisIf(boolean condition, Object value, int repeat) {
        if (condition && value != null) {
            String str = Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value);
            return this.parenthesis(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Conditionally appends the given string enclosed in parentheses repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The text to enclose in parentheses if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j parenthesisIf(boolean condition, String str, int repeat) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.parenthesis(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Appends the given string enclosed in square brackets.
     *
     * @param str The string to enclose in square brackets.
     * @return The current instance of Text4j.
     */
    public Text4j bracket(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s%s",
                Ascii.Punctuation.LEFT_SQUARE_BRACKET,
                str,
                Ascii.Punctuation.RIGHT_SQUARE_BRACKET);
        return this.append(f);
    }

    /**
     * Appends the string representation of the given object enclosed in square brackets.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param value The object to enclose in square brackets.
     * @return The current instance of Text4j.
     */
    public Text4j bracket(Object value) {
        if (value == null) {
            return this;
        }
        return this.bracket(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
    }

    /**
     * Conditionally appends the given string enclosed in square brackets based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to enclose in square brackets if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j bracketIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.bracket(str);
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object enclosed in square brackets based on the provided condition.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param value     The object to enclose in square brackets if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j bracketIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.bracket(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
        }
        return this;
    }

    /**
     * Conditionally appends the given string enclosed in square brackets repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param value     The text to enclose in square brackets if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j bracketIf(boolean condition, Object value, int repeat) {
        if (condition && value != null) {
            String str = Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value);
            return this.bracket(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Conditionally appends the given string enclosed in square brackets repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The text to enclose in square brackets if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j bracketIf(boolean condition, String str, int repeat) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.bracket(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Appends the given string enclosed in curly brackets.
     *
     * @param str The string to enclose in curly brackets.
     * @return The current instance of Text4j.
     */
    public Text4j curlyBracket(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s%s",
                Ascii.Punctuation.LEFT_CURLY_BRACKET,
                str,
                Ascii.Punctuation.RIGHT_CURLY_BRACKET);
        return this.append(f);
    }

    /**
     * Appends the string representation of the given object enclosed in curly brackets.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param value The object to enclose in curly brackets.
     * @return The current instance of Text4j.
     */
    public Text4j curlyBracket(Object value) {
        if (value == null) {
            return this;
        }
        return this.curlyBracket(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
    }

    /**
     * Conditionally appends the given string enclosed in curly brackets based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to enclose in curly brackets if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j curlyBracketIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.curlyBracket(str);
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object enclosed in curly brackets based on the provided condition.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param value     The object to enclose in curly brackets if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j curlyBracketIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.curlyBracket(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
        }
        return this;
    }

    /**
     * Conditionally appends the given string enclosed in curly brackets repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param value     The text to enclose in curly brackets if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j curlyBracketIf(boolean condition, Object value, int repeat) {
        if (condition && value != null) {
            String str = Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value);
            return this.curlyBracket(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Conditionally appends the given string enclosed in curly brackets repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The text to enclose in curly brackets if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j curlyBracketIf(boolean condition, String str, int repeat) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.curlyBracket(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Appends the given string enclosed in quotation marks.
     *
     * @param str The string to enclose in quotation marks.
     * @return The current instance of Text4j.
     */
    public Text4j quotation(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s%s",
                Ascii.Punctuation.QUOTATION_MARK,
                str,
                Ascii.Punctuation.QUOTATION_MARK);
        return this.append(f);
    }

    /**
     * Appends the string representation of the given object enclosed in quotation marks.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param value The object to enclose in quotation marks.
     * @return The current instance of Text4j.
     */
    public Text4j quotation(Object value) {
        if (value == null) {
            return this;
        }
        return this.quotation(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
    }

    /**
     * Conditionally appends the given string enclosed in quotation marks based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to enclose in quotation marks if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j quotationIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.quotation(str);
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object enclosed in quotation marks based on the provided condition.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param value     The object to enclose in quotation marks if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j quotationIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.quotation(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
        }
        return this;
    }

    /**
     * Conditionally appends the given string enclosed in quotation marks repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param value     The text to enclose in quotation marks if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j quotationIf(boolean condition, Object value, int repeat) {
        if (condition && value != null) {
            String str = Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value);
            return this.quotation(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Conditionally appends the given string enclosed in quotation marks repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The text to enclose in quotation marks if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j quotationIf(boolean condition, String str, int repeat) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.quotation(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Appends the given string prefixed with a hyphen-minus.
     *
     * @param str The string to prefix with a hyphen-minus.
     * @return The current instance of Text4j.
     */
    public Text4j hyphenMinus(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s",
                Ascii.Punctuation.HYPHEN_MINUS,
                str);
        return this.append(f);
    }

    /**
     * Appends the string representation of the given object prefixed with a hyphen-minus.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param value The object to prefix with a hyphen-minus.
     * @return The current instance of Text4j.
     */
    public Text4j hyphenMinus(Object value) {
        if (value == null) {
            return this;
        }
        return this.hyphenMinus(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
    }

    /**
     * Conditionally appends the given string prefixed with a hyphen-minus based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to prefix with a hyphen-minus if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j hyphenMinusIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.hyphenMinus(str);
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object prefixed with a hyphen-minus based on the provided condition.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param value     The object to prefix with a hyphen-minus if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j hyphenMinusIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.hyphenMinus(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
        }
        return this;
    }

    /**
     * Conditionally appends the given string prefixed with a hyphen-minus repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param value     The text to prefix with a hyphen-minus if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j hyphenMinusIf(boolean condition, Object value, int repeat) {
        if (condition && value != null) {
            String str = Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value);
            return this.hyphenMinus(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Conditionally appends the given string prefixed with a hyphen-minus repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The text to prefix with a hyphen-minus if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j hyphenMinusIf(boolean condition, String str, int repeat) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.hyphenMinus(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Appends the given string prefixed with an equals sign.
     *
     * @param str The string to prefix with an equals sign.
     * @return The current instance of Text4j.
     */
    public Text4j equal(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s",
                Ascii.Symbol.EQUALS_SIGN,
                str);
        return this.append(f);
    }

    /**
     * Appends the string representation of the given object prefixed with an equals sign.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param value The object to prefix with an equals sign.
     * @return The current instance of Text4j.
     */
    public Text4j equal(Object value) {
        if (value == null) {
            return this;
        }
        return this.equal(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
    }

    /**
     * Conditionally appends the given string prefixed with an equals sign based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to prefix with an equals sign if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j equalIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.equal(str);
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object prefixed with an equals sign based on the provided condition.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param value     The object to prefix with an equals sign if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j equalIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.equal(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
        }
        return this;
    }

    /**
     * Conditionally appends the given string prefixed with an equals sign repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param value     The text to prefix with an equals sign if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j equalIf(boolean condition, Object value, int repeat) {
        if (condition && value != null) {
            String str = Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value);
            return this.equal(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Conditionally appends the given string prefixed with an equals sign repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The text to prefix with an equals sign if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j equalIf(boolean condition, String str, int repeat) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.equal(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Appends the given string suffixed with an equals sign.
     *
     * @param str The string to suffix with an equals sign.
     * @return The current instance of Text4j.
     */
    public Text4j endingEqual(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s",
                str,
                Ascii.Symbol.EQUALS_SIGN);
        return this.append(f);
    }

    /**
     * Appends the string representation of the given object suffixed with an equals sign.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param value The object to suffix with an equals sign.
     * @return The current instance of Text4j.
     */
    public Text4j endingEqual(Object value) {
        if (value == null) {
            return this;
        }
        return this.endingEqual(Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value));
    }

    /**
     * Conditionally appends the given string suffixed with an equals sign based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The string to suffix with an equals sign if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j endingEqualIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.endingEqual(str);
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object suffixed with an equals sign based on the provided condition.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended; otherwise, no action is taken.
     * @param value     The object to suffix with an equals sign if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j endingEqualIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.endingEqual(value);
        }
        return this;
    }

    /**
     * Conditionally appends the given string suffixed with an equals sign repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param value     The text to suffix with an equals sign if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j endingEqualIf(boolean condition, Object value, int repeat) {
        if (condition && value != null) {
            String str = Class4j.isPrimitive(value.getClass()) ? value.toString() : Json4j.toJson(value);
            return this.endingEqual(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Conditionally appends the given string suffixed with an equals sign repeated a specified number of times based on the provided condition.
     *
     * @param condition If true, the string will be appended; otherwise, no action is taken.
     * @param str       The text to suffix with an equals sign if the condition is true.
     * @param repeat    The number of times to repeat the text.
     * @return The current instance of Text4j.
     */
    public Text4j endingEqualIf(boolean condition, String str, int repeat) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.endingEqual(String4j.repeat(str, repeat));
        }
        return this;
    }

    /**
     * Appends a new line to the message.
     *
     * @return The current instance of Text4j.
     */
    public Text4j line() {
        return this.append(System.lineSeparator());
    }

    /**
     * Appends a new line repeated a specified number of times to the message.
     *
     * @param repeat The number of times to append the new line.
     * @return The current instance of Text4j.
     */
    public Text4j line(int repeat) {
        return this.append(String4j.repeat(System.lineSeparator(), repeat));
    }

    /**
     * Conditionally appends a new line to the message based on the provided condition.
     *
     * @param condition If true, a new line will be appended; otherwise, no action is taken.
     * @return The current instance of Text4j.
     */
    public Text4j lineIf(boolean condition) {
        if (condition) {
            return this.line();
        }
        return this;
    }

    /**
     * Conditionally appends a new line repeated a specified number of times to the message based on the provided condition.
     *
     * @param condition If true, a new line will be appended; otherwise, no action is taken.
     * @param repeat    The number of times to append the new line.
     * @return The current instance of Text4j.
     */
    public Text4j lineIf(boolean condition, int repeat) {
        if (condition) {
            return this.line(repeat);
        }
        return this;
    }

    /**
     * Conditionally appends the given string followed by a new line to the message.
     * If the string is empty, no action is taken.
     *
     * @param condition If true, the string will be appended followed by a new line; otherwise, no action is taken.
     * @param str       The string to append followed by a new line if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j lineIf(boolean condition, String str) {
        if (condition && String4j.isNotEmpty(str)) {
            return this.append(str).line();
        }
        return this;
    }

    /**
     * Conditionally appends the string representation of the given object followed by a new line to the message.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param condition If true, the object's string representation will be appended followed by a new line; otherwise, no action is taken.
     * @param value     The object to append followed by a new line if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j lineIf(boolean condition, Object value) {
        if (condition && value != null) {
            return this.append(value).line();
        }
        return this;
    }

    /**
     * Appends a comma to the message.
     *
     * @return The current instance of Text4j.
     */
    public Text4j comma() {
        return this.append(Ascii.Punctuation.COMMA);
    }

    /**
     * Appends a comma followed by the given string to the message.
     * If the string is empty, no action is taken.
     *
     * @param str The string to append after the comma.
     * @return The current instance of Text4j.
     */
    public Text4j beginningComma(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s %s",
                Ascii.Punctuation.COMMA,
                str);
        return this.append(f);
    }

    /**
     * Appends a comma followed by the string representation of the given object to the message.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param o The object to append after the comma.
     * @return The current instance of Text4j.
     */
    public Text4j beginningComma(Object o) {
        if (o == null) {
            return this;
        }
        return this.beginningComma(Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o));
    }

    /**
     * Appends a comma followed by the given string to the message.
     * If the string is empty, no action is taken.
     *
     * @param str The string to append after the comma.
     * @return The current instance of Text4j.
     */
    public Text4j endingComma(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s",
                str,
                Ascii.Punctuation.COMMA);
        return this.append(f);
    }

    /**
     * Appends a comma followed by the string representation of the given object to the message.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param o The object to append after the comma.
     * @return The current instance of Text4j.
     */
    public Text4j endingComma(Object o) {
        if (o == null) {
            return this;
        }
        return this.endingComma(Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o));
    }

    /**
     * Appends a semicolon to the message.
     *
     * @return The current instance of Text4j.
     */
    public Text4j colon() {
        return this.append(Ascii.Punctuation.COLON);
    }

    /**
     * Appends a colon followed by the given string to the message.
     * If the string is empty, no action is taken.
     *
     * @param str The string to append after the colon.
     * @return The current instance of Text4j.
     */
    public Text4j beginningColon(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s %s",
                Ascii.Punctuation.COLON,
                str);
        return this.append(f);
    }

    /**
     * Appends a colon followed by the string representation of the given object to the message.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param o The object to append after the colon.
     * @return The current instance of Text4j.
     */
    public Text4j beginningColon(Object o) {
        if (o == null) {
            return this;
        }
        return this.beginningColon(Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o));
    }

    /**
     * Appends a colon followed by the given string to the message.
     * If the string is empty, no action is taken.
     *
     * @param str The string to append after the colon.
     * @return The current instance of Text4j.
     */
    public Text4j endingColon(String str) {
        if (String4j.isEmpty(str)) {
            return this;
        }
        String f = String.format("%s%s",
                str,
                Ascii.Punctuation.COLON);
        return this.append(f);
    }

    /**
     * Appends a colon followed by the string representation of the given object to the message.
     * If the object is a primitive, its string value is used. Otherwise, its JSON representation is used.
     *
     * @param o The object to append after the colon.
     * @return The current instance of Text4j.
     */
    public Text4j endingColon(Object o) {
        if (o == null) {
            return this;
        }
        return this.endingColon(Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o));
    }

    /**
     * Appends a space to the message.
     *
     * @return The current instance of Text4j.
     */
    public Text4j space() {
        message.append(Ascii.Punctuation.SPACE);
        return this;
    }

    /**
     * Appends a space repeated a specified number of times to the message.
     *
     * @param repeat The number of times to append the space.
     * @return The current instance of Text4j.
     */
    public Text4j space(int repeat) {
        message.append(String4j.repeat(Ascii.Punctuation.SPACE, repeat));
        return this;
    }

    /**
     * Conditionally appends a space to the message based on the provided condition.
     *
     * @param condition If true, a space will be appended; otherwise, no action is taken.
     * @return The current instance of Text4j.
     */
    public Text4j spaceIf(boolean condition) {
        if (condition) {
            return this.space();
        }
        return this;
    }

    /**
     * Conditionally appends a space repeated a specified number of times to the message based on the provided condition.
     *
     * @param condition If true, a space will be appended; otherwise, no action is taken.
     * @param repeat    The number of times to repeat the space if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j spaceIf(boolean condition, int repeat) {
        if (condition) {
            return this.space(repeat);
        }
        return this;
    }

    /**
     * Conditionally appends a space followed by a string to the message based on the provided condition.
     *
     * @param condition If true, a space followed by the string will be appended; otherwise, no action is taken.
     * @param str       The string to append after the space if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j spaceIf(boolean condition, String str) {
        if (condition) {
            return this.appendCompact(str).space();
        }
        return this;
    }

    /**
     * Conditionally appends a space followed by a formatted string to the message based on the provided condition.
     *
     * @param condition If true, a space followed by the formatted string will be appended; otherwise, no action is taken.
     * @param format    The format string.
     * @param args      The arguments to format.
     * @return The current instance of Text4j.
     */
    public Text4j spaceIf(boolean condition, String format, Object... args) {
        if (condition) {
            return this.appendCompact(format, args).space();
        }
        return this;
    }

    /**
     * Conditionally appends a space followed by an object's string representation to the message based on the provided condition.
     *
     * @param condition If true, a space followed by the object's string representation will be appended; otherwise, no action is taken.
     * @param o         The object to append after the space if the condition is true.
     * @return The current instance of Text4j.
     */
    public Text4j spaceIf(boolean condition, Object o) {
        if (condition && o != null) {
            return this.appendCompact(o).space();
        }
        return this;
    }

    /**
     * Conditionally appends a space followed by an object's string representation repeated a specified number of times to the message
     * based on the provided condition.
     *
     * @param condition If true, a space followed by the object's string representation repeated will be appended; otherwise, no action is taken.
     * @param o         The object to append after the space if the condition is true.
     * @param repeat    The number of times to repeat the object's string representation.
     * @return The current instance of Text4j.
     */
    public Text4j spaceIf(boolean condition, Object o, int repeat) {
        if (condition && o != null) {
            String value = Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o);
            return this.append(String4j.repeat(value, repeat)).space();
        }
        return this;
    }

    /**
     * Clears the message by setting its length to zero.
     *
     * @return The current instance of Text4j.
     */
    public Text4j clear() {
        message.setLength(0);
        return this;
    }

    @Override
    public String toString() {
        return message.toString();
    }
}
