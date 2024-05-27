package org.unify4j;

import org.junit.Test;
import org.unify4j.common.Class4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Class4jTest {

    @Test
    public void testComputeInheritanceDistance_sameClass() {
        assertEquals(0, Class4j.computeInheritanceDistance(Integer.class, Integer.class));
    }

    @Test
    public void testComputeInheritanceDistance_superclass() {
        assertEquals(1, Class4j.computeInheritanceDistance(Integer.class, Number.class));
    }

    @Test
    public void testComputeInheritanceDistance_interface() {
        assertEquals(1, Class4j.computeInheritanceDistance(HashMap.class, Map.class));
    }

    @Test
    public void testComputeInheritanceDistance_unrelatedClasses() {
        assertEquals(-1, Class4j.computeInheritanceDistance(Integer.class, String.class));
    }

    @Test
    public void testIsPrimitive() {
        assertTrue(Class4j.isPrimitive(int.class));
        assertTrue(Class4j.isPrimitive(Integer.class));
        assertFalse(Class4j.isPrimitive(String.class));
    }

    @Test
    public void testForName() {
        assertEquals(String.class, Class4j.forName("java.lang.String", ClassLoader.getSystemClassLoader()));
        assertEquals(Date.class, Class4j.forName("java.util.Date", ClassLoader.getSystemClassLoader()));
        assertNull(Class4j.forName("non.existent.Class", ClassLoader.getSystemClassLoader()));
    }

    @Test
    public void testIsClassFinal() {
        assertTrue(Class4j.isClassFinal(String.class));
        assertFalse(Class4j.isClassFinal(HashMap.class));
    }

    @Test
    public void testAreAllConstructorsPrivate() {
        assertTrue(Class4j.areAllConstructorsPrivate(PrivateConstructorsClass.class));
        assertFalse(Class4j.areAllConstructorsPrivate(String.class));
    }

    @Test
    public void testToPrimitiveWrapperClass() {
        assertEquals(Integer.class, Class4j.toPrimitiveWrapperClass(int.class));
        assertEquals(String.class, Class4j.toPrimitiveWrapperClass(String.class));
    }

    @Test
    public void testDoesOneWrapTheOther() {
        assertTrue(Class4j.doesOneWrapTheOther(Integer.class, int.class));
        assertFalse(Class4j.doesOneWrapTheOther(String.class, int.class));
    }

    private static class PrivateConstructorsClass {
        private PrivateConstructorsClass() {
        }
    }
}
