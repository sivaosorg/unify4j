package org.unify4j.common;

public final class Exception4j {

    /**
     * Safely ignores a given Throwable or rethrows it if it is an error that should not be ignored.
     * Specifically, it checks for {@link OutOfMemoryError} and rethrows it, as it should not be suppressed.
     * Other types of {@link Throwable} are ignored.
     *
     * @param t The {@link Throwable} to possibly ignore. If it is an instance of {@link OutOfMemoryError}, it is rethrown.
     */
    public static void safelyIgnoreException(Throwable t) {
        if (t instanceof OutOfMemoryError) {
            throw (OutOfMemoryError) t;
        }
    }

    /**
     * Retrieves the deepest (most nested) cause of a given {@link Throwable}.
     * This method iteratively traverses the exception chain via {@link Throwable#getCause()}
     * to find and return the root cause of the exception.
     *
     * @param e The {@link Throwable} whose deepest cause is to be found.
     * @return The deepest (root) cause {@link Throwable}.
     */
    public static Throwable getDeepestException(Throwable e) {
        while (e.getCause() != null) {
            e = e.getCause();
        }
        return e;
    }
}
