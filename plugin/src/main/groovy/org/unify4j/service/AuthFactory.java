package org.unify4j.service;

public interface AuthFactory {

    String retrieveAccessToken(String clientId, String clientSecret);
}
