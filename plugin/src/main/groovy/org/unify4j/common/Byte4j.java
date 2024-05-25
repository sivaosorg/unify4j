package org.unify4j.common;

public class Byte4j {
    protected static final char[] hexes = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'}; // Array of hexadecimal characters used for encoding bytes

    /**
     * Decodes a hexadecimal string into a byte array.
     *
     * @param s the hexadecimal string to decode
     * @return the decoded byte array, or null if the input string length is not even
     */
    public static byte[] decode(final String s) {
        final int len = s.length();
        // Check if the length of the string is even
        if (len % 2 != 0) {
            return null;
        }
        byte[] bytes = new byte[len / 2];
        int pos = 0;
        // Loop through the string two characters at a time
        for (int i = 0; i < len; i += 2) {
            byte hi = (byte) Character.digit(s.charAt(i), 16);
            byte lo = (byte) Character.digit(s.charAt(i + 1), 16);
            // Combine the high and low nibbles into a single byte
            bytes[pos++] = (byte) (hi * 16 + lo);
        }
        return bytes;
    }

    /**
     * Converts a byte array into a string of hexadecimal digits.
     *
     * @param bytes the byte array to encode
     * @return a string containing the hexadecimal representation of the byte array
     */
    public static String encode(final byte[] bytes) {
        // Use StringBuilder to construct the hexadecimal string
        StringBuilder sb = new StringBuilder(bytes.length << 1);
        // Loop through each byte and convert it to two hexadecimal characters
        for (byte aByte : bytes) {
            sb.append(toDigit(aByte >> 4));
            sb.append(toDigit(aByte & 0x0f));
        }
        return sb.toString();
    }

    /**
     * Converts a value (0 ... 15) to the corresponding hexadecimal digit.
     *
     * @param value the value to convert
     * @return the corresponding hexadecimal character ('0'...'F')
     */
    private static char toDigit(final int value) {
        return hexes[value & 0x0f];
    }

    /**
     * Checks if a byte array is gzip compressed.
     *
     * @param bytes the byte array to check
     * @return true if the byte array is gzip compressed, false otherwise
     */
    public static boolean isGzipped(byte[] bytes) {
        // Gzip files start with the magic number 0x1f8b
        return bytes[0] == (byte) 0x1f && bytes[1] == (byte) 0x8b;
    }
}
