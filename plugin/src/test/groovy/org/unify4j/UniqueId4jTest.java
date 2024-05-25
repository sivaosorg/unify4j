package org.unify4j;

import org.junit.Test;
import org.unify4j.common.UniqueId4j;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Math.abs;
import static java.lang.System.currentTimeMillis;
import static org.junit.Assert.assertEquals;
import static org.unify4j.common.UniqueId4j.*;

public class UniqueId4jTest {
    protected static final int bucketSize = 200000;

    @Test
    public void testIdLengths() {
        long id18 = getUniqueId();
        long id19 = getUniqueId19();

        assert String.valueOf(id18).length() == 18;
        assert String.valueOf(id19).length() == 19;
    }

    @Test
    public void testIDtoDate() {
        long id = getUniqueId();
        Date date = getDate(id);
        assert abs(date.getTime() - currentTimeMillis()) < 2;

        id = getUniqueId19();
        date = getDate19(id);
        assert abs(date.getTime() - currentTimeMillis()) < 2;
    }

    @Test
    public void testUniqueIdGeneration() {
        int testSize = 100000;
        Long[] keep = new Long[testSize];
        Long[] keep19 = new Long[testSize];

        for (int i = 0; i < testSize; i++) {
            keep[i] = getUniqueId();
            keep19[i] = getUniqueId19();
        }

        Set<Long> unique = new HashSet<>(testSize);
        Set<Long> unique19 = new HashSet<>(testSize);
        for (int i = 0; i < testSize; i++) {
            unique.add(keep[i]);
            unique19.add(keep19[i]);
        }
        assertEquals(unique.size(), testSize);
        assertEquals(unique19.size(), testSize);

        assertMonotonicallyIncreasing(keep);
        assertMonotonicallyIncreasing(keep19);
    }

    @Test
    public void speedTest() {
        long start = System.currentTimeMillis();
        int count = 0;
        while (System.currentTimeMillis() < start + 1000) {
            UniqueId4j.getUniqueId19();
            count++;
        }
        System.out.println("count = " + count);
    }

