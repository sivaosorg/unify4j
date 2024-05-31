package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.common.Object4j;
import org.unify4j.model.response.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"UnusedReturnValue"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapBuilder<T> implements Serializable {
    public WrapBuilder() {
        super();
    }

    private int statusCode;
    private String message;
    private int total;
    private T data;
    private Object errors;
    private HttpResponse headers;
    private MetaResponse meta;
    private PaginationResponse pagination;
    private String path;
    private List<BaseOutlineResponse> debug;

    public WrapBuilder<T> setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        this.headers = HttpStatusBuilder.valueOf(statusCode);
        return this;
    }

    public WrapBuilder<T> setStatusCode(HttpResponse value) {
        this.statusCode = value.getCode();
        this.headers = value;
        return this;
    }

    public WrapBuilder<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public WrapBuilder<T> setTotal(int total) {
        this.total = total;
        return this;
    }

    public WrapBuilder<T> setData(T data) {
        this.data = data;
        if (data instanceof List<?>) {
            this.setTotal(((List<?>) data).size());
        }
        return this;
    }

    public WrapBuilder<T> setErrors(Object errors) {
        this.errors = errors;
        return this;
    }

    public WrapBuilder<T> setErrors(Throwable errors) {
        return this.setErrors(errors.getMessage());
    }

    public WrapBuilder<T> setHeaders(HttpResponse headers) {
        this.headers = headers;
        return this;
    }

    public WrapBuilder<T> setMeta(MetaResponse meta) {
        this.meta = meta;
        return this;
    }

    public WrapBuilder<T> setMeta(MetaBuilder builder) {
        this.meta = builder.build();
        return this;
    }

    public WrapBuilder<T> setPagination(PaginationResponse pagination) {
        this.pagination = pagination;
        return this;
    }

    public WrapBuilder<T> setPagination(PaginationBuilder builder) {
        this.pagination = builder.build();
        return this;
    }

    public WrapBuilder<T> setPath(String path) {
        this.path = path;
        return this;
    }

    public WrapBuilder<T> setDebug(List<BaseOutlineResponse> debug) {
        this.debug = debug;
        return this;
    }

    public WrapBuilder<T> appendDebug(BaseOutlineResponse... values) {
        if (Object4j.isEmpty(values)) {
            return this;
        }
        if (this.debug == null) {
            this.debug = new ArrayList<>();
        }
        this.debug.addAll(Arrays.asList(values));
        return this;
    }

    public WrapBuilder<T> appendDebug(VerificationOutlineBuilder builder) {
        return this.appendDebug(builder.build());
    }

    public WrapResponse<T> build() {
        WrapResponse<T> e = new WrapResponse<>();
        e.setStatusCode(statusCode);
        e.setMessage(message);
        e.setTotal(total);
        e.setData(data);
        e.setErrors(errors);
        e.setHeaders(headers);
        e.setMeta(meta);
        e.setPagination(pagination);
        e.setPath(path);
        e.setDebug(debug);
        return e;
    }
}
