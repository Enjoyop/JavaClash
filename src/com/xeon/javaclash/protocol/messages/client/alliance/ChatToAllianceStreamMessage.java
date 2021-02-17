package com.xeon.javaclash.protocol.messages.client.alliance;

import com.xeon.javaclash.core.Connection;
import com.xeon.javaclash.logic.Alliance;
import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.messages.PiranhaMessage;
import com.xeon.javaclash.protocol.messages.server.alliance.AllianceStreamEntryMessage;
import com.xeon.javaclash.protocol.messages.server.home.OwnHomeDataMessage;

import static java.lang.Runtime.getRuntime;

public class ChatToAllianceStreamMessage extends PiranhaMessage {

    private String message;
    public ChatToAllianceStreamMessage(int id, byte[] payload, Connection connection){
        super(id, payload, connection);
    }

    @Override
    public void decode() {
        this.message = reader.readString();
    }

    @Override
    public void process() {
        AllianceStreamEntryMessage chatline;
        chatline = new AllianceStreamEntryMessage(this.connection, this.message);
        this.connection.messaging.sendMessage(chatline);
        if (message.startsWith("/")){ //Commands
            switch(message){
                case "/help":
                    chatline = new AllianceStreamEntryMessage(this.connection, "List of commands:\n /gems - add gems to your account \n /max - get max resources \n /leave - leave the clan \n/info - print server status");
                    chatline.isBot = true;
                    this.connection.messaging.sendMessage(chatline);
                    break;
                case "/gems":
                    this.connection.player.gems += 14000;
                    Player.saveData();
                    this.connection.messaging.sendMessage(new OwnHomeDataMessage(this.connection));
                    chatline = new AllianceStreamEntryMessage(this.connection, "Gems added!");
                    chatline.isBot = true;
                    this.connection.messaging.sendMessage(chatline);
                    break;
                case "/max":
                    this.connection.player.gold = 18000000;
                    this.connection.player.elixir = 18000000;
                    this.connection.player.darkelixir = 300000;
                    Player.saveData();
                    this.connection.messaging.sendMessage(new OwnHomeDataMessage(this.connection));
                    chatline = new AllianceStreamEntryMessage(this.connection, "Resources added!");
                    chatline.isBot = true;
                    this.connection.messaging.sendMessage(chatline);
                    break;
                case "/info":
                    long mem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
                    mem = mem / (1024 * 1024);
                    chatline = new AllianceStreamEntryMessage(this.connection, "Server status:\nClient version: 13.0.4\nUsed memory: " + mem + " MB\n");
                    chatline.isBot = true;
                    this.connection.messaging.sendMessage(chatline);
                    break;
                case "/leave":
                    Alliance alliance = Alliance.load(this.connection.player.allianceID);
                    alliance.removePlayer(this.connection.player.id);
                    Alliance.saveData();

                    this.connection.player.allianceID = 0;
                    this.connection.player.role = 0;
                    Player.saveData();

                    this.connection.messaging.sendMessage(new OwnHomeDataMessage(this.connection));
                    break;
                default: //UnknownCommand
                    chatline = new AllianceStreamEntryMessage(this.connection, "Unknown Command!");
                    chatline.isBot = true;
                    this.connection.messaging.sendMessage(chatline);
                    break;
            }
        }

    }
}
