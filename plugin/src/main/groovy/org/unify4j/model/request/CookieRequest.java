package org.unify4j.model.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.Duration;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CookieRequest implements Serializable {
    public CookieRequest() {
        super();
    }

    public CookieRequest(String name, String value, Duration expiredAt) {
        this.name = name;
        this.value = value;
        this.expiredAt = expiredAt;
    }

    private String name;
    private String value;
    @JsonProperty("expired_at")
    @JsonAlias({"expired_at", "expiredAt"})
    private Duration expiredAt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Duration getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Duration expiredAt) {
        this.expiredAt = expiredAt;
    }

    @Override
    public String toString() {
        return String.format("Cookie { name: '%s', value: '%s', expired_at: %s }", name, value, expiredAt);
    }
}
