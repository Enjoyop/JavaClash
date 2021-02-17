package com.xeon.javaclash.protocol.messages.client.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.alliance.AllianceInfoMessage;

public class AskForAllianceInfoMessage extends PiranhaMessage {

    private int allianceID;
    public AskForAllianceInfoMessage(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void decode() {
        reader.readInt();
        this.allianceID = reader.readInt();
    }

    @Override
    public void process(){
        AllianceInfoMessage allianceInfoMessage = new AllianceInfoMessage(connection);
        allianceInfoMessage.allianceID = this.allianceID;
        this.connection.messaging.sendMessage(allianceInfoMessage);
    }
}
