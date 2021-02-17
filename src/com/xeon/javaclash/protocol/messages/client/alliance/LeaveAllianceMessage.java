package com.xeon.javaclash.protocol.messages.client.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.logic.Alliance;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.home.OwnHomeDataMessage;

public class LeaveAllianceMessage extends PiranhaMessage {

    public LeaveAllianceMessage(int id, byte[] payload, Connection connection) {
        super(id, payload, connection);
    }

    @Override
    public void process() {
        Alliance alliance = Alliance.load(this.connection.player.allianceID);
        alliance.removePlayer(this.connection.player.id);
        Alliance.saveData();

        this.connection.player.allianceID = 0;
        this.connection.player.role = 0;
        Player.saveData();

        this.connection.messaging.sendMessage(new OwnHomeDataMessage(this.connection));
    }
}
