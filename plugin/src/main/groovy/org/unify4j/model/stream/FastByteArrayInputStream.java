package org.unify4j.model.stream;

import java.io.IOException;
import java.io.InputStream;

public class FastByteArrayInputStream extends InputStream {
    // The byte array buffer from which the stream reads
    private final byte[] buffer;

    // The total number of valid bytes in the buffer
    private final int bufferLength;

    // The current position in the buffer
    private int currentPosition;

    // The position in the buffer marked by the mark() method
    private int markedPosition = 0;

    /**
     * Constructs a new FastByteArrayInputStream using the specified byte array.
     *
     * @param buf the byte array to read from
     */
    public FastByteArrayInputStream(byte[] buf) {
        this.buffer = buf;
        this.currentPosition = 0;
        this.bufferLength = buf.length;
    }

    /**
     * Reads the next byte of data from the input stream.
     *
     * @return the next byte of data, or -1 if the end of the stream is reached
     */
    @Override
    public int read() {
        return (currentPosition < bufferLength) ? (buffer[currentPosition++] & 0xff) : -1;
    }

    /**
     * Reads up to len bytes of data from the input stream into an array of bytes.
     *
     * @param b   the buffer into which the data is read
     * @param off the start offset in array b at which the data is written
     * @param len the maximum number of bytes to read
     * @return the total number of bytes read into the buffer, or -1 if there is no more data because the end of the stream has been reached
     */
    @Override
    public int read(byte[] b, int off, int len) {
        if (b == null) {
            throw new NullPointerException("Buffer is null");
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException("Offset or length is out of bounds");
        } else if (currentPosition >= bufferLength) {
            return -1; // End of stream reached
        }

        int availableBytes = bufferLength - currentPosition;
        if (len > availableBytes) {
            len = availableBytes;
        }
        if (len <= 0) {
            return 0; // No bytes read
        }
        System.arraycopy(buffer, currentPosition, b, off, len);
        currentPosition += len;
        return len;
    }

    /**
     * Skips over and discards n bytes of data from this input stream.
     *
     * @param n the number of bytes to be skipped
     * @return the actual number of bytes skipped
     */
    @Override
    public long skip(long n) {
        long bytesToSkip = bufferLength - currentPosition;
        if (n < bytesToSkip) {
            bytesToSkip = n < 0 ? 0 : n;
        }

        currentPosition += (int) bytesToSkip;
        return bytesToSkip;
    }

    /**
     * Returns an estimate of the number of bytes that can be read (or skipped over) from this input stream without blocking.
     *
     * @return the number of bytes that can be read from this input stream without blocking
     */
    @Override
    public int available() {
        return bufferLength - currentPosition;
    }

    /**
     * Marks the current position in this input stream. A subsequent call to the reset() method repositions this stream at the last marked position so that subsequent reads re-read the same bytes.
     *
     * @param readLimit the maximum limit of bytes that can be read before the mark position becomes invalid
     */
    @Override
    public void mark(int readLimit) {
        markedPosition = currentPosition;
    }

    /**
     * Repositions this stream to the position at the time the mark method was last called on this input stream.
     */
    @Override
    public void reset() {
        currentPosition = markedPosition;
    }

    /**
     * Tests if this input stream supports the mark and reset methods.
     *
     * @return true, as this class supports the mark and reset methods
     */
    @Override
    public boolean markSupported() {
        return true;
    }

    /**
     * Closes this input stream and releases any system resources associated with the stream. This implementation does nothing.
     */
    @Override
    public void close() throws IOException {
        // No resources to release, but method is provided for compatibility
    }
}
