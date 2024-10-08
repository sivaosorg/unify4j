package org.unify4j.common;

import org.unify4j.model.c.Ascii;
import org.unify4j.model.enums.TimezoneType;
import org.unify4j.text.TimeFormatText;

import java.util.Date;
import java.util.TimeZone;

public class Text4j {
    protected final StringBuilder message;

    public Text4j() {
        message = new StringBuilder();
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
     * Appends a non-empty string to the message.
     *
     * @param str The string to append.
     * @return The current instance of Text4j.
     */
    public Text4j appendSkippedSpace(String str) {
        if (String4j.isNotEmpty(str)) {
            message.append(str);
        }
        return this;
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
     * Appends a non-null object's string representation (or its JSON if not a primitive).
     *
     * @param o The object to append.
     * @return The current instance of Text4j.
     */
    public Text4j appendSkippedSpace(Object o) {
        if (o != null) {
            return this.appendSkippedSpace(Class4j.isPrimitive(o.getClass()) ? o.toString() : Json4j.toJson(o));
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
     * Appends a comma to the message.
     *
     * @return The current instance of Text4j.
     */
    public Text4j comma() {
        return this.append(Ascii.Punctuation.COMMA);
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
