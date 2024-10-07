package org.unify4j;

import org.junit.Test;
import org.unify4j.common.Traverse4j;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class Traverse4jTest {
    static class Alpha {
        String name;
        Collection<Object> contacts;
        Beta beta;
    }

    static class Beta {
        int age;
        Map<Object, Object> friends;
        Charlie charlie;
    }

    static class Charlie {
        double salary;
        Collection<Object> timezones;
        Object[] dates;
        Alpha alpha;
        TimeZone zone = TimeZone.getDefault();
        Delta delta;
    }

    static class Delta {
        TimeZone timeZone = TimeZone.getDefault();
    }

    @Test
    public void testCyclicTraverse() {
        Alpha alpha = new Alpha();
        Beta beta = new Beta();
        Charlie charlie = new Charlie();

        alpha.name = "alpha";
        alpha.beta = beta;
        alpha.contacts = new ArrayList<>();
        alpha.contacts.add(beta);
        alpha.contacts.add(charlie);
        alpha.contacts.add("Harry");

        beta.age = 45;
        beta.charlie = charlie;
        beta.friends = new LinkedHashMap<>();
        beta.friends.put("Tom", "Tom Jones");
        beta.friends.put(alpha, "Alpha beta");
        beta.friends.put("beta", beta);

        charlie.salary = 150000.01;
        charlie.alpha = alpha;
        charlie.timezones = new LinkedList<>();
        charlie.timezones.add(TimeZone.getTimeZone("EST"));
        charlie.timezones.add(TimeZone.getTimeZone("GMT"));
        charlie.dates = new Date[]{new Date()};

        final int[] visited = new int[4];
        visited[0] = 0;
        visited[1] = 0;
        visited[2] = 0;
        visited[3] = 0;

        Traverse4j.Visitor visitor = new Traverse4j.Visitor() {
            public void process(Object o) {
                if (o instanceof Alpha) {
                    visited[0]++;
                } else if (o instanceof Beta) {
                    visited[1]++;
                } else if (o instanceof Charlie) {
                    visited[2]++;
                } else if (o instanceof TimeZone) {
                    visited[3]++;
                }
            }
        };
        Traverse4j.traverse(alpha, visitor);

        assertEquals(1, visited[0]);
        assertEquals(1, visited[1]);
        assertEquals(1, visited[2]);
        assertTrue(visited[3] >= 1);

        visited[0] = 0;
        visited[1] = 0;
        visited[2] = 0;
        visited[3] = 0;

        Traverse4j.traverse(alpha, new Class[]{TimeZone.class}, visitor);
        assertEquals(1, visited[0]);
        assertEquals(1, visited[1]);
        assertEquals(1, visited[2]);
        assertEquals(0, visited[3]);
    }
}
