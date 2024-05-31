package org.unify4j.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.unify4j.common.Json4j;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerificationOutlineResponse extends BaseOutlineResponse {
    public VerificationOutlineResponse() {
        super();
    }

    public VerificationOutlineResponse(String object, String message) {
        super();
        this.object = object;
        this.message = message;
    }

    public VerificationOutlineResponse(String object, String field, Object rejectedValue, String message) {
        super();
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public void setRejectedValue(Object rejectedValue) {
        this.rejectedValue = rejectedValue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static String getForms(VerificationOutlineResponse verification) {
        return String.format("%s: %s", verification.getField(), verification.getMessage());
    }

    @Override
    public String toString() {
        return String.format("Verification outline response { object: %s, field: %s, rejected_value: %s, message: %s }",
                object, field, Json4j.toJson(rejectedValue), message);
    }
}
