package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.model.response.VerificationResponse;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationBuilder implements Serializable {
    public VerificationBuilder() {
        super();
    }

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public VerificationBuilder setObject(String object) {
        this.object = object;
        return this;
    }

    public VerificationBuilder setField(String field) {
        this.field = field;
        return this;
    }

    public VerificationBuilder setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
        return this;
    }

    public VerificationBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public VerificationResponse build() {
        VerificationResponse e = new VerificationResponse();
        e.setObject(this.object);
        e.setField(this.field);
        e.setRejectedValue(this.rejectedValue);
        e.setMessage(this.message);
        return e;
    }
}
