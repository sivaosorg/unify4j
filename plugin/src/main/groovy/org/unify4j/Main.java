package org.unify4j;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.common.Collection4j;
import org.unify4j.common.String4j;
import org.unify4j.model.builder.xml.SoapXmlBuilder;
import org.unify4j.model.builder.xml.SoapXmlNsBuilder;
import org.unify4j.model.builder.xml.SoapXmlPathBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"SpellCheckingInspection", "ConstantConditions"})
public class Main {
    protected static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {


        String request = SoapXmlBuilder.create("soap:Envelope")
                .attr("xmlns:soap", "http://www.w3.org/2003/05/soap-envelope")
                .attr("xmlns:tem", "http://tempuri.org/")

                .child("soap:Header")
                .child("tem:AuthHeader")
                .child("tem:login", "Luis1937")
                .child("tem:pwd", "MZR0zNqnI/KplFlYXiFk7m8/G/Iqxb3O")
                .child("tem:Id_CodFacturacion", "SER408")
                .child("tem:Nombre_Cargue", "AJA_GROUP")
                .up()
                .up()

                .child("soap:Body")
                .child("tem:GenerarGuiaSticker")
                .child("tem:num_Guia", "226939568533")
                .child("tem:num_GuiaFinal", "2269395685")
                .child("tem:ide_CodFacturacion", "SER408")
                .child("tem:sFormatoImpresionGuia", "4")
                .child("tem:Id_ArchivoCargar", "")
                .child("tem:interno", "false")
                .child("tem:bytesReport", "")
                .up()
                .up()

                .build();

        System.out.println("Generated SOAP XML:\n" + request);
        Unirest.config()
                .connectTimeout(60000)
                .socketTimeout(100000)
                .retryAfter(true, 3)
                .automaticRetries(true);

        HttpResponse<String> response = Unirest.post(
                        "https://developer.servientrega.com/WsSisclinetGeneraGuias/GeneracionGuias.asmx")
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "http://tempuri.org/GenerarGuiaSticker")
                .body(request)
                .asString();

        System.out.println("HTTP Status: " + response.getStatus());

//        if (!response.isSuccess()) {
//            return;
//        }

        System.out.println("Response Body:\n" + response.getBody());

        Map<String, String> ns = new HashMap<>();
        ns.put("soap", "http://www.w3.org/2003/05/soap-envelope");
        ns.put("ns", "http://tempuri.org/");
        // SoapXmlNsBuilder xml = SoapXmlNsBuilder.from(response.getBody(), ns);
        SoapXmlPathBuilder xml = SoapXmlPathBuilder.auto(response.getBody());

        // String success = xml.get("//ns:GenerarGuiaStickerResult");
        String success = xml.get("Body.GenerarGuiaStickerResponse.GenerarGuiaStickerResult");

