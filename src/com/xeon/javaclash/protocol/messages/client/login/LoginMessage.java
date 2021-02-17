package com.xeon.javaclash.protocol.messages.client.login;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.core.Debugger;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.home.OwnHomeDataMessage;
import com.xeon.javaclash.protocol.messages.server.login.LoginFailedMessage;
import com.xeon.javaclash.protocol.messages.server.login.LoginOkMessage;

public class LoginMessage extends PiranhaMessage {
    public LoginMessage(int id, byte[] payload, Connection connection) {
        super(id, payload, connection);
    }

    private int playerID;
    @Override
    public void decode(){
        reader.readInt(); //HighID
        this.playerID = reader.readInt(); //LowID
    }

    @Override
    public void process(){
        this.connection.player = Player.load(playerID);
        if (this.connection.player != null){
            this.connection.messaging.sendMessage(new LoginOkMessage(this.connection));
            this.connection.messaging.sendMessage(new OwnHomeDataMessage(this.connection));
            Player.saveData();
        } else {
            LoginFailedMessage fail = new LoginFailedMessage(this.connection);
            fail.reason = "Account not found. Please clear app data.";
            this.connection.messaging.sendMessage(fail);
        }
    }
}
