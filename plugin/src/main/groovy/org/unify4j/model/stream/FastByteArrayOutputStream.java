package org.unify4j.model.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class FastByteArrayOutputStream extends OutputStream {
    // The buffer where data is stored
    private byte[] buffer;
    // The number of valid bytes in the buffer
    private int size;

    /**
     * Creates a new FastByteArrayOutputStream with a default initial capacity of 32 bytes.
     */
    public FastByteArrayOutputStream() {
        this(32);
    }

    /**
     * Creates a new FastByteArrayOutputStream with the specified initial capacity.
     *
     * @param initialCapacity the initial buffer size
     * @throws IllegalArgumentException if the initial capacity is negative
     */
    public FastByteArrayOutputStream(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException(String.format("Negative initial size: %d", initialCapacity));
        }
        buffer = new byte[initialCapacity];
    }

    /**
     * Ensures that the buffer has enough capacity to accommodate the specified minimum capacity.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void ensureCapacity(int minCapacity) {
        if (minCapacity - buffer.length > 0) {
            grow(minCapacity);
        }
    }

    /**
     * Increases the capacity of the buffer to accommodate the specified minimum capacity.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        int oldCapacity = buffer.length;
        int newCapacity = oldCapacity << 1; // Double the capacity
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        buffer = Arrays.copyOf(buffer, newCapacity);
    }

    /**
     * Writes the specified byte to this output stream.
     *
     * @param b the byte to be written
     */
    @Override
    public void write(int b) {
        ensureCapacity(size + 1);
        buffer[size] = (byte) b;
        size += 1;
    }

    /**
     * Writes len bytes from the specified byte array starting at offset off to this output stream.
     *
     * @param b   the data
     * @param off the start offset in the data
     * @param len the number of bytes to write
     * @throws IndexOutOfBoundsException if the preconditions on the offset and length are not met
     */
    @Override
    public void write(byte[] b, int off, int len) {
        if ((b == null) || (off < 0) || (len < 0) || (off > b.length) || (off + len > b.length) || (off + len < 0)) {
            throw new IndexOutOfBoundsException("Invalid offset or length");
        }
        ensureCapacity(size + len);
        System.arraycopy(b, off, buffer, size, len);
        size += len;
    }

    /**
     * Writes all the bytes from the specified byte array to this output stream.
     *
     * @param b the data to be written
     */
    public void writeBytes(byte[] b) {
        write(b, 0, b.length);
    }

    /**
     * Resets the count field of this output stream to zero, so that all currently accumulated output in the output stream is discarded.
     */
    public void reset() {
        size = 0;
    }

    /**
     * Creates a newly allocated byte array. Its size is the current size of this output stream and the valid contents of the buffer have been copied into it.
     *
     * @return the current contents of this output stream as a byte array
     */
    public byte[] toByteArray() {
        return Arrays.copyOf(buffer, size);
    }

    /**
     * For backward compatibility. Use toByteArray() instead.
     *
     * @return the current contents of this output stream as a byte array
     */
    public byte[] getBuffer() {
        return toByteArray();
    }

    /**
     * Returns the current size of the buffer.
     *
     * @return the number of valid bytes in the buffer
     */
    public int size() {
        return size;
    }

    /**
     * Converts the buffer's contents into a string, interpreting bytes as characters in the platform's default character set.
     *
     * @return the buffer contents as a string
     */
    @Override
    public String toString() {
        return new String(buffer, 0, size);
    }

    /**
     * Writes the contents of this byte array output stream to the specified output stream argument.
     *
     * @param out the output stream to which to write the data
     * @throws IOException if an I/O error occurs
     */
    public void writeTo(OutputStream out) throws IOException {
        out.write(buffer, 0, size);
    }

    /**
     * Closes this output stream and releases any system resources associated with this stream. This implementation does nothing.
     */
    @Override
    public void close() throws IOException {
        // No resources to close, but method is provided for compatibility
    }
}