# HttpStatusBuilder

The `HttpStatusBuilder` class in the `org.unify4j.model.builder` package provides a convenient way to access predefined
HTTP response objects for various status codes. These objects are instances of the `HttpResponse` class located in
the `org.unify4j.model.response` package.

## Purpose

The purpose of this class is to offer a collection of predefined HTTP response objects covering the entire range of HTTP
status codes. This facilitates the creation of HTTP responses in applications by providing easy access to commonly used
status codes and their associated information.

## Usage

To use the `HttpStatusBuilder` class, simply reference one of its public static final fields, each of which represents
an HTTP response object. For example:

```java
HttpResponse ok = HttpStatusBuilder.OK;
```

# HttpResponse

The `HttpResponse` class encapsulates information about an HTTP response, including the status code, reason phrase,
response category, and an optional description. This class is located in the org.unify4j.model.response package.

E.g,

```java
package org.unify4j;

import org.unify4j.model.builder.HttpStatusBuilder;

public class Main {

    public static void main(String[] args) {
        System.out.println(HttpStatusBuilder.CREATED);
    }
}
```

Output:

```bash
HTTP response { Code: 201, Text: 'Created', Type: 'Successful', Description: '' }
```
