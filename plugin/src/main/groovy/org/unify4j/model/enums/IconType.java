package org.unify4j.model.enums;

// @formatter:off
public enum IconType {

    SUCCESS("\uD83D\uDFE2"),
    ERROR("\uD83D\uDD34"),
    WARN("\uD83D\uDFE1"),
    DEBUG("\uD83D\uDD35"),
    TRACE("\uD83D\uDFE0"),
    BACKGROUND("\uD83D\uDFE3"),
    CHECKED("\uD83D\uDD35");

    private final String code;

    IconType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
// @formatter:on