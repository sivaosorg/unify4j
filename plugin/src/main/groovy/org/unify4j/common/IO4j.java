package org.unify4j.common;

import org.unify4j.model.stream.AdjustableGzipOutputStream;
import org.unify4j.model.stream.FastByteArrayOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.zip.*;

public class IO4j {
    protected static final int BUFFER_SIZE = 32768;  // Buffer size used for transferring data

    /**
     * Gets an InputStream from a URLConnection, applying decompression if needed.
     *
     * @param connection the URLConnection to read from
     * @return a BufferedInputStream, potentially wrapped with decompression
     * @throws IOException if an I/O error occurs
     */
    public static InputStream getInputStream(URLConnection connection) throws IOException {
        InputStream in = connection.getInputStream();
        String encoding = connection.getContentEncoding();
        if ("gzip".equalsIgnoreCase(encoding) || "x-gzip".equalsIgnoreCase(encoding)) {
            in = new GZIPInputStream(in, BUFFER_SIZE);
        } else if ("deflate".equalsIgnoreCase(encoding)) {
            in = new InflaterInputStream(in, new Inflater(), BUFFER_SIZE);
        }
        return new BufferedInputStream(in);
    }

    /**
     * Transfers a file to a URLConnection, invoking a callback during the transfer.
     *
     * @param file       the file to transfer
     * @param connection the URLConnection to transfer to
     * @param callback   the callback for transfer events
     * @throws Exception if an error occurs during transfer
     */
    public static void transfer(File file, URLConnection connection, TransferCallback callback) throws Exception {
        try (InputStream in = new BufferedInputStream(Files.newInputStream(file.toPath())); OutputStream out = new BufferedOutputStream(connection.getOutputStream())) {
            transfer(in, out, callback);
        }
    }

    /**
     * Transfers data from a URLConnection to a file, invoking a callback during the transfer.
     *
     * @param connection the URLConnection to read from
     * @param file       the file to write to
     * @param callback   the callback for transfer events
     * @throws Exception if an error occurs during transfer
     */
    public static void transfer(URLConnection connection, File file, TransferCallback callback) throws Exception {
        try (InputStream in = getInputStream(connection)) {
            transfer(in, file, callback);
        }
    }

