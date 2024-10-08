package org.unify4j.model.base;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMapNullSafe is a thread-safe implementation of ConcurrentMap
 * that allows null keys and null values by using sentinel objects internally.
 * <br>
 *
 * @param <K> The type of keys maintained by this map
 * @param <V> The type of mapped values
 */
public class ConcurrentHashMapNullSafe<K, V> extends AbstractConcurrentMapNullSafe<K, V> {
    /**
     * Constructs a new, empty ConcurrentHashMapNullSafe with default initial capacity (16) and load factor (0.75).
     */
    public ConcurrentHashMapNullSafe() {
        super(new ConcurrentHashMap<>());
    }

    /**
     * Constructs a new, empty ConcurrentHashMapNullSafe with the specified initial capacity and default load factor (0.75).
     *
     * @param initialCapacity the initial capacity. The implementation performs internal sizing
     *                        to accommodate this many elements.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public ConcurrentHashMapNullSafe(int initialCapacity) {
        super(new ConcurrentHashMap<>(initialCapacity));
    }

    /**
     * Constructs a new, empty ConcurrentHashMapNullSafe with the specified initial capacity and load factor.
     *
     * @param initialCapacity the initial capacity. The implementation
     *                        performs internal sizing to accommodate this many elements.
     * @param loadFactor      the load factor threshold, used to control resizing.
     *                        Resizing may be performed when the average number of elements per
     *                        bin exceeds this threshold.
     * @throws IllegalArgumentException if the initial capacity is negative or the load factor is non-positive
     */
    public ConcurrentHashMapNullSafe(int initialCapacity, float loadFactor) {
        super(new ConcurrentHashMap<>(initialCapacity, loadFactor));
    }

    /**
     * Constructs a new ConcurrentHashMapNullSafe with the same mappings as the specified map.
     *
     * @param m the map whose mappings are to be placed in this map
     * @throws NullPointerException if the specified map is null
     */
    public ConcurrentHashMapNullSafe(Map<? extends K, ? extends V> m) {
        super(new ConcurrentHashMap<>());
        putAll(m);
    }
}