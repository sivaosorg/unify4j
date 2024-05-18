package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.model.response.HttpResponse;
import org.unify4j.model.response.MetaResponse;
import org.unify4j.model.response.PaginationResponse;
import org.unify4j.model.response.WrapResponse;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HttpWrapBuilder<T> implements Serializable {
    protected final WrapBuilder<T> builder;

    public HttpWrapBuilder() {
        super();
        this.builder = new WrapBuilder<>();
        this.builder.setMeta(new MetaResponse());
    }

    public WrapBuilder<T> builder() {
        return builder;
    }

    public HttpWrapBuilder<T> with() {
        return this;
    }

    public WrapResponse<T> build() {
        return builder.build();
    }

    public HttpWrapBuilder<T> apiVersion(String version) {
        this.build().getMeta().setApiVersion(version);
        return this;
    }

    public HttpWrapBuilder<T> locale(String locale) {
        this.build().getMeta().setLocale(locale);
        return this;
    }

    public HttpWrapBuilder<T> locale(Locale locale) {
        this.build().getMeta().setLocale(locale.getCountry());
        return this;
    }

    public HttpWrapBuilder<T> requestId(String requestId) {
        this.build().getMeta().setRequestId(requestId);
        return this;
    }

    public HttpWrapBuilder<T> customFields(Map<String, ?> fields) {
        this.build().getMeta().setCustomFields(fields);
        return this;
    }

    public HttpWrapBuilder<T> statusCode(HttpResponse status) {
        this.builder().setStatusCode(status.getCode());
        this.builder().setHeaders(status);
        return this;
    }

    public HttpWrapBuilder<T> statusCode(int status) {
        return this.statusCode(HttpStatusBuilder.valueOf(status));
    }

    public HttpWrapBuilder<T> body(T data) {
        this.builder().setData(data);
        return this;
    }

    public HttpWrapBuilder<T> message(String message) {
        this.builder().setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> total(int total) {
        this.builder().setTotal(total);
        return this;
    }

    public HttpWrapBuilder<T> header(HttpResponse header) {
        this.builder().setHeaders(header);
        return this;
    }

    public HttpWrapBuilder<T> errors(Object error) {
        this.builder().setErrors(error);
        return this;
    }

    public HttpWrapBuilder<T> errors(Throwable error) {
        return this.errors(error.getMessage());
    }

    public HttpWrapBuilder<T> pagination(PaginationResponse pagination) {
        this.builder().setPagination(pagination);
        return this;
    }

    public HttpWrapBuilder<T> pagination(PaginationBuilder builder) {
        this.builder().setPagination(builder.build());
        return this;
    }

    public HttpWrapBuilder<T> ok(T data) {
        builder.setStatusCode(HttpStatusBuilder.OK);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> created(T data) {
        builder.setStatusCode(HttpStatusBuilder.CREATED);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> accepted(T data) {
        builder.setStatusCode(HttpStatusBuilder.ACCEPTED);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> nonAuthoritativeInfo(T data) {
        builder.setStatusCode(HttpStatusBuilder.NON_AUTHORITATIVE_INFORMATION);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> partialContent(T data) {
        builder.setStatusCode(HttpStatusBuilder.PARTIAL_CONTENT);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> multiStatus(T data) {
        builder.setStatusCode(HttpStatusBuilder.MULTI_STATUS);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> alreadyReported(T data) {
        builder.setStatusCode(HttpStatusBuilder.ALREADY_REPORTED);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> imUsed(T data) {
        builder.setStatusCode(HttpStatusBuilder.IM_USED);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> multipleChoices(T data) {
        builder.setStatusCode(HttpStatusBuilder.MULTIPLE_CHOICES);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> movedPermanently(T data) {
        builder.setStatusCode(HttpStatusBuilder.MOVED_PERMANENTLY);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> found(T data) {
        builder.setStatusCode(HttpStatusBuilder.FOUND);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> seeOther(T data) {
        builder.setStatusCode(HttpStatusBuilder.SEE_OTHER);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> temporaryRedirect(T data) {
        builder.setStatusCode(HttpStatusBuilder.TEMPORARY_REDIRECT);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> permanentRedirect(T data) {
        builder.setStatusCode(HttpStatusBuilder.PERMANENT_REDIRECT);
        builder.setData(data);
        return this;
    }

    public HttpWrapBuilder<T> unauthorized(String message) {
        builder.setStatusCode(HttpStatusBuilder.UNAUTHORIZED);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> forbidden(String message) {
        builder.setStatusCode(HttpStatusBuilder.FORBIDDEN);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> badRequest(String message) {
        builder.setStatusCode(HttpStatusBuilder.BAD_REQUEST);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> notFound(String message) {
        builder.setStatusCode(HttpStatusBuilder.NOT_FOUND);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> requestTimeout(String message) {
        builder.setStatusCode(HttpStatusBuilder.REQUEST_TIMEOUT);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> locked(String message) {
        builder.setStatusCode(HttpStatusBuilder.LOCKED);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> tooManyRequest(String message) {
        builder.setStatusCode(HttpStatusBuilder.TOO_MANY_REQUESTS);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> requestHeaderFieldsTooLarge(String message) {
        builder.setStatusCode(HttpStatusBuilder.REQUEST_HEADER_FIELDS_TOO_LARGE);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> retryWith(String message) {
        builder.setStatusCode(HttpStatusBuilder.RETRY_WITH);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> internalServerError(String message) {
        builder.setStatusCode(HttpStatusBuilder.INTERNAL_SERVER_ERROR);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> notImplemented(String message) {
        builder.setStatusCode(HttpStatusBuilder.NOT_IMPLEMENTED);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> badGateway(String message) {
        builder.setStatusCode(HttpStatusBuilder.BAD_GATEWAY);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> serviceUnavailable(String message) {
        builder.setStatusCode(HttpStatusBuilder.SERVICE_UNAVAILABLE);
        builder.setMessage(message);
        return this;
    }

    public HttpWrapBuilder<T> bandwidthLimitExceeded(String message) {
        builder.setStatusCode(HttpStatusBuilder.BANDWIDTH_LIMIT_EXCEEDED);
        builder.setMessage(message);
        return this;
    }
}
