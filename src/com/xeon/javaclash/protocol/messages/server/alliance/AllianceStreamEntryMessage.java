package com.xeon.javaclash.protocol.messages.server.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

public class AllianceStreamEntryMessage extends PiranhaMessage {

    public boolean isBot = false;
    public String message;

    public AllianceStreamEntryMessage(Connection connection, String message) {
        super(connection);
        this.id = 28046;
        this.message = message;
    }

    @Override
    public void encode() {
        this.connection.player.lastMessage += 1;
        writer.writeInt(2);

        writer.writeInt(0);

        writer.writeInt(this.connection.player.lastMessage); //message low id
        writer.writeByte(3);

        if (!isBot) {
            writer.writeLongLong(0, this.connection.player.id);
            writer.writeLongLong(0, this.connection.player.id);

            writer.writeString(this.connection.player.name);

            writer.writeInt(this.connection.player.lvl);
            writer.writeInt(0); //league

            writer.writeInt(2); //role

            writer.writeInt(0); //time
        } else {
            writer.writeLongLong(0, 1337);
            writer.writeLongLong(0, 1337);

            writer.writeString("JavaClash System");

            writer.writeInt(300);
            writer.writeInt(22); //league

            writer.writeInt(2); //role

            writer.writeInt(0); //time
        }

        writer.writeString(this.message);
    }
}
