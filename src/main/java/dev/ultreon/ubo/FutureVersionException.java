package dev.ultreon.ubo;

import java.io.IOException;

/**
 * An exception thrown when an attempt is made to read UBO data that is from a newer version than the current version.
 */
public class FutureVersionException extends IOException {
    private final short read;
    private final short current;

    public FutureVersionException(short read, short cur) {
        super("UBO data was saved in data version " + read + ", current version is " + cur);

        this.read = read;
        this.current = cur;
    }

    public short getRead() {
        return read;
    }

    public short getCurrent() {
        return current;
    }
}
