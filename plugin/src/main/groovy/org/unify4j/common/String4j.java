package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class String4j {

    protected static final Logger logger = LoggerFactory.getLogger(String4j.class);

    /**
     * Checks if the provided CharSequence is null or empty.
     *
     * @param cs the CharSequence to check
     * @return true if the CharSequence is null or empty, false otherwise
     */
    @SuppressWarnings({"SizeReplaceableByIsEmpty"})
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * Checks if the provided CharSequence is not null and not empty.
     *
     * @param cs the CharSequence to check
     * @return true if the CharSequence is not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }
}
