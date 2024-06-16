package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.c.Pair;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Collection4j {
    protected static final Logger logger = LoggerFactory.getLogger(Collection4j.class);
    private static final Set<?> unmodifiableEmptySet = Collections.unmodifiableSet(new HashSet<>());
    private static final List<?> unmodifiableEmptyList = Collections.unmodifiableList(new ArrayList<>());

    /**
     * Checks if the provided collection is null or empty.
     *
     * @param collection - the collection to check for emptiness
     * @return true if the collection is null or empty, false otherwise
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Checks if the provided collection is not null and not empty.
     *
     * @param collection - the collection to check for non-emptiness
     * @return true if the collection is not null and not empty, false otherwise
     */
    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    /**
     * Checks if the provided map is null or empty.
     *
     * @param map - the map to check for emptiness
     * @return true if the map is null or empty, false otherwise
     */
    public static boolean isEmptyMap(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    /**
     * Checks if the provided map is not null and not empty.
     *
     * @param map- the map to check for non-emptiness
     * @return true if the map is not null and not empty, false otherwise
     */
    public static boolean isNotEmptyMap(Map<?, ?> map) {
        return !isEmptyMap(map);
    }

    /**
     * Compares two objects for equality, handling null values and arrays.
     *
     * @param o1  - the first object to compare
     * @param o2- the second object to compare
     * @return true if the objects are equal, false otherwise
     */
    public static boolean nullSafeEquals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        if (o1 == null || o2 == null) {
            return false;
        }
        if (o1.equals(o2)) {
            return true;
        }
        if (o1.getClass().isArray() && o2.getClass().isArray()) {
            if (o1 instanceof Object[] && o2 instanceof Object[]) {
                return Arrays.equals((Object[]) o1, (Object[]) o2);
            }
            if (o1 instanceof boolean[] && o2 instanceof boolean[]) {
                return Arrays.equals((boolean[]) o1, (boolean[]) o2);
            }
            if (o1 instanceof byte[] && o2 instanceof byte[]) {
                return Arrays.equals((byte[]) o1, (byte[]) o2);
            }
            if (o1 instanceof char[] && o2 instanceof char[]) {
                return Arrays.equals((char[]) o1, (char[]) o2);
            }
            if (o1 instanceof double[] && o2 instanceof double[]) {
                return Arrays.equals((double[]) o1, (double[]) o2);
            }
            if (o1 instanceof float[] && o2 instanceof float[]) {
                return Arrays.equals((float[]) o1, (float[]) o2);
            }
            if (o1 instanceof int[] && o2 instanceof int[]) {
                return Arrays.equals((int[]) o1, (int[]) o2);
            }
            if (o1 instanceof long[] && o2 instanceof long[]) {
                return Arrays.equals((long[]) o1, (long[]) o2);
            }
            if (o1 instanceof short[] && o2 instanceof short[]) {
                return Arrays.equals((short[]) o1, (short[]) o2);
            }
        }
        return false;
    }

    /**
     * Checks if the provided enumeration contains the specified element.
     *
     * @param enumeration - the enumeration to check
     * @param element     - the element to search for
     * @return true if the enumeration contains the element, false otherwise
     */
    public static boolean contains(Enumeration<?> enumeration, Object element) {
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Object candidate = enumeration.nextElement();
                if (nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the provided iterator contains the specified element.
     *
     * @param iterator the iterator to check
     * @param element  the element to search for
     * @return true if the iterator contains the element, false otherwise
     */
    public static boolean contains(Iterator<?> iterator, Object element) {
        if (iterator != null) {
            while (iterator.hasNext()) {
                Object candidate = iterator.next();
                if (nullSafeEquals(candidate, element)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the provided source collection contains any of the elements from the candidates collection.
     *
     * @param source     the source collection to check
     * @param candidates the collection of candidates to search for
     * @return true if the source collection contains any of the candidates, false otherwise
     */
    public static boolean containsAny(Collection<?> source, Collection<?> candidates) {
        if (isEmpty(source) || isEmpty(candidates)) {
            return false;
        }
        for (Object candidate : candidates) {
            if (source.contains(candidate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the last element of the provided list.
     *
     * @param list the list from which to retrieve the last element
     * @param <T>  the type of elements in the list
     * @return the last element of the list, or null if the list is empty
     */
    public static <T> T last(List<T> list) {
        if (isEmpty(list)) {
            return null;
        }
        return list.get(list.size() - 1);
    }

    /**
     * Computes the difference between two collections.
     *
     * @param list1 the first collection
     * @param list2 the second collection
     * @param <T>   the type of elements in the collections
     * @return a collection containing the elements that are present in one collection but not the other
     */
    public static <T> Collection<T> difference(Collection<T> list1, Collection<T> list2) {
        if (isEmpty(list1) && isEmpty(list2)) {
            return Collections.emptyList();
        }
        if (isEmpty(list1)) {
            return list2;
        }
        if (isEmpty(list2)) {
            return list1;
        }
        if (list1.size() >= list2.size()) {
            return list1.stream().filter(element -> !list2.contains(element)).collect(Collectors.toList());
        } else {
            return list2.stream().filter(element -> !list1.contains(element)).collect(Collectors.toList());
        }
    }

    public static <T> List<T> difference(List<T> list1, List<T> list2) {
        if (isEmpty(list1) && isEmpty(list2)) {
            return Collections.emptyList();
        }
        if (isEmpty(list1)) {
            return list2;
        }
        if (isEmpty(list2)) {
            return list1;
        }
        if (list1.size() >= list2.size()) {
            return list1.stream().filter(element -> !list2.contains(element)).collect(Collectors.toList());
        } else {
            return list2.stream().filter(element -> !list1.contains(element)).collect(Collectors.toList());
        }
    }

    /**
     * Computes the intersection of two lists.
     *
     * @param list1 the first list
     * @param list2 the second list
     * @param <T>   the type of elements in the lists
     * @return a list containing the elements that are common to both input lists
     */
    public static <T> List<T> intersect(List<T> list1, List<T> list2) {
        if (isEmpty(list1) || isEmpty(list2)) {
            return Collections.emptyList();
        }
        return list1.stream().filter(list2::contains).collect(Collectors.toList());
    }

    /**
     * Computes the intersection of multiple collections.
     *
     * @param newCollection the collection to store the intersection
     * @param collections   the collections to intersect
     * @param <T>           the type of elements in the collections
     * @param <C>           the type of the new collection
     * @return a collection containing the elements that are common to all input collections
     */
    @SafeVarargs
    public static <T, C extends Collection<T>> C intersect(C newCollection, Collection<T>... collections) {
        boolean first = true;
        for (Collection<T> collection : collections) {
            if (first) {
                newCollection.addAll(collection);
                first = false;
            } else {
                newCollection.retainAll(collection);
            }
        }
        return newCollection;
    }

    /**
     * Find a single value of the given type in the given Collection.
     *
     * @param collection the Collection to search
     * @param type       the type to look for
     * @return a value of the given type found if there is a clear match, or
     * {@code null} if none or more than one such value found
     */
    @SuppressWarnings("unchecked")
    public static <T> T findValueOfType(Collection<?> collection, Class<T> type) {
        if (isEmpty(collection)) {
            return null;
        }
        T value = null;
        for (Object element : collection) {
            if (type == null || type.isInstance(element)) {
                if (value != null) {
                    return null;
                }
                value = (T) element;
            }
        }
        return value;
    }

    /**
     * Find a single value of one of the given types in the given Collection:
     * searching the Collection for a value of the first type, then searching
     * for a value of the second type, etc.
     *
     * @param collection the collection to search
     * @param types      the types to look for, in prioritized order
     * @return a value of one of the given types found if there is a clear
     * match, or {@code null} if none or more than one such value found
     */
    public static Object findValueOfType(Collection<?> collection, Class<?>[] types) {
        if (isEmpty(collection)) {
            return null;
        }
        for (Class<?> type : types) {
            Object value = findValueOfType(collection, type);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    /**
     * Find the common element type of the given Collection, if any.
     *
     * @param collection the Collection to check
     * @return the common element type, or {@code null} if no clear common type
     * has been found (or the collection was empty)
     */
    public static Class<?> findCommonElementType(Collection<?> collection) {
        if (isEmpty(collection)) {
            return null;
        }
        Class<?> candidate = null;
        for (Object val : collection) {
            if (val != null) {
                if (candidate == null) {
                    candidate = val.getClass();
                } else if (candidate != val.getClass()) {
                    return null;
                }
            }
        }
        return candidate;
    }

    /**
     * Retrieve the last element of the given Set, using
     * {@link SortedSet#last()} or otherwise iterating over all elements
     * (assuming a linked set).
     *
     * @param set the Set to check (maybe {@code null} or empty)
     * @return the last element, or {@code null} if none
     * @see SortedSet
     * @see LinkedHashMap#keySet()
     * @see LinkedHashSet
     * @since 5.0.3
     */
    public static <T> T last(Set<T> set) {
        if (isEmpty(set)) {
            return null;
        }
        if (set instanceof SortedSet) {
            return ((SortedSet<T>) set).last();
        }
        Iterator<T> it = set.iterator();
        T last = null;
        while (it.hasNext()) {
            last = it.next();
        }
        return last;
    }

    /**
     * Removes duplicated elements from the provided list.
     * Null elements are treated as unique and kept in the result list.
     *
     * @param list the list from which to remove duplicated elements
     * @param <T>  the type of elements in the list
     * @return a new list containing unique elements from the original list
     */
    public static <T> List<T> removeDuplicates(List<T> list) {
        if (isEmpty(list)) {
            return new ArrayList<>();
        }
        Set<T> uniqueElements = new LinkedHashSet<>(list);
        return new ArrayList<>(uniqueElements);
    }

    /**
     * Converts a collection to a set while preserving the order of elements.
     *
     * @param collection the collection to convert
     * @param <T>        the type of elements in the collection
     * @return a set containing the elements of the collection
     */
    public static <T> Set<T> asSet(Collection<T> collection) {
        return new LinkedHashSet<>(collection);
    }

    /**
     * Converts a variable number of elements to a set.
     *
     * @param elements the elements to convert to a set
     * @param <E>      the type of elements in the set
     * @return a set containing the specified elements
     */
    @SafeVarargs
    public static <E> Set<E> asSet(E... elements) {
        if (!Object4j.isEmpty(elements)) {
            return new HashSet<>(Arrays.asList(elements));
        }
        return Collections.emptySet();
    }

    /**
     * Filters the elements of the provided list based on the given predicate.
     *
     * @param list      the list to filter
     * @param quantity  the maximum number of elements to include in the filtered list, use 0 or negative number to include all elements
     * @param predicate the predicate to apply to each element of the list
     * @param <T>       the type of elements in the list
     * @return a new list containing elements that satisfy the predicate
     */
    public static <T> List<T> filterIfPresent(List<T> list, int quantity, Predicate<T> predicate) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        Stream<T> filter = list.stream().filter(predicate);
        if (quantity > 0) {
            filter = filter.limit(quantity);
        }
        return filter.collect(Collectors.toList());
    }

    /**
     * Filters the elements of the provided list up to the specified quantity.
     *
     * @param list     the list to filter
     * @param quantity the maximum number of elements to include in the filtered list, use 0 or negative number to include all elements
     * @param <T>      the type of elements in the list
     * @return a new list containing elements up to the specified quantity
     */
    public static <T> List<T> filterIfPresent(List<T> list, int quantity) {
        if (isEmpty(list)) {
            return Collections.emptyList();
        }
        if (quantity > 0) {
            list = list.stream().limit(quantity).collect(Collectors.toList());
        }
        return list;
    }

    /**
     * Removes elements from the provided list that satisfy the given predicate up to the specified quantity.
     *
     * @param list      the list from which to remove elements
     * @param quantity  the maximum number of elements to remove from the list, use 0 or negative number to remove all elements that satisfy the predicate
     * @param predicate the predicate to apply to each element of the list
     * @param <T>       the type of elements in the list
     * @return a new list containing elements remaining after removal
     * @apiNote This operation changes the size of the original list. It removes all elements that satisfy the predicate up to the specified quantity and returns the elements remaining in the list.
     */
    public static <T> List<T> removeIfPresent(List<T> list, int quantity, Predicate<T> predicate) {
        List<T> present = filterIfPresent(list, quantity, predicate);
        if (isEmpty(present)) {
            return Collections.emptyList();
        }
        unsupportedOperationIf(list);
        list.removeAll(present);
        return list;
    }

    /**
     * Retrieves and removes elements from the provided list that satisfy the given predicate up to the specified quantity.
     *
     * @param list      the list from which to retrieve and remove elements
     * @param quantity  the maximum number of elements to retrieve and remove from the list, use 0 or negative number to retrieve and remove all elements that satisfy the predicate
     * @param predicate the predicate to apply to each element of the list
     * @param <T>       the type of elements in the list
     * @return a new list containing elements retrieved and removed from the original list
     * @apiNote This operation changes the size of the original list. It retrieves and removes all elements that satisfy the predicate up to the specified quantity and returns them.
     */
    public static <T> List<T> peekIfPresent(List<T> list, int quantity, Predicate<T> predicate) {
        List<T> present = filterIfPresent(list, quantity, predicate);
        if (isEmpty(present)) {
            return Collections.emptyList();
        }
        unsupportedOperationIf(list);
        list.removeAll(present);
        return present;
    }

    /**
     * Retrieves and removes elements from the provided list up to the specified quantity.
     *
     * @param list     the list from which to retrieve and remove elements
     * @param quantity the maximum number of elements to retrieve and remove from the list, use 0 or negative number to retrieve and remove all elements
     * @param <T>      the type of elements in the list
     * @return a new list containing elements retrieved and removed from the original list
     * @apiNote This operation changes the size of the original list. It retrieves and removes all elements up to the specified quantity and returns them.
     */
    public static <T> List<T> peekIfPresent(List<T> list, int quantity) {
        List<T> elementsToRetrieve = filterIfPresent(list, quantity);
        if (isEmpty(elementsToRetrieve)) {
            return Collections.emptyList();
        }
        unsupportedOperationIf(list);
        list.removeAll(elementsToRetrieve);
        return elementsToRetrieve;
    }

    /**
     * Retrieves the element at the specified index from the provided list if present.
     *
     * @param list  the list from which to retrieve the element
     * @param index the index of the element to retrieve
     * @param <T>   the type of elements in the list
     * @return the element at the specified index if present, otherwise null
     */
    public static <T> T popIfPresent(List<T> list, int index) {
        if (isEmpty(list)) {
            return null;
        }
        if (index >= list.size() || index < 0) {
            return null;
        }
        return list.get(index);
    }

    /**
     * Converts the elements of the specified collection to an array. If the collection size exceeds the length of the provided array,
     * a new array of the appropriate type and size is created.
     *
     * @param c   the collection whose elements are to be converted to an array
     * @param a   the array into which the elements of the collection are to be stored, if it is large enough; otherwise, a new array of the same runtime type is allocated for this purpose
     * @param <T> the type of elements in the collection
     * @return an array containing the elements of the collection
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> c, T[] a) {
        return c.size() > a.length ? c.toArray((T[]) Array.newInstance(a.getClass().getComponentType(), c.size())) : c.toArray(a);
    }

    /**
     * Converts the elements of the specified collection to an array of the given class type.
     *
     * @param c     the collection whose elements are to be converted to an array
     * @param klass the class type of the array elements
     * @param <T>   the type of elements in the collection
     * @return an array containing the elements of the collection
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> c, Class<?> klass) {
        return toArray(c, (T[]) Array.newInstance(klass, c.size()));
    }

    /**
     * Converts the elements of the specified collection to an array of the same class type as the collection's elements.
     *
     * @param c   the collection whose elements are to be converted to an array
     * @param <T> the type of elements in the collection
     * @return an array containing the elements of the collection
     */
    public static <T> T[] toArray(Collection<T> c) {
        return toArray(c, c.iterator().next().getClass());
    }

    /**
     * Merges two arrays into a single array if needed.
     *
     * @param left  the first array
     * @param right the second array
     * @param <T>   the type of elements in the arrays
     * @return a merged array containing elements from both input arrays
     * @throws IllegalArgumentException if either input array is null
     */
    @SafeVarargs
    public static <T> T[] mergeIfNeeded(T[] left, T... right) {
        if (left == null || right == null) {
            throw new IllegalArgumentException("Arguments must not be null.");
        }
        int size = left.length + right.length;
        T[] array = Arrays.copyOf(left, size);
        if (size - left.length >= 0) {
            System.arraycopy(right, 0, array, left.length, size - left.length);
        }
        return array;
    }

    /**
     * Checks if the array contains an element at the specified index.
     *
     * @param array the array to check
     * @param index the index to check for element existence
     * @param <E>   the type of elements in the array
     * @return true if the array contains an element at the specified index, false otherwise
     */
    public static <E> boolean isConsistOf(E[] array, int index) {
        return !Object4j.isEmpty(array) && index >= 0 && index < array.length;
    }

    /**
     * Creates an immutable list containing the specified elements.
     * This method is preferable when creating immutable lists in Java versions
     * where Set.of() is not available (e.g., JDK versions before 11).
     * It returns an empty immutable list if no elements are provided or if the input array is null.
     *
     * @param items The elements to be included in the immutable list.
     * @param <T>   The type of elements in the list.
     * @return An immutable list containing the specified elements.
     * Returns an empty immutable list if no elements are provided or if the input array is null.
     */
    @SuppressWarnings({"unchecked"})
    @SafeVarargs
    public static <T> List<T> listOf(T... items) {
        if (items == null || items.length == 0) {
            return (List<T>) unmodifiableEmptyList;
        }
        List<T> list = new ArrayList<>();
        Collections.addAll(list, items);
        return Collections.unmodifiableList(list);
    }

    /**
     * Creates an immutable set containing the specified elements.
     * This method is preferable when creating immutable sets in Java versions
     * where Set.of() is not available (e.g., JDK versions before 11).
     * It returns an empty immutable set if no elements are provided or if the input array is null.
     * <p>
     * Note: For JDK 11 and above, consider using Set.of() instead.
     *
     * @param items The elements to be included in the immutable set.
     * @param <T>   The type of elements in the set.
     * @return An immutable set containing the specified elements.
     * Returns an empty immutable set if no elements are provided or if the input array is null.
     */
    @SuppressWarnings({"unchecked"})
    @SafeVarargs
    public static <T> Set<T> setOf(T... items) {
        if (items == null || items.length == 0) {
            return (Set<T>) unmodifiableEmptySet;
        }
        Set<T> set = new LinkedHashSet<>();
        Collections.addAll(set, items);
        return Collections.unmodifiableSet(set);
    }

    /**
     * Converts a collection of items to a string with a specified delimiter between elements.
     *
     * @param collections the collection of items to be converted to a string
     * @param delimiter   the delimiter to separate the items in the resulting string
     * @return a string representation of the collection with the specified delimiter
     */
    public static String toString(Collection<?> collections, String delimiter) {
        if (isEmpty(collections)) {
            return "";
        }
        // Use a default delimiter of "," if the provided delimiter is empty or null
        delimiter = String4j.isEmpty(delimiter) ? "," : delimiter;
        StringBuilder builder = new StringBuilder();
        for (Object item : collections) {
            builder.append(delimiter);
            builder.append(item.toString());
        }
        return builder.substring(delimiter.length());
    }

    /**
     * Creates an unmodifiable map from an array of Pair objects.
     * <p>
     * This method takes a variable number of Pair objects and constructs
     * a thread-safe map (using ConcurrentHashMap) from these pairs. The
     * resulting map is then wrapped with Collections.unmodifiableMap to
     * ensure it cannot be modified after creation.
     *
     * @param <KeyT>   the type of keys maintained by the returned map
     * @param <ValueT> the type of mapped values
     * @param pairs    an array of Pair objects from which the map will be created
     * @return an unmodifiable map containing the key-value pairs from the pairs array
     * @throws NullPointerException if any of the keys or values in the pairs are null
     */
    @SafeVarargs
    public static <KeyT, ValueT> Map<KeyT, ValueT> mapOf(Pair<KeyT, ValueT>... pairs) {
        Map<KeyT, ValueT> map = new ConcurrentHashMap<>(pairs.length);
        for (Pair<KeyT, ValueT> pair : pairs) {
            map.put(pair.getKey(), pair.getValue());
        }
        return Collections.unmodifiableMap(map);
    }

    /**
     * Throws UnsupportedOperationException if the list is not of type ArrayList.
     *
     * @param list the list to check
     * @param <T>  the type of elements in the list
     */
    private static <T> void unsupportedOperationIf(List<T> list) {
        if (!(list instanceof ArrayList)) {
            throw new UnsupportedOperationException("Unsupported operation for lists initialized with Arrays.asList(). Instead, use new ArrayList<T>()");
        }
    }
}
