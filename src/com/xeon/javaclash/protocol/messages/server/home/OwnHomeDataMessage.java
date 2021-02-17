package com.xeon.javaclash.protocol.messages.server.home;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

public class OwnHomeDataMessage extends PiranhaMessage {

    public OwnHomeDataMessage(Connection connection){
        super(connection);
        this.id = 25195;
    }

    @Override
    public void encode() {
        writer.writeInt(0);
        writer.writeInt(-1);

        //LogicClientHome here
        this.connection.player.LogicClientHome(writer);

        //LogicClientAvatar here
        this.connection.player.LogicClientAvatar(writer);

        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeByte(0);

    }
}
