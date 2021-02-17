package com.xeon.javaclash.protocol.messages.server.login;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

public class LoginFailedMessage extends PiranhaMessage {

    public String reason;
    public LoginFailedMessage(Connection connection) {
        super(connection);

        this.id = 20103;
        this.version = 4;
    }

    @Override
    public void encode() {
        writer.writeInt(0); //ErrorCode
        writer.writeString(null);
        writer.writeString(null);
        writer.writeString(null);
        writer.writeString(null); //UpdateURL
        writer.writeString(reason);
        writer.writeInt(0); //seconds until maintenance ends
    }
}
