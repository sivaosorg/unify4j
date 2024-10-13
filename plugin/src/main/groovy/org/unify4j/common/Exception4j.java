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

    /**
     * Retrieves the class name and line number from the stack trace of the provided
     * exception. This method is used to extract and return a string representation of
     * where the exception occurred, specifically the class and line number of the first
     * element in the stack trace.
     * <p>
     * If the exception or its stack trace is null or empty, the method returns an empty
     * string or "Unknown" as a fallback.
     *
     * @param e The {@link Exception} from which to extract the stack trace information.
     * @return A {@link String} representing the class name and line number in the format
     * "ClassName:LineNumber", or "Unknown" if the stack trace is not available.
     */
    public static String getClassLine(Exception e) {
        if (e == null) {
            return "";
        }
        StackTraceElement[] trace = e.getStackTrace();
        return (trace != null && trace.length > 0)
                ? trace[0].getClassName() + ":" + trace[0].getLineNumber()
                : "Unknown";
    }

    /**
     * Retrieves the calling class name and line number from the stack trace of the current thread.
     * This method extracts and returns a string representation of where the exception was triggered,
     * specifically the class and line number of the caller of this method.
     * <p>
     * If the exception or its stack trace is null or empty, the method returns an empty
     * string or "Unknown" as a fallback.
     *
     * @return A {@link String} representing the class name and line number in the format
     * "ClassName:LineNumber", or "Unknown" if the stack trace is not available.
     */
    public static String getClassLine() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        // Check if we have enough stack trace elements
        if (trace.length < 3) {
            return "Unknown";
        }
        // The 3rd element is the caller of this method
        StackTraceElement caller = trace[2]; // Adjust the index if needed based on your stack
        return caller.getClassName() + ":" + caller.getLineNumber();
    }

}
