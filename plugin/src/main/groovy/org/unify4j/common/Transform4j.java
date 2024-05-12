package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Transform4j {
    protected static final Logger logger = LoggerFactory.getLogger(Transform4j.class);

    /**
     * Converts an array of strings to a single character array.
     *
     * @param arrays The array of strings to be converted.
     * @return A character array containing the characters from the input strings.
     */
    public static char[] fromArray2Chars(String[] arrays) {
        if (arrays == null || arrays.length == 0) {
            return new char[0];
        }
        StringBuilder builder = new StringBuilder();
        for (String s : arrays) {
            if (s != null) {
                builder.append(s);
            }
        }
        return builder.toString().toCharArray();
    }
}
