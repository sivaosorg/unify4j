package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.common.Random4j;
import org.unify4j.model.response.MetaResponse;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaBuilder {
    private String apiVersion = "v1.0.0";
    private String requestId = Random4j.nextUUIDString();
    private String locale = Locale.getDefault().getCountry();
    private Date requestedTime = new Date();
    private Map<String, ?> customFields;

    public MetaBuilder setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public MetaBuilder setRequestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public MetaBuilder setLocale(String locale) {
        this.locale = locale;
        return this;
    }

    public MetaBuilder setRequestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
        return this;
    }

    public MetaBuilder setCustomFields(Map<String, ?> customFields) {
        this.customFields = customFields;
        return this;
    }

    public MetaResponse build() {
        MetaResponse e = new MetaResponse();
        e.setApiVersion(apiVersion);
        e.setRequestId(requestId);
        e.setLocale(locale);
        e.setRequestedTime(requestedTime);
        e.setCustomFields(customFields);
        return e;
    }
}
