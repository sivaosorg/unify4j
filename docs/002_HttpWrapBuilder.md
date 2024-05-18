# HttpWrapBuilder

The `HttpWrapBuilder` class is a utility for constructing HTTP response wrappers in a standardized way. It simplifies
the process of building complex HTTP responses by providing a fluent API for setting various properties such as status
codes, headers, meta information, and response bodies.

## Features

- `Fluent API`: Chainable methods for easy and readable response construction.
- `Status Codes`: Methods for setting standard HTTP status codes.
- `Custom Fields`: Add custom fields to the response metadata.
- `Localization`: Set locale information in the response metadata.
- `Error Handling`: Include error information in the response.
- `Pagination`: Support for pagination responses.

## Installation

Include the necessary dependencies in your project to use `HttpWrapBuilder`.

## Usage

### Basic example

```java
package org.unify4j;

import org.unify4j.model.builder.HttpWrapBuilder;
import org.unify4j.model.response.WrapResponse;

public class Main {

    public static void main(String[] args) {
        HttpWrapBuilder<String> builder = new HttpWrapBuilder<>();
        WrapResponse<String> response = builder
                .ok("Data retrieved successfully")
                .message("Operation completed successfully")
                .build();
        System.out.println(response);
    }
}
```

### Setting Metadata

```java
package org.unify4j;

import org.unify4j.model.builder.HttpWrapBuilder;
import org.unify4j.model.response.WrapResponse;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        HttpWrapBuilder<String> builder = new HttpWrapBuilder<>();
        WrapResponse<String> response = builder
                .apiVersion("1.0")
                .locale("en-US")
                .requestId("12345")
                .customFields(Map.of("customKey", "customValue"))
                .ok("Data retrieved successfully")
                .build();
        System.out.println(response);
    }
}
```

### Handling Errors

```java
HttpWrapBuilder<String> builder = new HttpWrapBuilder<>();
WrapResponse<String> response = builder
        .badRequest("Invalid request data")
        .errors("Detailed error message")
        .build();
```

### Pagination

```java
package org.unify4j;

import org.unify4j.model.builder.HttpWrapBuilder;
import org.unify4j.model.builder.PaginationBuilder;
import org.unify4j.model.response.PaginationResponse;
import org.unify4j.model.response.WrapResponse;

public class Main {

    public static void main(String[] args) {
        PaginationResponse pagination = new PaginationBuilder()
                .setPage(1)
                .setTotalPages(5)
                .build();
        HttpWrapBuilder<String> builder = new HttpWrapBuilder<>();
        WrapResponse<String> response = builder
                .pagination(pagination)
                .ok("Paginated data")
                .build();
        System.out.println(response);
    }
}
```

## API Reference

### Constructor

- `HttpWrapBuilder<T>()`: Initializes a new instance of the `HttpWrapBuilder`.

### Methods

- `WrapBuilder<T> builder()`: Returns the underlying `WrapBuilder` instance.
- `HttpWrapBuilder<T> with()`: Returns the current instance for method chaining.
- `WrapResponse<T> build()`: Builds and returns the `WrapResponse`.

### Meta Information

- `HttpWrapBuilder<T> apiVersion(String version)`: Sets the API version.
- `HttpWrapBuilder<T> locale(String locale)`: Sets the locale using a string.
- `HttpWrapBuilder<T> locale(Locale locale)`: Sets the locale using a Locale object.
- `HttpWrapBuilder<T> requestId(String requestId)`: Sets the request ID.
- `HttpWrapBuilder<T> customFields(Map<String, ?> fields)`: Sets custom fields in the meta information.

### Status Codes and Messages

- `HttpWrapBuilder<T> statusCode(HttpResponse status)`: Sets the status code using an `HttpResponse`.
- `HttpWrapBuilder<T> statusCode(int status)`: Sets the status code using an integer.
- `HttpWrapBuilder<T> message(String message)`: Sets the response message.

### Data and Errors

- `HttpWrapBuilder<T> body(T data)`: Sets the response body.
- `HttpWrapBuilder<T> errors(Object error)`: Sets the error information.
- `HttpWrapBuilder<T> errors(Throwable error)`: Sets the error information from an exception.

### Pagination

- `HttpWrapBuilder<T> pagination(PaginationResponse pagination)`: Sets the pagination information.
- `HttpWrapBuilder<T> pagination(PaginationBuilder builder)`: Sets the pagination information using
  a `PaginationBuilder`.

### Status Code Shortcuts

- `HttpWrapBuilder<T> ok(T data)`: Sets status code to 200 (OK) and sets the response body.
- `HttpWrapBuilder<T> created(T data)`: Sets status code to 201 (Created) and sets the response body.
- `HttpWrapBuilder<T> accepted(T data)`: Sets status code to 202 (Accepted) and sets the response body.
- ... (other HTTP status codes methods are similarly available)

## Example with Status Code Shortcuts

```java
package org.unify4j;

import org.unify4j.model.builder.HttpWrapBuilder;
import org.unify4j.model.response.WrapResponse;

public class Main {

    public static void main(String[] args) {
        HttpWrapBuilder<String> builder = new HttpWrapBuilder<>();
        WrapResponse<String> response = builder.ok("Operation completed successfully").build();
        System.out.println(response);
    }
}
```
