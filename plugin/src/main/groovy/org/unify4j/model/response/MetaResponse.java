package org.unify4j.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.unify4j.common.Json4j;
import org.unify4j.common.Random4j;

import java.io.Serializable;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaResponse implements Serializable {
    public MetaResponse() {
        super();
        this.setApiVersion("v1.0.0");
        this.setRequestId(Random4j.nextUUID().toString());
        this.setRequestedTime(new Date());
        this.setLocale(Locale.getDefault().getCountry());
    }

    @JsonProperty("api_version")
    private String apiVersion;
    @JsonProperty("request_id")
    private String requestId;
    @JsonProperty("locale")
    private String locale;
    @JsonProperty("requested_time")
    private Date requestedTime;
    @JsonProperty("custom_fields")
    private Map<String, ?> customFields;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Date getRequestedTime() {
        return requestedTime;
    }

    public void setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
    }

    public Map<String, ?> getCustomFields() {
        return customFields;
    }

    public void setCustomFields(Map<String, ?> customFields) {
        this.customFields = customFields;
    }

    @Override
    public String toString() {
        return String.format("Meta response { api_version: %s, request_id: %s, locale: %s, requested_time: %s, custom_fields: %s}", apiVersion, requestId, locale, requestedTime.toString(), Json4j.toJson(customFields));
    }
}
