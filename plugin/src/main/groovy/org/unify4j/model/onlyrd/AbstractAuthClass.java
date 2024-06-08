package org.unify4j.model.onlyrd;

import org.unify4j.model.enums.AuthType;
import org.unify4j.model.response.WrapResponse;
import org.unify4j.service.AuthFactory;

import java.util.Map;

public abstract class AbstractAuthClass {

    protected AuthType type;
    protected AuthFactory factory;
    protected String username;
    protected String password;
    protected String token;
    protected String apiKey;
    protected String headerNameApiKey;
    protected String clientId;
    protected String clientSecret;
    protected String accessToken;
    protected String refreshToken;

    public AuthType getType() {
        return this.type;
    }

    public abstract WrapResponse<?> verify();

    public abstract Map<String, String> getHeaders();

    public abstract String toString();

    protected AbstractAuthClass(Builder<?> builder) {
        this.type = builder.type;
        this.factory = builder.factory;
        this.username = builder.username;
        this.password = builder.password;
        this.token = builder.token;
        this.apiKey = builder.apiKey;
        this.headerNameApiKey = builder.headerNameApiKey;
        this.clientId = builder.clientId;
        this.clientSecret = builder.clientSecret;
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
    }

    public static abstract class Builder<T> {
        private AuthType type;
        private AuthFactory factory;
        private String username;
        private String password;
        private String token;
        private String apiKey;
        private String headerNameApiKey;
        private String clientId;
        private String clientSecret;
        private String accessToken;
        private String refreshToken;

        public abstract AbstractAuthClass build();

        protected abstract T self();

        public T type(AuthType type) {
            this.type = type;
            return this.self();
        }

        public T factory(AuthFactory factory) {
            this.factory = factory;
            return this.self();
        }

        public T username(String username) {
            this.username = username;
            return this.self();
        }

        public T password(String password) {
            this.password = password;
            return this.self();
        }

        public T token(String token) {
            this.token = token;
            return this.self();
        }

        public T apiKey(String apiKey) {
            this.apiKey = apiKey;
            return this.self();
        }

        public T headerNameApiKey(String value) {
            this.headerNameApiKey = value;
            return this.self();
        }

        public T clientId(String clientId) {
            this.clientId = clientId;
            return this.self();
        }

        public T clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this.self();
        }

        public T accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this.self();
        }

        public T refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this.self();
        }
    }
}
