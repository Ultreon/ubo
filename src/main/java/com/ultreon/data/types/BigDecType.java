package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecType implements IType<BigDecimal> {
    private BigDecimal obj;

    public BigDecType(BigDecimal obj) {
        this.obj = obj;
    }

    public BigDecType(String number) {
        this.obj = new BigDecimal(number);
    }

    @Override
    public BigDecimal getValue() {
        return obj;
    }

    @Override
    public void setValue(BigDecimal obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.BIG_DEC;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        byte[] bytes = obj.unscaledValue().toByteArray();
        output.writeInt(bytes.length);
        output.writeInt(obj.scale());
        for (byte aByte : bytes) {
            output.writeByte(aByte);
        }
    }

    public static BigDecType read(DataInput input) throws IOException {
        int len = input.readInt();
        int scale = input.readInt();
        byte[] bytes = new byte[len];
        for (int i = 0; i < len; i++) {
            bytes[i] = input.readByte();
        }

        return new BigDecType(new BigDecimal(new BigInteger(bytes), scale));
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BigDecType)) return false;
        BigDecType that = (BigDecType) other;
        return obj.equals(that.obj);
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }

    @Override
    public BigDecType copy() {
        return new BigDecType(obj);
    }

    @Override
    public String writeUso() {
        return obj.toString() + "D";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
