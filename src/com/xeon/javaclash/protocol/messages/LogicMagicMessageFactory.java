package com.xeon.javaclash.protocol.messages;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.core.Debugger;
import com.xeon.javaclash.protocol.messages.client.alliance.*;
import com.xeon.javaclash.protocol.messages.client.home.AskForAvatarProfileMessage;
import com.xeon.javaclash.protocol.messages.client.home.ChangeAvatarNameMessage;
import com.xeon.javaclash.protocol.messages.client.home.GoHomeMessage;
import com.xeon.javaclash.protocol.messages.client.login.KeepAliveMessage;
import com.xeon.javaclash.protocol.messages.client.login.LoginMessage;
import com.xeon.javaclash.protocol.messages.server.EndClientTurnMessage;

public class LogicMagicMessageFactory {

    public static PiranhaMessage createMessageByType(int id, byte[] payload, Connection connection){
        switch (id){
            case 10101:
                return new LoginMessage(id, payload, connection);
            case 10108:
                return new KeepAliveMessage(id, payload, connection);
            case 10936:
                return new GoHomeMessage(id, payload, connection);
            case 11186:
                return new AskForAllianceInfoMessage(id, payload, connection);
            case 11734:
                return new AskForAvatarProfileMessage(id, payload, connection);
            case 12733:
                return new JoinAllianceMessage(id, payload, connection);
            case 12906:
                return new EndClientTurnMessage(id, payload, connection);
            case 13439:
                return new LeaveAllianceMessage(id, payload, connection);
            case 14466:
                return new ChatToAllianceStreamMessage(id, payload, connection);
            case 15027:
                return new AskForJoinableAlliancesList(id, payload, connection);
            case 17173:
                return new ChangeAvatarNameMessage(id, payload, connection);
            case 19044:
                return new CreateAllianceMessage(id, payload, connection);
            default:
                Debugger.print("Unhandled: " + id);
                return null;
        }
    }
}
