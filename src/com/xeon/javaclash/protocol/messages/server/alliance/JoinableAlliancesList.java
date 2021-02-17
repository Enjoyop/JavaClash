package com.xeon.javaclash.protocol.messages.server.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.logic.Alliance;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

import java.util.List;

public class JoinableAlliancesList extends PiranhaMessage {

    public JoinableAlliancesList(Connection connection){
        super(connection);
        this.id = 23429;
    }

    @Override
    public void encode() {
        writer.writeInt(Alliance.count());

        List<Alliance> alliances = Alliance.getAlliancesList();

        for (Alliance alliance : alliances){
            writer.writeLongLong(0, alliance.id);
            writer.writeString(alliance.name);
            writer.writeInt(alliance.badge); //badge id
            writer.writeInt(1); //alliance type [1 - open, 2 - invites, 3 - closed]
            writer.writeInt(1); //members count
            writer.writeInt(0); //alliance trophies
            writer.writeInt(0); //builderbase alliance trophies
            writer.writeInt(0); //trophies required
            writer.writeInt(0); //builderbase trophies required

            writer.writeInt(1);
            writer.writeInt(0);
            writer.writeInt(0);
            writer.writeInt(0);

            writer.writeInt(0);
            writer.writeInt(1);

            writer.writeInt(0); //??

            writer.writeInt(0);
            writer.writeInt(1);

            writer.writeInt(0);
            writer.writeBool(true);

            writer.writeInt(1);
            writer.writeBool(true);

            writer.writeInt(0);
            writer.writeInt(3);

            writer.writeInt(56000000); //CSV ID of....?
            writer.writeInt(56000001);
            writer.writeInt(56000005);
        }
    }
}
