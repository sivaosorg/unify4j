package org.unify4j;

import org.junit.Test;
import org.unify4j.common.String4j;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class String4jTest {

    @Test
    public void testStripAccents_NullInput() {
        assertNull(String4j.stripAccents(null));
    }

    @Test
    public void testStripAccents_EmptyString() {
        assertEquals("", String4j.stripAccents(""));
    }

    @Test
    public void testStripAccents_NoAccents() {
        assertEquals("abcdefg", String4j.stripAccents("abcdefg"));
    }

    @Test
    public void testStripAccents_WithAccents() {
        assertEquals("aeiou", String4j.stripAccents("áéíóú"));
        assertEquals("aeiou", String4j.stripAccents("àèìòù"));
        assertEquals("aeiou", String4j.stripAccents("âêîôû"));
        assertEquals("aeiou", String4j.stripAccents("äëïöü"));
    }

    @Test
    public void testStripAccents_MixedCharacters() {
        assertEquals("Cafe", String4j.stripAccents("Café"));
        assertEquals("Jose", String4j.stripAccents("José"));
        assertEquals("Garcon", String4j.stripAccents("Garçon"));
    }

    @Test
    public void testStripAccents_ComplexCharacters() {
        assertEquals("Donna", String4j.stripAccents("Dõñna"));
        assertEquals("Cu Chulainn", String4j.stripAccents("Cú Chulainn"));
    }

    @Test
    public void testUnAccents_EmptyString() {
        assertEquals("", String4j.unAccents(""));
    }

    @Test
    public void testUnAccents_NoNonASCII() {
        assertEquals("abcdefg", String4j.unAccents("abcdefg"));
    }

    @Test
    public void testUnAccents_WithAccents() {
        assertEquals("aeiou", String4j.unAccents("áéíóú"));
        assertEquals("aeiou", String4j.unAccents("àèìòù"));
        assertEquals("aeiou", String4j.unAccents("âêîôû"));
        assertEquals("aeiou", String4j.unAccents("äëïöü"));
    }

    @Test
    public void testUnAccents_MixedCharacters() {
        assertEquals("Cafe", String4j.unAccents("Café"));
        assertEquals("Jose", String4j.unAccents("José"));
        assertEquals("Garcon", String4j.unAccents("Garçon"));
    }

    @Test
    public void testUnAccents_ComplexCharacters() {
        assertEquals("Donna", String4j.unAccents("Dõñna"));
        assertEquals("Cu Chulainn", String4j.unAccents("Cú Chulainn"));
    }

    @Test
    public void testUnAccents_WithNonASCII() {
        assertEquals("abc123", String4j.unAccents("ábç123"));
        assertEquals("Hello World", String4j.unAccents("Hëllö Wörld"));
        assertEquals("ASCIIOnly", String4j.unAccents("ASCIIÖnly"));
    }

    @Test
    public void testUnAccents_OnlyNonASCII() {
        assertEquals("aeioun", String4j.unAccents("áéíóúñ"));
        assertEquals("oau", String4j.unAccents("öäüß"));
        assertEquals("cccsz", String4j.unAccents("çćčšž"));
    }
}
