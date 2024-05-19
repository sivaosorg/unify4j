package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.common.Json4j;
import org.unify4j.model.request.CookieRequest;

import java.io.Serializable;
import java.time.Duration;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CookieBuilder implements Serializable {
    public CookieBuilder() {
        super();
    }

    private String name;
    private String value;
    private Duration expiredAt;

    public CookieBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public CookieBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public CookieBuilder withValue(Object value) {
        this.value = Json4j.toJson(value);
        return this;
    }

    public CookieBuilder withExpiredAt(Duration expiredAt) {
        this.expiredAt = expiredAt;
        return this;
    }

    public CookieRequest build() {
        return new CookieRequest(name, value, expiredAt);
    }
}