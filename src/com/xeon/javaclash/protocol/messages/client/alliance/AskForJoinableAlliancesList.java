package com.xeon.javaclash.protocol.messages.client.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.alliance.JoinableAlliancesList;

public class AskForJoinableAlliancesList extends PiranhaMessage {

    public AskForJoinableAlliancesList(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void process() {
        this.connection.messaging.sendMessage(new JoinableAlliancesList(connection));
    }
}
