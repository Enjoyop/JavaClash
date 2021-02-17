package com.xeon.javaclash.protocol.messages.client.login;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.login.KeepAliveServerMessage;

public class KeepAliveMessage extends PiranhaMessage {
    public KeepAliveMessage(int id, byte[] payload, Connection connection) {
        super(id, payload, connection);
    }

    @Override
    public void process() {
        this.connection.messaging.sendMessage(new KeepAliveServerMessage(this.connection));
    }
}
