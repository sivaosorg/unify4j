package org.unify4j.common;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

public class Os4j {
    protected final static char SOLIDUS_CHAR = '/';
    protected final static char REVERSE_SOLIDUS_CHAR = '\\';

    /**
     * Normalizes the given path by replacing all backslashes with forward slashes.
     *
     * @param path The path to be normalized.
     * @return The normalized path with all backslashes replaced by forward slashes.
     */
    public static String normalised(String path) {
        return normalised(path, REVERSE_SOLIDUS_CHAR, SOLIDUS_CHAR);
    }

    /**
     * Normalizes the given path by replacing all occurrences of the specified separator with the new separator.
     *
     * @param path      The path to be normalized.
     * @param separator The character to be replaced.
     * @return The normalized path with the specified separator replaced.
     */
    public static String normalised(String path, char separator) {
        return normalised(path, separator, separator);
    }

    /**
     * Normalizes the given path by replacing all occurrences of the specified separator with the new separator
     * and collapsing any consecutive separators into a single separator.
     *
     * @param path         The path to be normalized.
     * @param separator    The character to be replaced.
     * @param newSeparator The character to replace with.
     * @return The normalized path with specified changes.
     */
    public static String normalised(String path, char separator, char newSeparator) {
        if (String4j.isEmpty(path)) {
            return path;
        }
        String filename = path.trim();
        if (separator != newSeparator) {
            filename = filename.replace(separator, newSeparator);
        }
        return filename.replace(String.format("\\%s{2,}", newSeparator), "\\" + newSeparator);
    }

