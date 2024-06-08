package org.unify4j.model.c;

import org.jetbrains.annotations.NotNull;
import org.unify4j.common.String4j;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"SpellCheckingInspection"})
public class Method implements Comparable<Method> {
    protected static final Map<String, Method> _methods = new ConcurrentHashMap<>();
    private final String description;
    private final boolean idempotent;
    private final String name;
    private final boolean replying;
    private final boolean safe;

    /**
     * Pseudo-method use to match all methods.
     */
    public static final Method ALL = new Method("*", "Pseudo-method use to match all methods.");

    /**
     * Used with a proxy that can dynamically switch to being a tunnel.
     *
     * @see <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.9">HTTP
     * RFC - 9.9 CONNECT</a>
     */
    public static final Method CONNECT = new Method("CONNECT", "Used with a proxy that can dynamically switch to being a tunnel", false, false);

    /**
     * Creates a duplicate of the source resource, identified by the
     * Request-URI, in the destination resource, identified by the URI in the
     * Destination header.
     *
     * @see <a
     * href="http://www.webdav.org/specs/rfc2518.html#METHOD_COPY">WEBDAV
     * RFC - 8.8 COPY Method</a>
     */
    public static final Method COPY = new Method("COPY", "Creates a duplicate of the source resource, identified by the Request-URI, in the destination resource, identified by the URI in the Destination header", false, true);

    /**
     * Requests that the origin server deletes the resource identified by the
     * request URI.
     *
     * @see <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.7">HTTP
     * RFC - 9.7 DELETE</a>
     */
    public static final Method DELETE = new Method("DELETE", "Requests that the origin server deletes the resource identified by the request URI", false, true);

    /**
     * Retrieves whatever information (in the form of an entity) that is
     * identified by the request URI.
     *
     * @see <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.3">HTTP
     * RFC - 9.3 GET</a>
     */
    public static final Method GET = new Method("GET", "Retrieves whatever information (in the form of an entity) that is identified by the request URI", true, true);

    /**
     * Identical to GET except that the server must not return a message body in
     * the response but only the message header.
     *
     * @see <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.4">HTTP
     * RFC - 9.4 HEAD</a>
     */
    public static final Method HEAD = new Method("HEAD", "Identical to GET except that the server must not return a message body in the response", true, true);

    /**
     * Used to take out a lock of any access type on the resource identified by
     * the request URI.
     *
     * @see <a
     * href="http://www.webdav.org/specs/rfc2518.html#METHOD_LOCK">WEBDAV
     * RFC - 8.10 LOCK Method</a>
     */
    public static final Method LOCK = new Method("LOCK", "Used to take out a lock of any access type (WebDAV)", true, false);

    /**
     * MKCOL creates a new collection resource at the location specified by the
     * Request URI.
     *
     * @see <a
     * href="http://www.webdav.org/specs/rfc2518.html#METHOD_MKCOL">WEBDAV
     * RFC - 8.3 MKCOL Method</a>
     */
    public static final Method MKCOL = new Method("MKCOL", "Used to create a new collection (WebDAV)", false, true);

    /**
     * Logical equivalent of a copy, followed by consistency maintenance
     * processing, followed by a deleted of the source where all three actions
     * are performed atomically.
     *
     * @see <a
     * href="http://www.webdav.org/specs/rfc2518.html#METHOD_MOVE">WEBDAV
     * RFC - 8.3 MKCOL Method</a>
     */
    public static final Method MOVE = new Method("MOVE", "Logical equivalent of a copy, followed by consistency maintenance processing, followed by a delete of the source (WebDAV)", false, false);

    /**
     * Requests for information about the communication options available on the
     * request/response chain identified by the URI.
     *
     * @see <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.2">HTTP
     * RFC - 9.2 OPTIONS</a>
     */
    public static final Method OPTIONS = new Method("OPTIONS", "Requests for information about the communication options available on the request/response chain identified by the URI", true, true);

    /**
     * Requests that the origin server accepts the entity enclosed in the
     * request as a new subordinate of the resource identified by the request
     * URI.
     *
     * @see <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.5">HTTP
     * RFC - 9.5 POST</a>
     */
    public static final Method POST = new Method("POST", "Requests that the origin server accepts the entity enclosed in the request as a new subordinate of the resource identified by the request URI", false, false);

    /**
     * Retrieves properties defined on the resource identified by the request
     * URI.
     *
     * @see <a
     * href="http://www.webdav.org/specs/rfc2518.html#METHOD_PROPFIND">WEBDAV
     * RFC - 8.1 PROPFIND</a>
     */
    public static final Method PROPFIND = new Method("PROPFIND", "Retrieves properties defined on the resource identified by the request URI", true, true);

