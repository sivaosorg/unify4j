package org.unify4j.model.base;

import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiFunction;

/**
 * AbstractConcurrentNullSafeMap is an abstract class that provides a thread-safe implementation
 * of ConcurrentMap and Map interfaces, allowing null keys and null values by using sentinel objects internally.
 *
 * @param <K> The type of keys maintained by this map
 * @param <V> The type of mapped values
 */
public abstract class AbstractConcurrentMapNullSafe<K, V> implements ConcurrentMap<K, V> {
    protected enum NullSentinel {
        NULL_KEY, NULL_VALUE
    }

    // Internal ConcurrentMap storing Objects
    protected final ConcurrentMap<Object, Object> internalMap;

    /**
     * Constructs a new AbstractConcurrentNullSafeMap with the provided internal map.
     *
     * @param internalMap the internal ConcurrentMap to use
     */
    protected AbstractConcurrentMapNullSafe(ConcurrentMap<Object, Object> internalMap) {
        this.internalMap = internalMap;
    }

    protected Object maskNullKey(K key) {
        return key == null ? NullSentinel.NULL_KEY : key;
    }

    @SuppressWarnings("unchecked")
    protected K unmaskNullKey(Object key) {
        return key == NullSentinel.NULL_KEY ? null : (K) key;
    }

    protected Object maskNullValue(V value) {
        return value == null ? NullSentinel.NULL_VALUE : value;
    }

    @SuppressWarnings("unchecked")
    protected V unmaskNullValue(Object value) {
        return value == NullSentinel.NULL_VALUE ? null : (V) value;
    }

    @Override
    public int size() {
        return internalMap.size();
    }

    @Override
    public boolean isEmpty() {
        return internalMap.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean containsKey(Object key) {
        return internalMap.containsKey(maskNullKey((K) key));
    }

    @Override
    public boolean containsValue(Object value) {
        return value == null ? internalMap.containsValue(NullSentinel.NULL_VALUE) : internalMap.containsValue(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        Object val = internalMap.get(maskNullKey((K) key));
        return unmaskNullValue(val);
    }

    @Override
    public V put(K key, V value) {
        Object prev = internalMap.put(maskNullKey(key), maskNullValue(value));
        return unmaskNullValue(prev);
    }

    @SuppressWarnings("unchecked")
    @Override
    public V remove(Object key) {
        Object prev = internalMap.remove(maskNullKey((K) key));
        return unmaskNullValue(prev);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
            internalMap.put(maskNullKey(entry.getKey()), maskNullValue(entry.getValue()));
        }
    }

    @Override
    public void clear() {
        internalMap.clear();
    }

    @SuppressWarnings("unchecked")
    @Override
    public V getOrDefault(Object key, V defaultValue) {
        Object val = internalMap.get(maskNullKey((K) key));
        return (val != null) ? unmaskNullValue(val) : defaultValue;
    }

    @Override
    public V putIfAbsent(K key, V value) {
        Object prev = internalMap.putIfAbsent(maskNullKey(key), maskNullValue(value));
        return unmaskNullValue(prev);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean remove(Object key, Object value) {
        return internalMap.remove(maskNullKey((K) key), maskNullValue((V) value));
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return internalMap.replace(maskNullKey(key), maskNullValue(oldValue), maskNullValue(newValue));
    }

    @Override
    public V replace(K key, V value) {
        Object prev = internalMap.replace(maskNullKey(key), maskNullValue(value));
        return unmaskNullValue(prev);
    }

    @Override
    public V computeIfAbsent(K key, java.util.function.Function<? super K, ? extends V> mappingFunction) {
        Object maskedKey = maskNullKey(key);
        Object currentValue = internalMap.get(maskNullKey(key));
        if (currentValue != null && currentValue != NullSentinel.NULL_VALUE) {
            // The key exists with a non-null value, so we don't compute
            return unmaskNullValue(currentValue);
        }
        // The key doesn't exist or is mapped to null, so we should compute
        V newValue = mappingFunction.apply(unmaskNullKey(maskedKey));
        if (newValue != null) {
            Object result = internalMap.compute(maskedKey, (k, v) -> {
                if (v != null && v != NullSentinel.NULL_VALUE) {
                    return v;  // Another thread set a non-null value, so we keep it
                }
                return maskNullValue(newValue);
            });
            return unmaskNullValue(result);
        } else {
            // If the new computed value is null, ensure no mapping exists
            internalMap.remove(maskedKey);
            return null;
        }
    }

    @Override
    public V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        Object maskedKey = maskNullKey(key);
        Object result = internalMap.compute(maskedKey, (k, v) -> {
            V oldValue = unmaskNullValue(v);
            V newValue = remappingFunction.apply(unmaskNullKey(k), oldValue);
            return (newValue == null) ? null : maskNullValue(newValue);
        });

        return unmaskNullValue(result);
    }

    @Override
    public V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        Objects.requireNonNull(remappingFunction);
        Objects.requireNonNull(value); // Adjust based on whether you want to allow nulls
        Object maskedKey = maskNullKey(key);
        Object result = internalMap.merge(maskedKey, maskNullValue(value), (v1, v2) -> {
            V unmaskV1 = unmaskNullValue(v1);
            V unmaskV2 = unmaskNullValue(v2);
            V newValue = remappingFunction.apply(unmaskV1, unmaskV2);
            return (newValue == null) ? null : maskNullValue(newValue);
        });

        return unmaskNullValue(result);
    }

