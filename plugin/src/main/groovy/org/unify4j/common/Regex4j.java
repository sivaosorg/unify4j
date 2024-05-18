package org.unify4j.common;

import org.unify4j.text.ExpressionText;

import java.util.regex.Pattern;

public class Regex4j {

    /**
     * Checks if the provided string is a valid email address.
     *
     * @param email The email address to be validated.
     * @return true if the email address is valid, false otherwise.
     */
    public static boolean isEmail(String email) {
        if (String4j.isEmpty(email)) {
            return false;
        }
        Pattern pattern = Pattern.compile(ExpressionText.EMAIL_REGULAR_EXPRESSION);
        return pattern.matcher(email).matches();
    }

    /**
     * Checks if the provided string is a valid IPv4 address.
     *
     * @param ip The IPv4 address to be validated.
     * @return true if the IPv4 address is valid, false otherwise.
     */
    public static boolean isIp4(String ip) {
        if (String4j.isEmpty(ip)) {
            return false;
        }
        Pattern pattern = Pattern.compile(ExpressionText.IPV4_DIGITS_REGULAR_EXPRESSION);
        return pattern.matcher(ip.trim()).matches();
    }

    /**
     * Checks if the provided string is a valid standard IPv6 address.
     *
     * @param ip The IPv6 address to be validated.
     * @return true if the IPv6 address is valid, false otherwise.
     */
    public static boolean isIp6Std(final String ip) {
        if (String4j.isEmpty(ip)) {
            return false;
        }
        Pattern pattern = Pattern.compile(ExpressionText.IPV6_STD_DIGITS_REGULAR_EXPRESSION);
        return pattern.matcher(ip.trim()).matches();
    }

    /**
     * Checks if the provided string is a valid IPv6 address with hex compression.
     *
     * @param ip The IPv6 address to be validated.
     * @return true if the IPv6 address is valid with hex compression, false otherwise.
     */
    public static boolean isIp6HexCompressed(String ip) {
        if (String4j.isEmpty(ip)) {
            return false;
        }
        Pattern pattern = Pattern.compile(ExpressionText.IPV6_HEX_COMPRESSED_DIGITS_REGULAR_EXPRESSION);
        return pattern.matcher(ip.trim()).matches();
    }

    /**
     * Checks if the provided string is a valid IPv6 address.
     * This function checks both standard and hex compressed formats.
     *
     * @param ip The IPv6 address to be validated.
     * @return true if the IPv6 address is valid, false otherwise.
     */
    public static boolean isIp6(final String ip) {
        if (String4j.isEmpty(ip)) {
            return false;
        }
        return isIp6Std(ip.trim()) || isIp6HexCompressed(ip.trim());
    }

    /**
     * Checks if the provided string is a valid IP address.
     * This function validates both IPv4 and IPv6 addresses.
     *
     * @param ip The IP address to be validated.
     * @return true if the IP address is valid, false otherwise.
     */
    public static boolean isIp(String ip) {
        Pattern pattern = Pattern.compile(ExpressionText.IP_DIGITS_REPEAT_REGULAR_EXPRESSION);
        return pattern.matcher(ip.trim()).matches() && (isIp4(ip.trim()) || isIp6(ip.trim()));
    }

    /**
     * Checks if the provided string is a valid identifier.
     * An identifier typically conforms to certain naming conventions,
     * such as starting with a letter or underscore, followed by letters, digits, or underscores.
     *
     * @param identifier The string to be checked for identifier validity.
     * @return true if the string is a valid identifier, false otherwise.
     */
    public static boolean isIdentifier(String identifier) {
        Pattern pattern = Pattern.compile(ExpressionText.IDENTIFIER_REGULAR_EXPRESS);
        return pattern.matcher(identifier).matches();
    }

    /**
     * Checks if the provided string is a valid MAC (Media Access Control) address.
     * MAC addresses are typically represented as a series of hexadecimal digits separated by colons or hyphens.
     *
     * @param mac The string to be checked for MAC address validity.
     * @return true if the string is a valid MAC address, false otherwise.
     */
    public static boolean isMAC(String mac) {
        Pattern pattern = Pattern.compile(ExpressionText.MAC_REGULAR_EXPRESS);
        return pattern.matcher(mac).matches();
    }

    /**
     * Checks if the provided string is a valid CVV (Card Verification Value).
     * CVV is a security feature for "card not present" transactions, typically a 3 or 4-digit number.
     *
     * @param cvv The string to be checked for CVV validity.
     * @return true if the string is a valid CVV, false otherwise.
     */
    public static boolean isCVV(String cvv) {
        Pattern pattern = Pattern.compile(ExpressionText.CVV_REGULAR_EXPRESS);
        return pattern.matcher(cvv).matches();
    }

    /**
     * Checks if the provided string is a valid 12-hour time format.
     * The 12-hour time format consists of hours (1-12), minutes (00-59), and optionally AM/PM indicator.
     *
     * @param time The string to be checked for 12-hour time format validity.
     * @return true if the string is in a valid 12-hour time format, false otherwise.
     */
    public static boolean isTime12H(String time) {
        Pattern pattern = Pattern.compile(ExpressionText.TIME_12_HOUR_REGULAR_EXPRESS);
        return pattern.matcher(time).matches();
    }

