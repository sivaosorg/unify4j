package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class Random4j {
    protected static final Logger logger = LoggerFactory.getLogger(Random4j.class);

    /**
     * <p>
     * Returns a random integer within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     * @return the random integer
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static int nextInt(final int startInclusive, final int endExclusive) {
        Vi4j.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        Vi4j.isTrue(startInclusive >= 0, "Both range values must be non-negative.");
        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return startInclusive + new Random().nextInt(endExclusive - startInclusive);
    }

    /**
     * <p> Returns a random int within 0 - Integer.MAX_VALUE </p>
     *
     * @return the random integer
     * @see #nextInt(int, int)
     */
    public static int nextInt() {
        return nextInt(0, Integer.MAX_VALUE);
    }

    /**
     * <p>
     * Returns a random long within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endExclusive   the upper bound (not included)
     * @return the random long
     * @throws IllegalArgumentException if {@code startInclusive > endExclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static long nextLong(final long startInclusive, final long endExclusive) {
        Vi4j.isTrue(endExclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        Vi4j.isTrue(startInclusive >= 0, "Both range values must be non-negative.");
        if (startInclusive == endExclusive) {
            return startInclusive;
        }
        return (long) nextDouble(startInclusive, endExclusive);
    }

    /**
     * <p>
     * Returns a random double within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the random double
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static double nextDouble(final double startInclusive, final double endInclusive) {
        Vi4j.isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        Vi4j.isTrue(startInclusive >= 0, "Both range values must be non-negative.");
        if (startInclusive == endInclusive) {
            return startInclusive;
        }
        return startInclusive + ((endInclusive - startInclusive) * new Random().nextDouble());
    }

    /**
     * <p> Returns a random double within 0 - Double.MAX_VALUE </p>
     *
     * @return the random double
     * @see #nextDouble(double, double)
     */
    public static double nextDouble() {
        return nextDouble(0, Double.MAX_VALUE);
    }

    /**
     * <p>
     * Returns a random float within the specified range.
     * </p>
     *
     * @param startInclusive the smallest value that can be returned, must be non-negative
     * @param endInclusive   the upper bound (included)
     * @return the random float
     * @throws IllegalArgumentException if {@code startInclusive > endInclusive} or if
     *                                  {@code startInclusive} is negative
     */
    public static float nextFloat(final float startInclusive, final float endInclusive) {
        Vi4j.isTrue(endInclusive >= startInclusive, "Start value must be smaller or equal to end value.");
        Vi4j.isTrue(startInclusive >= 0, "Both range values must be non-negative.");
        if (startInclusive == endInclusive) {
            return startInclusive;
        }
        return startInclusive + ((endInclusive - startInclusive) * new Random().nextFloat());
    }

    /**
     * <p> Returns a random float within 0 - Float.MAX_VALUE </p>
     *
     * @return the random float
     * @see #nextFloat()
     */
    public static float nextFloat() {
        return nextFloat(0, Float.MAX_VALUE);
    }

    /**
     * Generates a random 4-digit OTP.
     *
     * @return A random 4-digit OTP.
     */
    public static int nextOTP() {
        return (int) ((Math.random() * 9000) + 1000);
    }

    /**
     * Generates a random 4-digit OTP as a string.
     *
     * @return A string representing a random 4-digit OTP.
     */
    public static String nextOTPString() {
        return String.valueOf(nextOTP());
    }

    /**
     * Generates a UUID based on current time and random values.
     *
     * @return A UUID.
     */
    public static UUID nextUUID() {
        long most64SigBits = get64MostSignificantBit();
        long least64SigBits = get64LeastSignificantBit();
        return new UUID(most64SigBits, least64SigBits);
    }

    /**
     * Generates a string representation of a UUID based on current time and random values.
     *
     * @return A string representation of a UUID.
     */
    @SuppressWarnings({"DuplicatedCode", "AccessStaticViaInstance"})
    public static String nextUUIDString() {
        return nextUUID().randomUUID().toString().replaceAll("-", "");
    }

    /**
     * Generates a random password of length equal to {@code length},
     * consisting only of the characters contained in {@code combination}.
     *
     * <p> If {@code combination} contains more than one occurrence of a character,
     * the overall probability of using it in password generation will be higher.
     *
     * @param length - the desired password length.
     * @param chars  - the letter set used in the generation process.
     * @return the generated password.
     */
    public static String nextPassword(int length, char[] chars) {
        StringBuilder builder = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0, n = chars.length; i < length; i++) {
            builder.append(chars[random.nextInt(n)]);
        }

        return builder.toString();
    }

    /**
     * Generates a random password of length equal to {@code length},
     * consisting only of the characters contained in {@code combination}.
     *
     * <p> If {@code combination} contains more than one occurrence of a character,
     * the overall probability of using it in password generation will be higher.
     *
     * @param length      - the desired password length.
     * @param combination - the letter set used in the generation process.
     * @return the generated password.
     */
    public static String nextPassword(int length, String combination) {
        char[] chars = combination.toCharArray();
        return nextPassword(length, chars);
    }

    /**
     * Generates a random password of 8 characters
     *
     * @param length - the desired password length.
     * @return The randomized password as a String
     */
    public static String nextPassword(int length) {
        String[] values = {"a", "b", "c", "d", "e", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "u", "v", "z", "x", "y", "1", "2", "3", "4", "5", "6", "7", "8", "9", "&", "%", "?", "!", "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "Z", "X", "Y"};
        return nextPassword(length, Transform4j.fromArray2Chars(values));
    }

    private static long get64LeastSignificantBit() {
        Random random = new Random();
        long random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
        long variant3BitFlag = 0x8000000000000000L;
        return random63BitLong + variant3BitFlag;
    }

    private static long get64MostSignificantBit() {
        LocalDateTime start = LocalDateTime.of(1900, 10, 15, 0, 0, 0);
        Duration duration = Duration.between(start, LocalDateTime.now());
        long seconds = duration.getSeconds();
        long nanos = duration.getNano();
        long timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
        long least12SignificantBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
        long version = 1 << 12;
        return (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificantBitOfTime;
    }
}
