package org.unify4j.common;

public class Os4j {
    protected final static char SLASH_CHAR = '/';
    protected final static char BACK_SLASH_CHAR = '\\';

    /**
     * Normalizes the given path by replacing all backslashes with forward slashes.
     *
     * @param path The path to be normalized.
     * @return The normalized path with all backslashes replaced by forward slashes.
     */
    public static String normalised(String path) {
        return normalised(path, BACK_SLASH_CHAR, SLASH_CHAR);
    }

    /**
     * Normalizes the given path by replacing all occurrences of the specified separator with the new separator.
     *
     * @param path      The path to be normalized.
     * @param separator The character to be replaced.
     * @return The normalized path with the specified separator replaced.
     */
    public static String normalised(String path, char separator) {
        return normalised(path, separator, separator);
    }

    /**
     * Normalizes the given path by replacing all occurrences of the specified separator with the new separator
     * and collapsing any consecutive separators into a single separator.
     *
     * @param path         The path to be normalized.
     * @param separator    The character to be replaced.
     * @param newSeparator The character to replace with.
     * @return The normalized path with specified changes.
     */
    public static String normalised(String path, char separator, char newSeparator) {
        if (String4j.isEmpty(path)) {
            return path;
        }
        String filename = path.trim();
        if (separator != newSeparator) {
            filename = filename.replace(separator, newSeparator);
        }
        return filename.replace(String.format("\\%s{2,}", newSeparator), "\\" + newSeparator);
    }

    /**
     * Normalizes the given path by collapsing any unnecessary segments (such as "." and "..") and
     * removing redundant slashes.
     *
     * @param path The path to be normalized.
     * @return The normalized path.
     */
    public static String withNormalised(String path) {
        if (String4j.isEmpty(path)) {
            return path;
        }
        if (path.indexOf('.') == -1 && path.indexOf('/') == -1) {
            return path;
        }
        boolean isNormalized = true;
        int noSegments = 0;
        int lastChar = path.length() - 1;
        for (int src = lastChar; src >= 0; ) {
            int slash = path.lastIndexOf('/', src);
            if (slash != -1) {
                if (slash == src) {
                    if (src != lastChar) {
                        isNormalized = false;
                    }
                } else {
                    noSegments++;
                }
            } else {
                noSegments++;
            }
            src = slash - 1;
        }
        int[] segments = new int[noSegments];
        char[] chars = new char[path.length()];
        path.getChars(0, chars.length, chars, 0);
        noSegments = 0;
        for (int src = 0; src < chars.length; ) {
            while (src < chars.length && chars[src] == '/') {
                src++;
            }
            if (src < chars.length) {
                segments[noSegments++] = src;
                while (src < chars.length && chars[src] != '/') {
                    src++;
                }
            }
        }
        final int DELETED = -1;
        for (int segment = 0; segment < noSegments; segment++) {
            int src = segments[segment];
            if (chars[src++] == '.') {
                if (src == chars.length || chars[src] == '/') {
                    segments[segment] = DELETED;
                    isNormalized = false;
                } else {
                    if (chars[src++] == '.' && (src == chars.length || chars[src] == '/')) {
                        for (int toDelete = segment - 1; toDelete >= 0; toDelete--) {
                            if (segments[toDelete] != DELETED) {
                                if (chars[segments[toDelete]] != '.') {
                                    segments[toDelete] = DELETED;
                                    segments[segment] = DELETED;
                                    isNormalized = false;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (isNormalized) {
            return path;
        } else {
            int dst = (chars[0] == '/') ? 1 : 0;
            for (int segment = 0; segment < noSegments; segment++) {
                int segmentStart = segments[segment];
                if (segmentStart != DELETED) {
                    for (int src = segmentStart; src < chars.length; src++) {
                        char ch = chars[src];
                        chars[dst++] = ch;
                        if (ch == '/') {
                            break;
                        }
                    }
                }
            }
            return new String(chars, 0, dst);
        }
    }

    /**
     * Fetch value from environment variable and if not set, then fetch from
     * System properties.  If neither available, return null.
     *
     * @param var String key of variable to return
     */
    public static String getExternalVariable(String var) {
        String value = System.getProperty(var);
        if (String4j.isEmpty(value)) {
            value = System.getenv(var);
        }
        return String4j.isEmpty(value) ? null : value;
    }
}
