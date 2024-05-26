package org.unify4j.model.builder;

import org.unify4j.common.Collection4j;
import org.unify4j.model.response.HttpResponse;

import java.util.Arrays;
import java.util.List;

/**
 * HttpStatusBuilder provides a collection of predefined {@link HttpResponse} objects
 * for various HTTP status codes. This class includes status codes across the entire
 * range of HTTP responses, including informational, successful, redirection,
 * client error, and server error responses.
 * <p>
 * Each constant is a {@link HttpResponse} object that encapsulates the HTTP status
 * code, its standard reason phrase, a category indicating the type of response,
 * and an optional description or message.
 */
public class HttpStatusBuilder {
    protected static List<HttpResponse> statuses;

    public static HttpResponse valueOf(int statusCode) {
        if (Collection4j.isEmpty(statuses)) {
            statuses = Arrays.asList(
                    HttpStatusBuilder.CONTINUE,
                    HttpStatusBuilder.SWITCHING_PROTOCOLS,
                    HttpStatusBuilder.PROCESSING,
                    HttpStatusBuilder.OK,
                    HttpStatusBuilder.CREATED,
                    HttpStatusBuilder.ACCEPTED,
                    HttpStatusBuilder.NON_AUTHORITATIVE_INFORMATION,
                    HttpStatusBuilder.NO_CONTENT,
                    HttpStatusBuilder.RESET_CONTENT,
                    HttpStatusBuilder.PARTIAL_CONTENT,
                    HttpStatusBuilder.MULTI_STATUS,
                    HttpStatusBuilder.ALREADY_REPORTED,
                    HttpStatusBuilder.IM_USED,
                    HttpStatusBuilder.MULTIPLE_CHOICES,
                    HttpStatusBuilder.MOVED_PERMANENTLY,
                    HttpStatusBuilder.FOUND,
                    HttpStatusBuilder.SEE_OTHER,
                    HttpStatusBuilder.NOT_MODIFIED,
                    HttpStatusBuilder.USE_PROXY,
                    HttpStatusBuilder.RESERVED,
                    HttpStatusBuilder.TEMPORARY_REDIRECT,
                    HttpStatusBuilder.PERMANENT_REDIRECT,
                    HttpStatusBuilder.BAD_REQUEST,
                    HttpStatusBuilder.UNAUTHORIZED,
                    HttpStatusBuilder.PAYMENT_REQUIRED,
                    HttpStatusBuilder.FORBIDDEN,
                    HttpStatusBuilder.NOT_FOUND,
                    HttpStatusBuilder.METHOD_NOT_ALLOWED,
                    HttpStatusBuilder.NOT_ACCEPTABLE,
                    HttpStatusBuilder.PROXY_AUTHENTICATION_REQUIRED,
                    HttpStatusBuilder.REQUEST_TIMEOUT,
                    HttpStatusBuilder.CONFLICT,
                    HttpStatusBuilder.GONE,
                    HttpStatusBuilder.LENGTH_REQUIRED,
                    HttpStatusBuilder.PRECONDITION_FAILED,
                    HttpStatusBuilder.REQUEST_ENTITY_TOO_LARGE,
                    HttpStatusBuilder.REQUEST_URI_TOO_LONG,
                    HttpStatusBuilder.UNSUPPORTED_MEDIA_TYPE,
                    HttpStatusBuilder.REQUESTED_RANGE_NOT_SATISFIABLE,
                    HttpStatusBuilder.EXPECTATION_FAILED,
                    HttpStatusBuilder.IM_A_TEAPOT,
                    HttpStatusBuilder.ENHANCE_YOUR_CALM,
                    HttpStatusBuilder.UN_PROCESSABLE_ENTITY,
                    HttpStatusBuilder.LOCKED,
                    HttpStatusBuilder.FAILED_DEPENDENCY,
                    HttpStatusBuilder.UNORDERED_COLLECTION,
                    HttpStatusBuilder.UPGRADE_REQUIRED,
                    HttpStatusBuilder.PRECONDITION_REQUIRED,
                    HttpStatusBuilder.TOO_MANY_REQUESTS,
                    HttpStatusBuilder.REQUEST_HEADER_FIELDS_TOO_LARGE,
                    HttpStatusBuilder.NO_RESPONSE,
                    HttpStatusBuilder.RETRY_WITH,
                    HttpStatusBuilder.BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS,
                    HttpStatusBuilder.UNAVAILABLE_FOR_LEGAL_REASONS,
                    HttpStatusBuilder.CLIENT_CLOSED_REQUEST,
                    HttpStatusBuilder.INTERNAL_SERVER_ERROR,
                    HttpStatusBuilder.NOT_IMPLEMENTED,
                    HttpStatusBuilder.BAD_GATEWAY,
                    HttpStatusBuilder.SERVICE_UNAVAILABLE,
                    HttpStatusBuilder.GATEWAY_TIMEOUT,
                    HttpStatusBuilder.HTTP_VERSION_NOT_SUPPORTED,
                    HttpStatusBuilder.VARIANT_ALSO_NEGOTIATES,
                    HttpStatusBuilder.INSUFFICIENT_STORAGE,
                    HttpStatusBuilder.LOOP_DETECTED,
                    HttpStatusBuilder.BANDWIDTH_LIMIT_EXCEEDED,
                    HttpStatusBuilder.NOT_EXTENDED,
                    HttpStatusBuilder.NETWORK_AUTHENTICATION_REQUIRED,
                    HttpStatusBuilder.NETWORK_READ_TIMEOUT_ERROR,
                    HttpStatusBuilder.NETWORK_CONNECT_TIMEOUT_ERROR
            );
        }
        return statuses.stream().filter(e -> e.getCode() == statusCode).findFirst().orElse(new HttpResponse(-1, "unknown", "unknown", "out of HTTP status version"));
    }

