package com.xeon.javaclash.protocol.messages.server.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.logic.Alliance;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

public class AllianceInfoMessage extends PiranhaMessage {

    private Alliance alliance;
    public int allianceID;
    public AllianceInfoMessage(Connection connection){
        super(connection);

        this.id = 25413;
    }

    @Override
    public void encode() {
        this.alliance = Alliance.load(this.allianceID);

        writer.writeLongLong(0, this.alliance.id);

        writer.writeString(this.alliance.name);
        writer.writeInt(this.alliance.badge); //badge id
        writer.writeInt(1); //alliance type [1 - open, 2 - invites, 3 - closed]
        writer.writeInt(this.alliance.players.size()); //members count
        writer.writeInt(this.connection.player.trophies / 2); //alliance trophies
        writer.writeInt(this.connection.player.trophies / 2); //builderbase alliance trophies
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

        writer.writeString(this.alliance.description); //description

        writer.writeInt(this.alliance.players.size());
        for (int i = 0; i < this.alliance.players.size(); i++) {
            Player player = Player.load(this.alliance.players.get(i));

            writer.writeLongLong(0, player.id);
            writer.writeString(player.name);
            writer.writeInt(player.role); //role [1 - new, 2 - leader, 3 - elder, 4 - co-leader]
            writer.writeInt(player.lvl); //level
            writer.writeInt(22); //league
            writer.writeInt(player.trophies); //trophies
            writer.writeInt(player.trophies); //builderbase trophies

            writer.writeInt(0); //troops donated
            writer.writeInt(0); //troops received

            writer.writeInt(1); //??
            writer.writeInt(1); //??
            writer.writeInt(1); //??
            writer.writeInt(1); //??
            writer.writeInt(1); //??

            writer.writeInt(0);
            writer.writeInt(0);
            writer.writeInt(1);

            writer.writeInt(0);
            writer.writeInt(0);
            writer.writeBool(true);

            writer.writeLongLong(0, 1);
        }

        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeByte(0);
    }
}
