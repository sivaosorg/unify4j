package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.c.Protocol;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Request4j {
    protected static final Logger logger = LoggerFactory.getLogger(Request4j.class);

    /**
     * Appends query parameters to the given URL string.
     * This function constructs a new URL string by appending query parameters represented as key-value pairs.
     *
     * @param url    The original URL string.
     * @param params A map containing query parameters to be appended to the URL.
     * @return The URL string with appended query parameters.
     */
    public static String appendQueryParams(String url, Map<String, Object> params) throws UnsupportedEncodingException {
        if (Collection4j.isEmptyMap(params) || String4j.isEmpty(url)) {
            return url;
        }
        StringBuilder builder = new StringBuilder(url);
        boolean first = (builder.toString().indexOf('?') == -1);
        for (String param : params.keySet()) {
            if (first) {
                builder.append('?');
                first = false;
            } else {
                builder.append('&');
            }
            String value = String.valueOf(params.get(param));
            builder.append(URLEncoder.encode(param, StandardCharsets.UTF_8.displayName())).append('=');
            builder.append(URLEncoder.encode(value, StandardCharsets.UTF_8.displayName()));
        }
        return builder.toString();
    }

    /**
     * Extracts query parameters from the given URL and returns them as a map.
     * This function takes a URL object and extracts query parameters from its query component.
     *
     * @param url The URL object from which to extract query parameters.
     * @return A map containing query parameters parsed from the URL.
     */
    public static Map<String, String> getQueryParams(URL url) {
        if (url == null) {
            return Collections.emptyMap();
        }
        return getQueryParams(url.getQuery());
    }

    /**
     * Extracts the URL without query parameters from the given URL string.
     * This function removes any query parameters from the provided URL string and returns the resulting URL.
     *
     * @param url The URL string from which to remove query parameters.
     * @return The URL string without query parameters.
     */
    public static String getUrlWithoutParameters(String url) {
        if (String4j.isEmpty(url)) {
            return "";
        }
        try {
            URI uri = new URI(url);
            return new URI(uri.getScheme(), uri.getAuthority(), uri.getPath(), null, uri.getFragment()).toString();
        } catch (Exception e) {
            logger.error("Getting URL without parameters got an exception: {} by URL: {}", e.getMessage(), url, e);
            return "";
        }
    }

    /**
     * Attempts to establish a connection to the provided URL.
     * This function creates an HTTP connection to the specified URL and checks if a response code can be obtained.
     *
     * @param url The URL string to which a connection is attempted.
     * @return true if a connection could be established and a response code was received, false otherwise.
     */
    public static boolean canConnect(String url) {
        try {
            URL _url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
            connection.setInstanceFollowRedirects(true); // Allow redirects
            connection.connect();
            int responseCode = connection.getResponseCode();
            return (responseCode >= HttpURLConnection.HTTP_OK && responseCode < HttpURLConnection.HTTP_MULT_CHOICE);
        } catch (IOException e) {
            logger.error("Verifying URL connection got an exception: {} by URL: {}", e.getMessage(), url, e);
            return false;
        }
    }

    /**
     * Retrieves all headers from the given HttpServletRequest and returns them as a Map.
     *
     * @param request The HttpServletRequest object containing the headers.
     * @return A Map containing all the headers as key-value pairs.
     */
    public static Map<String, Object> getHeaders(HttpServletRequest request) {
        Map<String, Object> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            headers.put(name, value);
        }
        return headers;
    }

    /**
     * Retrieves all parameters from the HttpServletRequest as a Map.
     * This method iterates over the parameter names and adds them to a Map with their corresponding values.
     *
     * @param request the HttpServletRequest object containing the client request
     * @return a Map containing all parameter names and values from the request
     */
    public static Map<String, String> getParameters(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            params.put(name, request.getParameter(name));
        }
        return params;
    }

    /**
     * Retrieves the full URL from the HttpServletRequest.
     * This method constructs the full URL from the request, including query parameters.
     *
     * @param request the HttpServletRequest object containing the client request
     * @return the full URL as a String
     */
    public static String getFullUrl(HttpServletRequest request) {
        StringBuilder builder = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        if (queryString != null) {
            builder.append('?').append(queryString);
        }
        return builder.toString();
    }

    /**
     * Retrieves the session ID from the given HttpServletRequest.
     * <p>
     * This method gets the current HttpSession associated with the request,
     * and then extracts the session ID from it. If there is no current session
     * and create is false, it returns null.
     *
     * @param request the HttpServletRequest from which to retrieve the session ID
     * @return the session ID, or null if there is no current session
     */
    public static String getSessionId(HttpServletRequest request) {
        if (request == null) {
            return String.valueOf(UniqueId4j.getUniqueId19());
        }
        HttpSession session = request.getSession(false); // Pass false to prevent creating a new session if one does not exist
        return (session != null) ? session.getId() : null;
    }

    /**
     * Retrieves the requested session ID from the HttpServletRequest.
     * This method retrieves the session ID from the request, if available.
     *
     * @param request the HttpServletRequest object containing the client request
     * @return the session ID as a String, or null if no session ID is available
     */
    public static String getDirectSessionId(HttpServletRequest request) {
        if (request == null) {
            return String.valueOf(UniqueId4j.getUniqueId19());
        }
        return request.getRequestedSessionId();
    }

    /**
     * Retrieves the base URL from the HttpServletRequest.
     * This method returns the base URL of the request (protocol, server name, and port).
     *
     * @param request the HttpServletRequest object containing the client request
     * @return the base URL of the request as a String
     */
    public static String getBaseUrl(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        StringBuilder baseUrl = new StringBuilder();
        Protocol HTTP = Protocol.HTTP;
        Protocol HTTPS = Protocol.HTTPS;
        Protocol protocol = new Protocol(
                request.getScheme(),
                request.getScheme().toUpperCase(),
                request.getServerName(),
                request.getServerPort());
        baseUrl.append(protocol.getSchemeName()).append("://").append(protocol.getDescription());
        if ((HTTP.getSchemeName().equalsIgnoreCase(protocol.getSchemeName()) && HTTP.getDefaultPort() != protocol.getDefaultPort()) ||
                (HTTPS.getSchemeName().equalsIgnoreCase(protocol.getSchemeName()) && HTTPS.getDefaultPort() != protocol.getDefaultPort())) {
            baseUrl.append(":").append(protocol.getDefaultPort());
        }
        return baseUrl.toString();
    }

    /**
     * Parses query parameters from the given query string and returns them as a map.
     * This function takes a query string and extracts key-value pairs representing query parameters.
     *
     * @param query The query string containing URL parameters.
     * @return A map containing query parameters parsed from the query string.
     */
    protected static Map<String, String> getQueryParams(String query) {
        if (String4j.isEmpty(query)) {
            return Collections.emptyMap();
        }
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            String[] currentParam = param.split("=");
            if (currentParam.length != 2) {
                continue;
            }
            String name = currentParam[0];
            String value = currentParam[1];
            map.put(name, value);
        }
        return map;
    }
}
