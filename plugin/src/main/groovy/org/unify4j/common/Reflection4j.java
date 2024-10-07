package org.unify4j.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Utilities to simplify writing reflective code as well as improve performance of reflective operations like
 * method and annotation look-ups.
 */
public final class Reflection4j {
    private static final ConcurrentMap<String, Collection<Field>> FIELD_MAP = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Method> METHOD_MAP1 = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Method> METHOD_MAP2 = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Method> METHOD_MAP3 = new ConcurrentHashMap<>();
    private static final ConcurrentMap<String, Constructor<?>> CONSTRUCTORS = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, List<Field>> FIELD_META_CACHE = new ConcurrentHashMap<>();

    public Reflection4j() {
        super();
    }

    /**
     * Recursively checks if the specified class or any of its superclasses, interfaces,
     * or super interfaces contains the specified annotation.
     * This method performs an exhaustive search throughout the complete inheritance hierarchy
     * of the provided class, including interfaces and superinterfaces.
     *
     * @param <T>          The type of the annotation to search for.
     * @param classToCheck The class to be checked for the annotation.
     * @param annoClass    The annotation class to look for.
     * @return The found annotation if present, or {@code null} if the annotation is not found.
     */
    public static <T extends Annotation> T getClassAnnotation(final Class<?> classToCheck, final Class<T> annoClass) {
        final Set<Class<?>> visited = new HashSet<>();
        final LinkedList<Class<?>> stack = new LinkedList<>();
        stack.add(classToCheck);
        while (!stack.isEmpty()) {
            Class<?> classToChk = stack.pop();
            if (classToChk == null || visited.contains(classToChk)) {
                continue;
            }
            visited.add(classToChk);
            T a = classToChk.getAnnotation(annoClass);
            if (a != null) {
                return a;
            }
            stack.push(classToChk.getSuperclass());
            addInterfaces(classToChk, stack);
        }
        return null;
    }

    /**
     * Recursively checks if the specified method or any overridden method in the class hierarchy
     * (including superclasses and interfaces) contains the specified annotation.
     * This method searches for the annotation by traversing up through the inheritance hierarchy of the method's declaring class.
     *
     * @param <T>       The type of the annotation to search for.
     * @param method    The {@link Method} to check for the annotation.
     * @param annoClass The annotation class to look for on the method.
     * @return The found annotation if present, or {@code null} if the annotation is not found on the method.
     */
    public static <T extends Annotation> T getMethodAnnotation(final Method method, final Class<T> annoClass) {
        final Set<Class<?>> visited = new HashSet<>();
        final LinkedList<Class<?>> stack = new LinkedList<>();
        stack.add(method.getDeclaringClass());

        while (!stack.isEmpty()) {
            Class<?> classToChk = stack.pop();
            if (classToChk == null || visited.contains(classToChk)) {
                continue;
            }
            visited.add(classToChk);
            Method m = getMethod(classToChk, method.getName(), method.getParameterTypes());
            if (m == null) {
                continue;
            }
            T a = m.getAnnotation(annoClass);
            if (a != null) {
                return a;
            }
            stack.push(classToChk.getSuperclass());
            addInterfaces(method.getDeclaringClass(), stack);
        }
        return null;
    }

