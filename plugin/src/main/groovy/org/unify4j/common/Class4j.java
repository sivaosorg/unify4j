package org.unify4j.common;

import java.lang.reflect.*;
import java.util.*;

public class Class4j {
    // Set of primitive wrapper classes
    protected static final Set<Class<?>> PRIMITIVES_WRAPPER = new HashSet<>();

    // Map from primitive types to their corresponding wrapper classes
    protected static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPER = new HashMap<>(20, .8f);

    // Map from class names to their corresponding Class objects
    protected static final Map<String, Class<?>> NAMES_TO_CLASS = new HashMap<>();

    // Map from classes to their wrapped primitive classes and vice versa
    protected static final Map<Class<?>, Class<?>> WRAPPER_TO_PRIMITIVES = new HashMap<>();

    static {
        // Initializing the set of primitive wrapper classes
        PRIMITIVES_WRAPPER.add(Byte.class);
        PRIMITIVES_WRAPPER.add(Short.class);
        PRIMITIVES_WRAPPER.add(Integer.class);
        PRIMITIVES_WRAPPER.add(Long.class);
        PRIMITIVES_WRAPPER.add(Float.class);
        PRIMITIVES_WRAPPER.add(Double.class);
        PRIMITIVES_WRAPPER.add(Character.class);
        PRIMITIVES_WRAPPER.add(Boolean.class);

        // Mapping primitive names to their Class objects
        NAMES_TO_CLASS.put("boolean", Boolean.TYPE);
        NAMES_TO_CLASS.put("char", Character.TYPE);
        NAMES_TO_CLASS.put("byte", Byte.TYPE);
        NAMES_TO_CLASS.put("short", Short.TYPE);
        NAMES_TO_CLASS.put("int", Integer.TYPE);
        NAMES_TO_CLASS.put("long", Long.TYPE);
        NAMES_TO_CLASS.put("float", Float.TYPE);
        NAMES_TO_CLASS.put("double", Double.TYPE);
        NAMES_TO_CLASS.put("string", String.class);
        NAMES_TO_CLASS.put("date", Date.class);
        NAMES_TO_CLASS.put("class", Class.class);

        // Mapping primitive types to their corresponding wrapper classes
        PRIMITIVES_TO_WRAPPER.put(int.class, Integer.class);
        PRIMITIVES_TO_WRAPPER.put(long.class, Long.class);
        PRIMITIVES_TO_WRAPPER.put(double.class, Double.class);
        PRIMITIVES_TO_WRAPPER.put(float.class, Float.class);
        PRIMITIVES_TO_WRAPPER.put(boolean.class, Boolean.class);
        PRIMITIVES_TO_WRAPPER.put(char.class, Character.class);
        PRIMITIVES_TO_WRAPPER.put(byte.class, Byte.class);
        PRIMITIVES_TO_WRAPPER.put(short.class, Short.class);
        PRIMITIVES_TO_WRAPPER.put(void.class, Void.class);

        // Mapping wrapper classes to their corresponding primitive types
        WRAPPER_TO_PRIMITIVES.put(int.class, Integer.class);
        WRAPPER_TO_PRIMITIVES.put(Integer.class, int.class);
        WRAPPER_TO_PRIMITIVES.put(char.class, Character.class);
        WRAPPER_TO_PRIMITIVES.put(Character.class, char.class);
        WRAPPER_TO_PRIMITIVES.put(byte.class, Byte.class);
        WRAPPER_TO_PRIMITIVES.put(Byte.class, byte.class);
        WRAPPER_TO_PRIMITIVES.put(short.class, Short.class);
        WRAPPER_TO_PRIMITIVES.put(Short.class, short.class);
        WRAPPER_TO_PRIMITIVES.put(long.class, Long.class);
        WRAPPER_TO_PRIMITIVES.put(Long.class, long.class);
        WRAPPER_TO_PRIMITIVES.put(float.class, Float.class);
        WRAPPER_TO_PRIMITIVES.put(Float.class, float.class);
        WRAPPER_TO_PRIMITIVES.put(double.class, Double.class);
        WRAPPER_TO_PRIMITIVES.put(Double.class, double.class);
        WRAPPER_TO_PRIMITIVES.put(boolean.class, Boolean.class);
        WRAPPER_TO_PRIMITIVES.put(Boolean.class, boolean.class);
    }

