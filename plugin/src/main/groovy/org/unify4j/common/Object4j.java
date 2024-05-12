package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Objects;

public class Object4j {
    protected static final Logger logger = LoggerFactory.getLogger(Object4j.class);

    /**
     * Checks if all the provided values are not null.
     *
     * @param values the values to check for null
     * @return true if all values are not null, false otherwise
     */
    public static boolean allNotNull(Object... values) {
        if (values == null) {
            return false;
        }
        return Arrays.stream(values).allMatch(Objects::nonNull);
    }

    /**
     * Checks if the provided array is null or empty.
     *
     * @param array the array to check
     * @param <E>   the type of elements in the array
     * @return true if the array is null or empty, false otherwise
     */
    @SuppressWarnings({"BooleanMethodIsAlwaysInverted"})
    public static <E> boolean isEmpty(E[] array) {
        return (array == null || array.length == 0);
    }

}
