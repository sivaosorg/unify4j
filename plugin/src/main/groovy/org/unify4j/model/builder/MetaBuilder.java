package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.common.Collection4j;
import org.unify4j.common.Random4j;
import org.unify4j.model.response.MetaResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaBuilder {
    private String apiVersion = "v1.0.0";
    private String requestId = Random4j.nextUUIDString();
    private String locale = Locale.getDefault().getCountry();
    private Date requestedTime = new Date();
    private Map<String, Object> customFields;

    public MetaBuilder apiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }

    public MetaBuilder requestId(String requestId) {
        this.requestId = requestId;
        return this;
    }

    public MetaBuilder locale(String locale) {
        this.locale = locale;
        return this;
    }

    public MetaBuilder requestedTime(Date requestedTime) {
        this.requestedTime = requestedTime;
        return this;
    }

    public MetaBuilder customFields(Map<String, Object> customFields) {
        this.customFields = customFields;
        return this;
    }

    public MetaBuilder appendCustomField(String key, Object value) {
        if (Collection4j.isEmptyMap(this.customFields)) {
            this.customFields = new HashMap<>();
        }
        this.customFields.put(key, value);
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
