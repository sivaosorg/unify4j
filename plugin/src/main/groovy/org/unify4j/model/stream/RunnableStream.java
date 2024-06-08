package org.unify4j.model.stream;

import org.unify4j.common.IO4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RunnableStream implements Runnable {
    private final InputStream stream;
    private String result;

    /**
     * Constructor for the StreamProcess class.
     *
     * @param is InputStream object to be processed.
     */
    public RunnableStream(InputStream is) {
        this.stream = is;
    }

    /**
     * Getter for the result of the stream processing.
     *
     * @return String representation of the processed stream.
     */
    public String getResult() {
        return result;
    }

    /**
     * The run method processes the InputStream.
     * It reads the streamline by line and appends it to a StringBuilder.
     * In case of an IOException, it sets the result to the exception message.
     * Finally, it closes the InputStreamReader and BufferedReader.
     */
    public void run() {
        StringBuilder builder = new StringBuilder();
        InputStreamReader in = null;
        BufferedReader reader = null;
        String line;
        try {
            in = new InputStreamReader(stream);
            reader = new BufferedReader(in);
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
            result = builder.toString();
        } catch (IOException e) {
            result = e.getMessage();
        } finally {
            IO4j.close(in);
            IO4j.close(reader);
        }
    }
}
