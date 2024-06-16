package org.unify4j.model.c;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@SuppressWarnings({"ClassCanBeRecord"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pair<K, V> implements Serializable {
    public Pair(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }

    private final K key;
    private final V value;

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public static <K, V> Pair<K, V> of(K key, V value) {
        return new Pair<>(key, value);
    }
}