    @SuppressWarnings({"NullableProblems"})
    @Override
    public Collection<V> values() {
        Collection<Object> internalValues = internalMap.values();
        return new AbstractCollection<V>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public Iterator<V> iterator() {
                Iterator<Object> it = internalValues.iterator();
                return new Iterator<V>() {
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public V next() {
                        return unmaskNullValue(it.next());
                    }

                    @Override
                    public void remove() {
                        it.remove();
                    }
                };
            }

            @Override
            public int size() {
                return internalValues.size();
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean contains(Object o) {
                return internalMap.containsValue(maskNullValue((V) o));
            }

            @Override
            public void clear() {
                internalMap.clear();
            }
        };
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Set<K> keySet() {
        Set<Object> internalKeys = internalMap.keySet();
        return new AbstractSet<K>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public Iterator<K> iterator() {
                Iterator<Object> it = internalKeys.iterator();
                return new Iterator<K>() {
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public K next() {
                        return unmaskNullKey(it.next());
                    }

                    @Override
                    public void remove() {
                        it.remove();
                    }
                };
            }

            @Override
            public int size() {
                return internalKeys.size();
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean contains(Object o) {
                return internalMap.containsKey(maskNullKey((K) o));
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean remove(Object o) {
                return internalMap.remove(maskNullKey((K) o)) != null;
            }

            @Override
            public void clear() {
                internalMap.clear();
            }
        };
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<Object, Object>> internalEntries = internalMap.entrySet();
        return new AbstractSet<Entry<K, V>>() {
            @SuppressWarnings("NullableProblems")
            @Override
            public Iterator<Entry<K, V>> iterator() {
                Iterator<Entry<Object, Object>> it = internalEntries.iterator();
                return new Iterator<Entry<K, V>>() {
                    @Override
                    public boolean hasNext() {
                        return it.hasNext();
                    }

                    @Override
                    public Entry<K, V> next() {
                        Entry<Object, Object> internalEntry = it.next();
                        return new Entry<K, V>() {
                            @Override
                            public K getKey() {
                                return unmaskNullKey(internalEntry.getKey());
                            }

                            @Override
                            public V getValue() {
                                return unmaskNullValue(internalEntry.getValue());
                            }

                            @Override
                            public V setValue(V value) {
                                Object oldValue = internalEntry.setValue(maskNullValue(value));
                                return unmaskNullValue(oldValue);
                            }

                            @Override
                            public boolean equals(Object o) {
                                if (!(o instanceof Entry)) return false;
                                Entry<?, ?> e = (Entry<?, ?>) o;
                                return Objects.equals(getKey(), e.getKey()) &&
                                        Objects.equals(getValue(), e.getValue());
                            }

                            @Override
                            public int hashCode() {
                                return Objects.hashCode(getKey()) ^ Objects.hashCode(getValue());
                            }

                            @Override
                            public String toString() {
                                return getKey() + "=" + getValue();
                            }
                        };
                    }

                    @Override
                    public void remove() {
                        it.remove();
                    }
                };
            }

            @Override
            public int size() {
                return internalEntries.size();
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean contains(Object o) {
                if (!(o instanceof Entry)) return false;
                Entry<?, ?> e = (Entry<?, ?>) o;
                Object val = internalMap.get(maskNullKey((K) e.getKey()));
                return maskNullValue((V) e.getValue()).equals(val);
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean remove(Object o) {
                if (!(o instanceof Entry)) return false;
                Entry<?, ?> e = (Entry<?, ?>) o;
                return internalMap.remove(maskNullKey((K) e.getKey()), maskNullValue((V) e.getValue()));
            }

            @Override
            public void clear() {
                internalMap.clear();
            }
        };
    }

    /**
     * Overrides the equals method to ensure proper comparison between two maps.
     * Two maps are considered equal if they contain the same key-value mappings.
     *
     * @param o the object to be compared for equality with this map
     * @return true if the specified object is equal to this map
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Map)) return false;
        Map<?, ?> other = (Map<?, ?>) o;
        if (this.size() != other.size()) return false;
        for (Entry<K, V> entry : this.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            if (!other.containsKey(key)) return false;
            Object otherValue = other.get(key);
            if (!Objects.equals(value, otherValue)) return false;
        }
        return true;
    }

    /**
     * Overrides the hashCode method to ensure consistency with equals.
     * The hash code of a map is defined to be the sum of the hash codes of each entry in the map.
     *
     * @return the hash code value for this map
     */
    @Override
    public int hashCode() {
        int h = 0;
        for (Entry<K, V> entry : this.entrySet()) {
            K key = entry.getKey();
            V value = entry.getValue();
            int keyHash = (key == null) ? 0 : key.hashCode();
            int valueHash = (value == null) ? 0 : value.hashCode();
            h += keyHash ^ valueHash;
        }
        return h;
    }

    /**
     * Overrides the toString method to provide a string representation of the map.
     * The string representation consists of a list of key-value mappings in the order returned by the map's entrySet view's iterator,
     * enclosed in braces ("{}"). Adjacent mappings are separated by the characters ", " (comma and space).
     *
     * @return a string representation of this map
     */
    @Override
    public String toString() {
        Iterator<Entry<K, V>> it = this.entrySet().iterator();
        if (!it.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (; ; ) {
            Entry<K, V> e = it.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (!it.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
}