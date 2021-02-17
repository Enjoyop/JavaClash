package com.xeon.javaclash.protocol.commands;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.datastream.Reader;
import com.xeon.javaclash.datastream.Writer;

public abstract class LogicCommand {

    public int commandID;
    public byte[] payload;
    public Reader reader;
    public Writer writer;
    public Connection connection;

    public LogicCommand(int commandID, Reader reader, Connection connection) {
        this.commandID = commandID;
        this.reader = reader;
        this.connection = connection;
    }

    public LogicCommand(int commandID) {
        this.commandID = commandID;
        this.writer = new Writer();
    }

    public void encode() {

    }

    public void decode() {

    }

    public void process() {

    }
}
