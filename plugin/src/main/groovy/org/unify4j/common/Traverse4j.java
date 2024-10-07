package org.unify4j.common;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

/**
 * A utility class for traversing Java object graphs. This class visits all object reference fields
 * in a Java object and calls a passed-in Visitor instance for each object encountered, including
 * the root. It can detect and avoid cycles in the object graph.
 */
public class Traverse4j {

    /**
     * An interface to be implemented by classes that want to process objects during traversal.
     * The `#process()` method will be called for every object encountered in the traversal.
     */
    public interface Visitor {
        void process(Object o);
    }

    protected final Map<Class<?>, ClassInfo> _classCache = new HashMap<>();
    private final Map<Object, Object> _objVisited = new IdentityHashMap<>();

    /**
     * Public method to start traversing the object graph starting from the root object.
     * It uses a visitor to process each object and avoids cycles.
     *
     * @param o       The root object from which the traversal starts.
     * @param visitor The Visitor instance that processes each object during traversal.
     */
    public static void traverse(Object o, Visitor visitor) {
        traverse(o, null, visitor);
    }

    /**
     * Public method to start traversing the object graph with a list of classes to skip.
     * Uses a visitor to process each object encountered during the traversal.
     *
     * @param o       The root object from which the traversal starts.
     * @param skip    An array of class types to be skipped during traversal.
     * @param visitor The Visitor instance that processes each object during traversal.
     */
    public static void traverse(Object o, Class<?>[] skip, Visitor visitor) {
        Traverse4j traverse = new Traverse4j();
        traverse.walk(o, skip, visitor);
        traverse._objVisited.clear();
        traverse._classCache.clear();
    }

    /**
     * Traverses the object graph starting from the root object. Handles arrays, collections, maps,
     * and regular objects, while avoiding cycles.
     *
     * @param root    The root object to start the traversal from.
     * @param skip    An array of class types to be skipped during traversal (can be null).
     * @param visitor The Visitor instance that processes each object during traversal.
     */
    public void walk(Object root, Class<?>[] skip, Visitor visitor) {
        Deque<Object> stack = new LinkedList<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            Object current = stack.removeFirst();
            if (current == null || _objVisited.containsKey(current)) {
                continue;
            }
            final Class<?> clazz = current.getClass();
            ClassInfo classInfo = getClassInfo(clazz, skip);
            if (classInfo._skip) {  // Do not process any classes that are assignableFrom the skip classes list.
                continue;
            }
            _objVisited.put(current, null);
            visitor.process(current);
            if (clazz.isArray()) {
                final int len = Array.getLength(current);
                Class<?> compType = clazz.getComponentType();
                if (!compType.isPrimitive()) {   // Speed up: do not walk primitives
                    ClassInfo info = getClassInfo(compType, skip);
                    if (!info._skip) {   // Do not walk array elements of a class type that is to be skipped.
                        for (int i = 0; i < len; i++) {
                            Object element = Array.get(current, i);
                            if (element != null) {   // Skip processing null array elements
                                stack.add(Array.get(current, i));
                            }
                        }
                    }
                }
            } else {   // Process fields of an object instance
                if (current instanceof Collection) {
                    walkCollection(stack, (Collection<?>) current);
                } else if (current instanceof Map) {
                    walkMap(stack, (Map<?, ?>) current);
                } else {
                    walkFields(stack, current, skip);
                }
            }
        }
    }

    /**
     * Processes all reference fields of the given object by adding them to the traversal stack.
     * It skips fields that are static, primitive, or part of the skipped class list.
     *
     * @param stack   The stack used to keep track of objects to be visited during traversal.
     * @param current The current object whose fields are being traversed.
     * @param skip    Array of classes to skip (can be null).
     */
    private void walkFields(Deque<Object> stack, Object current, Class<?>[] skip) {
        ClassInfo classInfo = getClassInfo(current.getClass(), skip);
        for (Field field : classInfo._refFields) {
            try {
                Object value = field.get(current);
                if (value == null || value.getClass().isPrimitive()) {
                    continue;
                }
                stack.add(value);
            } catch (IllegalAccessException ignored) {
            }
        }
    }

    /**
     * Processes all elements in a collection by adding them to the traversal stack.
     * It skips null or primitive elements.
     *
     * @param stack The stack used to keep track of objects to be visited during traversal.
     * @param col   The collection being traversed.
     */
    private static void walkCollection(Deque<Object> stack, Collection<?> col) {
        for (Object o : col) {
            if (o != null && !o.getClass().isPrimitive()) {
                stack.add(o);
            }
        }
    }

    /**
     * Processes all keys and values in a map by adding them to the traversal stack.
     * It skips null or primitive keys and values.
     *
     * @param stack The stack used to keep track of objects to be visited during traversal.
     * @param map   The map being traversed.
     */
    private static void walkMap(Deque<Object> stack, Map<?, ?> map) {
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            Object o = entry.getKey();
            if (o != null && !o.getClass().isPrimitive()) {
                stack.add(entry.getKey());
                stack.add(entry.getValue());
            }
        }
    }

    /**
     * Retrieves or creates metadata (ClassInfo) for a class, including its fields.
     * It caches the information to avoid redundant reflective calls.
     *
     * @param current The class for which the ClassInfo is being retrieved.
     * @param skip    An array of classes to be skipped during traversal.
     * @return A ClassInfo object containing metadata for the class.
     */
    private ClassInfo getClassInfo(Class<?> current, Class<?>[] skip) {
        ClassInfo classCache = _classCache.get(current);
        if (classCache != null) {
            return classCache;
        }
        classCache = new ClassInfo(current, skip);
        _classCache.put(current, classCache);
        return classCache;
    }

    /**
     * This class wraps a class in order to cache the fields so they
     * are only reflectively obtained once.
     */
    public static class ClassInfo {
        private boolean _skip = false;
        private final Collection<Field> _refFields = new ArrayList<>();

        /**
         * ClassInfo stores metadata about a class, including its reference fields. It is used to cache
         * this information to avoid repeated reflective field look.ups.
         *
         * @param c    The class for which metadata is being constructed.
         * @param skip An array of classes to be skipped during traversal.
         */
        public ClassInfo(Class<?> c, Class<?>[] skip) {
            if (skip != null) {
                for (Class<?> klass : skip) {
                    if (klass.isAssignableFrom(c)) {
                        _skip = true;
                        return;
                    }
                }
            }
            Collection<Field> fields = Reflection4j.getDeepDeclaredFields(c);
            for (Field field : fields) {
                Class<?> fc = field.getType();

                if (!fc.isPrimitive()) {
                    _refFields.add(field);
                }
            }
        }
    }
}
