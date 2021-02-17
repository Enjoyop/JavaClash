package com.xeon.javaclash.protocol;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.core.Debugger;
import com.xeon.javaclash.protocol.messages.LogicMagicMessageFactory;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

import java.io.IOException;

public class Messaging {
    private Connection connection;

    public Messaging(Connection connection){
        this.connection = connection;
    }

    public void onReceive(int id, byte[] payload){
        PiranhaMessage message = LogicMagicMessageFactory.createMessageByType(id, payload, connection);
        if (message != null) {
            message.decode();
            message.process();
        }
    }

    public void sendMessage(PiranhaMessage message){
        message.encode();
        message.process();
        message.payload = message.writer.toByteArray();
        try {
            connection.outputStream.writeChar((char) message.id);
            connection.outputStream.write(message.payload.length >>> 16);
            connection.outputStream.write(message.payload.length >>> 8);
            connection.outputStream.write(message.payload.length >>> 0);
            connection.outputStream.writeChar(message.version);
            connection.outputStream.write(message.payload);
            connection.outputStream.write(new byte[]{(byte)0xFF, (byte)0xFF, 0, 0, 0, 0, 0});
        } catch (IOException e){
            Debugger.error("Error while sending message!");
            throw new RuntimeException(e);
        }
    }
}
