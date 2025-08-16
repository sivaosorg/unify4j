package org.unify4j.model.request;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.unify4j.common.Json4j;
import org.unify4j.common.StringBuilder4j;
import org.unify4j.common.Time4j;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IDecisionRequest implements Serializable {
    public IDecisionRequest() {
        super();
    }

    public IDecisionRequest(Object value) {
        this.value = value;
        this.enabled = true;
    }

    public IDecisionRequest(boolean enabled, Object value) {
        this.enabled = enabled;
        this.value = value;
    }

    @JsonProperty("enabled")
    private boolean enabled = false;
    @JsonProperty("value")
    private Object value;
    @JsonAlias({"label", "name"})
    private String label;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return StringBuilder4j.create()
                .append("Submission")
                .appendColon()
                .appendSpace()
                .appendLBrace()
                .appendSpace()
                .appendIf(label != null, "label: ")
                .appendIf(label != null, label)
                .appendIf(label != null, ", ")
                .append("enabled: ")
                .append(enabled)
                .appendComma()
                .appendSpace()
                .append("value: ")
                .append(Json4j.toJson(value))
                .appendSpace()
                .appendRBrace()
                .build();
    }
}