    /**
     * Transfers data from an InputStream to a file, invoking a callback during the transfer.
     *
     * @param in       the InputStream to read from
     * @param file     the file to write to
     * @param callback the callback for transfer events
     * @throws Exception if an error occurs during transfer
     */
    public static void transfer(InputStream in, File file, TransferCallback callback) throws Exception {
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(file.toPath()))) {
            transfer(in, out, callback);
        }
    }

    /**
     * Transfers data from an InputStream to an OutputStream, invoking a callback during the transfer.
     * Callers of this method are responsible for closing the streams.
     *
     * @param in       the InputStream to read from
     * @param out      the OutputStream to write to
     * @param callback the callback for transfer events
     * @throws IOException if an I/O error occurs
     */
    public static void transfer(InputStream in, OutputStream out, TransferCallback callback) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
            if (callback != null) {
                callback.bytesTransferred(buffer, bytesRead);
                if (callback.isCancelled()) {
                    break;
                }
            }
        }
    }

    /**
     * Transfers bytes from an InputStream to a byte array.
     *
     * @param in     the InputStream to read from
     * @param buffer the byte array to write to
     * @throws IOException if an I/O error occurs
     */
    public static void transfer(InputStream in, byte[] buffer) throws IOException {
        int offset = 0;
        int bytesRead;
        while (offset < buffer.length && (bytesRead = in.read(buffer, offset, buffer.length - offset)) >= 0) {
            offset += bytesRead;
        }
        if (offset < buffer.length) {
            throw new IOException("Retry: Not all bytes were transferred correctly.");
        }
    }

    /**
     * Transfers data from an InputStream to an OutputStream without a callback.
     * Callers of this method are responsible for closing the streams.
     *
     * @param in  the InputStream to read from
     * @param out the OutputStream to write to
     * @throws IOException if an I/O error occurs
     */
    public static void transfer(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }
    }

    /**
     * Transfers a file to an OutputStream.
     *
     * @param file the file to read from
     * @param out  the OutputStream to write to
     * @throws IOException if an I/O error occurs
     */
    public static void transfer(File file, OutputStream out) throws IOException {
        try (InputStream inputStream = new BufferedInputStream(Files.newInputStream(file.toPath()), BUFFER_SIZE)) {
            transfer(inputStream, out);
        } finally {
            flush(out);
        }
    }

    /**
     * Closes an XMLStreamReader quietly.
     *
     * @param reader the XMLStreamReader to close
     */
    public static void close(XMLStreamReader reader) {
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (XMLStreamException ignore) {
        }
    }

    /**
     * Closes an XMLStreamWriter quietly.
     *
     * @param writer the XMLStreamWriter to close
     */
    public static void close(XMLStreamWriter writer) {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (XMLStreamException ignore) {
        }
    }

    /**
     * Closes a Closeable resource quietly.
     *
     * @param closeable the Closeable resource to close
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ignore) {
        }
    }

    /**
     * Flushes a Flushable resource quietly.
     *
     * @param flushable the Flushable resource to flush
     */
    public static void flush(Flushable flushable) {
        try {
            if (flushable != null) {
                flushable.flush();
            }
        } catch (IOException ignore) {
        }
    }

    /**
     * Flushes an XMLStreamWriter quietly.
     *
     * @param writer the XMLStreamWriter to flush
     */
    public static void flush(XMLStreamWriter writer) {
        try {
            if (writer != null) {
                writer.flush();
            }
        } catch (XMLStreamException ignore) {
        }
    }

    /**
     * Converts InputStream contents to a byte array. Returns null on error.
     * Only use this method if you know that the stream length will be relatively small.
     *
     * @param in the InputStream to read from
     * @return a byte array containing the InputStream contents, or null on error
     */
    public static byte[] inStreamToBytes(InputStream in) {
        try {
            FastByteArrayOutputStream out = new FastByteArrayOutputStream(16384);
            transfer(in, out);
            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Transfers a byte array to the output stream of a URLConnection.
     *
     * @param connection the URLConnection to transfer output to
     * @param bytes      the byte array to send
     * @throws IOException if an I/O error occurs
     */
    public static void transfer(URLConnection connection, byte[] bytes) throws IOException {
        try (OutputStream out = new BufferedOutputStream(connection.getOutputStream())) {
            out.write(bytes);
        }
    }

    /**
     * Compresses the contents of one ByteArrayOutputStream into another using GZIP.
     *
     * @param original   the original ByteArrayOutputStream to compress
     * @param compressed the ByteArrayOutputStream to hold the compressed data
     * @throws IOException if an I/O error occurs
     */
    public static void compressBytes(ByteArrayOutputStream original, ByteArrayOutputStream compressed) throws IOException {
        try (DeflaterOutputStream gzip = new AdjustableGzipOutputStream(compressed, Deflater.BEST_SPEED)) {
            original.writeTo(gzip);
            gzip.flush();
        }
    }

    /**
     * Compresses the contents of one FastByteArrayOutputStream into another using GZIP.
     *
     * @param original   the original FastByteArrayOutputStream to compress
     * @param compressed the FastByteArrayOutputStream to hold the compressed data
     * @throws IOException if an I/O error occurs
     */
    public static void compressBytes(FastByteArrayOutputStream original, FastByteArrayOutputStream compressed) throws IOException {
        try (DeflaterOutputStream gzip = new AdjustableGzipOutputStream(compressed, Deflater.BEST_SPEED)) {
            gzip.write(original.toByteArray(), 0, original.size());
            gzip.flush();
        }
    }

    /**
     * Compresses a byte array using GZIP.
     *
     * @param bytes the byte array to compress
     * @return the compressed byte array
     */
    public static byte[] compressBytes(byte[] bytes) {
        return compressBytes(bytes, 0, bytes.length);
    }

    /**
     * Compresses a portion of a byte array using GZIP.
     *
     * @param bytes  the byte array to compress
     * @param offset the offset in the byte array
     * @param length the number of bytes to compress
     * @return the compressed byte array
     */
    public static byte[] compressBytes(byte[] bytes, int offset, int length) {
        try (FastByteArrayOutputStream s = new FastByteArrayOutputStream()) {
            try (DeflaterOutputStream gzip = new AdjustableGzipOutputStream(s, Deflater.BEST_SPEED)) {
                gzip.write(bytes, offset, length);
                gzip.flush();
            }
            return s.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error compressing bytes.", e);
        }
    }

    /**
     * Decompresses a GZIP-compressed byte array.
     *
     * @param bytes the byte array to decompress
     * @return the decompressed byte array
     */
    public static byte[] uncompressedBytes(byte[] bytes) {
        return uncompressedBytes(bytes, 0, bytes.length);
    }

    /**
     * Decompresses a portion of a GZIP-compressed byte array.
     *
     * @param bytes  the byte array to decompress
     * @param offset the offset in the byte array
     * @param length the number of bytes to decompress
     * @return the decompressed byte array
     */
    public static byte[] uncompressedBytes(byte[] bytes, int offset, int length) {
        if (Byte4j.isGzipped(bytes)) {
            try (ByteArrayInputStream s = new ByteArrayInputStream(bytes, offset, length);
                 GZIPInputStream gzip = new GZIPInputStream(s, 16384)) {
                return inStreamToBytes(gzip);
            } catch (Exception e) {
                throw new RuntimeException("Error uncompressed bytes", e);
            }
        }
        return bytes;
    }

    /**
     * Interface for handling transfer callbacks.
     */
    public interface TransferCallback {
        void bytesTransferred(byte[] bytes, int count);

        boolean isCancelled();
    }
}
