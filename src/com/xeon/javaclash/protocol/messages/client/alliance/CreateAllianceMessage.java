package com.xeon.javaclash.protocol.messages.client.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.logic.Alliance;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.home.OwnHomeDataMessage;

public class CreateAllianceMessage extends PiranhaMessage {
    private String name;
    private String description;
    private int badge;
    public CreateAllianceMessage(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void decode() {
        this.name = reader.readString();
        this.description = reader.readString();
        this.badge = reader.readInt();
    }

    @Override
    public void process() {

        Alliance alliance = Alliance.load(0);
        alliance.name = this.name;
        alliance.description = this.description;
        alliance.badge = this.badge;

        alliance.addPlayer(this.connection.player.id);
        Alliance.saveData();

        this.connection.player.allianceID = alliance.id;
        this.connection.player.role = 2;
        Player.saveData();

        this.connection.messaging.sendMessage(new OwnHomeDataMessage(connection));
    }
}
