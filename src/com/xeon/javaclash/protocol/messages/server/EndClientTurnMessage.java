package com.xeon.javaclash.protocol.messages.server;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.protocol.commands.LogicCommand;
import com.xeon.javaclash.protocol.commands.LogicCommandManager;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;

public class EndClientTurnMessage extends PiranhaMessage {
    private int commandID;
    public EndClientTurnMessage(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void decode() {
        reader.readInt(); //SubTick
        reader.readInt();
        reader.readInt(); //Count
        this.commandID = reader.readInt();
    }

    @Override
    public void process() {
        if (this.commandID > 0) {
            LogicCommand cmd = LogicCommandManager.createCommand(this.commandID, this.reader, this.connection);
            if (cmd != null) {
                cmd.decode();
                cmd.process();
            }
        }
    }
}
