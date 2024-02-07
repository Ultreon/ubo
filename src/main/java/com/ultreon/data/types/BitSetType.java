package com.ultreon.data.types;

import com.ultreon.data.Types;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;
import java.util.Objects;

public class BitSetType implements IType<BitSet> {
    private BitSet obj;

    public BitSetType(byte[] bits) {
        this.obj = BitSet.valueOf(bits);
    }

    public BitSetType(long[] bits) {
        this.obj = BitSet.valueOf(bits);
    }

    public BitSetType(BitSet obj) {
        this.obj = obj;
    }

    @Override
    public BitSet getValue() {
        return obj;
    }

    @Override
    public void setValue(BitSet obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    @Override
    public int id() {
        return Types.BIT_SET;
    }

    @Override
    public void write(DataOutputStream stream) throws IOException {
        byte[] arr = this.obj.toByteArray();
        if (arr.length >= 32768) throw new IllegalArgumentException("Bitset is too big to be written");
        stream.writeShort(arr.length);
        for (byte b : arr) {
            stream.writeByte(b);
        }
    }

    public static BitSetType read(DataInputStream stream) throws IOException {
        int len = stream.readShort();
        byte[] arr = new byte[len];
        for (int i = 0; i < len; i++) {
            arr[i] = stream.readByte();
        }
        return new BitSetType(arr);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof BitSetType)) return false;
        BitSetType uuidType = (BitSetType) other;
        return Objects.equals(obj, uuidType.obj);
    }

    @Override
    public int hashCode() {
        return obj.hashCode();
    }

    @Override
    public BitSetType copy() {
        return new BitSetType((BitSet) this.obj.clone());
    }

    @Override
    public String writeUso() {
        StringBuilder builder = new StringBuilder("x");
        for (int i = 0; i < obj.length(); i++) {
            builder.append(obj.get(i) ? "1" : "0");
        }

        return builder.toString() + ";";
    }

    public void setBit(int index, boolean value) {
        if (value) obj.set(index);
        else obj.clear(index);
    }

    public boolean getBit(int index) {
        return obj.get(index);
    }

    public int length() {
        return obj.length();
    }

    public int cardinality() {
        return obj.cardinality();
    }

    public int nextSetBit(int fromIndex) {
        return obj.nextSetBit(fromIndex);
    }

    public int nextClearBit(int fromIndex) {
        return obj.nextClearBit(fromIndex);
    }

    public int previousSetBit(int fromIndex) {
        return obj.previousSetBit(fromIndex);
    }

    public int previousClearBit(int fromIndex) {
        return obj.previousClearBit(fromIndex);
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
