package org.unify4j.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.unify4j.common.Json4j;
import org.unify4j.model.builder.HttpStatusBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapResponse<T> implements Serializable {
    public WrapResponse() {
        super();
    }

    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("message")
    private String message;
    @JsonProperty("total")
    private int total;
    @JsonProperty("data")
    private T data;
    @JsonProperty("errors")
    private Object errors;
    @JsonProperty("headers")
    private HttpResponse headers;
    @JsonProperty("meta")
    private MetaResponse meta;
    @JsonProperty("pagination")
    private PaginationResponse pagination;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public HttpResponse getHeaders() {
        return headers;
    }

    public void setHeaders(HttpResponse headers) {
        this.headers = headers;
    }

    public MetaResponse getMeta() {
        return meta;
    }

    public void setMeta(MetaResponse meta) {
        this.meta = meta;
    }

    public PaginationResponse getPagination() {
        return pagination;
    }

    public void setPagination(PaginationResponse pagination) {
        this.pagination = pagination;
    }

    public boolean isError() {
        return this.errors != null;
    }

    public boolean isSuccess() {
        return this.statusCode >= HttpStatusBuilder.OK.getCode() && this.statusCode < HttpStatusBuilder.MULTIPLE_CHOICES.getCode();
    }

    @Override
    public String toString() {
        return String.format("Wrap response { status_code: %d, message: %s, total: %d, data: %s, errors: %s, headers: %s, meta: %s, pagination: %s }", statusCode, message, total, Json4j.toJson(data), Json4j.toJson(errors), Json4j.toJson(headers), Json4j.toJson(meta), Json4j.toJson(pagination));
    }
}
