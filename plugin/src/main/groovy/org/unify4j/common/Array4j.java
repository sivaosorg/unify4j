package org.unify4j.common;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Array4j {
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
    public static final char[] EMPTY_CHAR_ARRAY = new char[0];
    public static final Character[] EMPTY_CHARACTER_ARRAY = new Character[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

    /**
     * Checks if the specified array is empty or null.
     * <p>
     * An array is considered empty if it is either null or has a length of zero.
     *
     * @param array The array to check for emptiness.
     * @return {@code true} if the array is empty or null, {@code false} otherwise.
     */
    public static boolean isEmpty(final Object array) {
        return array == null || Array.getLength(array) == 0;
    }

    /**
     * Retrieves the size of the specified array.
     * <p>
     * If the array is null, the size returned is 0.
     *
     * @param array The array to determine the size of.
     * @return The size of the array. Returns 0 if the array is null.
     */
    public static int size(final Object array) {
        return array == null ? 0 : Array.getLength(array);
    }

    /**
     * <p>Shallow copies an array of Objects
     * </p>
     * <p>The objects in the array are not cloned, thus there is no special
     * handling for multi-dimensional arrays.
     * </p>
     * <p>This method returns <code>null</code> if <code>null</code> array input.</p>
     *
     * @param array the array to shallow clone, may be <code>null</code>
     * @param <T>   the array type
     * @return the cloned array, <code>null</code> if <code>null</code> input
     */
    public static <T> T[] shallowCopy(final T[] array) {
        if (isEmpty(array)) {
            return null;
        }
        return array.clone();
    }

    /**
     * Adds all the elements of the given arrays into a new array.
     * <p>
     * The new array contains all of the elements of the first array followed
     * by all of the elements of the second array. When an array is returned,
     * it is always a new array.
     * </p>
     * <pre>
     * ArrayUtilities.addAll(null, null)     = null
     * ArrayUtilities.addAll(array1, null)   = cloned copy of array1
     * ArrayUtilities.addAll(null, array2)   = cloned copy of array2
     * ArrayUtilities.addAll([], [])         = []
     * ArrayUtilities.addAll([null], [null]) = [null, null]
     * ArrayUtilities.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
     * </pre>
     *
     * @param array1 the first array whose elements are added to the new array, may be <code>null</code>
     * @param array2 the second array whose elements are added to the new array, may be <code>null</code>
     * @param <T>    the array type
     * @return The new array, <code>null</code> if <code>null</code> array inputs.
     * The type of the new array is the type of the first array.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] addAll(final T[] array1, final T[] array2) {
        if (isEmpty(array1)) {
            return shallowCopy(array2);
        } else if (isEmpty(array2)) {
            return shallowCopy(array1);
        }
        // Create a new array with a length equal to the sum of the lengths of array1 and array2.
        final T[] newArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
        // Copy the elements of array1 and array2 into the new array.
        System.arraycopy(array1, 0, newArray, 0, array1.length);
        System.arraycopy(array2, 0, newArray, array1.length, array2.length);
        return newArray;
    }

    /**
     * Removes an item from the given array at the specified position.
     * <p>
     * This method creates a new array with one fewer element than the original array
     * by removing the element at the specified position. If the input array is empty
     * or null, it returns the input array as is.
     * </p>
     *
     * @param array    the array from which the item is to be removed, may be <code>null</code>
     * @param position the position of the item to remove
     * @param <T>      the type of the array elements
     * @return A new array with the specified item removed, or the original array if it's empty or null.
     * If the specified position is out of bounds, returns a shallow copy of the original array.
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] removeItem(T[] array, int position) {
        if (isEmpty(array)) {
            return array;
        }
        final int len = Array.getLength(array);
        // Create a new array with one fewer element.
        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), len - 1);
        // Copy elements before the specified position.
        System.arraycopy(array, 0, newArray, 0, position);
        // Copy elements after the specified position.
        System.arraycopy(array, position + 1, newArray, position, len - position - 1);
        return newArray;
    }

    /**
     * Returns a subset of the given array between the specified start (inclusive) and end (exclusive) indices.
     * <p>
     * This method creates a new array containing elements from the input array, starting from the
     * specified start index up to, but not including, the specified end index.
     * If the input array is empty or null, it returns the input array as is.
     * </p>
     *
     * @param array the array from which the subset is to be extracted, may be <code>null</code>
     * @param start the start index (inclusive) of the subset
     * @param end   the end index (exclusive) of the subset
     * @param <T>   the type of the array elements
     * @return A new array containing elements from the input array within the specified range.
     * Returns the original array if it's empty or null.
     * If the specified indices are out of bounds, returns a shallow copy of the original array.
     */
    public static <T> T[] getArraySubset(T[] array, int start, int end) {
        if (isEmpty(array)) {
            return array;
        }
        return Arrays.copyOfRange(array, start, end);
    }
}