    // 1xx Informational responses
    public static final HttpResponse CONTINUE = new HttpResponse(100, "Continue", "Informational", "");
    public static final HttpResponse SWITCHING_PROTOCOLS = new HttpResponse(101, "Switching Protocols", "Informational", "");
    public static final HttpResponse PROCESSING = new HttpResponse(102, "Processing", "Informational", "");

    // 2xx Successful responses
    public static final HttpResponse OK = new HttpResponse(200, "OK", "Successful", "");
    public static final HttpResponse CREATED = new HttpResponse(201, "Created", "Successful", "");
    public static final HttpResponse ACCEPTED = new HttpResponse(202, "Accepted", "Successful", "");
    public static final HttpResponse NON_AUTHORITATIVE_INFORMATION = new HttpResponse(203, "Non-Authoritative Information", "Successful", "");
    public static final HttpResponse NO_CONTENT = new HttpResponse(204, "No Content", "Successful", "");
    public static final HttpResponse RESET_CONTENT = new HttpResponse(205, "Reset Content", "Successful", "");
    public static final HttpResponse PARTIAL_CONTENT = new HttpResponse(206, "Partial Content", "Successful", "");
    public static final HttpResponse MULTI_STATUS = new HttpResponse(207, "Multi-Status", "Successful", "");
    public static final HttpResponse ALREADY_REPORTED = new HttpResponse(208, "Already Reported", "Successful", "");
    public static final HttpResponse IM_USED = new HttpResponse(226, "IM Used", "Successful", "");

    // 3xx Redirection responses
    public static final HttpResponse MULTIPLE_CHOICES = new HttpResponse(300, "Multiple Choices", "Redirection", "");
    public static final HttpResponse MOVED_PERMANENTLY = new HttpResponse(301, "Moved Permanently", "Redirection", "");
    public static final HttpResponse FOUND = new HttpResponse(302, "Found", "Redirection", "");
    public static final HttpResponse SEE_OTHER = new HttpResponse(303, "See Other", "Redirection", "");
    public static final HttpResponse NOT_MODIFIED = new HttpResponse(304, "Not Modified", "Redirection", "");
    public static final HttpResponse USE_PROXY = new HttpResponse(305, "Use Proxy", "Redirection", "");
    public static final HttpResponse RESERVED = new HttpResponse(306, "Reserved", "Redirection", "");
    public static final HttpResponse TEMPORARY_REDIRECT = new HttpResponse(307, "Temporary Redirect", "Redirection", "");
    public static final HttpResponse PERMANENT_REDIRECT = new HttpResponse(308, "Permanent Redirect", "Redirection", "");

