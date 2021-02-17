package com.xeon.javaclash.protocol.commands.client;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.core.Debugger;
import com.xeon.javaclash.datastream.Reader;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.commands.LogicCommand;

public class LogicBuyResourceCommand extends LogicCommand {

    private int resourceData;
    private int resourceCount;
    public LogicBuyResourceCommand(int id, Reader reader, Connection connection) {
        super(id, reader, connection);
    }

    @Override
    public void decode() {
        this.resourceCount = reader.readInt();
        this.resourceData = reader.readInt();
    }

    @Override
    public void process() {
        switch (resourceData){
            case 3000001:
                this.connection.player.gold += resourceCount;
                break;
            case 3000002:
                this.connection.player.elixir += resourceCount;
                break;
            case 3000003:
                this.connection.player.darkelixir += resourceCount;
                break;
        }
        Player.saveData();
    }
}