    /**
     * Computes the inheritance distance between two classes/interfaces/primitive types.
     *
     * @param source      The source class, interface, or primitive type.
     * @param destination The destination class, interface, or primitive type.
     * @return The number of steps from the source to the destination, or -1 if no path exists.
     */
    public static int computeInheritanceDistance(Class<?> source, Class<?> destination) {
        if (source == null || destination == null) {
            return -1;
        }
        if (source.equals(destination)) {
            return 0;
        }
        // Check if the source and destination are primitive types
        if (source.isPrimitive()) {
            if (destination.isPrimitive()) {
                return -1; // Already checked for equality
            }
            if (!isPrimitive(destination)) {
                return -1;
            }
            return comparePrimitiveToWrapper(destination, source);
        }
        if (destination.isPrimitive()) {
            if (!isPrimitive(source)) {
                return -1;
            }
            return comparePrimitiveToWrapper(source, destination);
        }
        // Breadth-First Search (BFS) to find the shortest path in the inheritance tree
        Queue<Class<?>> queue = new LinkedList<>();
        Map<Class<?>, String> visited = new IdentityHashMap<>();
        queue.add(source);
        visited.put(source, null);
        int distance = 0;
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            distance++;

            for (int i = 0; i < levelSize; i++) {
                Class<?> current = queue.poll();
                // Check superclass
                if (current != null && current.getSuperclass() != null) {
                    if (current.getSuperclass().equals(destination)) {
                        return distance;
                    }
                    if (!visited.containsKey(current.getSuperclass())) {
                        queue.add(current.getSuperclass());
                        visited.put(current.getSuperclass(), null);
                    }
                }
                // Check interfaces
                for (Class<?> interfaceClass : current != null ? current.getInterfaces() : new Class[0]) {
                    if (interfaceClass.equals(destination)) {
                        return distance;
                    }
                    if (!visited.containsKey(interfaceClass)) {
                        queue.add(interfaceClass);
                        visited.put(interfaceClass, null);
                    }
                }
            }
        }
        return -1; // No path found
    }

    /**
     * Checks if a class is a primitive type or a primitive wrapper class.
     *
     * @param clazz The class to check.
     * @return true if the class is a primitive type or a primitive wrapper class, false otherwise.
     */
    @SuppressWarnings({"BooleanMethodIsAlwaysInverted"})
    public static boolean isPrimitive(Class<?> clazz) {
        return clazz.isPrimitive() || PRIMITIVES_WRAPPER.contains(clazz);
    }

    /**
     * Retrieves a class by its name using the specified class loader.
     *
     * @param name        The name of the class.
     * @param classLoader The class loader to use for loading the class.
     * @return The Class object for the specified name, or null if the class cannot be found.
     */
    public static Class<?> forName(String name, ClassLoader classLoader) {
        if (name == null || name.isEmpty()) {
            return null;
        }
        try {
            return internalClassForName(name, classLoader);
        } catch (SecurityException e) {
            throw new IllegalArgumentException("Security exception, classForName() call on: " + name, e);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Checks if a class is declared as final.
     *
     * @param clazz The class to check.
     * @return true if the class is final, false otherwise.
     */
    public static boolean isClassFinal(Class<?> clazz) {
        return (clazz.getModifiers() & Modifier.FINAL) != 0;
    }

    /**
     * Checks if all constructors of a class are private.
     *
     * @param clazz The class to check.
     * @return true if all constructors are private, false otherwise.
     */
    public static boolean areAllConstructorsPrivate(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            if ((constructor.getModifiers() & Modifier.PRIVATE) == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts a primitive class to its corresponding wrapper class.
     *
     * @param primitiveClass The primitive class to convert.
     * @return The corresponding wrapper class.
     * @throws IllegalArgumentException if the provided class is not a primitive type.
     */
    public static Class<?> toPrimitiveWrapperClass(Class<?> primitiveClass) {
        if (!primitiveClass.isPrimitive()) {
            return primitiveClass;
        }
        Class<?> wrapperClass = PRIMITIVES_TO_WRAPPER.get(primitiveClass);
        if (wrapperClass == null) {
            throw new IllegalArgumentException("Passed in class: " + primitiveClass + " is not a primitive class");
        }
        return wrapperClass;
    }

    /**
     * Checks if one class wraps the other.
     *
     * @param class1 The first class.
     * @param class2 The second class.
     * @return true if one class wraps the other, false otherwise.
     */
    public static boolean doesOneWrapTheOther(Class<?> class1, Class<?> class2) {
        return WRAPPER_TO_PRIMITIVES.get(class1) == class2;
    }

    /**
     * Compares a primitive type with its wrapper class.
     *
     * @param source      The source class, expected to be a primitive type.
     * @param destination The destination class, expected to be a wrapper class.
     * @return 0 if the classes are equivalent, -1 otherwise.
     */
    private static int comparePrimitiveToWrapper(Class<?> source, Class<?> destination) {
        try {
            return source.getField("TYPE").get(null).equals(destination) ? 0 : -1;
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Loads a class by its name using the specified class loader.
     *
     * @param name        The name of the class.
     * @param classLoader The class loader to use for loading the class.
     * @return The Class object for the specified name.
     * @throws ClassNotFoundException if the class cannot be found.
     */
    private static Class<?> loadClass(String name, ClassLoader classLoader) throws ClassNotFoundException {
        String className = name;
        boolean isArrayType = false;
        Class<?> primitiveArrayClass = null;
        // Check if the class is an array type
        while (className.startsWith("[")) {
            isArrayType = true;
            if (className.endsWith(";")) {
                className = className.substring(0, className.length() - 1);
            }
            switch (className) {
                case "[B":
                    primitiveArrayClass = byte[].class;
                    break;
                case "[S":
                    primitiveArrayClass = short[].class;
                    break;
                case "[I":
                    primitiveArrayClass = int[].class;
                    break;
                case "[J":
                    primitiveArrayClass = long[].class;
                    break;
                case "[F":
                    primitiveArrayClass = float[].class;
                    break;
                case "[D":
                    primitiveArrayClass = double[].class;
                    break;
                case "[Z":
                    primitiveArrayClass = boolean[].class;
                    break;
                case "[C":
                    primitiveArrayClass = char[].class;
                    break;
            }
            int startPosition = className.startsWith("[L") ? 2 : 1;
            className = className.substring(startPosition);
        }
        Class<?> currentClass = null;
        if (primitiveArrayClass == null) {
            try {
                currentClass = classLoader.loadClass(className);
            } catch (ClassNotFoundException e) {
                currentClass = Thread.currentThread().getContextClassLoader().loadClass(className);
            }
        }
        if (isArrayType) {
            currentClass = (primitiveArrayClass != null) ? primitiveArrayClass : Array.newInstance(currentClass, 0).getClass();
            while (name.startsWith("[[")) {
                currentClass = Array.newInstance(currentClass, 0).getClass();
                name = name.substring(1);
            }
        }
        return currentClass;
    }

    /**
     * Internal method to load a class by name and cache the result for future use.
     *
     * @param name        The name of the class.
     * @param classLoader The class loader to use for loading the class.
     * @return The Class object for the specified name.
     * @throws ClassNotFoundException if the class cannot be found.
     */
    private static Class<?> internalClassForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
        Class<?> clazz = NAMES_TO_CLASS.get(name);
        if (clazz != null) {
            return clazz;
        }
        clazz = loadClass(name, classLoader);
        // Security check for certain classes
        if (ClassLoader.class.isAssignableFrom(clazz) || ProcessBuilder.class.isAssignableFrom(clazz) ||
                Process.class.isAssignableFrom(clazz) || Constructor.class.isAssignableFrom(clazz) ||
                Method.class.isAssignableFrom(clazz) || Field.class.isAssignableFrom(clazz)) {
            throw new SecurityException(String.format("For security reasons, cannot instantiate: %s when loading JSON.", clazz.getName()));
        }
        NAMES_TO_CLASS.put(name, clazz);
        return clazz;
    }
}
