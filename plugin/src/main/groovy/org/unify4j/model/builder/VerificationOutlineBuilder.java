package org.unify4j.model.builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.model.response.VerificationOutlineResponse;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationOutlineBuilder implements Serializable {
    public VerificationOutlineBuilder() {
        super();
    }

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public VerificationOutlineBuilder setObject(String object) {
        this.object = object;
        return this;
    }

    public VerificationOutlineBuilder setField(String field) {
        this.field = field;
        return this;
    }

    public VerificationOutlineBuilder setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
        return this;
    }

    public VerificationOutlineBuilder setMessage(String message) {
        this.message = message;
        return this;
    }

    public VerificationOutlineResponse build() {
        VerificationOutlineResponse e = new VerificationOutlineResponse();
        e.setObject(this.object);
        e.setField(this.field);
        e.setRejectedValue(this.rejectedValue);
        e.setMessage(this.message);
        return e;
    }
}
