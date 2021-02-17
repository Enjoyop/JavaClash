package com.xeon.javaclash.protocol.commands;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.core.Debugger;
import com.xeon.javaclash.datastream.Reader;
import com.xeon.javaclash.protocol.commands.client.LogicAttackNpcCommand;
import com.xeon.javaclash.protocol.commands.client.LogicBuyResourceCommand;

public class LogicCommandManager {
    public static LogicCommand createCommand(int id, Reader reader, Connection connection){
        switch (id){
            case 518:
                return new LogicBuyResourceCommand(id, reader, connection);
            case 640:
                return new LogicAttackNpcCommand(id, reader, connection);
            default:
                Debugger.print("Unhandled command: " + id);
                return null;
        }
    }
}
