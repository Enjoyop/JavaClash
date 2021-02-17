package com.xeon.javaclash.protocol.commands.client;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.datastream.Reader;
import com.xeon.javaclash.protocol.commands.LogicCommand;
import com.xeon.javaclash.protocol.messages.server.battle.NpcDataMessage;

public class LogicAttackNpcCommand extends LogicCommand {

    public LogicAttackNpcCommand(int id, Reader reader, Connection connection){
        super(id, reader, connection);
    }

    @Override
    public void process() {
        this.connection.messaging.sendMessage(new NpcDataMessage(connection));
    }
}