    /**
     * Processes instructions specified in the request body to set and/or remove
     * properties defined on the resource identified by the request URI.
     *
     * @see <a
     * href="http://www.webdav.org/specs/rfc2518.html#METHOD_PROPPATCH">WEBDAV
     * RFC - 8.2 PROPPATCH</a>
     */
    public static final Method PROPPATCH = new Method("PROPPATCH", "Processes instructions specified in the request body to set and/or remove properties defined on the resource identified by the request URI", false, true);

    /**
     * Requests that the enclosed entity be stored under the supplied request
     * URI.
     *
     * @see <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.6"
     * HTTP RFC - 9.6 PUT</a>
     */
    public static final Method PUT = new Method("PUT", "Requests that the enclosed entity be stored under the supplied request URI", false, true);

    /**
     * Used to invoke a remote, application-layer loop-back of the request
     * message.
     *
     * @see <a
     * href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec9.html#sec9.8">HTTP
     * RFC - 9.8 TRACE</a>
     */
    public static final Method TRACE = new Method("TRACE", "Used to invoke a remote, application-layer loop-back of the request message", true, true);

    /**
     * Removes the lock identified by the lock token from the request URI, and
     * all other resources included in the lock.
     *
     * @see <a
     * href="http://www.webdav.org/specs/rfc2518.html#METHOD_UNLOCK">WEBDAV
     * RFC - 8.11 UNLOCK Method</a>
     */
    public static final Method UNLOCK = new Method("UNLOCK", "Removes the lock identified by the lock token from the request URI, and all other resources included in the lock", true, false);

    /**
     * Constructor for unsafe and non-idempotent methods.
     *
     * @param name The technical name of the method.
     */
    public Method(final String name) {
        this(name, null);
    }

    /**
     * Constructor for unsafe and non-idempotent methods.
     *
     * @param name        The technical name.
     * @param description The description.
     */
    public Method(String name, String description) {
        this(name, description, false, false);
    }

    /**
     * Constructor for methods that reply to requests with responses.
     *
     * @param name        The technical name.
     * @param description The description.
     * @param safe        Indicates if the method is safe.
     * @param idempotent  Indicates if the method is idempotent.
     */
    public Method(String name, String description, boolean safe, boolean idempotent) {
        this(name, description, safe, idempotent, true);
    }

    /**
     * Constructor.
     *
     * @param name        The technical name.
     * @param description The description.
     * @param safe        Indicates if the method is safe.
     * @param idempotent  Indicates if the method is idempotent.
     * @param replying    Indicates if the method replies with a response.
     */
    public Method(String name, String description, boolean safe, boolean idempotent, boolean replying) {
        this.name = name;
        this.description = description;
        this.safe = safe;
        this.idempotent = idempotent;
        this.replying = replying;
    }

    public String getDescription() {
        return this.description;
    }

    public String getName() {
        return name;
    }

    /**
     * Indicates if the side effects of several requests is the same as a single
     * request.
     *
     * @return True if the method is idempotent.
     */
    public boolean isIdempotent() {
        return idempotent;
    }

    /**
     * Indicates if the method replies with a response.
     *
     * @return True if the method replies with a response.
     */
    public boolean isReplying() {
        return replying;
    }

    /**
     * Indicates if it should have the significance of taking an action other
     * than retrieval.
     *
     * @return True if the method is safe.
     */
    public boolean isSafe() {
        return safe;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(@NotNull Method o) {
        return this.getName().compareTo(o.getName());
    }

    @Override
    public boolean equals(final Object object) {
        return (object instanceof Method) && ((Method) object).getName().equals(getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return (getName() == null) ? 0 : getName().hashCode();
    }

    /**
     * Adds a new Method to the list of registered methods.
     *
     * @param method The method to register.
     */
    public static void register(Method method) {
        String name = (method == null) ? null : method.getName().toLowerCase();
        if (String4j.isNotEmpty(name)) {
            _methods.put(name, method);
        }
    }

    /**
     * Sorts the given list of methods by name.
     *
     * @param methods The methods to sort.
     */
    @SuppressWarnings({"Java8ListSort"})
    public static void sort(List<Method> methods) {
        Collections.sort(methods, new Comparator<Method>() {
            public int compare(Method m1, Method m2) {
                return m1.getName().compareTo(m2.getName());
            }
        });
    }

    /**
     * Returns the method associated to a given method name. If an existing
     * constant exists then it is returned, otherwise a new instance is created.
     *
     * @param name The method name.
     * @return The associated method.
     */
    public static Method valueOf(final String name) {
        Method result = null;
        if (String4j.isNotEmpty(name)) {
            result = Method._methods.get(name.toLowerCase());
            if (result == null) {
                result = new Method(name);
            }
        }
        return result;
    }
}
