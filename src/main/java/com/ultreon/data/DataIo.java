package com.ultreon.data;

import com.ultreon.data.types.MapType;

import java.io.*;
import java.net.URL;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DataIo {
    public static MapType read(File file) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return read(stream);
        }
    }
    
    public static MapType read(InputStream stream) throws IOException {
        ObjectInputStream inputStream;
        if (stream instanceof ObjectInputStream s) {
            inputStream = s;
        } else {
            inputStream = new ObjectInputStream(stream);
        }
        
        return (MapType) TypeRegistry.read(Types.MAP, inputStream);
    }
    
    public static MapType readCompressed(File file) throws IOException {
        try (FileInputStream stream = new FileInputStream(file)) {
            return readCompressed(stream);
        }
    }

    public static MapType readCompressed(URL url) throws IOException {
        try (InputStream stream = url.openStream()) {
            return readCompressed(stream);
        }
    }

    public static MapType readCompressed(InputStream stream) throws IOException {
        GZIPInputStream gzipStream = new GZIPInputStream(stream);
        return read(gzipStream);
    }
    
    public static void write(MapType type, File file) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            write(type, stream);
        }
    }

    public static void write(MapType type, OutputStream stream) throws IOException {
        ObjectOutputStream outputStream;
        if (stream instanceof ObjectOutputStream s) {
            outputStream = s;
        } else {
            outputStream = new ObjectOutputStream(stream);
        }
        
        type.write(outputStream);
    }
    
    public static void writeCompressed(MapType type, URL file) throws IOException {
        try (OutputStream stream = file.openConnection().getOutputStream()) {
            writeCompressed(type, stream);
        }
    }

    public static void writeCompressed(MapType type, File file) throws IOException {
        try (FileOutputStream stream = new FileOutputStream(file)) {
            writeCompressed(type, stream);
        }
    }

    public static void writeCompressed(MapType type, OutputStream stream) throws IOException {
        GZIPOutputStream gzipStream = new GZIPOutputStream(stream);
        write(type, gzipStream);
    }
}
