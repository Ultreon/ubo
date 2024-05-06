package dev.ultreon.ubo;

import java.io.IOException;

/**
 * Exception thrown when a data type cannot be read from or written to.
 */
public class DataTypeException extends IOException {
    public DataTypeException(String message) {
        super(message);
    }
}