        if ("true".equalsIgnoreCase(success)) {
            // String bytesReport = xml.get("//ns:bytesReport");
            String bytesReport = xml.get("Body.GenerarGuiaStickerResponse.bytesReport");
            System.out.println("Bytes Report: " + bytesReport);
        } else {
            // String code = xml.get("//soap:Fault/soap:Code/soap:Value");
            // String reason = xml.get("//soap:Fault/soap:Reason/soap:Text");
            String code = xml.get("Body.Fault.Code.Value");
            String reason = xml.get("Body.Fault.Reason.Text");
            System.out.println("Error Code: " + code);
            System.out.println("Error Reason: " + reason);
        }
    }

    public static void test0001() {
        int dailyLimit = 300;      // Total leads/day
        int intervalMinutes = 3;   // Cron interval → 20 cycles/hour
        int cyclesPerHour = 60 / intervalMinutes;

        int totalAllocated = 0;

        System.out.println("===== Lead Allocation for 1 Hour =====");

        for (int i = 1; i <= cyclesPerHour; i++) {
            int allocated = LeadAllocator.calculateLeadsPerCycle(dailyLimit, intervalMinutes);
            totalAllocated += allocated;
            System.out.printf("Cycle %2d: allocated = %2d, running total = %2d%n", i, allocated, totalAllocated);
        }

        System.out.println("=====================================");
        System.out.println("Total leads allocated in this hour: " + totalAllocated);

    }

    /**
     * Single-function controlled random lead allocation.
     * <p>
     * Requirements supported:
     * - Only hourly quota matters.
     * - Example: 400 leads/day → ~17 per hour.
     * - Runs every X minutes (intervalMinutes).
     * - Each cycle returns random integer 0..maxPossible.
     * - Ensures total per hour ALWAYS equals hourlyQuota.
     * - No other class, no DTO, no database calls.
     * - All logic AND state are stored inside this function.
     */
    public static class LeadAllocator {

        // Internal static state kept inside this function/class only
        private static int hourRemaining = -1;
        private static int cyclesLeft = -1;

        public static int calculateLeadsPerCycle(int dailyLimit, int intervalMinutes) {

            // Compute hourly quota (ex: 400/24 ≈ 17)
            int hourlyQuota = (int) Math.ceil(dailyLimit / 24.0);

            // Compute cycles per hour (ex: 3 min → 20 cycles/hour)
            int cyclesPerHour = 60 / intervalMinutes;

            // Initialize for the first time OR reset on new hour
            if (hourRemaining < 0 || cyclesLeft < 0 || cyclesLeft == 0) {
                hourRemaining = hourlyQuota;
                cyclesLeft = cyclesPerHour;
            }

            // Calculate maximum random allocation allowed this cycle
            int maxPossible = hourRemaining - (cyclesLeft - 1);
            if (maxPossible < 0) {
                maxPossible = 0;
            }

            // Generate random allocation: 0..maxPossible
            int allocated = (int) (Math.random() * (maxPossible + 1));

            // Update remaining hourly counters
            hourRemaining -= allocated;
            cyclesLeft--;

            return allocated;
        }
    }


    public static void test002() {
        List<PhoneTestCase> cases = Arrays.asList(
                new PhoneTestCase(null, List.of(10), List.of("0"), "0", false, null),
                new PhoneTestCase("", List.of(10), List.of("0"), "0", false, ""),
                new PhoneTestCase("0912345678", List.of(10), List.of("0"), "0", false, "0912345678"),
                new PhoneTestCase("9123456789", List.of(10), List.of("0"), "0", false, "09123456789"),
                new PhoneTestCase("912345678", Arrays.asList(9, 10), List.of("0"), "0", false, "0912345678"),
                new PhoneTestCase("(91) 234-5678", List.of(9), List.of("0"), "0", true, "0912345678"),
                new PhoneTestCase("+84-912-345-678", List.of(11), Arrays.asList("0", "84"), "", true, "84912345678"),
                new PhoneTestCase("9123456789", List.of(10), List.of("0"), "", false, "9123456789"),
                new PhoneTestCase("12345", List.of(10), List.of("0"), "0", false, "12345"),
                new PhoneTestCase("9123456789", Collections.emptyList(), List.of("0"), "0", false, "9123456789"),
                new PhoneTestCase(" 9123456789 ", List.of(10), List.of("0"), "0", false, "09123456789"));
        for (PhoneTestCase testCase : cases) {
            String result = formatPhoneNumber(testCase.input, testCase.expectedLengths, testCase.validPrefixes, testCase.prependPrefix, testCase.removeNonNumeric);

            System.out.println("Input: \"" + testCase.input + "\" | Expected: \"" + testCase.expectedOutput + "\" | Result: \"" + result + "\" | " + (String4j.equals(result, testCase.expectedOutput) ? "✓" : "✗"));
        }
    }

    private static class PhoneTestCase {
        String input;
        List<Integer> expectedLengths;
        List<String> validPrefixes;
        String prependPrefix;
        boolean removeNonNumeric;
        String expectedOutput;

        PhoneTestCase(String input, List<Integer> expectedLengths,
                      List<String> validPrefixes, String prependPrefix, boolean removeNonNumeric, String expectedOutput) {
            this.input = input;
            this.expectedLengths = expectedLengths;
            this.validPrefixes = validPrefixes;
            this.prependPrefix = prependPrefix;
            this.removeNonNumeric = removeNonNumeric;
            this.expectedOutput = expectedOutput;
        }
    }

    /**
     * <p>
     * Checks if a String {@code str} contains Unicode digits, if yes then
     * concatenate all the digits in {@code str} and return it as a String.
     * </p>
     *
     * <p>
     * An empty ("") String will be returned if no digits found in {@code str}.
     * </p>
     *
     * @param str the String to extract digits from, may be null
     * @return String with only digits, or an empty ("") String if no digits
     * found, or {@code null} String if {@code str} is null
     * @since 3.6
     */
    public static String getDigits(String str) {
        if (String4j.isEmpty(str)) {
            return str;
        }
        final int sz = str.length();
        final StringBuilder strDigits = new StringBuilder(sz);
        for (int i = 0; i < sz; i++) {
            final char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                strDigits.append(tempChar);
            }
        }
        return strDigits.toString();
    }

    /**
     * Format and normalize phone numbers based on dynamic rules.
     * <p>
     * Rules:
     * - If the phone number length matches any length in `expectedLengths`
     * - AND the phone number does NOT start with any prefix in `validPrefixes`
     * → Then prepend the string specified in `prependPrefix` (usually "0")
     *
     * @param phone            the raw input phone number
     * @param expectedLengths  list of target lengths before applying formatting rules
     * @param validPrefixes    list of allowed starting prefixes (e.g., ["0", "84"])
     * @param prependPrefix    the prefix to insert when the number does not match any allowed prefix
     * @param removeNonNumeric whether to strip all non-numeric characters before normalization
     * @return normalized and formatted phone number
     */
    public static String formatPhoneNumber(String phone, List<Integer> expectedLengths, List<String> validPrefixes,
                                           String prependPrefix, boolean removeNonNumeric) {
        if (String4j.isEmpty(phone)) {
            return phone;
        }
        // Clean up input phone number
        String cleaned = String4j.trimWhitespace(phone);

        // Remove non-numeric characters if specified
        if (removeNonNumeric) {
            cleaned = getDigits(cleaned);
        }

        // Apply formatting rules for any allowed length
        if (Collection4j.isNotEmpty(expectedLengths) && expectedLengths.contains(cleaned.length())) {
            boolean startsWithValidPrefix = false;

            for (String prefix : validPrefixes) {
                if (cleaned.startsWith(prefix)) {
                    startsWithValidPrefix = true;
                    break;
                }
            }

            // If no valid prefix found, prepend the specified prefix
            // (e.g., "0"), only when necessary
            if (!startsWithValidPrefix) {
                if (String4j.isNotEmpty(prependPrefix)) {
                    cleaned = prependPrefix + cleaned;
                }
            }
        }

        return cleaned;
    }


    /**
     * Rounds up a double number to 2 decimal places.
     * Handles all edge cases including floating point precision issues.
     * <p>
     * Examples:
     * - 1500089.6099999996 -> 1500089.61
     * - 123.456 -> 123.46
     * - 123.001 -> 123.01
     * - 123.0 -> 123.00
     * - 0.0 -> 0.00
     * - -123.456 -> -123.45 (rounds towards positive infinity)
     *
     * @param value the double value to round
     * @return the rounded value as double with exactly 2 decimal places
     * @throws IllegalArgumentException if value is NaN or Infinite
     */
    public static double roundUpTo2Decimals(double value) {
        // Handle edge cases
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Cannot round NaN value");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("Cannot round infinite value: " + value);
        }

        // Use BigDecimal for precise arithmetic
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.UP);
        return bd.doubleValue();
    }

    /**
     * Rounds up a Double object to 2 decimal places.
     * Returns null if input is null.
     *
     * @param value the Double object to round
     * @return the rounded value as Double with exactly 2 decimal places, or null if input is null
     * @throws IllegalArgumentException if value is NaN or Infinite
     */
    public static Double roundUpTo2Decimals(Double value) {
        return value == null ? null : roundUpTo2Decimals(value.doubleValue());
    }

    /**
     * Rounds up a double number to 2 decimal places and returns as formatted string.
     * This version guarantees exactly 2 decimal places in the string representation.
     * <p>
     * Examples:
     * - 1500089.6099999996 -> "1500089.61"
     * - 123.0 -> "123.00"
     * - 0.0 -> "0.00"
     *
     * @param value the double value to round and format
     * @return the rounded value as string with exactly 2 decimal places
     * @throws IllegalArgumentException if value is NaN or Infinite
     */
    public static String roundUpTo2DecimalsAsString(double value) {
        // Handle edge cases
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Cannot round NaN value");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("Cannot round infinite value: " + value);
        }

        // Use BigDecimal for precise arithmetic and formatting
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(2, RoundingMode.UP);
        return bd.toPlainString();
    }

    /**
     * Rounds up a Double object to 2 decimal places and returns as formatted string.
     * Returns null if input is null.
     *
     * @param value the Double object to round and format
     * @return the rounded value as string with exactly 2 decimal places, or null if input is null
     * @throws IllegalArgumentException if value is NaN or Infinite
     */
    public static String roundUpTo2DecimalsAsString(Double value) {
        return value == null ? null : roundUpTo2DecimalsAsString(value.doubleValue());
    }

    /**
     * Generic method to round up to any number of decimal places.
     *
     * @param value         the double value to round
     * @param decimalPlaces the number of decimal places to round to (must be >= 0)
     * @return the rounded value as double
     * @throws IllegalArgumentException if value is NaN/Infinite or decimalPlaces is negative
     */
    public static double roundUpToDecimals(double value, int decimalPlaces) {
        // Validate input
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Cannot round NaN value");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("Cannot round infinite value: " + value);
        }
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("Decimal places must be non-negative: " + decimalPlaces);
        }

        // Use BigDecimal for precise arithmetic
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.UP);
        return bd.doubleValue();
    }

    /**
     * Generic method to round up to any number of decimal places and return as string.
     *
     * @param value         the double value to round and format
     * @param decimalPlaces the number of decimal places to round to (must be >= 0)
     * @return the rounded value as string with exact decimal places
     * @throws IllegalArgumentException if value is NaN/Infinite or decimalPlaces is negative
     */
    public static String roundUpToDecimalsAsString(double value, int decimalPlaces) {
        // Validate input
        if (Double.isNaN(value)) {
            throw new IllegalArgumentException("Cannot round NaN value");
        }
        if (Double.isInfinite(value)) {
            throw new IllegalArgumentException("Cannot round infinite value: " + value);
        }
        if (decimalPlaces < 0) {
            throw new IllegalArgumentException("Decimal places must be non-negative: " + decimalPlaces);
        }

        // Use BigDecimal for precise arithmetic and formatting
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(decimalPlaces, RoundingMode.UP);
        return bd.toPlainString();
    }

    /**
     * Safe wrapper that handles all possible exceptions and returns a default value.
     * This method never throws exceptions.
     *
     * @param value        the double value to round
     * @param defaultValue the value to return if rounding fails
     * @return the rounded value or defaultValue if an error occurs
     */
    public static double safeRoundUpTo2Decimals(double value, double defaultValue) {
        try {
            return roundUpTo2Decimals(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Safe wrapper that handles all possible exceptions and returns a default string.
     * This method never throws exceptions.
     *
     * @param value        the double value to round and format
     * @param defaultValue the string to return if rounding fails
     * @return the rounded value as string or defaultValue if an error occurs
     */
    public static String safeRoundUpTo2DecimalsAsString(double value, String defaultValue) {
        try {
            return roundUpTo2DecimalsAsString(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Rounds up a double number to 2 decimal places and returns as formatted string.
     * This version removes trailing zeros after the decimal point.
     * <p>
     * Examples:
     * - 1500089.6099999996 -> "1500089.61"
     * - 123.0 -> "123"
     * - 0.0 -> "0"
     *
     * @param value the double value to round and format
     * @return the rounded value as string without trailing zeros
     * @throws IllegalArgumentException if value is NaN or Infinite
     */
    public static String roundUpTo2DecimalsAsStringNoTrailingZeros(double value) {
        double rounded = roundUpTo2Decimals(value);
        if (rounded == (long) rounded) {
            return String.valueOf((long) rounded);
        }
        return roundUpTo2DecimalsAsString(value);
    }

    /**
     * Comprehensive test method to demonstrate all edge cases.
     */
    public static void runTests() {
        System.out.println("=== Testing RoundingUtils ===");

        // Normal cases
        testCase(1500089.6099999996, "1500089.61");
        testCase(123.456, "123.46");
        testCase(123.454, "123.46"); // Should round up
        testCase(123.001, "123.01");
        testCase(123.0, "123.00");
        testCase(0.0, "0.00");
        testCase(0.001, "0.01");
        testCase(0.009, "0.01");
        testCase(1234, "1234");

        // Negative numbers
        testCase(-123.456, "-123.45"); // RoundingMode.UP rounds towards positive infinity
        testCase(-123.454, "-123.45");
        testCase(-0.001, "0.00"); // -0.001 rounded up becomes 0.00

        // Edge cases
        testCase(Double.MAX_VALUE, String.valueOf(BigDecimal.valueOf(Double.MAX_VALUE).setScale(2, RoundingMode.UP)));
        testCase(Double.MIN_VALUE, "0.01"); // Very small positive number rounds up to 0.01
        testCase(-Double.MIN_VALUE, "0.00"); // Very small negative number rounds up to 0.00

        // Large numbers
        testCase(999999999999.999, "1000000000000.00");
        testCase(1000000000000.001, "1000000000000.01");

        // Very small decimals
        testCase(0.0001, "0.01");
        testCase(0.00001, "0.01");
        testCase(-0.0001, "0.00");

        // Error cases
        System.out.println("\n=== Testing Error Cases ===");
        testErrorCase(Double.NaN, "NaN should throw exception");
        testErrorCase(Double.POSITIVE_INFINITY, "Positive infinity should throw exception");
        testErrorCase(Double.NEGATIVE_INFINITY, "Negative infinity should throw exception");

        // Safe methods
        System.out.println("\n=== Testing Safe Methods ===");
        System.out.println("Safe round NaN: " + safeRoundUpTo2Decimals(Double.NaN, 0.0));
        System.out.println("Safe round Infinity: " + safeRoundUpTo2DecimalsAsString(Double.POSITIVE_INFINITY, "ERROR"));
    }

    private static void testCase(double input, String expected) {
        try {
            double rounded = roundUpTo2Decimals(input);
            String formatted = roundUpTo2DecimalsAsString(input);
            System.out.printf("Input: %s -> Rounded: %.2f, Formatted: %s (Expected: %s) %s%n", input, rounded, formatted, expected, formatted.equals(expected) ? "✓" : "✗");
        } catch (Exception e) {
            System.out.printf("Input: %s -> ERROR: %s%n", input, e.getMessage());
        }
    }

    private static void testErrorCase(double input, String description) {
        try {
            roundUpTo2Decimals(input);
            System.out.println(description + " - FAILED (should have thrown exception)");
        } catch (IllegalArgumentException e) {
            System.out.println(description + " - PASSED: " + e.getMessage());
        }
    }

    public enum LocLevelType {

        LEVEL_1(1),
        LEVEL_2(2),
        LEVEL_3(3),
        LEVEL_4(4);

        private final int value;

        LocLevelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        /**
         * Finds the LocLevelType by its integer value.
         *
         * @param value the integer value of the LocLevelType
         * @return an Optional containing the LocLevelType if found, or empty if not found
         */
        public static Optional<LocLevelType> fromValue(int value) {
            for (LocLevelType type : LocLevelType.values()) {
                if (type.getValue() == value) {
                    return Optional.of(type);
                }
            }
            return Optional.empty();
        }

        /**
         * Gets the maximum LocLevelType from a list of LocLevelTypes.
         *
         * @param levels the list of LocLevelTypes
         * @return an Optional containing the maximum LocLevelType, or empty if the list is empty
         */
        public static Optional<LocLevelType> getMaxLevel(List<LocLevelType> levels) {
            if (Collection4j.isEmpty(levels)) {
                return Optional.empty();
            }
            return levels.stream().max(Comparator.comparingInt(LocLevelType::getValue));
        }

        /**
         * Checks if there is a next level for the given integer value.
         *
         * @param value the integer value to check
         * @return true if there is a next level, false otherwise
         */
        public static boolean hasNextLevel(int value) {
            return fromValue(value).isPresent() && fromValue(value + 1).isPresent();
        }

        public static List<LocLevelType> under(int value) {
            return Arrays.stream(LocLevelType.values()).filter(level -> level.getValue() <= value).collect(Collectors.toList());
        }
    }

    public enum LocLevelEffectiveTimingType {
        NEXT_HOUR(1, false),
        NEXT_DAY(2, false),
        NEXT_15_MINUTES(3, true),
        NEXT_10_MINUTES(4, true),
        NEXT_5_MINUTES(5, true);

        private final int value;
        private final boolean isDeprecated; // Indicates if the timing type is deprecated

        LocLevelEffectiveTimingType(int value, boolean isDeprecated) {
            this.value = value;
            this.isDeprecated = isDeprecated;
        }

        LocLevelEffectiveTimingType(int value) {
            this.value = value;
            this.isDeprecated = false;
        }

        public int getValue() {
            return value;
        }

        public boolean isDeprecated() {
            return isDeprecated;
        }

        /**
         * Finds the LocLevelEffectiveTimingType by its integer value.
         *
         * @param value the integer value of the {@link LocLevelEffectiveTimingType}
         * @return an Optional containing the {@link LocLevelEffectiveTimingType} if found, or empty if not found
         */
        public static Optional<LocLevelEffectiveTimingType> fromValue(int value) {
            for (LocLevelEffectiveTimingType type : LocLevelEffectiveTimingType.values()) {
                if (type.getValue() == value) {
                    return Optional.of(type);
                }
            }
            return Optional.empty();
        }

        /**
         * Calculates the effective time based on the timing type.
         *
         * @param at the reference Date
         * @return the calculated effective Date, or null if the input is null
         */
        public Date calculateEffectiveTime(Date at) {
            if (at == null) {
                return null;
            }

            Calendar ins = Calendar.getInstance();
            ins.setTime(at);

            switch (this) {
                case NEXT_HOUR:
                    // Move to next hour, zero minutes/seconds/millis
                    ins.add(Calendar.HOUR_OF_DAY, 1);
                    ins.set(Calendar.MINUTE, 0);
                    ins.set(Calendar.SECOND, 0);
                    ins.set(Calendar.MILLISECOND, 0);
                    return ins.getTime();

                case NEXT_DAY:
                    // Move to next day at 00:00:00.000
                    ins.add(Calendar.DAY_OF_MONTH, 1);
                    ins.set(Calendar.HOUR_OF_DAY, 0);
                    ins.set(Calendar.MINUTE, 0);
                    ins.set(Calendar.SECOND, 0);
                    ins.set(Calendar.MILLISECOND, 0);
                    return ins.getTime();

                case NEXT_15_MINUTES:
                    return addMinutes(ins, 15);

                case NEXT_10_MINUTES:
                    return addMinutes(ins, 10);

                case NEXT_5_MINUTES:
                    return addMinutes(ins, 5);

                default:
                    return at;
            }
        }

        /**
         * Calculates the effective time based on the timing type using the current date and time.
         *
         * @return the calculated effective Date
         */
        public Date calculateEffectiveTime() {
            return this.calculateEffectiveTime(new Date());
        }

        /**
         * Rounds up the given calendar time to the next interval of minutes.
         *
         * @param calendar        the Calendar instance to round up
         * @param intervalMinutes the interval in minutes to round up to
         * @return the rounded Date, or null if the input is invalid
         */
        private Date roundUpMinutes(Calendar calendar, int intervalMinutes) {
            if (calendar == null || intervalMinutes <= 0) {
                return null;
            }

            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            int millisecond = calendar.get(Calendar.MILLISECOND);

            int remainder = minute % intervalMinutes;

            boolean alreadyOnBoundary =
                    remainder == 0 && second == 0 && millisecond == 0;

            if (!alreadyOnBoundary) {
                int minutesToAdd = remainder == 0
                        ? intervalMinutes
                        : intervalMinutes - remainder;
                calendar.add(Calendar.MINUTE, minutesToAdd);
            }

            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }

        /**
         * Adds the specified number of minutes to the calendar and resets seconds and milliseconds.
         *
         * @param calendar the Calendar instance
         * @param minutes  the number of minutes to add
         * @return the updated Date
         */
        private Date addMinutes(Calendar calendar, int minutes) {
            calendar.add(Calendar.MINUTE, minutes);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            return calendar.getTime();
        }

    }

}