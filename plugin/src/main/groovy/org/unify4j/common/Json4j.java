package org.unify4j.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import kong.unirest.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Json4j {
    protected static final Logger logger = LoggerFactory.getLogger(Json4j.class);
    protected static final ObjectMapper instance = createInstance();

    /**
     * Creates and configures a new instance of ObjectMapper with commonly used settings.
     *
     * @return a new instance of ObjectMapper configured with common settings
     */
    public static ObjectMapper createInstance() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        return mapper;
    }

    /**
     * Converts the specified object to a JSON string.
     *
     * @param data the object to convert to JSON
     * @return a JSON string representing the object
     */
    public static String toJson(Object data) {
        StringWriter writer = new StringWriter();
        JsonGenerator generator = null;
        try {
            generator = new JsonFactory().createGenerator(writer);
            instance.writeValue(generator, data);
        } catch (IOException e) {
            logger.error("Error occurred while converting object to JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while converting object to JSON: " + e.getMessage(), e);
        } finally {
            if (generator != null) {
                try {
                    generator.close();
                } catch (IOException e) {
                    logger.error("Error occurred while closing JSON generator: {}", e.getMessage(), e);
                }
            }
        }
        return writer.toString();
    }

    /**
     * Converts the specified object to a JSON string using the provided ObjectMapper instance.
     *
     * @param data     the object to convert to JSON
     * @param instance the ObjectMapper instance to use for serialization
     * @return a JSON string representing the object
     */
    public static String toJson(Object data, ObjectMapper instance) {
        StringWriter writer = new StringWriter();
        JsonGenerator generator = null;
        try {
            generator = new JsonFactory().createGenerator(writer);
            instance.writeValue(generator, data);
        } catch (IOException e) {
            logger.error("Error occurred while converting object to JSON: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while converting object to JSON: " + e.getMessage(), e);
        } finally {
            if (generator != null) {
                try {
                    generator.close();
                } catch (IOException e) {
                    logger.error("Error occurred while closing JSON generator: {}", e.getMessage(), e);
                }
            }
        }
        return writer.toString();
    }

    /**
     * Converts the specified object to a Map<String, ?> using the default ObjectMapper instance.
     *
     * @param data the object to convert to a map
     * @return a map containing key-value pairs representing the object
     */
    @SuppressWarnings("unchecked")
    public static Map<String, ?> toMapFrom(Object data) {
        return (Map<String, ?>) instance.convertValue(data, Map.class);
    }

    /**
     * Converts the specified object to a Map<String, ?> using the provided ObjectMapper instance.
     *
     * @param data     the object to convert to a map
     * @param instance the ObjectMapper instance to use for conversion
     * @return a map containing key-value pairs representing the object
     */
    @SuppressWarnings("unchecked")
    public static Map<String, ?> toMapFrom(Object data, ObjectMapper instance) {
        return (Map<String, ?>) instance.convertValue(data, Map.class);
    }

    /**
     * Deserializes the specified JSON string into an instance of the specified class using the default ObjectMapper instance.
     *
     * @param json  the JSON string to deserialize
     * @param clazz the class of the target object
     * @param <T>   the type of the target object
     * @return an instance of the specified class representing the JSON data
     */
    public static <T> T json2Target(String json, Class<T> clazz) {
        try {
            return instance.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("Error occurred while deserializing JSON string: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while deserializing JSON string: " + e.getMessage(), e);
        }
    }

    /**
     * Deserializes the specified JSON string into an instance of the specified class using the provided ObjectMapper instance.
     *
     * @param json     the JSON string to deserialize
     * @param clazz    the class of the target object
     * @param instance the ObjectMapper instance to use for deserialization
     * @param <T>      the type of the target object
     * @return an instance of the specified class representing the JSON data
     */
    public static <T> T json2Target(String json, Class<T> clazz, ObjectMapper instance) {
        try {
            return instance.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("Error occurred while deserializing JSON string: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while deserializing JSON string: " + e.getMessage(), e);
        }
    }

    /**
     * Deserializes the specified JSON string into a list of instances of the specified class using the default ObjectMapper instance.
     *
     * @param json  the JSON string to deserialize
     * @param clazz the class of the target objects
     * @param <T>   the type of the target objects
     * @return a list containing instances of the specified class representing the JSON data
     * @throws IOException if an error occurs during deserialization
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) throws IOException {
        JavaType type = instance.getTypeFactory().constructCollectionType(List.class, clazz);
        return instance.readValue(json, type);
    }

    /**
     * Deserializes the specified JSON string into a list of instances of the specified class using the provided ObjectMapper instance.
     *
     * @param json     the JSON string to deserialize
     * @param clazz    the class of the target objects
     * @param instance the ObjectMapper instance to use for deserialization
     * @param <T>      the type of the target objects
     * @return a list containing instances of the specified class representing the JSON data
     * @throws IOException if an error occurs during deserialization
     */
    public static <T> List<T> json2List(String json, Class<T> clazz, ObjectMapper instance) throws IOException {
        JavaType type = instance.getTypeFactory().constructCollectionType(List.class, clazz);
        return instance.readValue(json, type);
    }

    /**
     * Deserializes the specified JSON string into an array of instances of the specified class using the default ObjectMapper instance.
     *
     * @param json  the JSON string to deserialize
     * @param clazz the class representing the array type
     * @param <T>   the component type of the target array
     * @return an array containing instances of the specified class representing the JSON data
     * @throws IOException if an error occurs during deserialization
     */
    public static <T> T[] json2Array(String json, Class<T[]> clazz) throws IOException {
        return instance.readValue(json, clazz);
    }

    /**
     * Deserializes the specified JSON string into an array of instances of the specified class using the provided ObjectMapper instance.
     *
     * @param json     the JSON string to deserialize
     * @param clazz    the class representing the array type
     * @param instance the ObjectMapper instance to use for deserialization
     * @param <T>      the component type of the target array
     * @return an array containing instances of the specified class representing the JSON data
     * @throws IOException if an error occurs during deserialization
     */
    public static <T> T[] json2Array(String json, Class<T[]> clazz, ObjectMapper instance) throws IOException {
        return instance.readValue(json, clazz);
    }

    /**
     * Converts the specified JsonNode to an instance of the specified class using the default ObjectMapper instance.
     *
     * @param node  the JsonNode to convert
     * @param clazz the class of the target object
     * @param <T>   the type of the target object
     * @return an instance of the specified class representing the JsonNode data
     * @throws RuntimeException if an error occurs during conversion
     */
    public static <T> T node2Target(JsonNode node, Class<T> clazz) {
        try {
            return instance.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while converting JsonNode to target object: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while converting JsonNode to target object: " + e.getMessage(), e);
        }
    }

    /**
     * Converts the specified JsonNode to an instance of the specified class using the provided ObjectMapper instance.
     *
     * @param node     the JsonNode to convert
     * @param clazz    the class of the target object
     * @param instance the ObjectMapper instance to use for conversion
     * @param <T>      the type of the target object
     * @return an instance of the specified class representing the JsonNode data
     * @throws RuntimeException if an error occurs during conversion
     */
    public static <T> T node2Target(JsonNode node, Class<T> clazz, ObjectMapper instance) {
        try {
            return instance.treeToValue(node, clazz);
        } catch (JsonProcessingException e) {
            logger.error("Error occurred while converting JsonNode to target object: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while converting JsonNode to target object: " + e.getMessage(), e);
        }
    }

    /**
     * Converts the specified Java object to a JsonNode using the default ObjectMapper instance.
     *
     * @param data the Java object to convert
     * @return a JsonNode representing the Java object
     * @throws RuntimeException if an error occurs during conversion
     */
    public static JsonNode target2Node(Object data) {
        try {
            return (data == null ? instance.createObjectNode() : instance.convertValue(data, JsonNode.class));
        } catch (Exception e) {
            logger.error("Error occurred while converting target object to JsonNode: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while converting target object to JsonNode: " + e.getMessage(), e);
        }
    }

    /**
     * Converts the specified Java object to a JsonNode using the provided ObjectMapper instance.
     *
     * @param data     the Java object to convert
     * @param instance the ObjectMapper instance to use for conversion
     * @return a JsonNode representing the Java object
     * @throws RuntimeException if an error occurs during conversion
     */
    public static JsonNode target2Node(Object data, ObjectMapper instance) {
        try {
            return (data == null ? instance.createObjectNode() : instance.convertValue(data, JsonNode.class));
        } catch (Exception e) {
            logger.error("Error occurred while converting target object to JsonNode: {}", e.getMessage(), e);
            throw new RuntimeException("Error occurred while converting target object to JsonNode: " + e.getMessage(), e);
        }
    }

    /**
     * Deserializes the specified JSON string into a generic type using the default ObjectMapper instance.
     *
     * @param json      the JSON string to deserialize
     * @param reference the TypeReference representing the generic type
     * @param <T>       the type of the generic type
     * @return an instance of the specified generic type representing the JSON data
     * @throws IllegalArgumentException if the JSON string is empty or null
     * @throws RuntimeException         if an error occurs during deserialization
     */
    public static <T> T json2Generics(String json, TypeReference<T> reference) {
        if (String4j.isNotEmpty(json)) {
            try {
                return instance.readValue(json, reference);
            } catch (Exception e) {
                logger.error("Error occurred while deserializing JSON string: {}", e.getMessage(), e);
                throw new RuntimeException("Error occurred while deserializing JSON string: " + e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("JSON string is required");
        }
    }

    /**
     * Deserializes the specified JSON string into a generic type using the provided ObjectMapper instance.
     *
     * @param json      the JSON string to deserialize
     * @param reference the TypeReference representing the generic type
     * @param instance  the ObjectMapper instance to use for deserialization
     * @param <T>       the type of the generic type
     * @return an instance of the specified generic type representing the JSON data
     * @throws IllegalArgumentException if the JSON string is empty or null
     * @throws RuntimeException         if an error occurs during deserialization
     */
    public static <T> T json2Generics(String json, TypeReference<T> reference, ObjectMapper instance) {
        if (String4j.isNotEmpty(json)) {
            try {
                return instance.readValue(json, reference);
            } catch (Exception e) {
                logger.error("Error occurred while deserializing JSON string: {}", e.getMessage(), e);
                throw new RuntimeException("Error occurred while deserializing JSON string: " + e.getMessage(), e);
            }
        } else {
            throw new IllegalArgumentException("JSON string is required");
        }
    }

    /**
     * Reads the value at the specified JSONPath expression from the JSON string.
     *
     * @param json the JSON string to read from
     * @param path the JSONPath expression to use for reading
     * @param <T>  the type of the value to read
     * @return the value at the specified JSONPath expression
     */
    public static <T> T readIf(String json, String path) {
        Configuration conf = Configuration.defaultConfiguration().addOptions(Option.DEFAULT_PATH_LEAF_TO_NULL);
        return JsonPath.using(conf).parse(json).read(path);
    }

    /**
     * Translates the HttpResponse to a Map<String, ?>.
     *
     * @param response the HttpResponse to translate
     * @return a Map containing the translated data
     */
    @SuppressWarnings({"unchecked"})
    public static Map<String, ?> translate(HttpResponse<kong.unirest.JsonNode> response) {
        if (response == null) {
            return Collections.emptyMap();
        }
        return json2Target(response.getBody().toString(), Map.class);
    }
}
