package org.example.otomotoclon.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CSVFileManager implements AutoCloseable{

    private final ByteArrayOutputStream outputStream;
    private final String DATA_SEPARATOR = ";";

    public CSVFileManager(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void writeHeader(String... headers) throws IOException {
        try {
            String headerLine = String.join(DATA_SEPARATOR, headers) + System.lineSeparator();
            outputStream.write(headerLine.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IOException("Error writing header to CSV file", e);
        }
    }

    public void writeData(String... data) throws IOException {
        try {
            String dataLine = String.join(DATA_SEPARATOR, data) + System.lineSeparator();
            outputStream.write(dataLine.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new IOException("Error writing data to CSV file", e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new IOException("Error closing CSV file", e);
        }
    }
}
