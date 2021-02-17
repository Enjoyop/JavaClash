package com.xeon.javaclash.protocol.messages.client.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.logic.Alliance;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.home.OwnHomeDataMessage;

public class JoinAllianceMessage extends PiranhaMessage {

    private int allianceID;
    public JoinAllianceMessage(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void decode() {
        reader.readInt();
        this.allianceID = reader.readInt();
    }

    @Override
    public void process() {
        Alliance alliance = Alliance.load(this.allianceID);
        alliance.addPlayer(this.connection.player.id);
        Alliance.saveData();

        this.connection.player.allianceID = alliance.id;
        Player.saveData();

        this.connection.messaging.sendMessage(new OwnHomeDataMessage(connection));
    }
}
