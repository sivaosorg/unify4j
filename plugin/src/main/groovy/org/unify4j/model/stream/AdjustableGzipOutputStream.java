package org.unify4j.model.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

public class AdjustableGzipOutputStream extends GZIPOutputStream {
    public AdjustableGzipOutputStream(OutputStream out, int level) throws IOException {
        super(out);
        def.setLevel(level);
    }

    public AdjustableGzipOutputStream(OutputStream out, int size, int level) throws IOException {
        super(out, size);
        def.setLevel(level);
    }
}