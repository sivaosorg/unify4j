# Auth4j

`Auth4j` is a class utility designed to handle various types of authentication mechanisms. It supports Basic, Bearer, API
Key, OAuth2, and OAuth2 Client Credentials authentication types.

## Features

- Basic Authentication
- Bearer Token Authentication
- API Key Authentication
- OAuth2 Authentication
- OAuth2 Client Credentials Authentication

## Usage

### Basic Authentication

```java
Auth4j auth = new Auth4j.Builder()
        .type(AuthType.BASIC)
        .username("yourUsername")
        .password("yourPassword")
        .build();

WrapResponse<?> response = auth.verify();
if (response.isSuccess()) {
    Map<String, String> headers = auth.getHeaders();
    // Use headers in your HTTP request
}
```

### Bearer Token Authentication

```java
Auth4j auth = new Auth4j.Builder()
        .type(AuthType.BEARER)
        .token("yourBearerToken")
        .build();

WrapResponse<?> response = auth.verify();
if (response.isSuccess()) {
    Map<String, String> headers = auth.getHeaders();
    // Use headers in your HTTP request
}
```

### API Key Authentication

```java
Auth4j auth = new Auth4j.Builder()
        .type(AuthType.API_KEY)
        .headerNameApiKey("X-API-KEY")
        .apiKey("yourApiKey")
        .build();

WrapResponse<?> response = auth.verify();
if (response.isSuccess()) {
    Map<String, String> headers = auth.getHeaders();
    // Use headers in your HTTP request
}
```

### OAuth2 Authentication

```java
Auth4j auth = new Auth4j.Builder()
        .type(AuthType.OAUTH2)
        .accessToken("yourAccessToken")
        .build();

WrapResponse<?> response = auth.verify();
if (response.isSuccess()) {
    Map<String, String> headers = auth.getHeaders();
    // Use headers in your HTTP request
}
```

### OAuth2 Client Credentials Authentication

```java
AuthFactory authFactory = new AuthFactory() {
    @Override
    public String retrieveAccessToken(String clientId, String clientSecret) {
        // Implement your logic to retrieve the access token
        return "yourAccessToken";
    }
};

Auth4j auth = new Auth4j.Builder()
        .type(AuthType.OAUTH2_CLIENT_CREDENTIALS)
        .factory(authFactory)
        .clientId("yourClientId")
        .clientSecret("yourClientSecret")
        .build();

WrapResponse<?> response = auth.verify();
if (response.isSuccess()) {
    Map<String, String> headers = auth.getHeaders();
    // Use headers in your HTTP request
}
```

### Custom Authentication Logic

To implement custom logic for retrieving the access token in the OAuth2 Client Credentials flow, you can extend the AuthFactory and provide your implementation.

```java
public class CustomAuthFactory extends AuthFactory {
    @Override
    public String retrieveAccessToken(String clientId, String clientSecret) {
        // Custom logic to retrieve the access token
        return "yourAccessToken";
    }
}
```
