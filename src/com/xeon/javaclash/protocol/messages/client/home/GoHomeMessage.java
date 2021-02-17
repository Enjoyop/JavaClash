package com.xeon.javaclash.protocol.messages.client.home;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.home.OwnHomeDataMessage;

public class GoHomeMessage extends PiranhaMessage {
    public GoHomeMessage(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void process() {
        this.connection.messaging.sendMessage(new OwnHomeDataMessage(this.connection));
    }
}
