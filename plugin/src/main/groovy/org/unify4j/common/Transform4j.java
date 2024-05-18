package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

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

    /**
     * Converts a collection of objects to an array of strings.
     * <p>
     * This method converts the elements of the input collection to an array of strings.
     *
     * @param collection the collection of objects to be converted, may be null or empty
     * @return an array of strings containing the string representations of the elements in the collection,
     * or null if the input collection is null or empty
     * <p>
     * This method first checks if the input collection is not empty using the {@code isEmpty} method from CollectionUtils.
     * If the collection is empty, it returns null.
     * If the collection is not empty, it converts the collection to an array of strings using the toArray method.
     * The returned array contains the string representations of the elements in the collection.
     */
    public static String[] fromColl2StringArray(Collection<?> collection) {
        if (!Collection4j.isEmpty(collection)) {
            return collection.stream().map(Object::toString).toArray(String[]::new);
        }
        return null;
    }
}