    /**
     * Normalizes the given path by collapsing any unnecessary segments (such as "." and "..") and
     * removing redundant slashes.
     *
     * @param path The path to be normalized.
     * @return The normalized path.
     */
    public static String withNormalised(String path) {
        if (String4j.isEmpty(path)) {
            return path;
        }
        if (path.indexOf('.') == -1 && path.indexOf('/') == -1) {
            return path;
        }
        boolean isNormalized = true;
        int noSegments = 0;
        int lastChar = path.length() - 1;
        for (int src = lastChar; src >= 0; ) {
            int slash = path.lastIndexOf('/', src);
            if (slash != -1) {
                if (slash == src) {
                    if (src != lastChar) {
                        isNormalized = false;
                    }
                } else {
                    noSegments++;
                }
            } else {
                noSegments++;
            }
            src = slash - 1;
        }
        int[] segments = new int[noSegments];
        char[] chars = new char[path.length()];
        path.getChars(0, chars.length, chars, 0);
        noSegments = 0;
        for (int src = 0; src < chars.length; ) {
            while (src < chars.length && chars[src] == '/') {
                src++;
            }
            if (src < chars.length) {
                segments[noSegments++] = src;
                while (src < chars.length && chars[src] != '/') {
                    src++;
                }
            }
        }
        final int DELETED = -1;
        for (int segment = 0; segment < noSegments; segment++) {
            int src = segments[segment];
            if (chars[src++] == '.') {
                if (src == chars.length || chars[src] == '/') {
                    segments[segment] = DELETED;
                    isNormalized = false;
                } else {
                    if (chars[src++] == '.' && (src == chars.length || chars[src] == '/')) {
                        for (int toDelete = segment - 1; toDelete >= 0; toDelete--) {
                            if (segments[toDelete] != DELETED) {
                                if (chars[segments[toDelete]] != '.') {
                                    segments[toDelete] = DELETED;
                                    segments[segment] = DELETED;
                                    isNormalized = false;
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (isNormalized) {
            return path;
        } else {
            int dst = (chars[0] == '/') ? 1 : 0;
            for (int segment = 0; segment < noSegments; segment++) {
                int segmentStart = segments[segment];
                if (segmentStart != DELETED) {
                    for (int src = segmentStart; src < chars.length; src++) {
                        char ch = chars[src];
                        chars[dst++] = ch;
                        if (ch == '/') {
                            break;
                        }
                    }
                }
            }
            return new String(chars, 0, dst);
        }
    }

    /**
     * Fetch value from environment variable and if not set, then fetch from
     * System properties.  If neither available, return null.
     *
     * @param var String key of variable to return
     */
    public static String getExternalVariable(String var) {
        String value = System.getProperty(var);
        if (String4j.isEmpty(value)) {
            value = System.getenv(var);
        }
        return String4j.isEmpty(value) ? null : value;
    }

    /**
     * Checks if the specified file or directory exists.
     *
     * @param filename the path to the file or directory to check; may be null
     * @return {@code true} if the file or directory exists, {@code false} if the path is null or does not exist
     */
    public static boolean exists(Path filename) {
        if (filename == null) {
            return false;
        }
        return Files.exists(filename);
    }

    /**
     * Checks if the specified file or directory exists based on the given filename string.
     *
     * @param filename the name of the file or directory to check; may be empty or null
     * @return {@code true} if the file or directory exists, {@code false} if the filename is empty or null, or the file or directory does not exist
     */
    public static boolean exists(String filename) {
        if (String4j.isEmpty(filename)) {
            return false;
        }
        return exists(toPath(filename));
    }

    /**
     * Converts the given filename string to a normalized, absolute Path object.
     *
     * @param filename the name of the file or directory to convert; must not be empty or null
     * @return a Path object representing the absolute and normalized path of the given filename
     * @throws IllegalArgumentException if the filename is empty or null
     */
    public static Path toPath(String filename) {
        if (String4j.isEmpty(filename)) {
            throw new IllegalArgumentException("toPath, filename must be provided");
        }
        return Paths.get(filename).toAbsolutePath().normalize();
    }

    /**
     * Checks if all specified files or directories exist based on the given array of Path objects.
     *
     * @param filename an array of Path objects representing the files or directories to check; may contain null values
     * @return {@code true} if all specified files or directories exist, {@code false} if any of them do not exist
     */
    public static boolean allExists(Path... filename) {
        List<Boolean> values = new ArrayList<>();
        for (Path path : filename) {
            values.add(exists(path));
        }
        return values.stream().allMatch(item -> item);
    }

    /**
     * Checks if all specified files or directories exist based on the given array of Path objects.
     *
     * @param filename an array of Path objects representing the files or directories to check; may contain null values
     * @return {@code true} if all specified files or directories exist, {@code false} if any of them do not exist
     */
    public static boolean allExists(String... filename) {
        List<Boolean> values = new ArrayList<>();
        for (String path : filename) {
            values.add(exists(path));
        }
        return values.stream().allMatch(item -> item);
    }

    /**
     * Creates a new file at the specified Path location.
     *
     * @param path the Path object representing the location where the new file should be created
     * @return the Path object representing the newly created file
     * @throws IOException if an I/O error occurs or the file already exists
     */
    public static Path createFile(Path path) throws IOException {
        return Files.createFile(path);
    }

    /**
     * Creates a new file at the specified filename location.
     *
     * @param filename the String representing the location where the new file should be created
     * @return the Path object representing the newly created file
     * @throws IOException if an I/O error occurs or the file already exists
     */
    public static Path createFile(String filename) throws IOException {
        return createFile(toPath(filename));
    }

    /**
     * Creates a new file at the specified filename location if it does not already exist.
     *
     * @param filename the String representing the location where the file should be created if needed
     * @return the Path object representing the newly created or existing file
     * @throws IOException if an I/O error occurs
     */
    public static Path createFileIfNeeded(String filename) throws IOException {
        if (!exists(filename)) {
            return createFile(filename);
        }
        return toPath(filename);
    }

    /**
     * Creates a new file at the specified Path location if it does not already exist.
     *
     * @param filename the Path representing the location where the file should be created if needed
     * @return the Path object representing the newly created or existing file
     * @throws IOException if an I/O error occurs
     */
    public static Path createFileIfNeeded(Path filename) throws IOException {
        if (!exists(filename)) {
            return createFile(filename);
        }
        return filename;
    }

    /**
     * Writes the specified event string to the file at the given Path if it does not already exist.
     * If the file does not exist, it will be created.
     *
     * @param filename the Path representing the location where the file should be written
     * @param event    the string content to be written to the file
     * @param options  options specifying how the file is opened
     * @return the Path object representing the file where the content was written
     * @throws IOException if an I/O error occurs
     */
    public static Path writeFileIfNotExist(Path filename, String event, StandardOpenOption... options) throws IOException {
        filename = createFileIfNeeded(filename);
        Files.write(filename, event.getBytes(), options);
        return filename;
    }

    /**
     * Writes the specified event string to the file at the given filename if it does not already exist.
     * If the file does not exist, it will be created.
     *
     * @param filename the filename representing the location where the file should be written
     * @param event    the string content to be written to the file
     * @param options  options specifying how the file is opened
     * @return the Path object representing the file where the content was written
     * @throws IOException if an I/O error occurs
     */
    public static Path writeFileIfNotExist(String filename, String event, StandardOpenOption... options) throws IOException {
        return writeFileIfNotExist(toPath(filename), event, options);
    }

    /**
     * Writes the specified event string asynchronously to the file at the given filename if it does not already exist.
     * If the file does not exist, it will be created.
     *
     * @param filename the filename representing the location where the file should be written
     * @param event    the string content to be written to the file
     * @param allocate the size of the buffer to allocate, e.g: 1024, 4096
     * @param options  options specifying how the file is opened
     * @return the Path object representing the file where the content was written
     * @throws IllegalArgumentException if allocate is less than 0
     * @throws RuntimeException         if an error occurs during the asynchronous write operation
     */
    public static Path writeFileAsyncFutureIfNeeded(Path filename, String event, int allocate, StandardOpenOption... options) {
        if (allocate < 0) {
            throw new IllegalArgumentException("writeFileAsyncFutureIfNeeded, Allocate must be positive number");
        }
        try {
            // Ensure the file exists or create a new one
            filename = createFileIfNeeded(filename);
            // Prepare the buffer for writing
            ByteBuffer buffer = ByteBuffer.allocate(allocate);
            buffer.put(event.getBytes());
            buffer.flip();
            // Open the asynchronous file channel
            try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(filename, options)) {
                // Initiate the asynchronous write operation
                Future<Integer> future = channel.write(buffer, 0);
                // Wait for the write operation to complete
                while (!future.isDone()) {
                    System.out.println("Waiting for async write operation...");
                }
                buffer.clear();
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return filename;
    }

    /**
     * Writes the specified event string asynchronously to the file at the given filename if it does not already exist.
     * If the file does not exist, it will be created.
     *
     * @param filename the filename representing the location where the file should be written
     * @param event    the string content to be written to the file
     * @param allocate the size of the buffer to allocate, e.g: 1024, 4096
     * @param options  options specifying how the file is opened
     * @return the Path object representing the file where the content was written
     * @throws IllegalArgumentException if allocate is less than 0
     * @throws RuntimeException         if an error occurs during the asynchronous write operation
     */
    public static Path writeFileAsyncFutureIfNeeded(String filename, String event, int allocate, StandardOpenOption... options) {
        return writeFileAsyncFutureIfNeeded(toPath(filename), event, allocate, options);
    }

    /**
     * Writes the specified event string asynchronously to the file at the given filename if it does not already exist,
     * using a CompletionHandler for handling completion or failure of the write operation.
     * If the file does not exist, it will be created.
     *
     * @param filename the Path object representing the location where the file should be written
     * @param event    the string content to be written to the file
     * @param allocate the size of the buffer to allocate
     * @param options  options specifying how the file is opened
     * @return the Path object representing the file where the content was written
     * @throws IllegalArgumentException if allocate is less than 0
     * @throws RuntimeException         if an error occurs during the asynchronous write operation
     */
    public static Path writeFileAsyncHandlerIfNeeded(Path filename, String event, int allocate, StandardOpenOption... options) throws Exception {
        if (allocate < 0) {
            throw new IllegalArgumentException("writeFileAsyncHandlerIfNeeded, Allocate must be a positive number");
        }
        // Ensure the file exists or create a new one if it doesn't
        filename = createFileIfNeeded(filename);
        ByteBuffer buffer = ByteBuffer.allocate(allocate);
        buffer.put(event.getBytes());
        buffer.flip();

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(filename, options)) {
            // Write asynchronously to the file using CompletionHandler
            channel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    System.out.println("Total bytes written... " + result);
                }

                @Override
                public void failed(Throwable e, ByteBuffer attachment) {
                    System.err.println("Write operation failed: " + e.getMessage());
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return filename;
    }

    /**
     * Writes the specified event string asynchronously to the file at the given filename if it does not already exist,
     * using a CompletionHandler for handling completion or failure of the write operation.
     * If the file does not exist, it will be created.
     *
     * @param filename the Path object representing the location where the file should be written
     * @param event    the string content to be written to the file
     * @param allocate the size of the buffer to allocate
     * @param options  options specifying how the file is opened
     * @return the Path object representing the file where the content was written
     * @throws IllegalArgumentException if allocate is less than 0
     * @throws RuntimeException         if an error occurs during the asynchronous write operation
     */
    public static Path writeFileAsyncHandlerIfNeeded(String filename, String event, int allocate, StandardOpenOption... options) throws Exception {
        return writeFileAsyncHandlerIfNeeded(toPath(filename), event, allocate, options);
    }
}
