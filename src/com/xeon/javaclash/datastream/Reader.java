package com.xeon.javaclash.datastream;

import java.io.*;

public class Reader {
    private ByteArrayInputStream stream;

    public Reader(byte[] array) {
        stream = new ByteArrayInputStream(array);
    }

    public int readByte() {
        return stream.read();
    }

    public boolean readBool() {
        int b = stream.read();
        return (b != 0);
    }

    public byte[] readAllBytes() {
        byte[] bytes = new byte[stream.available()];
        stream.read(bytes, 0, stream.available());
        return bytes;
    }

    public byte[] readBytes(int startIndex, int count) {
        byte[] b = new byte[count];
        stream.read(b, startIndex, count);
        return b;
    }

    public byte[] readBytes(int count) {
        byte[] b = new byte[count];
        try {
            stream.read(b);
        } catch (IOException e) { throw new RuntimeException(e); }
        return b;
    }

    public short readInt16() {
        int ch1 = stream.read();
        int ch2 = stream.read();
        return (short)((ch1 << 8) + (ch2 << 0));
    }

    public int readInt() {
        int ch1 = stream.read();
        int ch2 = stream.read();
        int ch3 = stream.read();
        int ch4 = stream.read();
        return ((ch1 << 24) + (ch2 << 16) + (ch3 << 8) + (ch4 << 0));
    }

    public int readUInt24() {
        int i = stream.read() << 16 | stream.read() << 8 | stream.read();
        return i;
    }

    public long readLong() {
        byte[] bytes = readBytes(0, 8);
        return (((long)bytes[0] << 56) +
                ((long)(bytes[1] & 255) << 48) +
                ((long)(bytes[2] & 255) << 40) +
                ((long)(bytes[3] & 255) << 32) +
                ((long)(bytes[4] & 255) << 24) +
                ((bytes[5] & 255) << 16) +
                ((bytes[6] & 255) <<  8) +
                ((bytes[7] & 255) <<  0));
    }

    public String readString() {
        int len = readInt();
        if (len < 1){
            return "";
        }
        byte[] bytes = readBytes(len);
        return new String(bytes);
    }

    public String readUtf8String(int len) {
        if (len < 1){
            return "";
        }
        byte[] bytes = readBytes(len);
        return new String(bytes);
    }
}
