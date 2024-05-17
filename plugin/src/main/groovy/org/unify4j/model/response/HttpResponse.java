package org.unify4j.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HttpResponse implements Serializable {
    public HttpResponse() {
        super();
    }

    /**
     * Constructs a HeaderResponse with the specified parameters.
     *
     * @param code        the HTTP status code
     * @param text        the reason phrase associated with the status code
     * @param type        the category of the status code (e.g., "Informational", "Successful")
     * @param description an optional description or message
     */
    public HttpResponse(int code, String text, String type, String description) {
        super();
        this.code = code;
        this.text = text;
        this.type = type;
        this.description = description;
    }

    private int code;
    private String text;
    private String type;
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("HTTP response { Code: %d, Text: '%s', Type: '%s', Description: '%s' }", code, text, type, description);
    }
}
