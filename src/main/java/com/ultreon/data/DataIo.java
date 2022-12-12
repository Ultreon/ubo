package com.ultreon.data;

import com.ultreon.data.types.IType;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataIo {
    private static final short VERSION = 1;

    @SafeVarargs
    public static <T extends IType<?>> T read(File file, T... type) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return read(stream);
        }
    }

    @SafeVarargs
    @SuppressWarnings("unchecked")
    public static <T extends IType<?>> T read(InputStream stream, T... type) throws IOException {
        ObjectInputStream inputStream;
        if (stream instanceof ObjectInputStream s) {
            inputStream = s;
        } else {
            inputStream = new ObjectInputStream(stream);
        }

        short readVersion = inputStream.readShort();
        if (readVersion > VERSION) {
            throw new FutureVersionException(readVersion, VERSION);
        }

        Class<T> componentType = (Class<T>) type.getClass().getComponentType();
        int componentId = TypeRegistry.getId(componentType);
        int id = inputStream.readByte();

        if (componentId != id) {
            throw new DataTypeException("The read data id " + id + " is different from the expected id: " + id);
        }

        return (T) TypeRegistry.read(id, inputStream);
    }
    
    @SafeVarargs
    public static <T extends IType<?>> T readCompressed(File file, T... type) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return DataIo.readCompressed(stream);
        }
    }

    @SafeVarargs
    public static <T extends IType<?>> T readCompressed(URL url, T... type) throws IOException {
        try (InputStream stream = url.openStream()) {
            return readCompressed(stream);
        }
    }

    @SafeVarargs
    public static <T extends IType<?>> T readCompressed(InputStream stream, T... type) throws IOException {
        GZIPInputStream gzipStream = new GZIPInputStream(stream);
        return read(gzipStream);
    }
    
    public static void write(IType<?> type, File file) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            write(type, stream);
        }
    }

    public static void write(IType<?> type, OutputStream stream) throws IOException {
        ObjectOutputStream outputStream;
        if (stream instanceof ObjectOutputStream s) {
            outputStream = s;
        } else {
            outputStream = new ObjectOutputStream(stream);
        }

        outputStream.writeShort(VERSION); // Version
        outputStream.writeByte(type.id()); // Type
        type.write(outputStream);
    }
    
    public static void writeCompressed(IType<?> type, URL file) throws IOException {
        try (OutputStream stream = file.openConnection().getOutputStream()) {
            writeCompressed(type, stream);
        }
    }

    public static void writeCompressed(IType<?> type, File file) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            writeCompressed(type, stream);
        }
    }

    public static void writeCompressed(IType<?> type, OutputStream stream) throws IOException {
        GZIPOutputStream gzipStream = new GZIPOutputStream(stream);
        write(type, gzipStream);
    }
}
