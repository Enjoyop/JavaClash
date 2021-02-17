package com.xeon.javaclash.datastream;

import com.xeon.javaclash.datastream.compression.Zlib;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class Writer {
    private ByteArrayOutputStream stream;

    public Writer() {
        this.stream = new ByteArrayOutputStream();
    }

    public void writeByte(int b) {
        stream.write(b);
    }

    public void writeByteArray(byte[] array) {
        stream.write(array, 0, array.length);
    }

    public void writeByteArray(byte[] array, int startIndex, int endIndex) {
        stream.write(array, startIndex, endIndex);
    }

    public void writeBool(boolean v) {
        writeByte(v ? 1 : 0);
    }

    public void writeBools(boolean a, boolean b) {
        writeByte(a ? 1 : 0);
        writeByte(b ? 1 : 0);
    }

    public void writeInt(int v) {
        stream.write((v >>> 24) & 0xFF);
        stream.write((v >>> 16) & 0xFF);
        stream.write((v >>>  8) & 0xFF);
        stream.write((v >>>  0) & 0xFF);
    }

    public void writeInt(int a, int b) {
        writeInt(a);
        writeInt(b);
    }

    public void writeLongLong(int a, int b) {
        writeInt(a);
        writeInt(b);
    }
    public void writeInt16(int s) {
        stream.write((s >>> 8) & 0xFF);
        stream.write((s >>> 0) & 0xFF);
    }

    public void writeUInt24(int b) {
        stream.write(b >>> 16);
        stream.write(b >>> 8);
        stream.write(b >>> 0);
    }

    public void writeHexa(String hex) {
        byte[] bytes = new byte[hex.length()/2];
        String[] strBytes = new String[hex.length()/2];
        int k = 0;
        for (int i = 0; i < hex.length(); i=i+2) {
            int j = i+2;
            strBytes[k] = hex.substring(i,j);
            bytes[k] = (byte)Integer.parseInt(strBytes[k], 16);
            k++;
        }
        stream.write(bytes, 0, bytes.length);
    }

    public void writeUtf8String(String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        stream.write(bytes, 0, bytes.length);
    }

    public void writeInt32LE(int v) {
        byte[] bytes = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(v).flip().array();

        writeByteArray(bytes, 0, 4);
    }

    public void writeString(String str) {
        if(str == null){
            writeInt(-1);
        }
        else if(str.length() == 0){
            writeInt(0);
        }
        else {
            writeInt(str.length());
            writeByteArray(str.getBytes());
        }
    }

    public void writeCompressedString(String str) {
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        byte[] compressed;
        try {
            compressed = Zlib.compress(data);
        } catch (Exception e) {throw new RuntimeException();}

        writeBool(true);
        writeInt(compressed.length + 4);
        writeInt32LE(str.length());

        writeByteArray(compressed, 0, compressed.length);
    }

    public void writeCompressableString(String str) {
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        byte[] compressed;
        try {
            compressed = Zlib.compress(data);
        } catch (Exception e) {throw new RuntimeException();}

        writeInt(compressed.length + 4);
        writeInt32LE(str.length());

        writeByteArray(compressed, 0, compressed.length);
    }

    public void writeDataReference(int csv, int item) {
        writeInt((csv * 1000000) + item);
    }

    public byte[] toByteArray() {
        return(stream.toByteArray());
    }
}
