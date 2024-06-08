package org.unify4j.common;

import org.unify4j.model.builder.HttpWrapBuilder;
import org.unify4j.model.c.HttpHeaders;
import org.unify4j.model.enums.AuthType;
import org.unify4j.model.onlyrd.AbstractAuthClass;
import org.unify4j.model.response.WrapResponse;

import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Auth4j extends AbstractAuthClass {
    protected Auth4j(Builder builder) {
        super(builder);
    }

    /**
     * @return a WrapResponse<?> object containing the response from the Auth manager API, class {@link WrapResponse}
     */
    @Override
    public WrapResponse<?> verify() {
        if (this.type == null) {
            return new HttpWrapBuilder<>().badRequest("Auth Type is required").build();
        }
        switch (this.type) {
            case BASIC:
                if (String4j.isEmpty(this.username)) {
                    return new HttpWrapBuilder<>().badRequest("Username is required").build();
                }
                if (String4j.isEmpty(this.password)) {
                    return new HttpWrapBuilder<>().badRequest("Password is required").build();
                }
                break;
            case BEARER:
                if (String4j.isEmpty(this.token)) {
                    return new HttpWrapBuilder<>().badRequest("Token is required").build();
                }
                break;
            case API_KEY:
                if (String4j.isEmpty(this.headerNameApiKey)) {
                    return new HttpWrapBuilder<>().badRequest("Header name API-KEY is required").build();
                }
                if (String4j.isEmpty(this.apiKey)) {
                    return new HttpWrapBuilder<>().badRequest("API-KEY is required").build();
                }
                break;
            case OAUTH2:
                if (String4j.isEmpty(this.accessToken)) {
                    return new HttpWrapBuilder<>().badRequest("Access-Token is required").build();
                }
                break;
            case OAUTH2_CLIENT_CREDENTIALS:
                if (this.factory == null) {
                    return new HttpWrapBuilder<>().badRequest("AuthFactory is required for OAUTH2_CLIENT_CREDENTIALS").build();
                }
                if (String4j.isEmpty(this.clientId)) {
                    return new HttpWrapBuilder<>().badRequest("Client-Id is required").build();
                }
                if (String4j.isEmpty(this.clientSecret)) {
                    return new HttpWrapBuilder<>().badRequest("Client-Secret is required").build();
                }
                break;
        }
        return new HttpWrapBuilder<>().ok(null).build();
    }

    /**
     * @return map header request, class {@link Map}
     */
    @Override
    public Map<String, String> getHeaders() {
        WrapResponse<?> verified = this.verify();
        if (!verified.isSuccess()) {
            return Collections.emptyMap();
        }
        Map<String, String> header = new HashMap<>();
        switch (this.type) {
            case BASIC:
                String authValue = Base64.getEncoder().encodeToString(String.format("%s:%s", this.username, this.password).getBytes());
                header.put(HttpHeaders.AUTHORIZATION, String.format("%s %s", String4j.capitalizeEachWords(AuthType.BASIC.name()), authValue));
                break;
            case BEARER:
                header.put(HttpHeaders.AUTHORIZATION, String.format("%s %s", String4j.capitalizeEachWords(AuthType.BEARER.name()), this.token));
                break;
            case API_KEY:
                header.put(this.headerNameApiKey, this.apiKey);
                break;
            case OAUTH2:
                header.put(HttpHeaders.AUTHORIZATION, String.format("%s %s", String4j.capitalizeEachWords(AuthType.BEARER.name()), this.accessToken));
                break;
            case OAUTH2_CLIENT_CREDENTIALS:
                this.accessToken = this.factory.retrieveAccessToken(this.clientId, this.clientSecret);
                header.put(HttpHeaders.AUTHORIZATION, String.format("%s %s", String4j.capitalizeEachWords(AuthType.BEARER.name()), this.accessToken));
                break;
            default:
                throw new IllegalArgumentException(String.format("Unsupported Auth Type: %s", this.type.name()));
        }
        return header;
    }

    /**
     * @return the string includes information
     */
    @Override
    public String toString() {
        return String.format("Auth4j { type: %s, username: %s, password: %s, token: %s, api_key: %s, header_name_api_key: %s, client_id: %s, client_secret: %s, access_token: %s, refresh_token: %s }",
                this.type, this.username, String4j.isEmpty(this.password) ? "" : "***", this.token, this.apiKey, this.headerNameApiKey, this.clientId, this.clientSecret, this.accessToken, this.refreshToken);
    }

    public static class Builder extends AbstractAuthClass.Builder<Builder> {
        /**
         * @return new instance Auth, class {@link Auth4j}
         */
        @Override
        public Auth4j build() {
            return new Auth4j(this);
        }

        /**
         * @return the current self, class {@link Builder}
         */
        @Override
        protected Builder self() {
            return this;
        }
    }
}
