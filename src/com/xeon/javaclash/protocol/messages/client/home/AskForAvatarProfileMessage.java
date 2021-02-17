package com.xeon.javaclash.protocol.messages.client.home;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.home.AvatarProfileMessage;

public class AskForAvatarProfileMessage extends PiranhaMessage {

    public AskForAvatarProfileMessage(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void process(){
        this.connection.messaging.sendMessage(new AvatarProfileMessage(this.connection));
    }
}
