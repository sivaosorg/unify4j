package org.unify4j;

import org.junit.Test;
import org.unify4j.common.Request4j;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class Request4jTest {

    @Test
    public void testAppendQueryParams() throws UnsupportedEncodingException {
        String url = "http://example.com";
        Map<String, Object> params = new HashMap<>();
        params.put("key1", "value1");
        params.put("key2", "value2");

        String result = Request4j.appendQueryParams(url, params);
        assertTrue(result.contains("key1=value1"));
        assertTrue(result.contains("key2=value2"));
        assertTrue(result.startsWith("http://example.com?"));
    }

    @Test
    public void testAppendQueryParams_emptyParams() throws UnsupportedEncodingException {
        String url = "http://example.com";
        Map<String, Object> params = new HashMap<>();

        String result = Request4j.appendQueryParams(url, params);
        assertEquals(url, result);
    }

    @Test
    public void testGetQueryParamsFromUrl() throws Exception {
        URL url = new URL("http://example.com?key1=value1&key2=value2");
        Map<String, String> params = Request4j.getQueryParams(url);

        assertEquals(2, params.size());
        assertEquals("value1", params.get("key1"));
        assertEquals("value2", params.get("key2"));
    }

    @Test
    public void testGetQueryParams_emptyUrl() {
        Map<String, String> params = Request4j.getQueryParams((URL) null);
        assertTrue(params.isEmpty());
    }

    @Test
    public void testGetUrlWithoutParameters() {
        String url = "http://example.com?key1=value1&key2=value2";
        String result = Request4j.getUrlWithoutParameters(url);
        assertEquals("http://example.com", result);
    }

    @Test
    public void testGetUrlWithoutParameters_noParams() {
        String url = "http://example.com";
        String result = Request4j.getUrlWithoutParameters(url);
        assertEquals(url, result);
    }

    @Test
    public void testCanConnect() {
        String url = "http://www.google.com"; // Assuming this URL is always reachable
        boolean canConnect = Request4j.canConnect(url);
        assertTrue(canConnect);
    }

    @Test
    public void testCanConnect_invalidUrl() {
        String url = "http://invalid.url";
        boolean canConnect = Request4j.canConnect(url);
        assertFalse(canConnect);
    }
}