    /**
     * Checks if the provided string is a valid 12-hour time format with hours, minutes, seconds, and AM/PM indicator.
     * The 12-hour time format consists of hours (1-12), minutes (00-59), seconds (00-59), and optionally AM/PM indicator.
     *
     * @param time The string to be checked for full 12-hour time format validity.
     * @return true if the string is in a valid full 12-hour time format, false otherwise.
     */
    public static boolean isTime12HFully(String time) {
        Pattern pattern = Pattern.compile(ExpressionText.TIME_12_HOUR_FULL_REGULAR_EXPRESS);
        return pattern.matcher(time).matches();
    }

    /**
     * Checks if the provided string is a valid 24-hour time format.
     * The 24-hour time format consists of hours (00-23) and minutes (00-59).
     *
     * @param time The string to be checked for 24-hour time format validity.
     * @return true if the string is in a valid 24-hour time format, false otherwise.
     */
    public static boolean isTime24H(String time) {
        Pattern pattern = Pattern.compile(ExpressionText.TIME_24_HOUR_REGULAR_EXPRESS);
        return pattern.matcher(time).matches();
    }

    /**
     * Checks if the provided string is a valid 24-hour time format with hours, minutes, and seconds.
     * The 24-hour time format consists of hours (00-23), minutes (00-59), and seconds (00-59).
     *
     * @param time The string to be checked for full 24-hour time format validity.
     * @return true if the string is in a valid full 24-hour time format, false otherwise.
     */
    public static boolean isTime24HFully(String time) {
        Pattern pattern = Pattern.compile(ExpressionText.TIME_24_HOUR_FULL_REGULAR_EXPRESS);
        return pattern.matcher(time).matches();
    }

    /**
     * Checks if the provided string is a valid date format.
     * The date format can vary depending on the context and locale.
     *
     * @param date The string to be checked for date format validity.
     * @return true if the string is in a valid date format, false otherwise.
     */
    public static boolean isDate(String date) {
        Pattern pattern = Pattern.compile(ExpressionText.DATE_REGULAR_EXPRESS);
        return pattern.matcher(date).matches();
    }

    /**
     * Checks if the provided string is a valid domain name.
     * A domain name typically consists of a series of labels separated by dots.
     *
     * @param domain The string to be checked for domain name validity.
     * @return true if the string is a valid domain name, false otherwise.
     */
    public static boolean isDomain(String domain) {
        Pattern pattern = Pattern.compile(ExpressionText.DOMAIN_REGULAR_EXPRESS);
        return pattern.matcher(domain).matches();
    }

    /**
     * Checks if the provided string is a valid Mastercard number.
     * Mastercard numbers typically start with certain prefixes and follow specific length and checksum rules.
     *
     * @param mastercard The string to be checked for Mastercard number validity.
     * @return true if the string is a valid Mastercard number, false otherwise.
     */
    public static boolean isMastercard(String mastercard) {
        Pattern pattern = Pattern.compile(ExpressionText.MASTERCARD_REGULAR_EXPRESS);
        return pattern.matcher(mastercard).matches();
    }

    /**
     * Checks if the provided string is a valid Visa card number.
     * Visa card numbers typically start with certain prefixes and follow specific length and checksum rules.
     *
     * @param visaCard The string to be checked for Visa card number validity.
     * @return true if the string is a valid Visa card number, false otherwise.
     */
    public static boolean isVisaCard(String visaCard) {
        Pattern pattern = Pattern.compile(ExpressionText.VISA_CARD_REGULAR_EXPRESS);
        return pattern.matcher(visaCard).matches();
    }

    /**
     * Checks if the provided string is a valid numeric value.
     * A numeric value can be an integer or a decimal number, optionally with a sign (+/-).
     *
     * @param number The string to be checked for numeric validity.
     * @return true if the string is a valid numeric value, false otherwise.
     */
    public static boolean isNumeric(String number) {
        Pattern pattern = Pattern.compile(ExpressionText.NUMBER_REGULAR_EXPRESS);
        return pattern.matcher(number).matches();
    }

    /**
     * Checks if the provided string is a valid URL (Uniform Resource Locator).
     * A URL typically consists of a protocol specifier (e.g., http:// or <a href="https://">...</a>) followed by a domain name and optional path.
     *
     * @param url The string to be checked for URL validity.
     * @return true if the string is a valid URL, false otherwise.
     */
    public static boolean isURL(String url) {
        Pattern pattern = Pattern.compile(ExpressionText.URL_REGULAR_EXPRESS);
        return pattern.matcher(url.trim()).matches();
    }

    /**
     * Checks if the provided string is a valid API version number.
     * An API version number typically consists of one to three numeric segments separated by dots.
     *
     * @param version The string to be checked for API version number validity.
     * @return true if the string is a valid API version number, false otherwise.
     */
    public static boolean isApiVersion(String version) {
        Pattern pattern = Pattern.compile(ExpressionText.VERSION_NUMBER_REGULAR_EXPRESS);
        return pattern.matcher(String4j.trimWhitespace(version)).matches();
    }
}
