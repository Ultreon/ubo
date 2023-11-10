package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;

public class BigIntType implements IType<BigInteger> {
    private BigInteger obj;

    public BigIntType(BigInteger obj) {
        this.obj = obj;
    }

    @Override
    public BigInteger getValue() {
        return obj;
    }

    @Override
    public void setValue(BigInteger obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.BIG_INT;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        byte[] bytes = obj.toByteArray();
        stream.writeInt(bytes.length);
        for (byte aByte : bytes) {
            stream.writeByte(aByte);
        }
    }

    public static BigIntType read(DataInputStream stream) throws IOException {
        int len = stream.readInt();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = stream.readByte();
        }

        return new BigIntType(new BigInteger(bytes));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BigIntType)) return false;
        BigIntType that = (BigIntType) other;
        return obj.equals(that.obj);
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }

    @Override
    public BigIntType copy() {
        return new BigIntType(obj);
    }
}
