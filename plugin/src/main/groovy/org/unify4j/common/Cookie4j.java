package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.unify4j.model.request.CookieRequest;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
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
                    if (logger.isInfoEnabled()) {
                        logger.info("cookie duplicated key found by cookie-1: {} and cookie-2: {}", cookie1, cookie2);
                    }
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

    /**
     * Retrieves the value of a specified cookie from the HttpServletRequest.
     *
     * @param request The HttpServletRequest containing cookies.
     * @param name    The name of the cookie to be retrieved.
     * @return An Optional containing the cookie value if found, otherwise an empty Optional.
     */
    public static Optional<String> getCookie(HttpServletRequest request, String name) {
        if (request == null || String4j.isEmpty(name) || Object4j.isEmpty(request.getCookies())) {
            return Optional.empty();
        }
        name = String4j.trimWhitespace(name).toLowerCase();
        String tmp = name;
        return Arrays.stream(request.getCookies()).filter(cookie -> tmp.equalsIgnoreCase(cookie.getName())).map(Cookie::getValue).findFirst();
    }

    /**
     * Retrieves the value of a specified cookie from the ServletRequest.
     *
     * @param request The ServletRequest containing cookies.
     * @param name    The name of the cookie to be retrieved.
     * @return An Optional containing the cookie value if found, otherwise an empty Optional.
     */
    public static Optional<String> getCookie(ServletRequest request, String name) {
        if (!(request instanceof HttpServletRequest)) {
            return Optional.empty();
        }
        return getCookie((HttpServletRequest) request, name);
    }

    /**
     * Retrieves the value of a specified cookie from the HttpServletRequest.
     *
     * @param request The HttpServletRequest containing cookies.
     * @param cookie  The Cookie object whose value is to be retrieved.
     * @return An Optional containing the cookie value if found, otherwise an empty Optional.
     */
    public static Optional<String> getCookie(HttpServletRequest request, Cookie cookie) {
        return getCookie(request, cookie.getName());
    }

    /**
     * Retrieves the value of a specified cookie from the ServletRequest.
     *
     * @param request The ServletRequest containing cookies.
     * @param cookie  The Cookie object whose value is to be retrieved.
     * @return An Optional containing the cookie value if found, otherwise an empty Optional.
     */
    public static Optional<String> getCookie(ServletRequest request, Cookie cookie) {
        if (!(request instanceof HttpServletRequest)) {
            return Optional.empty();
        }
        return getCookie((HttpServletRequest) request, cookie);
    }

    /**
     * Adds a specified cookie to the HttpServletResponse.
     *
     * @param response The HttpServletResponse to which the cookie is added.
     * @param cookie   The Cookie object to be added.
     */
    public static void setCookie(HttpServletResponse response, Cookie cookie) {
        response.addCookie(cookie);
    }

    /**
     * Adds a specified cookie to the ServletResponse.
     *
     * @param response The ServletResponse to which the cookie is added.
     * @param cookie   The Cookie object to be added.
     */
    public static void setCookie(ServletResponse response, Cookie cookie) {
        if (!(response instanceof HttpServletResponse)) {
            return;
        }
        setCookie((HttpServletResponse) response, cookie);
    }

    /**
     * Adds a list of cookies to the HttpServletResponse.
     *
     * @param response The HttpServletResponse to which the cookies are added.
     * @param cookies  A List of Cookie objects to be added.
     */
    public static void setCookies(HttpServletResponse response, List<Cookie> cookies) {
        if (Collection4j.isEmpty(cookies)) {
            return;
        }
        cookies.forEach(cookie -> setCookie(response, cookie));
    }

    /**
     * Adds a list of cookies to the ServletResponse.
     *
     * @param response The ServletResponse to which the cookies are added.
     * @param cookies  A List of Cookie objects to be added.
     */
    public static void setCookies(ServletResponse response, List<Cookie> cookies) {
        if (!(response instanceof HttpServletResponse)) {
            return;
        }
        setCookies((HttpServletResponse) response, cookies);
    }

    /**
     * Deletes a specified cookie from the HttpServletResponse.
     *
     * @param response The HttpServletResponse from which the cookie is deleted.
     * @param cookie   The Cookie object to be deleted.
     */
    public static void deleteCookie(HttpServletResponse response, Cookie cookie) {
        cookie.setMaxAge(0);
        setCookie(response, cookie);
    }

    /**
     * Deletes a specified cookie from the ServletResponse.
     *
     * @param response The ServletResponse from which the cookie is deleted.
     * @param cookie   The Cookie object to be deleted.
     */
    public static void deleteCookie(ServletResponse response, Cookie cookie) {
        if (!(response instanceof HttpServletResponse)) {
            return;
        }
        deleteCookie((HttpServletResponse) response, cookie);
    }

    /**
     * Deletes a specified cookie from the HttpServletResponse by name.
     *
     * @param response The HttpServletResponse from which the cookie is deleted.
     * @param name     The name of the cookie to be deleted.
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        setCookie(response, cookie);
    }

    /**
     * Deletes a specified cookie from the ServletResponse by name.
     *
     * @param response The ServletResponse from which the cookie is deleted.
     * @param name     The name of the cookie to be deleted.
     */
    public static void deleteCookie(ServletResponse response, String name) {
        if (!(response instanceof HttpServletResponse)) {
            return;
        }
        deleteCookie((HttpServletResponse) response, name);
    }

    /**
     * Deletes a list of cookies from the HttpServletResponse by name.
     *
     * @param response The HttpServletResponse from which the cookies are deleted.
     * @param cookies  A List of cookie names to be deleted.
     */
    public static void deleteCookies(HttpServletResponse response, List<String> cookies) {
        if (Collection4j.isEmpty(cookies)) {
            return;
        }
        cookies.forEach(cookie -> deleteCookie(response, cookie));
    }

    /**
     * Deletes a list of cookies from the ServletResponse by name.
     *
     * @param response The ServletResponse from which the cookies are deleted.
     * @param cookies  A List of cookie names to be deleted.
     */
    public static void deleteCookies(ServletResponse response, List<String> cookies) {
        if (!(response instanceof HttpServletResponse)) {
            return;
        }
        deleteCookies((HttpServletResponse) response, cookies);
    }

    /**
     * Deletes a list of Cookie objects from the HttpServletResponse.
     *
     * @param response The HttpServletResponse from which the cookies are deleted.
     * @param cookies  A List of Cookie objects to be deleted.
     */
    public static void delete_cookies(HttpServletResponse response, List<Cookie> cookies) {
        if (Collection4j.isEmpty(cookies)) {
            return;
        }
        cookies.forEach(cookie -> deleteCookie(response, cookie));
    }

    /**
     * Deletes a list of Cookie objects from the ServletResponse.
     *
     * @param response The ServletResponse from which the cookies are deleted.
     * @param cookies  A List of Cookie objects to be deleted.
     */
    public static void delete_cookies(ServletResponse response, List<Cookie> cookies) {
        if (!(response instanceof HttpServletResponse)) {
            return;
        }
        delete_cookies((HttpServletResponse) response, cookies);
    }

    /**
     * Creates a Cookie object from a CookieRequest.
     *
     * @param request The CookieRequest object containing cookie details.
     * @return A Cookie object created based on the provided CookieRequest.
     */
    public static Cookie createCookie(CookieRequest request) {
        Cookie cookie = new Cookie(request.getName(), request.getValue());
        cookie.setMaxAge((int) request.getExpiredAt().getSeconds());
        return cookie;
    }

    /**
     * Creates a list of Cookie objects from a list of CookieRequest objects.
     *
     * @param cookies The list of CookieRequest objects containing cookie details.
     * @return A List of Cookie objects created based on the provided CookieRequest objects.
     */
    public static List<Cookie> createCookies(List<CookieRequest> cookies) {
        if (Collection4j.isEmpty(cookies)) {
            return Collections.emptyList();
        }
        return cookies.stream().map(Cookie4j::createCookie).collect(Collectors.toList());
    }

    /**
     * Retrieves a Cookie object from the HttpServletRequest by its name, if it exists.
     *
     * @param request The HttpServletRequest containing cookies.
     * @param name    The name of the cookie to be retrieved.
     * @return An Optional containing the Cookie object if found, otherwise an empty Optional.
     */
    public static Optional<Cookie> getCookieValidity(HttpServletRequest request, String name) {
        List<Cookie> cookies = getCollections(request);
        if (Collection4j.isEmpty(cookies)) {
            return Optional.empty();
        }
        return cookies.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Checks if a specified cookie in the HttpServletRequest is expired.
     *
     * @param request The HttpServletRequest containing cookies.
     * @param name    The name of the cookie to be checked.
     * @return True if the cookie is expired or does not exist, false otherwise.
     */
    public static boolean isExpired(HttpServletRequest request, String name) {
        // Get the cookie with the specified name, if it exists
        Optional<Cookie> cookie = getCookieValidity(request, name);
        // If the cookie exists, check if its max age is greater than the current time in seconds
        if (cookie.isPresent()) {
            Duration now = Duration.between(LocalTime.NOON, LocalTime.MAX);
            return cookie.get().getMaxAge() > now.getSeconds();
        }
        return true;
    }
}
