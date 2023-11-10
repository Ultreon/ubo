package com.ultreon.data;

public class FutureVersionException extends IllegalStateException {
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
