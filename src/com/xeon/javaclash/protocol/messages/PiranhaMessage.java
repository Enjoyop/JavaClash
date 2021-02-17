package com.xeon.javaclash.protocol.messages;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.datastream.*;

public abstract class PiranhaMessage {
    public int id, version, length;
    public byte[] payload;
    public Reader reader;
    public Writer writer;
    public Connection connection;

    public PiranhaMessage(Connection connection){
        this.writer = new Writer();
        this.connection = connection;
    }

    public PiranhaMessage(int id, byte[] payload, Connection connection){
        this.id = id;
        this.payload = payload;
        this.length = this.payload.length;
        this.reader = new Reader(payload);
        this.connection = connection;
    }

    public void encode(){

    }

    public void decode(){

    }

    public void process(){

    }

    public int getType(){
        return id;
    }
}
