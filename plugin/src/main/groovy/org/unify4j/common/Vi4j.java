package org.unify4j.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.*;
import java.util.Map;
import java.util.Set;

public class Vi4j {
    protected static final Logger logger = LoggerFactory.getLogger(Vi4j.class);

    /**
     * <p>Validate that the argument condition is {@code true}; otherwise
     * throwing an exception with the specified message. This method is useful when
     * validating according to an arbitrary boolean expression, such as validating a
     * primitive number or using your own custom validation expression.</p>
     *
     * <pre>Validate.isTrue(i &gt; 0.0, "The value must be greater than zero: &#37;d", i);</pre>
     *
     * <p>For performance reasons, the long value is passed as a separate parameter and
     * appended to the exception message only in the case of an error.</p>
     *
     * @param expression the boolean expression to check
     * @param message    the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param value      the value to append to the message when invalid
     * @throws IllegalArgumentException if expression is {@code false}
     * @see #isTrue(boolean)
     * @see #isTrue(boolean, String, double)
     * @see #isTrue(boolean, String, Object...)
     */
    public static void isTrue(final boolean expression, final String message, final long value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, value));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}; otherwise
     * throwing an exception with the specified message. This method is useful when
     * validating according to an arbitrary boolean expression, such as validating a
     * primitive number or using your own custom validation expression.</p>
     *
     * <pre>Validate.isTrue(d &gt; 0.0, "The value must be greater than zero: &#37;s", d);</pre>
     *
     * <p>For performance reasons, the double value is passed as a separate parameter and
     * appended to the exception message only in the case of an error.</p>
     *
     * @param expression the boolean expression to check
     * @param message    the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param value      the value to append to the message when invalid
     * @throws IllegalArgumentException if expression is {@code false}
     * @see #isTrue(boolean)
     * @see #isTrue(boolean, String, long)
     * @see #isTrue(boolean, String, Object...)
     */
    public static void isTrue(final boolean expression, final String message, final double value) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, value));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}; otherwise
     * throwing an exception with the specified message. This method is useful when
     * validating according to an arbitrary boolean expression, such as validating a
     * primitive number or using your own custom validation expression.</p>
     *
     * <pre>
     * Validate.isTrue(i &gt;= min &amp;&amp; i &lt;= max, "The value must be between &#37;d and &#37;d", min, max);
     * Validate.isTrue(myObject.isOk(), "The object is not okay");</pre>
     *
     * @param expression the boolean expression to check
     * @param message    the {@link String#format(String, Object...)} exception message if invalid, not null
     * @param values     the optional values for the formatted exception message, null array not recommended
     * @throws IllegalArgumentException if expression is {@code false}
     * @see #isTrue(boolean)
     * @see #isTrue(boolean, String, long)
     * @see #isTrue(boolean, String, double)
     */
    public static void isTrue(final boolean expression, final String message, final Object... values) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(message, values));
        }
    }

    /**
     * <p>Validate that the argument condition is {@code true}; otherwise
     * throwing an exception. This method is useful when validating according
     * to an arbitrary boolean expression, such as validating a
     * primitive number or using your own custom validation expression.</p>
     *
     * <pre>
     * Validate.isTrue(i &gt; 0);
     * Validate.isTrue(myObject.isOk());</pre>
     *
     * <p>The message of the exception is &quot;The validated expression is
     * false&quot;.</p>
     *
     * @param expression the boolean expression to check
     * @throws IllegalArgumentException if expression is {@code false}
     * @see #isTrue(boolean, String, long)
     * @see #isTrue(boolean, String, double)
     * @see #isTrue(boolean, String, Object...)
     */
    public static void isTrue(final boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException("The validated expression is false");
        }
    }

    /**
     * Retrieves the default validator factory and creates a validator.
     *
     * @return The default validator instance.
     */
    public static Validator defaultFactory() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            return factory.getValidator();
        }
    }

    /**
     * Validates the provided request object using the specified validator.
     * Throws an exception if any constraint violations are found.
     *
     * @param <T>       the type of the request object
     * @param request   the request object to validate
     * @param validator the validator to use for validation
     * @throws IllegalArgumentException     if the request object is null
     * @throws ConstraintViolationException if any constraint violations are found
     */
    public static <T> void validate(T request, Validator validator) {
        if (request == null) {
            throw new IllegalArgumentException("Class<?> is required");
        }
        Set<ConstraintViolation<T>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<T> constraintViolation : violations) {
                builder.append(constraintViolation.getMessage());
                builder.append(", ");
            }
            throw new ConstraintViolationException("Invalid fields: " + builder, violations);
        }
    }

    /**
     * Validates the provided request object using the specified validator.
     * Throws an exception if any constraint violations are found.
     *
     * @param <T>     the type of the request object
     * @param request the request object to validate
     * @throws IllegalArgumentException     if the request object is null
     * @throws ConstraintViolationException if any constraint violations are found
     */
    public static <T> void validate(T request) {
        validate(request, defaultFactory());
    }

    /**
     * Throws an IllegalArgumentException if the provided value is null.
     *
     * @param value   The object to check for null.
     * @param message The message to include in the exception if the value is null.
     * @throws IllegalArgumentException if the value is null.
     */
    public static void throwIfNull(Object value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Throws an IllegalArgumentException if the provided string is null or empty.
     *
     * @param value   The string to check for null or emptiness.
     * @param message The message to include in the exception if the string is null or empty.
     * @throws IllegalArgumentException if the string is null or empty.
     */
    public static void throwIfNullOrEmpty(String value, String message) {
        if (String4j.isEmpty(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Throws an IllegalArgumentException if the class with the provided fully qualified name is not found using the specified class loader.
     *
     * @param fullyQualifiedClassName The fully qualified name of the class to check.
     * @param loader                  The class loader to use for loading the class.
     * @throws IllegalArgumentException if the fully qualified class name is null or empty, the loader is null, or the class is not found.
     */
    public static void throwIfClassNotFound(String fullyQualifiedClassName, ClassLoader loader) {
        throwIfNullOrEmpty(fullyQualifiedClassName, "fully qualified class name cannot be null or empty");
        throwIfNull(loader, "loader cannot be null");

        Class<?> clazz = Class4j.forName(fullyQualifiedClassName, loader); // Assuming ClassUtils has a forName method
        if (clazz == null) {
            throw new IllegalArgumentException("Unknown class: " + fullyQualifiedClassName + " was not found.");
        }
    }

    /**
     * Throws an IllegalArgumentException if the specified key already exists in the provided map.
     *
     * @param <K>     The type of keys in the map.
     * @param <V>     The type of values in the map.
     * @param map     The map to check for the presence of the key.
     * @param key     The key to check for existence in the map.
     * @param message The message to include in the exception if the key already exists in the map.
     * @throws IllegalArgumentException if the map or key is null, or if the key already exists in the map.
     */
    public static <K, V> void throwIfKeyExists(Map<K, V> map, K key, String message) {
        throwIfNull(map, "map cannot be null");
        throwIfNull(key, "key cannot be null");
        if (map.containsKey(key)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Throws an IllegalArgumentException if the provided logic condition is false.
     *
     * @param logic   The boolean condition to evaluate.
     * @param message The message to include in the exception if the condition is false.
     * @throws IllegalArgumentException if the logic condition is false.
     */
    public static void throwIfFalse(boolean logic, String message) {
        if (!logic) {
            throw new IllegalArgumentException(message);
        }
    }
}
