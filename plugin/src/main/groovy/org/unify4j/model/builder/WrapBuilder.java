package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.model.response.HttpResponse;
import org.unify4j.model.response.MetaResponse;
import org.unify4j.model.response.PaginationResponse;
import org.unify4j.model.response.WrapResponse;

import java.io.Serializable;
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

    public WrapBuilder<T> setStatusCode(int statusCode) {
        this.statusCode = statusCode;
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

    public WrapBuilder<T> setHeaders(HttpResponse headers) {
        this.headers = headers;
        return this;
    }

    public WrapBuilder<T> setMeta(MetaResponse meta) {
        this.meta = meta;
        return this;
    }

    public WrapBuilder<T> setPagination(PaginationResponse pagination) {
        this.pagination = pagination;
        return this;
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
        return e;
    }
}
