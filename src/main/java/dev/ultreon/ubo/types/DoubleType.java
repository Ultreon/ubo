package dev.ultreon.ubo.types;

import dev.ultreon.ubo.DataTypes;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class DoubleType implements DataType<Double> {
    private double obj;

    public DoubleType(double obj) {
        this.obj = obj;
    }

    @Override
    public Double getValue() {
        return obj;
    }

    public double getDoubleValue() {
        return obj;
    }

    @Override
    public void setValue(Double obj) {
        if (obj == null) throw new IllegalArgumentException("Value can't be set to null");
        this.obj = obj;
    }

    public void setValue(double val) {
        this.obj = val;
    }

    @Override
    public int id() {
        return DataTypes.DOUBLE;
    }

    @Override
    public void write(DataOutput output) throws IOException {
        output.writeDouble(obj);
    }

    public static DoubleType read(DataInput input) throws IOException {
        return new DoubleType(input.readDouble());
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof DoubleType)) return false;
        DoubleType that = (DoubleType) other;
        return Double.compare(obj, that.obj) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(obj);
    }

    @Override
    public DoubleType copy() {
        return new DoubleType(obj);
    }

    @Override
    public String writeUso() {
        return obj + "d";
    }

    @Override
    public String toString() {
        return writeUso();
    }
}
