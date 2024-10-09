package org.unify4j;

import org.junit.After;
import org.junit.Test;
import org.unify4j.common.SoftCache4j;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class SoftCache4jTest {
    private SoftCache4j<Integer, String> ttlCache;

    @After
    public void tearDown() {
        SoftCache4j.shutdown();
    }

    @Test
    public void testPutAndGet() {
        ttlCache = new SoftCache4j<>(10000, -1); // TTL of 10 seconds, no LRU
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.put(3, "C");

        assertEquals("A", ttlCache.get(1));
        assertEquals("B", ttlCache.get(2));
        assertEquals("C", ttlCache.get(3));
    }

    @Test
    public void testEntryExpiration() throws InterruptedException {
        ttlCache = new SoftCache4j<>(200, -1, 100); // TTL of 1 second, no LRU
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.put(3, "C");

        // Entries should be present initially
        assertEquals(3, ttlCache.size());
        assertTrue(ttlCache.containsKey(1));
        assertTrue(ttlCache.containsKey(2));
        assertTrue(ttlCache.containsKey(3));

        // Wait for TTL to expire
        Thread.sleep(350);

        // Entries should have expired
        assertEquals(0, ttlCache.size());
        assertFalse(ttlCache.containsKey(1));
        assertFalse(ttlCache.containsKey(2));
        assertFalse(ttlCache.containsKey(3));
    }

    @Test
    public void testLRUEviction() {
        ttlCache = new SoftCache4j<>(10000, 3); // TTL of 10 seconds, max size of 3
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.put(3, "C");
        ttlCache.get(1); // Access key 1 to make it recently used
        ttlCache.put(4, "D"); // This should evict key 2 (least recently used)

        assertNull(" Entry for key 2 should be evicted", ttlCache.get(2));
        assertEquals("Entry for key 1 should still be present", "A", ttlCache.get(1));
        assertEquals("Entry for key 4 should be present", "D", ttlCache.get(4));
    }

    @Test
    public void testSize() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");

        assertEquals(2, ttlCache.size());
    }

    @Test
    public void testIsEmpty() {
        ttlCache = new SoftCache4j<>(10000, -1);
        assertTrue(ttlCache.isEmpty());

        ttlCache.put(1, "A");

        assertFalse(ttlCache.isEmpty());
    }

    @Test
    public void testRemove() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.remove(1);

        assertNull(ttlCache.get(1));
    }

    @Test
    public void testContainsKey() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");

        assertTrue(ttlCache.containsKey(1));
        assertFalse(ttlCache.containsKey(2));
    }

    @Test
    public void testContainsValue() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");

        assertTrue(ttlCache.containsValue("A"));
        assertFalse(ttlCache.containsValue("B"));
    }

    @Test
    public void testKeySet() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");

        Set<Integer> keys = ttlCache.keySet();
        assertTrue(keys.contains(1));
        assertTrue(keys.contains(2));
    }

    @Test
    public void testValues() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");

        Collection<String> values = ttlCache.values();
        assertTrue(values.contains("A"));
        assertTrue(values.contains("B"));
    }

    @Test
    public void testClear() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.clear();

        assertTrue(ttlCache.isEmpty());
    }

    @Test
    public void testPutAll() {
        ttlCache = new SoftCache4j<>(10000, -1);
        Map<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        ttlCache.putAll(map);

        assertEquals("A", ttlCache.get(1));
        assertEquals("B", ttlCache.get(2));
    }

    @Test
    public void testEntrySet() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");

        assertEquals(2, ttlCache.entrySet().size());
    }

    @Test
    public void testSmallSizes() {
        for (int capacity : new int[]{1, 3, 5, 10}) {
            ttlCache = new SoftCache4j<>(10000, capacity);
            for (int i = 0; i < capacity; i++) {
                ttlCache.put(i, "Value" + i);
            }
            for (int i = 0; i < capacity; i++) {
                ttlCache.get(i);
            }
            for (int i = 0; i < capacity; i++) {
                ttlCache.remove(i);
            }

            assertTrue(ttlCache.isEmpty());
            ttlCache.clear();
        }
    }

    @Test
    public void testConcurrency() throws InterruptedException {
        ttlCache = new SoftCache4j<>(10000, 10000);
        ExecutorService service = Executors.newFixedThreadPool(10);

        int max = 10000;
        int attempts = 0;
        Random random = new SecureRandom();
        while (attempts++ < max) {
            final int key = random.nextInt(max);
            final String value = "V" + key;

            service.submit(() -> ttlCache.put(key, value));
            service.submit(() -> ttlCache.get(key));
            service.submit(() -> ttlCache.size());
            service.submit(() -> ttlCache.keySet().remove(random.nextInt(max)));
            service.submit(() -> ttlCache.values().remove("V" + random.nextInt(max)));
            final int attemptsCopy = attempts;
            service.submit(() -> {
                Iterator<Map.Entry<Integer, String>> i = ttlCache.entrySet().iterator();
                int walk = random.nextInt(attemptsCopy);
                while (i.hasNext() && walk-- > 0) {
                    i.next();
                }
                int chunk = 10;
                while (i.hasNext() && chunk-- > 0) {
                    i.remove();
                    i.next();
                }
            });
            service.submit(() -> ttlCache.remove(random.nextInt(max)));
        }

        service.shutdown();
        assertTrue(service.awaitTermination(1, TimeUnit.MINUTES));
    }

    @Test
    public void testEquals() {
        SoftCache4j<Integer, String> cache1 = new SoftCache4j<>(10000, 3);
        SoftCache4j<Integer, String> cache2 = new SoftCache4j<>(10000, 3);

        cache1.put(1, "A");
        cache1.put(2, "B");
        cache1.put(3, "C");

        cache2.put(1, "A");
        cache2.put(2, "B");
        cache2.put(3, "C");

        assertEquals(cache1, cache2);
        assertEquals(cache2, cache1);

        cache2.put(4, "D");
        assertNotEquals(cache1, cache2);
        assertNotEquals(cache2, cache1);

        assertNotEquals(cache1, Boolean.TRUE);

        assertEquals(cache1, cache1);
    }

    @Test
    public void testHashCode() {
        SoftCache4j<Integer, String> cache1 = new SoftCache4j<>(10000, 3);
        SoftCache4j<Integer, String> cache2 = new SoftCache4j<>(10000, 3);

        cache1.put(1, "A");
        cache1.put(2, "B");
        cache1.put(3, "C");

        cache2.put(1, "A");
        cache2.put(2, "B");
        cache2.put(3, "C");

        assertEquals(cache1.hashCode(), cache2.hashCode());

        cache2.put(4, "D");
        assertNotEquals(cache1.hashCode(), cache2.hashCode());
    }

    @Test
    public void testToString() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.put(3, "C");

        String cacheString = ttlCache.toString();
        assertTrue(cacheString.contains("1=A"));
        assertTrue(cacheString.contains("2=B"));
        assertTrue(cacheString.contains("3=C"));

        SoftCache4j<String, String> cache = new SoftCache4j<>(10000, 100);
        assertEquals("{}", cache.toString());
        assertEquals(0, cache.size());
    }

    @Test
    public void testFullCycle() {
        ttlCache = new SoftCache4j<>(10000, 3);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.put(3, "C");
        ttlCache.put(4, "D");
        ttlCache.put(5, "E");
        ttlCache.put(6, "F");

        // Only the last 3 entries should be present due to LRU eviction
        assertEquals(3, ttlCache.size());
        assertTrue(ttlCache.containsKey(4));
        assertTrue(ttlCache.containsKey(5));
        assertTrue(ttlCache.containsKey(6));
        assertFalse(ttlCache.containsKey(1));
        assertFalse(ttlCache.containsKey(2));
        assertFalse(ttlCache.containsKey(3));

        assertEquals("D", ttlCache.get(4));
        assertEquals("E", ttlCache.get(5));
        assertEquals("F", ttlCache.get(6));

        ttlCache.remove(6);
        ttlCache.remove(5);
        ttlCache.remove(4);
        assertEquals(0, ttlCache.size());
    }

    @Test
    public void testCacheWhenEmpty() {
        ttlCache = new SoftCache4j<>(10000, -1);
        assertNull(ttlCache.get(1));
    }

    @Test
    public void testCacheClear() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.clear();

        assertNull(ttlCache.get(1));
        assertNull(ttlCache.get(2));
    }

    @Test
    public void testNullValue() {
        ttlCache = new SoftCache4j<>(10000, 100);
        ttlCache.put(1, null);
        assertTrue(ttlCache.containsKey(1));
        assertTrue(ttlCache.containsValue(null));
        assertTrue(ttlCache.toString().contains("1=null"));
        assertNotEquals(0, ttlCache.hashCode());
    }

    @Test
    public void testNullKey() {
        ttlCache = new SoftCache4j<>(10000, 100);
        ttlCache.put(null, "true");
        assertTrue(ttlCache.containsKey(null));
        assertTrue(ttlCache.containsValue("true"));
        assertTrue(ttlCache.toString().contains("null=true"));
        assertNotEquals(0, ttlCache.hashCode());
    }

    @Test
    public void testNullKeyValue() {
        ttlCache = new SoftCache4j<>(10000, 100);
        ttlCache.put(null, null);
        assertTrue(ttlCache.containsKey(null));
        assertTrue(ttlCache.containsValue(null));
        assertTrue(ttlCache.toString().contains("null=null"));
        assertNotEquals(0, ttlCache.hashCode());

        SoftCache4j<Integer, String> cache1 = new SoftCache4j<>(10000, 3);
        cache1.put(null, null);
        SoftCache4j<Integer, String> cache2 = new SoftCache4j<>(10000, 3);
        cache2.put(null, null);
        assertEquals(cache1, cache2);
    }

    @Test
    public void testSpeed() {
        long startTime = System.currentTimeMillis();
        SoftCache4j<Integer, Boolean> cache = new SoftCache4j<>(100000, 1000000);
        for (int i = 0; i < 1000000; i++) {
            cache.put(i, true);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("TTLCache speed: " + (endTime - startTime) + "ms");
    }

    @Test
    public void testTTLWithoutLRU() throws InterruptedException {
        ttlCache = new SoftCache4j<>(2000, -1); // TTL of 2 seconds, no LRU
        ttlCache.put(1, "A");

        // Immediately check that the entry exists
        assertEquals("A", ttlCache.get(1));

        // Wait for less than TTL
        Thread.sleep(1000);
        assertEquals("A", ttlCache.get(1));

        // Wait for TTL to expire
        Thread.sleep(1500);
        assertNull("Entry should have expired after TTL", ttlCache.get(1));
    }

    @Test
    public void testTTLWithLRU() throws InterruptedException {
        ttlCache = new SoftCache4j<>(2000, 2); // TTL of 2 seconds, max size of 2
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.put(3, "C"); // This should evict key 1 (least recently used)

        assertNull("Entry for key 1 should be evicted due to LRU", ttlCache.get(1));
        assertEquals("B", ttlCache.get(2));
        assertEquals("C", ttlCache.get(3));

        // Wait for TTL to expire
        Thread.sleep(2500);
        assertNull("Entry for key 2 should have expired due to TTL", ttlCache.get(2));
        assertNull("Entry for key 3 should have expired due to TTL", ttlCache.get(3));
    }

    @Test
    public void testAccessResetsLRUOrder() {
        ttlCache = new SoftCache4j<>(10000, 3);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.put(3, "C");

        // Access key 1 and 2
        ttlCache.get(1);
        ttlCache.get(2);

        // Add another entry to trigger eviction
        ttlCache.put(4, "D");

        // Key 3 should be evicted (least recently used)
        assertNull("Entry for key 3 should be evicted", ttlCache.get(3));
        assertEquals("A", ttlCache.get(1));
        assertEquals("B", ttlCache.get(2));
        assertEquals("D", ttlCache.get(4));
    }

    @Test
    public void testIteratorRemove() {
        ttlCache = new SoftCache4j<>(10000, -1);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");
        ttlCache.put(3, "C");

        Iterator<Map.Entry<Integer, String>> iterator = ttlCache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, String> entry = iterator.next();
            if (entry.getKey().equals(2)) {
                iterator.remove();
            }
        }

        assertEquals(2, ttlCache.size());
        assertFalse(ttlCache.containsKey(2));
    }

    @Test
    public void testExpirationDuringIteration() throws InterruptedException {
        ttlCache = new SoftCache4j<>(1000, -1, 100);
        ttlCache.put(1, "A");
        ttlCache.put(2, "B");

        // Wait for TTL to expire
        Thread.sleep(1500);

        int count = 0;
        for (Map.Entry<Integer, String> entry : ttlCache.entrySet()) {
            count++;
        }

        assertEquals(0, count); //  "No entries should be iterated after TTL expiry"
    }

    @Test
    public void testTwoIndependentCaches() {
        SoftCache4j<Integer, String> ttlCache1 = new SoftCache4j<>(1000, -1, 100);
        ttlCache1.put(1, "A");
        ttlCache1.put(2, "B");

        SoftCache4j<Integer, String> ttlCache2 = new SoftCache4j<>(2000, -1, 200);
        ttlCache2.put(10, "X");
        ttlCache2.put(20, "Y");
        ttlCache2.put(30, "Z");

        try {
            Thread.sleep(1100);
            assert ttlCache1.isEmpty();
            assert !ttlCache2.isEmpty();
            Thread.sleep(1300);
            assert ttlCache2.isEmpty();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
