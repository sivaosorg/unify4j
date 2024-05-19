package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Cookie4j {
    protected static final Logger logger = LoggerFactory.getLogger(Cookie4j.class);

    /**
     * Retrieves cookies from the provided HttpServletRequest and returns them as a map of cookie names to values.
     * If the request is null or contains no cookies, returns an empty map.
     *
     * @param request The HttpServletRequest from which to retrieve cookies.
     * @return A map of cookie names to values extracted from the request.
     */
    public static Map<String, String> getCookies(HttpServletRequest request) {
        if (request == null || Object4j.isEmpty(request.getCookies())) {
            return Collections.emptyMap();
        }
        return Arrays.stream(request.getCookies()).collect(Collectors.toMap(Cookie::getName, // Use cookie's name as the map key
                Cookie::getValue, // Use cookie's value as the map value
                (cookie1, cookie2) -> { // Merge function for handling duplicate keys
                    logger.info("cookie duplicated key found by cookie-1: {} and cookie-2: {}", cookie1, cookie2);
                    return cookie1; // Keep the value of the first cookie in case of a duplicate key
                }));
    }

    /**
     * Retrieves cookies from the provided ServletRequest and returns them as a map of cookie names to values.
     * If the request is null or not of type HttpServletRequest, returns an empty map.
     *
     * @param request The ServletRequest from which to retrieve cookies.
     * @return A map of cookie names to values extracted from the request, or an empty map if the request is null or not an HttpServletRequest.
     */
    public static Map<String, String> getCookies(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            return Collections.emptyMap();
        }
        return getCookies((HttpServletRequest) request);
    }

    /**
     * Constructs a string representation of cookies present in the provided HttpServletRequest.
     * The string format is "name1=value1,name2=value2,..." for all cookies.
     * If the request is null or contains no cookies, returns an empty string.
     *
     * @param request The HttpServletRequest from which to retrieve cookies.
     * @return A string representation of cookies in the format "name1=value1,name2=value2,...", or an empty string if the request is null or contains no cookies.
     */
    public static String cookieString(HttpServletRequest request) {
        if (request == null || Object4j.isEmpty(request.getCookies())) {
            return "";
        }
        return Arrays.stream(request.getCookies()).map(cookie -> cookie.getName().concat("=").concat(cookie.getValue())) // Map each cookie to "name=value" format
                .collect(Collectors.joining(",")); // Join all cookie strings with ","
    }

    /**
     * Constructs a string representation of cookies present in the provided ServletRequest.
     * The string format is "name1=value1,name2=value2,..." for all cookies.
     * If the request is not an instance of HttpServletRequest, returns an empty string.
     *
     * @param request The ServletRequest from which to retrieve cookies.
     * @return A string representation of cookies in the format "name1=value1,name2=value2,...", or an empty string if the request is not an instance of HttpServletRequest.
     */
    public static String cookieString(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            return "";
        }
        return cookieString((HttpServletRequest) request);
    }

    /**
     * Retrieves cookies from the provided HttpServletRequest and returns them as a List of Cookie objects.
     * If the request is null or does not contain any cookies, returns an empty list.
     *
     * @param request The HttpServletRequest from which to retrieve cookies.
     * @return A List of Cookie objects extracted from the request, or an empty list if the request is null or contains no cookies.
     */
    public static List<Cookie> getCollections(HttpServletRequest request) {
        if (request == null) {
            return Collections.emptyList();
        }
        return Arrays.stream(request.getCookies()).collect(Collectors.toList());
    }

    /**
     * Checks if the provided HttpServletRequest contains all the specified cookies.
     *
     * @param request The HttpServletRequest object containing cookies to be checked.
     * @param cookies A List of cookie names to be checked for existence.
     * @return true if all specified cookies are present in the request, false otherwise.
     */
    public static boolean hasCookies(HttpServletRequest request, List<String> cookies) {
        if (request == null || Collection4j.isEmpty(cookies) || Object4j.isEmpty(request.getCookies())) {
            return false;
        }
        List<String> list = Arrays.stream(request.getCookies()).map(Cookie::getName).collect(Collectors.toList());
        return list.stream().flatMap(x -> cookies.stream().filter(x::equalsIgnoreCase).limit(1)).findFirst().isPresent();
    }

    /**
     * Checks if the provided ServletRequest contains all the specified cookies.
     *
     * @param request The ServletRequest object containing cookies to be checked.
     * @param cookies A List of cookie names to be checked for existence.
     * @return true if all specified cookies are present in the request, false otherwise.
     */
    public static boolean hasCookies(ServletRequest request, List<String> cookies) {
        return hasCookies((HttpServletRequest) request, cookies);
    }

    /**
     * Checks if the provided HttpServletRequest contains the specified cookie.
     *
     * @param request The HttpServletRequest object containing cookies to be checked.
     * @param cookie  The name of the cookie to be checked for existence.
     * @return true if the specified cookie is present in the request, false otherwise.
     */
    public static boolean hasCookie(HttpServletRequest request, String cookie) {
        return hasCookies(request, Collections.singletonList(cookie));
    }

    /**
     * Checks if the provided ServletRequest contains the specified cookie.
     *
     * @param request The ServletRequest object containing cookies to be checked.
     * @param cookie  The name of the cookie to be checked for existence.
     * @return true if the specified cookie is present in the request, false otherwise.
     */
    public static boolean hasCookie(ServletRequest request, String cookie) {
        return hasCookies(request, Collections.singletonList(cookie));
    }

    /**
     * Checks if the provided HttpServletRequest contains all the specified cookies.
     *
     * @param request The HttpServletRequest object containing cookies to be checked.
     * @param cookies A variable number of cookie names to be checked for existence.
     * @return true if all specified cookies are present in the request, false otherwise.
     */
    public static boolean hasCookies(HttpServletRequest request, String... cookies) {
        return hasCookies(request, Transform4j.fromArray2Coll(cookies));
    }

    /**
     * Checks if the provided ServletRequest contains all the specified cookies.
     *
     * @param request The ServletRequest object containing cookies to be checked.
     * @param cookies A variable number of cookie names to be checked for existence.
     * @return true if all specified cookies are present in the request, false otherwise.
     */
    public static boolean hasCookies(ServletRequest request, String... cookies) {
        if (!(request instanceof HttpServletRequest)) {
            return false;
        }
        return hasCookies((HttpServletRequest) request, cookies);
    }

    /**
     * Checks if the provided HttpServletRequest contains the specified cookie.
     *
     * @param request The HttpServletRequest object containing cookies to be checked.
     * @param cookie  The Cookie object to be checked for existence.
     * @return true if the specified cookie is present in the request, false otherwise.
     */
    public static boolean hasCookie(HttpServletRequest request, Cookie cookie) {
        return hasCookies(request, Collections.singletonList(cookie.getName()));
    }

    /**
     * Checks if the provided ServletRequest contains the specified cookie.
     *
     * @param request The ServletRequest object containing cookies to be checked.
     * @param cookie  The Cookie object to be checked for existence.
     * @return true if the specified cookie is present in the request, false otherwise.
     */
    public static boolean hasCookie(ServletRequest request, Cookie cookie) {
        if (!(request instanceof HttpServletRequest)) {
            return false;
        }
        return hasCookie((HttpServletRequest) request, cookie);
    }

    /**
     * Checks if the provided HttpServletRequest has any cookies.
     *
     * @param request The HttpServletRequest object to check for available cookies.
     * @return true if the request contains cookies, false otherwise.
     */
    public static boolean isAvailableCookie(HttpServletRequest request) {
        return Collection4j.isNotEmptyMap(getCookies(request));
    }

    /**
     * Checks if the provided ServletRequest has any cookies.
     *
     * @param request The ServletRequest object to check for available cookies.
     * @return true if the request contains cookies, false otherwise.
     */
    public static boolean isAvailableCookie(ServletRequest request) {
        if (!(request instanceof HttpServletRequest)) {
            return false;
        }
        return isAvailableCookie((HttpServletRequest) request);
    }
}