    /**
     * Fetch a public method reflectively by name with argument types.  This method caches the lookup, so that
     * subsequent calls are significantly faster.  The method can be on an inherited class of the passed in [starting]
     * Class.
     *
     * @param c          Class on which method is to be found.
     * @param methodName String name of method to find.
     * @param types      Argument types for the method (null is used for no argument methods).
     * @return Method located, or null if not found.
     */
    @SuppressWarnings({"StringBufferReplaceableByString"})
    public static Method getMethod(Class<?> c, String methodName, Class<?>... types) {
        try {
            StringBuilder builder = new StringBuilder(getClassLoaderName(c)); // ClassName
            builder.append('.');
            builder.append(c.getName()); // methodName
            builder.append('.');
            builder.append(methodName);
            builder.append(makeParamKey(types)); // arg1.class|arg2.class|...

            // methodKey is in form ClassName.methodName:arg1.class|arg2.class|...
            String methodKey = builder.toString();
            Method method = METHOD_MAP1.get(methodKey);
            if (method == null) {
                method = c.getMethod(methodName, types);
                Method other = METHOD_MAP1.putIfAbsent(methodKey, method);
                if (other != null) {
                    method = other;
                }
            }
            return method;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retrieve the declared fields on a Class.
     */
    public static List<Field> getDeclaredFields(final Class<?> c) {
        return FIELD_META_CACHE.computeIfAbsent(c, Reflection4j::buildDeclaredFields);
    }

    /**
     * Get all non-static, non-transient, fields of the passed in class, including
     * private fields. Note, the special this$ field is also not returned.  The result
     * is cached in a static ConcurrentHashMap to benefit execution performance.
     *
     * @param c Class instance
     * @return Collection of only the fields in the past in class
     * that would need further processing (reference fields).  This
     * makes field traversal on a class faster as it does not need to
     * continually process known fields like primitives.
     */
    @SuppressWarnings({"StringBufferReplaceableByString"})
    public static Collection<Field> getDeepDeclaredFields(Class<?> c) {
        StringBuilder builder = new StringBuilder(getClassLoaderName(c));
        builder.append('.');
        builder.append(c.getName());
        String key = builder.toString();
        Collection<Field> fields = FIELD_MAP.get(key);
        if (fields != null) {
            return fields;
        }
        fields = new ArrayList<>();
        Class<?> _c = c;
        while (_c != null) {
            getDeclaredFields(_c, fields);
            _c = _c.getSuperclass();
        }
        FIELD_MAP.put(key, fields);
        return fields;
    }

    /**
     * Get all non-static, non-transient, fields of the passed in class, including
     * private fields. Note, the special this$ field is also not returned.  The
     * resulting fields are stored in a Collection.
     *
     * @param c Class instance
     *          that would need further processing (reference fields).  This
     *          makes field traversal on a class faster as it does not need to
     *          continually process known fields like primitives.
     */
    @SuppressWarnings({"CatchMayIgnoreException"})
    public static void getDeclaredFields(Class<?> c, Collection<Field> fields) {
        try {
            Field[] local = c.getDeclaredFields();
            for (Field field : local) {
                int modifiers = field.getModifiers();
                if (Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers)) {   // skip static and transient fields
                    continue;
                }
                String fieldName = field.getName();
                if ("metaClass".equals(fieldName) && "groovy.lang.MetaClass".equals(field.getType().getName())) {   // skip Groovy metaClass field if present (without tying this project to Groovy in any way).
                    continue;
                }
                if (fieldName.startsWith("this$")) {   // Skip field in nested class pointing to enclosing outer class instance
                    continue;
                }
                if (Modifier.isPublic(modifiers)) {
                    fields.add(field);
                } else {
                    // JDK11+ field.trySetAccessible();
                    try {
                        field.setAccessible(true);
                    } catch (Exception ignored) {
                    }
                    // JDK11+
                    fields.add(field);
                }
            }
        } catch (Throwable e) {
            Exception4j.safelyIgnoreException(e);
        }
    }

    /**
     * Retrieves all fields from the specified class, including inherited fields,
     * and maps them from their names to their corresponding {@link java.lang.reflect.Field} objects.
     * If multiple fields share the same name (e.g., in parent and child classes), the declaring class name
     * is prepended to the field name in the map to ensure uniqueness.
     *
     * @param c The {@link Class} object representing the class from which to retrieve fields.
     * @return A {@link Map} where the keys are field names (with declaring class names for duplicates)
     * and the values are the corresponding {@link java.lang.reflect.Field} objects.
     */
    public static Map<String, Field> getDeepDeclaredFieldMap(Class<?> c) {
        Map<String, Field> fieldMap = new HashMap<>();
        Collection<Field> fields = getDeepDeclaredFields(c);
        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldMap.containsKey(fieldName)) {   // Can happen when parent and child class both have private field with same name
                fieldMap.put(field.getDeclaringClass().getName() + '.' + fieldName, field);
            } else {
                fieldMap.put(fieldName, field);
            }
        }
        return fieldMap;
    }

    /**
     * Make reflective method calls without having to handle two checked exceptions (IllegalAccessException and
     * InvocationTargetException).  These exceptions are caught and rethrown as RuntimeExceptions, with the original
     * exception passed (nested) on.
     *
     * @param bean   Object (instance) on which to call method.
     * @param method Method instance from target object [easily obtained by calling ReflectionUtils.getMethod()].
     * @param args   Arguments to pass to method.
     * @return Object Value from reflectively called method.
     */
    public static Object call(Object bean, Method method, Object... args) {
        if (method == null) {
            String className = bean == null ? "null bean" : bean.getClass().getName();
            throw new IllegalArgumentException("null Method passed to Reflection4j.call() on bean of type: " + className);
        }
        if (bean == null) {
            throw new IllegalArgumentException("Cannot call [" + method.getName() + "()] on a null object.");
        }
        try {
            return method.invoke(bean, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException occurred attempting to reflectively call method: " + method.getName() + "()", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception thrown inside reflectively called method: " + method.getName() + "()", e.getTargetException());
        }
    }

    /**
     * Make a reflective method call in one step.  This approach does not support calling two different methods with
     * the same argument count, since it caches methods internally by "className.methodName|argCount".  For example,
     * if you had a class with two methods, foo(int, String) and foo(String, String), you cannot use this method.
     * However, this method would support calling foo(int), foo(int, String), foo(int, String, Object), etc.
     * Internally, it is caching the reflective method lookups as mentioned earlier for speed, using argument count
     * as part of the key (not all argument types).
     * <p>
     * Ideally, use the call(Object, Method, Object...args) method when possible, as it will support any method, and
     * also provides caching.  There are times, however, when all that is passed in (REST APIs) is argument values,
     * and if some of those are null, you may have an ambiguous targeted method.  With this approach, you can still
     * call these methods, assuming the methods are not overloaded with the same number of arguments and differing
     * types.
     *
     * @param bean       Object instance on which to call method.
     * @param methodName String name of method to call.
     * @param args       Arguments to pass.
     * @return Object value returned from the reflectively invoked method.
     */
    public static Object call(Object bean, String methodName, Object... args) {
        Method method = getMethod(bean, methodName, args.length);
        try {
            return method.invoke(bean, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("IllegalAccessException occurred attempting to reflectively call method: " + method.getName() + "()", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Exception thrown inside reflectively called method: " + method.getName() + "()", e.getTargetException());
        }
    }

    /**
     * Retrieves a method by name from the specified object instance, caching the results to avoid
     * repeated reflective lookups. This method is particularly useful for accessing overloaded
     * methods, as it allows for method resolution based on the number of arguments rather than their
     * types. If the specified method cannot be found, an {@link IllegalArgumentException} is thrown.
     *
     * @param bean       The object instance from which to retrieve the method.
     * @param methodName The name of the method to locate.
     * @param argCount   The number of arguments the method takes, used to differentiate between
     *                   overloaded methods with the same name.
     * @return The {@link Method} object representing the requested method.
     * @throws IllegalArgumentException if the bean is null, the method name is null, or the method
     *                                  cannot be found on the specified class.
     */
    @SuppressWarnings({"StringBufferReplaceableByString"})
    public static Method getMethod(Object bean, String methodName, int argCount) {
        if (bean == null) {
            throw new IllegalArgumentException("Attempted to call getMethod() [" + methodName + "()] on a null instance.");
        }
        if (methodName == null) {
            throw new IllegalArgumentException("Attempted to call getMethod() with a null method name on an instance of: " + bean.getClass().getName());
        }
        Class<?> beanClass = bean.getClass();
        StringBuilder builder = new StringBuilder(getClassLoaderName(beanClass));
        builder.append('.');
        builder.append(beanClass.getName());
        builder.append('.');
        builder.append(methodName);
        builder.append('|');
        builder.append(argCount);
        String methodKey = builder.toString();
        Method method = METHOD_MAP2.get(methodKey);
        if (method == null) {
            method = getMethodWithArgs(beanClass, methodName, argCount);
            if (method == null) {
                throw new IllegalArgumentException("Method: " + methodName + "() is not found on class: " + beanClass.getName() + ". Perhaps the method is protected, private, or misspelled?");
            }
            Method other = METHOD_MAP2.putIfAbsent(methodKey, method);
            if (other != null) {
                method = other;
            }
        }
        return method;
    }

    /**
     * Retrieves a constructor from the specified class that matches the provided parameter types.
     * This method caches the constructors to optimize performance by avoiding repeated reflective lookups.
     * If the specified constructor is not found, an {@link IllegalArgumentException} is thrown.
     *
     * @param clazz          The class from which to retrieve the constructor.
     * @param parameterTypes The parameter types of the constructor to locate.
     * @return The {@link Constructor} object representing the requested constructor.
     * @throws IllegalArgumentException if the constructor does not exist in the specified class.
     */
    public static Constructor<?> getConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        try {
            String key = clazz.getName() + makeParamKey(parameterTypes);
            Constructor<?> constructor = CONSTRUCTORS.get(key);
            if (constructor == null) {
                constructor = clazz.getConstructor(parameterTypes);
                Constructor<?> constructorRef = CONSTRUCTORS.putIfAbsent(key, constructor);
                if (constructorRef != null) {
                    constructor = constructorRef;
                }
            }
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Attempted to get Constructor that did not exist.", e);
        }
    }

    /**
     * Retrieves a method by its name from the specified class, assuming the method is not overloaded.
     * This method caches the found methods to improve performance by avoiding repeated reflective searches.
     * It is essential to provide a unique method name that is not overloaded within the class.
     *
     * @param clazz      The {@link Class} object representing the class containing the desired method.
     * @param methodName The name of the method to locate within the specified class.
     * @return A {@link Method} instance corresponding to the specified method name in the class.
     * @throws IllegalArgumentException if the class is null, the method name is null, or if the method cannot be found.
     */
    @SuppressWarnings({"StringBufferReplaceableByString"})
    public static Method getNonOverloadedMethod(Class<?> clazz, String methodName) {
        if (clazz == null) {
            throw new IllegalArgumentException("Attempted to call getMethod() [" + methodName + "()] on a null class.");
        }
        if (methodName == null) {
            throw new IllegalArgumentException("Attempted to call getMethod() with a null method name on class: " + clazz.getName());
        }
        StringBuilder builder = new StringBuilder(getClassLoaderName(clazz));
        builder.append('.');
        builder.append(clazz.getName());
        builder.append('.');
        builder.append(methodName);
        String methodKey = builder.toString();
        Method method = METHOD_MAP3.get(methodKey);
        if (method == null) {
            method = getMethodNoArgs(clazz, methodName);
            if (method == null) {
                throw new IllegalArgumentException("Method: " + methodName + "() is not found on class: " + clazz.getName() + ". Perhaps the method is protected, private, or misspelled?");
            }
            Method other = METHOD_MAP3.putIfAbsent(methodKey, method);
            if (other != null) {
                method = other;
            }
        }
        return method;
    }

    /**
     * Return the name of the class on the object, or "null" if the object is null.
     *
     * @param o Object to get the class name.
     * @return String name of the class or "null"
     */
    public static String getClassName(Object o) {
        return o == null ? "null" : o.getClass().getName();
    }

    /**
     * Extracts and returns the class name from a byte array representing the compiled bytecode of a Java `.class` file.
     * The method reads the constant pool from the class file, which includes the class name and other relevant metadata.
     * <p>
     * The method handles various constant types (like `CONSTANT_Class`, `CONSTANT_Utf8`, etc.) according to the JVM class file format specification.
     *
     * @param byteCode A byte array representing the compiled Java bytecode.
     * @return A {@link String} containing the fully qualified class name in standard Java format (e.g., "com.example.MyClass").
     * @throws Exception If any IO errors occur while reading the bytecode, or if the bytecode is in an unexpected format.
     */
    public static String getClassNameFromByteCode(byte[] byteCode) throws Exception {
        InputStream is = new ByteArrayInputStream(byteCode);
        DataInputStream dis = new DataInputStream(is);
        dis.readInt(); // magic number
        dis.readShort(); // minor version
        dis.readShort(); // major version
        int cnt = (dis.readShort() & 0xffff) - 1;
        int[] classes = new int[cnt];
        String[] strings = new String[cnt];
        int t = 0;
        for (int i = 0; i < cnt; i++) {
            t = dis.read(); // tag - 1 byte

            if (t == 1) // CONSTANT_Utf8
            {
                strings[i] = dis.readUTF();
            } else if (t == 3 || t == 4) // CONSTANT_Integer || CONSTANT_Float
            {
                dis.readInt(); // bytes
            } else if (t == 5 || t == 6) // CONSTANT_Long || CONSTANT_Double
            {
                dis.readInt(); // high_bytes
                dis.readInt(); // low_bytes
                i++; // All 8-byte constants take up two entries in the constant_pool table of the class file.
            } else if (t == 7) // CONSTANT_Class
            {
                classes[i] = dis.readShort() & 0xffff;
            } else if (t == 8) // CONSTANT_String
            {
                dis.readShort(); // string_index
            } else if (t == 9 || t == 10 || t == 11)  // CONSTANT_Fieldref || CONSTANT_Methodref || CONSTANT_InterfaceMethodref
            {
                dis.readShort(); // class_index
                dis.readShort(); // name_and_type_index
            } else if (t == 12) // CONSTANT_NameAndType
            {
                dis.readShort(); // name_index
                dis.readShort(); // descriptor_index
            } else if (t == 15) // CONSTANT_MethodHandle
            {
                dis.readByte(); // reference_kind
                dis.readShort(); // reference_index
            } else if (t == 16) // CONSTANT_MethodType
            {
                dis.readShort(); // descriptor_index
            } else if (t == 17 || t == 18) // CONSTANT_Dynamic || CONSTANT_InvokeDynamic
            {
                dis.readShort(); // bootstrap_method_attr_index
                dis.readShort(); // name_and_type_index
            } else if (t == 19 || t == 20) // CONSTANT_Module || CONSTANT_Package
            {
                dis.readShort(); // name_index
            } else {
                throw new IllegalStateException("Byte code format exceeds JDK 17 format.");
            }
        }

        dis.readShort(); // access flags
        int thisClassIndex = dis.readShort() & 0xffff; // this_class
        int stringIndex = classes[thisClassIndex - 1];
        String className = strings[stringIndex - 1];
        return className.replace('/', '.');
    }

    /**
     * Retrieves the name of the class loader for a given class. If the class was loaded by the bootstrap class loader
     * (i.e., the system class loader for core Java classes), the method returns "bootstrap". Otherwise, it returns the
     * string representation of the class loader.
     *
     * @param c The {@link Class} whose class loader is being identified.
     * @return A {@link String} representing the name of the class loader, or "bootstrap" if the class loader is null.
     */
    private static String getClassLoaderName(Class<?> c) {
        ClassLoader loader = c.getClassLoader();
        return loader == null ? "bootstrap" : loader.toString();
    }

    /**
     * Reflectively finds a method with the given name on the specified class. This method only matches based on the
     * method name and does not consider argument types. If there are overloaded methods with the same name,
     * it throws an {@link IllegalArgumentException} to avoid ambiguity.
     *
     * @param c          The {@link Class} object on which the method is to be searched.
     * @param methodName The name of the method to find.
     * @return The {@link Method} object for the found method, or null if no matching method is found.
     * @throws IllegalArgumentException If the method name is overloaded, making the search ambiguous.
     */
    private static Method getMethodNoArgs(Class<?> c, String methodName) {
        Method[] methods = c.getMethods();
        Method _method = null;
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                if (_method != null) {
                    throw new IllegalArgumentException("Method: " + methodName + "() called on a class with overloaded methods - ambiguous as to which one to return.  Use getMethod() that takes argument types or argument count.");
                }
                _method = method;
            }
        }
        return _method;
    }

    /**
     * Builds a list of non-static and non-enum fields declared in the given class. This method filters out certain fields
     * that should not be included, such as static fields, synthetic fields specific to enums (e.g., "internal", "ENUM$VALUES"),
     * and internal fields for enums (like "hash" or "ordinal").
     *
     * @param c The {@link Class} object whose declared fields are to be retrieved.
     * @return A {@link List} of {@link Field} objects representing the filtered declared fields of the class.
     * @throws NullPointerException If the input class is null, a null-check is enforced via {@link Vi4j#throwIfNull(Object, String)}.
     */
    private static List<Field> buildDeclaredFields(final Class<?> c) {
        Vi4j.throwIfNull(c, "class cannot be null");
        Field[] fields = c.getDeclaredFields();
        List<Field> list = new ArrayList<>(fields.length);
        for (Field field : fields) {
            if (Modifier.isStatic(field.getModifiers()) ||
                    (field.getDeclaringClass().isEnum() && ("internal".equals(field.getName()) || "ENUM$VALUES".equals(field.getName()))) ||
                    (field.getDeclaringClass().isAssignableFrom(Enum.class) && ("hash".equals(field.getName()) || "ordinal".equals(field.getName())))) {
                continue;
            }
            list.add(field);
        }
        return list;
    }

    /**
     * Adds all interfaces implemented by the given class to the provided stack for further processing.
     * This is typically used in reflection-based methods to ensure that interfaces, in addition to
     * the class hierarchy, are checked when performing operations like searching for annotations or methods.
     *
     * @param classToCheck The {@link Class} object whose interfaces are to be added to the stack.
     * @param stack        The {@link LinkedList} that holds classes for processing, which will be populated with the interfaces of the class.
     */
    private static void addInterfaces(final Class<?> classToCheck, final LinkedList<Class<?>> stack) {
        for (Class<?> interFace : classToCheck.getInterfaces()) {
            stack.push(interFace);
        }
    }

    /**
     * Searches for a method with the specified name and argument count in the given class.
     * This method retrieves all public methods of the class and returns the first method that matches
     * the specified name and has the exact number of parameters.
     * If no matching method is found, it returns null.
     *
     * @param c          The class in which to search for the method.
     * @param methodName The name of the method to find.
     * @param argc       The expected number of arguments for the method.
     * @return The {@link Method} object representing the matching method, or null if no match is found.
     */
    private static Method getMethodWithArgs(Class<?> c, String methodName, int argc) {
        Method[] methods = c.getMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName()) && method.getParameterTypes().length == argc) {
                return method;
            }
        }
        return null;
    }

    /**
     * Constructs a unique key representing the provided parameter types.
     * The key is formatted as a colon (:) followed by the fully qualified names
     * of the parameter types, separated by pipes (|). This key can be used for
     * caching or mapping purposes where distinguishing between different sets
     * of parameter types is required.
     *
     * @param parameterTypes An array of {@link Class} objects representing the parameter types.
     * @return A string key representing the parameter types, or an empty string if no parameter types are provided.
     */
    private static String makeParamKey(Class<?>... parameterTypes) {
        if (parameterTypes == null || parameterTypes.length == 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder(":");
        Iterator<Class<?>> i = Arrays.stream(parameterTypes).iterator();
        while (i.hasNext()) {
            Class<?> param = i.next();
            builder.append(param.getName());
            if (i.hasNext()) {
                builder.append('|');
            }
        }
        return builder.toString();
    }
}