package com.xeon.javaclash.protocol.messages.client.home;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.home.OwnHomeDataMessage;

public class ChangeAvatarNameMessage extends PiranhaMessage {

    private String name;
    public ChangeAvatarNameMessage(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void decode() {
        this.name = reader.readString();
    }

    @Override
    public void process() {
        connection.player.name = this.name;
        connection.player.nameSet = 1;
        Player.saveData();

        connection.messaging.sendMessage(new OwnHomeDataMessage(connection));
    }
}
