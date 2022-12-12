package com.ultreon.data;

public class FutureVersionException extends RuntimeException {
    private final short read;
    private final short current;

    public FutureVersionException(short read, short cur) {
        super("UBO Data was saved in data version " + read + ", current version is " + cur);

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