    @Test
    public void testConcurrency() {
        final CountDownLatch startLatch = new CountDownLatch(1);
        int numTests = 4;
        final CountDownLatch finishedLatch = new CountDownLatch(numTests);

        // 18 digit ID buckets
        final Set<Long> bucket1 = new LinkedHashSet<>();
        final Set<Long> bucket2 = new LinkedHashSet<>();
        final Set<Long> bucket3 = new LinkedHashSet<>();
        final Set<Long> bucket4 = new LinkedHashSet<>();

        // 19 digit ID buckets
        final Set<Long> bucketA = new LinkedHashSet<>();
        final Set<Long> bucketB = new LinkedHashSet<>();
        final Set<Long> bucketC = new LinkedHashSet<>();
        final Set<Long> bucketD = new LinkedHashSet<>();

        Runnable test1 = () -> {
            await(startLatch);
            fillBucket(bucket1);
            fillBucket19(bucketA);
            finishedLatch.countDown();
        };

        Runnable test2 = () -> {
            await(startLatch);
            fillBucket(bucket2);
            fillBucket19(bucketB);
            finishedLatch.countDown();
        };

        Runnable test3 = () -> {
            await(startLatch);
            fillBucket(bucket3);
            fillBucket19(bucketC);
            finishedLatch.countDown();
        };

        Runnable test4 = () -> {
            await(startLatch);
            fillBucket(bucket4);
            fillBucket19(bucketD);
            finishedLatch.countDown();
        };

        long start = System.nanoTime();
        ExecutorService executor = Executors.newFixedThreadPool(numTests);
        executor.execute(test1);
        executor.execute(test2);
        executor.execute(test3);
        executor.execute(test4);

        startLatch.countDown();  // trigger all threads to begin
        await(finishedLatch);   // wait for all threads to finish

        long end = System.nanoTime();
        System.out.println("(end - start) / 1000000.0 = " + (end - start) / 1000000.0);

        assertMonotonicallyIncreasing(bucket1.toArray(new Long[]{}));
        assertMonotonicallyIncreasing(bucket2.toArray(new Long[]{}));
        assertMonotonicallyIncreasing(bucket3.toArray(new Long[]{}));
        assertMonotonicallyIncreasing(bucket4.toArray(new Long[]{}));

        assertMonotonicallyIncreasing(bucketA.toArray(new Long[]{}));
        assertMonotonicallyIncreasing(bucketB.toArray(new Long[]{}));
        assertMonotonicallyIncreasing(bucketC.toArray(new Long[]{}));
        assertMonotonicallyIncreasing(bucketD.toArray(new Long[]{}));

        // Assert that there are no duplicates between any buckets
        // Compare:
        //     1->2, 1->3, 1->4
        //     2->3, 2->4
        //     3->4
        // That covers all combinations.  Each bucket has 3 comparisons (can be on either side of the comparison).
        Set<Long> copy = new HashSet<>(bucket1);
        assert bucket1.size() == bucketSize;
        bucket1.retainAll(bucket2);
        assert bucket1.isEmpty();
        bucket1.addAll(copy);

        assert bucket1.size() == bucketSize;
        bucket1.retainAll(bucket3);
        assert bucket1.isEmpty();
        bucket1.addAll(copy);

        assert bucket1.size() == bucketSize;
        bucket1.retainAll(bucket4);
        assert bucket1.isEmpty();
        bucket1.addAll(copy);

        // Assert that there are no duplicates between bucket2 and any of the other buckets (bucket1/bucket2 has already been checked).
        copy = new HashSet<>(bucket2);
        assert bucket2.size() == bucketSize;
        bucket2.retainAll(bucket3);
        assert bucket2.isEmpty();
        bucket2.addAll(copy);

        assert bucket2.size() == bucketSize;
        bucket2.retainAll(bucket4);
        assert bucket2.isEmpty();
        bucket2.addAll(copy);

        // Assert that there are no duplicates between bucket3 and any of the other buckets (bucket3 has already been compared to 1 & 2)
        copy = new HashSet<>(bucket3);
        assert bucket3.size() == bucketSize;
        bucket3.retainAll(bucket4);
        assert bucket3.isEmpty();
        bucket3.addAll(copy);

        // Assert that there are no duplicates between bucketA and any of the other buckets (19 digit buckets).
        copy = new HashSet<>(bucketA);
        assert bucketA.size() == bucketSize;
        bucketA.retainAll(bucketB);
        assert bucketA.isEmpty();
        bucketA.addAll(copy);

        assert bucketA.size() == bucketSize;
        bucketA.retainAll(bucketC);
        assert bucketA.isEmpty();
        bucketA.addAll(copy);

        assert bucketA.size() == bucketSize;
        bucketA.retainAll(bucketD);
        assert bucketA.isEmpty();
        bucketA.addAll(copy);

        // Assert that there are no duplicates between bucket2 and any of the other buckets (bucketA/bucketB has already been checked).
        copy = new HashSet<>(bucketB);
        assert bucketB.size() == bucketSize;
        bucketB.retainAll(bucketC);
        assert bucketB.isEmpty();
        bucketB.addAll(copy);

        assert bucketB.size() == bucketSize;
        bucketB.retainAll(bucketD);
        assert bucketB.isEmpty();
        bucketB.addAll(copy);

        // Assert that there are no duplicates between bucket3 and any of the other buckets (bucketC has already been compared to A & B)
        copy = new HashSet<>(bucketC);
        assert bucketC.size() == bucketSize;
        bucketC.retainAll(bucketD);
        assert bucketC.isEmpty();
        bucketC.addAll(copy);

        executor.shutdown();
    }

    private void assertMonotonicallyIncreasing(Long[] ids) {
        final long len = ids.length;
        long prevId = -1;
        for (int i = 0; i < len; i++) {
            long id = ids[i];
            if (prevId != -1) {
                if (prevId >= id) {
                    System.out.println("index = " + i);
                    System.out.println(prevId);
                    System.out.println(id);
                    System.out.flush();
                    assert false : "ids are not monotonically increasing";
                }
            }
            prevId = id;
        }
    }

    @SuppressWarnings({"CallToPrintStackTrace"})
    private void await(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void fillBucket(Set<Long> bucket) {
        for (int i = 0; i < bucketSize; i++) {
            bucket.add(getUniqueId());
        }
    }

    private void fillBucket19(Set<Long> bucket) {
        for (int i = 0; i < bucketSize; i++) {
            bucket.add(getUniqueId19());
        }
    }
}
