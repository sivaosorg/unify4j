package org.unify4j.model.response;

import com.fasterxml.jackson.annotation.*;
import org.unify4j.common.Json4j;
import org.unify4j.model.builder.HttpStatusBuilder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WrapResponse<T> implements Serializable {
    public WrapResponse() {
        super();
    }

    @JsonAlias({"code", "http_code", "status_http_code"})
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
    @JsonProperty("path")
    private String path; // optional
    @JsonProperty("debug")
    private List<BaseOutlineResponse> debug; // optional

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

    @JsonIgnore
    public boolean isError() {
        return this.errors != null;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.statusCode >= HttpStatusBuilder.OK.getCode() && this.statusCode < HttpStatusBuilder.MULTIPLE_CHOICES.getCode();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<BaseOutlineResponse> getDebug() {
        return debug;
    }

    public void setDebug(List<BaseOutlineResponse> debug) {
        this.debug = debug;
    }

    @Override
    public String toString() {
        return String.format("Wrap response { status_code: %d, message: %s, total: %d, data: %s, errors: %s, headers: %s, meta: %s, pagination: %s, path: %s, debug: %s }",
                statusCode, message, total, Json4j.toJson(data),
                Json4j.toJson(errors), Json4j.toJson(headers),
                Json4j.toJson(meta), Json4j.toJson(pagination),
                path, Json4j.toJson(debug));
    }
}
