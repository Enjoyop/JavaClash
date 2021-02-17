package com.xeon.javaclash.protocol.messages.server.login;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

public class KeepAliveServerMessage extends PiranhaMessage {

    public KeepAliveServerMessage(Connection connection){
        super(connection);
        this.id = 20108;
    }
}
