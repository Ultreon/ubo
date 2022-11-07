package com.ultreon.data;

import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.CSS;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.renderable.RenderableImage;
import java.awt.image.renderable.RenderableImageOp;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;

public class DataWriter {
    private final ObjectOutputStream out;

    private DataWriter(ObjectOutputStream out) {
        this.out = out;
    }

    public static void dumps(final Object data, OutputStream output) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(output);
        DataWriter dataWriter = new DataWriter(out);
        dataWriter.write(data);
    }
    
    public static byte[] dumps(final Object data) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        dumps(data, out);
        return out.toByteArray();
    }

    private void write(final Object data) throws IOException {
        if (data == null) {
            out.writeShort(Constants.NIL);
        } else if (data.getClass().equals(Byte.class)) {
            Byte o = (Byte) data;
            out.writeShort(Constants.BYTE);
            writeByte(o);
        } else if (data.getClass().equals(Short.class)) {
            Short o = (Short) data;
            out.writeShort(Constants.SHORT);
            writeShort(o);
        } else if (data.getClass().equals(Integer.class)) {
            Integer o = (Integer) data;
            out.writeShort(Constants.INT);
            writeInt(o);
        } else if (data.getClass().equals(Long.class)) {
            Long o = (Long) data;
            out.writeShort(Constants.LONG);
            writeLong(o);
        } else if (data.getClass().equals(Float.class)) {
            Float o = (Float) data;
            out.writeShort(Constants.FLOAT);
            writeFloat(o);
        } else if (data.getClass().equals(Double.class)) {
            Double o = (Double) data;
            out.writeShort(Constants.DOUBLE);
            writeDouble(o);
        } else if (data instanceof byte[] o) {
            out.writeShort(Constants.BYTE_ARRAY);
            writeByteArray(o);
        } else if (data instanceof short[] o) {
            out.writeShort(Constants.SHORT_ARRAY);
            writeShortArray(o);
        } else if (data instanceof int[] o) {
            out.writeShort(Constants.INT_ARRAY);
            writeIntArray(o);
        } else if (data instanceof long[] o) {
            out.writeShort(Constants.LONG_ARRAY);
            writeLongArray(o);
        } else if (data instanceof float[] o) {
            out.writeShort(Constants.FLOAT_ARRAY);
            writeFloatArray(o);
        } else if (data instanceof double[] o) {
            out.writeShort(Constants.DOUBLE_ARRAY);
            writeDoubleArray(o);
        } else if (data.getClass().equals(Boolean.class)) {
            Boolean o = (Boolean) data;
            out.writeShort(Constants.BOOL);
            writeBool(o);
        } else if (data instanceof boolean[] o) {
            out.writeShort(Constants.BOOL_ARRAY);
            writeBoolArray(o);
        } else if (data.getClass().equals(Character.class)) {
            Character o = (Character) data;
            out.writeShort(Constants.CHAR);
            writeChar(o);
        } else if (data instanceof char[] o) {
            out.writeShort(Constants.CHAR_ARRAY);
            writeCharArray(o);
        } else if (data.getClass().equals(String.class)) {
            String o = (String) data;
            out.writeShort(Constants.STRING);
            writeString(o);
        } else if (data.getClass().equals(UUID.class)) {
            UUID o = (UUID) data;
            out.writeShort(Constants.UUID);
            writeUUID(o);
        } else if (data.getClass().equals(Point.class)) {
            Point o = (Point) data;
            out.writeShort(Constants.POINT);
            writeInt(o.x);
            writeInt(o.y);
        } else if (data.getClass().equals(Point2D.Float.class)) {
            Point2D.Float o = (Point2D.Float) data;
            out.writeShort(Constants.POINT_FLOAT);
            writeFloat(o.x);
            writeFloat(o.y);
        } else if (data.getClass().equals(Point2D.Double.class)) {
            Point2D.Double o = (Point2D.Double) data;
            out.writeShort(Constants.POINT_DOUBLE);
            writeDouble(o.x);
            writeDouble(o.y);
        } else if (data.getClass().equals(Rectangle.class)) {
            Rectangle o = (Rectangle) data;
            out.writeShort(Constants.RECTANGLE);
            writeInt(o.x);
            writeInt(o.y);
            writeInt(o.width);
            writeInt(o.height);
        } else if (data.getClass().equals(Rectangle2D.Float.class)) {
            Rectangle2D.Float o = (Rectangle2D.Float) data;
            out.writeShort(Constants.RECTANGLE_FLOAT);
            writeFloat(o.x);
            writeFloat(o.y);
            writeFloat(o.width);
            writeFloat(o.height);
        } else if (data.getClass().equals(Rectangle2D.Double.class)) {
            Rectangle2D.Double o = (Rectangle2D.Double) data;
            out.writeShort(Constants.RECTANGLE_DOUBLE);
            writeDouble(o.x);
            writeDouble(o.y);
            writeDouble(o.width);
            writeDouble(o.height);
        } else if (data.getClass().equals(Ellipse2D.Float.class)) {
            Ellipse2D.Float o = (Ellipse2D.Float) data;
            out.writeShort(Constants.ELLIPSE_FLOAT);
            writeFloat(o.x);
            writeFloat(o.y);
            writeFloat(o.width);
            writeFloat(o.height);
        } else if (data.getClass().equals(Ellipse2D.Double.class)) {
            Ellipse2D.Double o = (Ellipse2D.Double) data;
            out.writeShort(Constants.ELLIPSE_DOUBLE);
            writeDouble(o.x);
            writeDouble(o.y);
            writeDouble(o.width);
            writeDouble(o.height);
        } else if (data.getClass().equals(Dimension.class)) {
            Dimension o = (Dimension) data;
            out.writeShort(Constants.DIMENSION);
            writeInt(o.width);
            writeInt(o.height);
        } else if (data.getClass().equals(Line2D.Float.class)) {
            Line2D.Float o = (Line2D.Float) data;
            out.writeShort(Constants.LINE_FLOAT);
            writeFloat(o.x1);
            writeFloat(o.y1);
            writeFloat(o.x2);
            writeFloat(o.y2);
        } else if (data.getClass().equals(Line2D.Double.class)) {
            Line2D.Double o = (Line2D.Double) data;
            out.writeShort(Constants.LINE_DOUBLE);
            writeDouble(o.x1);
            writeDouble(o.y1);
            writeDouble(o.x2);
            writeDouble(o.y2);
        } else if (data.getClass().equals(Arc2D.Float.class)) {
            Arc2D.Float o = (Arc2D.Float) data;
            out.writeShort(Constants.ARC_FLOAT);
            writeFloat(o.x);
            writeFloat(o.y);
            writeFloat(o.width);
            writeFloat(o.height);
            writeFloat(o.start);
            writeFloat(o.extent);
        } else if (data.getClass().equals(Arc2D.Double.class)) {
            Arc2D.Double o = (Arc2D.Double) data;
            out.writeShort(Constants.ARC_DOUBLE);
            writeDouble(o.x);
            writeDouble(o.y);
            writeDouble(o.width);
            writeDouble(o.height);
            writeDouble(o.start);
            writeDouble(o.extent);
        } else if (data.getClass().equals(Font.class)) {
            Font o = (Font) data;
            out.writeShort(Constants.FONT);
            writeString(o.getName());
            writeFloat(o.getSize2D());
            writeInt(o.getStyle());
        } else if (data.getClass().equals(Insets.class)) {
            Insets o = (Insets) data;
            out.writeShort(Constants.INSETS);
            writeInt(o.top);
            writeInt(o.left);
            writeInt(o.bottom);
            writeInt(o.right);
        } else if (data.getClass().equals(Stack.class)) {
            Stack<?> o = (Stack<?>) data;
            out.writeShort(Constants.STACK);
            writeInt(o.size());
            for (Object v : o) {
                write(v);
            }
        } else if (data.getClass().equals(Vector.class)) {
            Vector<?> o = (Vector<?>) data;
            out.writeShort(Constants.VECTOR);
            writeInt(o.size());
            for (Object v : o) {
                write(v);
            }
        } else if (data.getClass().equals(ArrayList.class)) {
            ArrayList<?> o = (ArrayList<?>) data;
            out.writeShort(Constants.ARRAY_LIST);
            writeInt(o.size());
            for (Object v : o) {
                write(v);
            }
        } else if (data.getClass().equals(LinkedList.class)) {
            LinkedList<?> o = (LinkedList<?>) data;
            out.writeShort(Constants.LINKED_LIST);
            writeInt(o.size());
            for (Object v : o) {
                write(v);
            }
        } else if (data.getClass().equals(CopyOnWriteArrayList.class)) {
            CopyOnWriteArrayList<?> o = (CopyOnWriteArrayList<?>) data;
            out.writeShort(Constants.COPY_ON_WRITE_ARRAY_LIST);
            writeInt(o.size());
            for (Object v : o) {
                write(v);
            }
        } else if (data.getClass().equals(HashSet.class)) {
            HashSet<?> o = (HashSet<?>) data;
            out.writeShort(Constants.HASH_SET);
            writeInt(o.size());
            for (Object v : o) {
                write(v);
            }
        } else if (data.getClass().equals(LinkedHashSet.class)) {
            LinkedHashSet<?> o = (LinkedHashSet<?>) data;
            out.writeShort(Constants.LINKED_HASH_SET);
            writeInt(o.size());
            for (Object v : o) {
                write(v);
            }
        } else if (data.getClass().equals(CopyOnWriteArraySet.class)) {
            CopyOnWriteArraySet<?> o = (CopyOnWriteArraySet<?>) data;
            out.writeShort(Constants.COPY_ON_WRITE_ARRAY_SET);
            writeInt(o.size());
            for (Object v : o) {
                write(v);
            }
        } else if (data.getClass().equals(HashMap.class)) {
            HashMap<?, ?> o = (HashMap<?, ?>) data;
            out.writeShort(Constants.HASH_MAP);
            writeInt(o.size());
            for (Map.Entry<?, ?> e : o.entrySet()) {
                write(e.getKey());
                write(e.getValue());
            }
        } else if (data.getClass().equals(LinkedHashMap.class)) {
            LinkedHashMap<?, ?> o = (LinkedHashMap<?, ?>) data;
            out.writeShort(Constants.LINKED_HASH_MAP);
            writeInt(o.size());
            for (Map.Entry<?, ?> e : o.entrySet()) {
                write(e.getKey());
                write(e.getValue());
            }
        } else if (data.getClass().equals(IdentityHashMap.class)) {
            IdentityHashMap<?, ?> o = (IdentityHashMap<?, ?>) data;
            out.writeShort(Constants.IDENTITY_HASH_MAP);
            writeInt(o.size());
            for (Map.Entry<?, ?> e : o.entrySet()) {
                write(e.getKey());
                write(e.getValue());
            }
        } else if (data.getClass().equals(WeakHashMap.class)) {
            WeakHashMap<?, ?> o = (WeakHashMap<?, ?>) data;
            out.writeShort(Constants.WEAK_HASH_MAP);
            writeInt(o.size());
            for (Map.Entry<?, ?> e : o.entrySet()) {
                write(e.getKey());
                write(e.getValue());
            }
        } else if (data.getClass().equals(ConcurrentHashMap.class)) {
            ConcurrentHashMap<?, ?> o = (ConcurrentHashMap<?, ?>) data;
            out.writeShort(Constants.CONCURRENT_HASH_MAP);
            writeInt(o.size());
            for (Map.Entry<?, ?> e : o.entrySet()) {
                write(e.getKey());
                write(e.getValue());
            }
        } else if (data.getClass().equals(ConcurrentSkipListMap.class)) {
            ConcurrentSkipListMap<?, ?> o = (ConcurrentSkipListMap<?, ?>) data;
            out.writeShort(Constants.CONCURRENT_SKIP_LIST_MAP);
            writeInt(o.size());
            for (Map.Entry<?, ?> e : o.entrySet()) {
                write(e.getKey());
                write(e.getValue());
            }
        } else if (data.getClass().equals(Hashtable.class)) {
            Hashtable<?, ?> o = (Hashtable<?, ?>) data;
            out.writeShort(Constants.HASHTABLE);
            writeInt(o.size());
            for (Map.Entry<?, ?> e : o.entrySet()) {
                write(e.getKey());
                write(e.getValue());
            }
        } else if (data.getClass().equals(Color.class)) {
            Color o = (Color) data;
            out.writeShort(Constants.COLOR);
            writeByte((byte) o.getRed());
            writeByte((byte) o.getGreen());
            writeByte((byte) o.getBlue());
            writeByte((byte) o.getAlpha());
        } else if (data.getClass().equals(BufferedImage.class)) {
            BufferedImage o = (BufferedImage) data;
            out.writeShort(Constants.BUFFERED_IMAGE);
            ImageIO.write(o, "png", out);
        } else if (data.getClass().equals(BigInteger.class)) {
            BigInteger o = (BigInteger) data;
            out.writeShort(Constants.BUFFERED_IMAGE);
            writeByteArray(o.toByteArray());
        } else if (data.getClass().equals(BigDecimal.class)) {
            BigDecimal o = (BigDecimal) data;
            out.writeShort(Constants.BUFFERED_IMAGE);
            writeInt(o.scale());
            writeByteArray(o.unscaledValue().toByteArray());
        } else if (data instanceof Serializable serializable) {
            out.writeShort(Constants.MISC);
            writeObject(serializable);
        }
    }

    private void writeObject(Serializable serializable) throws IOException {
        out.writeObject(serializable);
    }

    private void writeByte(byte v) throws IOException {
        out.writeByte(v);
    }

    private void writeShort(short v) throws IOException {
        out.writeShort(v);
    }

    private void writeInt(int v) throws IOException {
        out.writeInt(v);
    }

    private void writeLong(long v) throws IOException {
        out.writeLong(v);
    }

    private void writeFloat(float v) throws IOException {
        out.writeFloat(v);
    }

    private void writeDouble(double v) throws IOException {
        out.writeDouble(v);
    }

    private void writeByteArray(byte[] o) throws IOException {
        out.writeInt(o.length);
        for (byte v : o) {
            out.writeByte(v);
        }
    }

    private void writeShortArray(short[] o) throws IOException {
        out.writeInt(o.length);
        for (short v : o) {
            out.writeShort(v);
        }
    }

    private void writeIntArray(int[] o) throws IOException {
        out.writeInt(o.length);
        for (int v : o) {
            out.writeInt(v);
        }
    }

    private void writeLongArray(long[] o) throws IOException {
        out.writeInt(o.length);
        for (long v : o) {
            out.writeLong(v);
        }
    }

    private void writeFloatArray(float[] o) throws IOException {
        out.writeInt(o.length);
        for (float v : o) {
            out.writeFloat(v);
        }
    }

    private void writeDoubleArray(double[] o) throws IOException {
        out.writeInt(o.length);
        for (double v : o) {
            out.writeDouble(v);
        }
    }

    private void writeBool(boolean b) throws IOException {
        out.writeByte(b ? 1 : 0);
    }

    private void writeBoolArray(boolean[] booleans) throws IOException {
        int arrayLen = booleans.length;
        int len = (int) Math.ceil(((double) arrayLen / 4f));
        writeInt(arrayLen);
        for (int i = 0; i < len; i++) {
            byte cur = 0;
            for (int j = 0; j < 4; j++) {
                cur |= (booleans[(i * 4) + j] ? 1 : 0) << j;
            }
            writeByte(cur);
        }
    }

    private void writeChar(char c) throws IOException {
        out.writeChar(c);
    }

    private void writeCharArray(char[] chars) throws IOException {
        out.writeInt(chars.length);
        for (char c : chars) {
            out.writeChar(c);
        }
    }

    private void writeString(String s) throws IOException {
        writeByteArray(s.getBytes());
    }

    private void writeUUID(UUID id) throws IOException {
        writeLong(id.getMostSignificantBits());
        writeLong(id.getLeastSignificantBits());
    }
}
