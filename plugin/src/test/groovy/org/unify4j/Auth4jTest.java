package org.unify4j;

import org.junit.Before;
import org.junit.Test;
import org.unify4j.common.Auth4j;
import org.unify4j.model.enums.AuthType;
import org.unify4j.model.response.WrapResponse;
import org.unify4j.service.AuthFactory;

import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Auth4jTest {
    private Auth4j auth4j;
    private AuthFactory mockFactory;

    @Before
    public void setUp() {
        mockFactory = new AuthFactory() {
            @Override
            public String retrieveAccessToken(String clientId, String clientSecret) {
                return "validAccessToken";
            }
        };
    }

    @Test
    public void testBasicAuthWithValidCredentials() {
        auth4j = new Auth4j.Builder().type(AuthType.BASIC).username("user").password("pass").build();

        WrapResponse<?> response = auth4j.verify();
        assertTrue(response.isSuccess());

        Map<String, String> headers = auth4j.getHeaders();
        assertTrue(headers.containsKey("Authorization"));
    }

    @Test
    public void testBasicAuthWithMissingUsername() {
        auth4j = new Auth4j.Builder().type(AuthType.BASIC).password("pass").build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }

    @Test
    public void testBasicAuthWithMissingPassword() {
        auth4j = new Auth4j.Builder().type(AuthType.BASIC).username("user").build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }

    @Test
    public void testBearerAuthWithValidToken() {
        auth4j = new Auth4j.Builder().type(AuthType.BEARER).token("validToken").build();

        WrapResponse<?> response = auth4j.verify();
        assertTrue(response.isSuccess());

        Map<String, String> headers = auth4j.getHeaders();
        assertTrue(headers.containsKey("Authorization"));
    }

    @Test
    public void testBearerAuthWithMissingToken() {
        auth4j = new Auth4j.Builder().type(AuthType.BEARER).build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }

    @Test
    public void testApiKeyAuthWithValidApiKey() {
        auth4j = new Auth4j.Builder().type(AuthType.API_KEY).headerNameApiKey("X-API-KEY").apiKey("validApiKey").build();

        WrapResponse<?> response = auth4j.verify();
        assertTrue(response.isSuccess());

        Map<String, String> headers = auth4j.getHeaders();
        assertTrue(headers.containsKey("X-API-KEY"));
    }

    @Test
    public void testApiKeyAuthWithMissingHeaderName() {
        auth4j = new Auth4j.Builder().type(AuthType.API_KEY).apiKey("validApiKey").build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }

    @Test
    public void testApiKeyAuthWithMissingApiKey() {
        auth4j = new Auth4j.Builder().type(AuthType.API_KEY).headerNameApiKey("X-API-KEY").build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }

    @Test
    public void testOAuth2AuthWithValidAccessToken() {
        auth4j = new Auth4j.Builder().type(AuthType.OAUTH2).accessToken("validAccessToken").build();

        WrapResponse<?> response = auth4j.verify();
        assertTrue(response.isSuccess());

        Map<String, String> headers = auth4j.getHeaders();
        assertTrue(headers.containsKey("Authorization"));
    }

    @Test
    public void testOAuth2AuthWithMissingAccessToken() {
        auth4j = new Auth4j.Builder().type(AuthType.OAUTH2).build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }

    @Test
    public void testOAuth2ClientCredentialsAuthWithValidCredentials() {
        auth4j = new Auth4j.Builder().type(AuthType.OAUTH2_CLIENT_CREDENTIALS).factory(mockFactory).clientId("clientId").clientSecret("clientSecret").build();

        WrapResponse<?> response = auth4j.verify();
        assertTrue(response.isSuccess());

        Map<String, String> headers = auth4j.getHeaders();
        assertTrue(headers.containsKey("Authorization"));
    }

    @Test
    public void testOAuth2ClientCredentialsAuthWithMissingFactory() {
        auth4j = new Auth4j.Builder().type(AuthType.OAUTH2_CLIENT_CREDENTIALS).clientId("clientId").clientSecret("clientSecret").build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }

    @Test
    public void testOAuth2ClientCredentialsAuthWithMissingClientId() {
        auth4j = new Auth4j.Builder().type(AuthType.OAUTH2_CLIENT_CREDENTIALS).factory(mockFactory).clientSecret("clientSecret").build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }

    @Test
    public void testOAuth2ClientCredentialsAuthWithMissingClientSecret() {
        auth4j = new Auth4j.Builder().type(AuthType.OAUTH2_CLIENT_CREDENTIALS).factory(mockFactory).clientId("clientId").build();

        WrapResponse<?> response = auth4j.verify();
        assertFalse(response.isSuccess());
    }
}