    // 4xx Client error responses
    public static final HttpResponse BAD_REQUEST = new HttpResponse(400, "Bad Request", "Client Error", "");
    public static final HttpResponse UNAUTHORIZED = new HttpResponse(401, "Unauthorized", "Client Error", "");
    public static final HttpResponse PAYMENT_REQUIRED = new HttpResponse(402, "Payment Required", "Client Error", "");
    public static final HttpResponse FORBIDDEN = new HttpResponse(403, "Forbidden", "Client Error", "");
    public static final HttpResponse NOT_FOUND = new HttpResponse(404, "Not Found", "Client Error", "");
    public static final HttpResponse METHOD_NOT_ALLOWED = new HttpResponse(405, "Method Not Allowed", "Client Error", "");
    public static final HttpResponse NOT_ACCEPTABLE = new HttpResponse(406, "Not Acceptable", "Client Error", "");
    public static final HttpResponse PROXY_AUTHENTICATION_REQUIRED = new HttpResponse(407, "Proxy Authentication Required", "Client Error", "");
    public static final HttpResponse REQUEST_TIMEOUT = new HttpResponse(408, "Request Timeout", "Client Error", "");
    public static final HttpResponse CONFLICT = new HttpResponse(409, "Conflict", "Client Error", "");
    public static final HttpResponse GONE = new HttpResponse(410, "Gone", "Client Error", "");
    public static final HttpResponse LENGTH_REQUIRED = new HttpResponse(411, "Length Required", "Client Error", "");
    public static final HttpResponse PRECONDITION_FAILED = new HttpResponse(412, "Precondition Failed", "Client Error", "");
    public static final HttpResponse REQUEST_ENTITY_TOO_LARGE = new HttpResponse(413, "Request Entity Too Large", "Client Error", "");
    public static final HttpResponse REQUEST_URI_TOO_LONG = new HttpResponse(414, "Request-URI Too Long", "Client Error", "");
    public static final HttpResponse UNSUPPORTED_MEDIA_TYPE = new HttpResponse(415, "Unsupported Media Type", "Client Error", "");
    public static final HttpResponse REQUESTED_RANGE_NOT_SATISFIABLE = new HttpResponse(416, "Requested Range Not Satisfiable", "Client Error", "");
    public static final HttpResponse EXPECTATION_FAILED = new HttpResponse(417, "Expectation Failed", "Client Error", "");
    public static final HttpResponse IM_A_TEAPOT = new HttpResponse(418, "I’m a teapot", "Client Error", "");
    public static final HttpResponse ENHANCE_YOUR_CALM = new HttpResponse(420, "Enhance Your Calm", "Client Error", "");
    public static final HttpResponse UN_PROCESSABLE_ENTITY = new HttpResponse(422, "Unprocessable Entity", "Client Error", "");
    public static final HttpResponse LOCKED = new HttpResponse(423, "Locked", "Client Error", "");
    public static final HttpResponse FAILED_DEPENDENCY = new HttpResponse(424, "Failed Dependency", "Client Error", "");
    public static final HttpResponse UNORDERED_COLLECTION = new HttpResponse(425, "Unordered Collection", "Client Error", "");
    public static final HttpResponse UPGRADE_REQUIRED = new HttpResponse(426, "Upgrade Required", "Client Error", "");
    public static final HttpResponse PRECONDITION_REQUIRED = new HttpResponse(428, "Precondition Required", "Client Error", "");
    public static final HttpResponse TOO_MANY_REQUESTS = new HttpResponse(429, "Too Many Requests", "Client Error", "");
    public static final HttpResponse REQUEST_HEADER_FIELDS_TOO_LARGE = new HttpResponse(431, "Request Header Fields Too Large", "Client Error", "");
    public static final HttpResponse NO_RESPONSE = new HttpResponse(444, "No Response", "Client Error", "");
    public static final HttpResponse RETRY_WITH = new HttpResponse(449, "Retry With", "Client Error", "");
    public static final HttpResponse BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS = new HttpResponse(450, "Blocked by Windows Parental Controls", "Client Error", "");
    public static final HttpResponse UNAVAILABLE_FOR_LEGAL_REASONS = new HttpResponse(451, "Unavailable For Legal Reasons", "Client Error", "");
    public static final HttpResponse CLIENT_CLOSED_REQUEST = new HttpResponse(499, "Client Closed Request", "Client Error", "");

    // 5xx Server error responses
    public static final HttpResponse INTERNAL_SERVER_ERROR = new HttpResponse(500, "Internal Server Error", "Server Error", "");
    public static final HttpResponse NOT_IMPLEMENTED = new HttpResponse(501, "Not Implemented", "Server Error", "");
    public static final HttpResponse BAD_GATEWAY = new HttpResponse(502, "Bad Gateway", "Server Error", "");
    public static final HttpResponse SERVICE_UNAVAILABLE = new HttpResponse(503, "Service Unavailable", "Server Error", "");
    public static final HttpResponse GATEWAY_TIMEOUT = new HttpResponse(504, "Gateway Timeout", "Server Error", "");
    public static final HttpResponse HTTP_VERSION_NOT_SUPPORTED = new HttpResponse(505, "HTTP Version Not Supported", "Server Error", "");
    public static final HttpResponse VARIANT_ALSO_NEGOTIATES = new HttpResponse(506, "Variant Also Negotiates", "Server Error", "");
    public static final HttpResponse INSUFFICIENT_STORAGE = new HttpResponse(507, "Insufficient Storage", "Server Error", "");
    public static final HttpResponse LOOP_DETECTED = new HttpResponse(508, "Loop Detected", "Server Error", "");
    public static final HttpResponse BANDWIDTH_LIMIT_EXCEEDED = new HttpResponse(509, "Bandwidth Limit Exceeded", "Server Error", "");
    public static final HttpResponse NOT_EXTENDED = new HttpResponse(510, "Not Extended", "Server Error", "");
    public static final HttpResponse NETWORK_AUTHENTICATION_REQUIRED = new HttpResponse(511, "Network Authentication Required", "Server Error", "");
    public static final HttpResponse NETWORK_READ_TIMEOUT_ERROR = new HttpResponse(598, "Network Read Timeout Error", "Server Error", "");
    public static final HttpResponse NETWORK_CONNECT_TIMEOUT_ERROR = new HttpResponse(599, "Network Connect Timeout Error", "Server Error", "");
}
