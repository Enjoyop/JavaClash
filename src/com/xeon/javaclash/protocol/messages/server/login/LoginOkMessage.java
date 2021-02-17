package com.xeon.javaclash.protocol.messages.server.login;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

public class LoginOkMessage extends PiranhaMessage {
    public LoginOkMessage(Connection connection) {
        super(connection);
        this.id = 23654;
    }

    @Override
    public void encode(){
        writer.writeLongLong(0, this.connection.player.id);
        writer.writeLongLong(0, this.connection.player.id);

        writer.writeString(this.connection.player.token);
        writer.writeString(null);
        writer.writeString(null);

        writer.writeInt(13);
        writer.writeInt(0);
        writer.writeInt(4);

        writer.writeString(null);

        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
    }
}
