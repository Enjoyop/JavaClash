package com.xeon.javaclash.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {

    public ServerConnection(int port){
        try (ServerSocket server = new ServerSocket(port)){
            Debugger.print("Server is listening on port " + port);

            while (true) {
                Socket socket = server.accept();
                new Thread (() -> {
                    Debugger.print("New connection accepted.");
                    Connection connection = new Connection(socket);
                }).start();
            }

        } catch (IOException e){
            Debugger.error("IOException in ServerConnection!");
            throw new RuntimeException(e);
        }
    }
}
