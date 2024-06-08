package org.unify4j;

import org.junit.Test;
import org.unify4j.model.c.MediaType;

import java.util.Map;

import static org.junit.Assert.*;

public class MediaTypeTest {

    @Test
    public void testRegisterAndRetrieveMediaType() {
        MediaType mediaType = MediaType.register("application/test", "Test Application");
        assertNotNull(mediaType);
        assertEquals("application/test", mediaType.getName());
        assertEquals("Test Application", mediaType.getDescription());

        MediaType retrievedMediaType = MediaType.valueOf("application/test");
        assertSame(mediaType, retrievedMediaType);
    }

    @Test
    public void testGetMostSpecific() {
        MediaType mediaType1 = MediaType.TEXT_HTML;
        MediaType mediaType2 = MediaType.TEXT_PLAIN;
        MediaType mediaType3 = MediaType.ALL;

        MediaType result = MediaType.getMostSpecific(mediaType1, mediaType2, mediaType3);
        System.out.println(result);
        assertEquals(mediaType1, result);
    }

    @Test
    public void testIncludes() {
        assertTrue(MediaType.ALL.includes(MediaType.APPLICATION_JSON));
        assertTrue(MediaType.TEXT_ALL.includes(MediaType.TEXT_PLAIN));
        assertFalse(MediaType.TEXT_PLAIN.includes(MediaType.TEXT_ALL));
    }

    @Test
    public void testGetMainType() {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        assertEquals("application", mediaType.getMainType());

        MediaType mediaType2 = MediaType.TEXT_PLAIN;
        assertEquals("text", mediaType2.getMainType());
    }

    @Test
    public void testGetSubType() {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        assertEquals("json", mediaType.getSubType());

        MediaType mediaType2 = MediaType.TEXT_PLAIN;
        assertEquals("plain", mediaType2.getSubType());
    }

    @Test
    public void testEqualsAndHashCode() {
        MediaType mediaType1 = MediaType.APPLICATION_JSON;
        MediaType mediaType2 = MediaType.valueOf("application/json");

        assertEquals(mediaType1, mediaType2);
        assertEquals(mediaType1.hashCode(), mediaType2.hashCode());
    }

    @Test
    public void testGetTypes() {
        Map<String, MediaType> types = MediaType.getTypes();
        assertNotNull(types);
        assertTrue(types.containsKey("application/json"));
        assertSame(MediaType.APPLICATION_JSON, types.get("application/json"));
    }
}
