package org.unify4j;

import org.junit.Test;
import org.unify4j.model.c.Method;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MethodTest {
    @Test
    public void testMethodCreation() {
        Method method = new Method("TEST", "This is a test method", true, true, true);
        assertEquals("TEST", method.getName());
        assertEquals("This is a test method", method.getDescription());
        assertTrue(method.isSafe());
        assertTrue(method.isIdempotent());
        assertTrue(method.isReplying());
    }

    @Test
    public void testEqualsAndHashCode() {
        Method method1 = new Method("EQUALS_TEST");
        Method method2 = new Method("EQUALS_TEST");
        Method method3 = new Method("DIFFERENT_TEST");

        assertEquals(method1, method2);
        assertNotEquals(method1, method3);

        assertEquals(method1.hashCode(), method2.hashCode());
        assertNotEquals(method1.hashCode(), method3.hashCode());
    }

    @Test
    public void testCompareTo() {
        Method method1 = new Method("A");
        Method method2 = new Method("B");
        Method method3 = new Method("A");

        assertTrue(method1.compareTo(method2) < 0);
        assertTrue(method2.compareTo(method1) > 0);
        assertEquals(0, method1.compareTo(method3));
    }

    @Test
    public void testRegisterAndValueOf() {
        Method customMethod = new Method("CUSTOM_METHOD");
        Method.register(customMethod);

        Method retrievedMethod = Method.valueOf("CUSTOM_METHOD");
        assertEquals(customMethod, retrievedMethod);
    }

    @Test
    public void testSort() {
        Method methodA = new Method("A");
        Method methodB = new Method("B");
        Method methodC = new Method("C");

        List<Method> methods = new ArrayList<>();
        methods.add(methodC);
        methods.add(methodA);
        methods.add(methodB);

        Method.sort(methods);

        assertEquals(methodA, methods.get(0));
        assertEquals(methodB, methods.get(1));
        assertEquals(methodC, methods.get(2));
    }

    @Test
    public void testStaticMethods() {
        assertNotNull(Method.ALL);
        assertNotNull(Method.CONNECT);
        assertNotNull(Method.COPY);
        assertNotNull(Method.DELETE);
        assertNotNull(Method.GET);
        assertNotNull(Method.HEAD);
        assertNotNull(Method.LOCK);
        assertNotNull(Method.MKCOL);
        assertNotNull(Method.MOVE);
        assertNotNull(Method.OPTIONS);
        assertNotNull(Method.POST);
        assertNotNull(Method.PROPFIND);
        assertNotNull(Method.PROPPATCH);
        assertNotNull(Method.PUT);
        assertNotNull(Method.TRACE);
        assertNotNull(Method.UNLOCK);
    }
}
