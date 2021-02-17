package com.xeon.javaclash.core;

import com.xeon.javaclash.logic.Player;
import com.xeon.javaclash.protocol.Messaging;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Connection implements Closeable{
    private Socket socket;
    public Messaging messaging;
    private DataInputStream inputStream;
    public DataOutputStream outputStream;
    public Player player;

    public boolean isConnected = true;

    public Connection(Socket socket){
        this.socket = socket;
        this.messaging = new Messaging(this);
        try {
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e){
            Debugger.error("Error while creating streams");
            Debugger.error(e.getMessage());
            throw new RuntimeException();
        }
        while (!socket.isClosed()){
            this.receive();
        }
    }

    public void receive(){
        try {
            int id = inputStream.readChar(); //unsigned short
            int length = inputStream.read() << 16 | inputStream.read() << 8 | inputStream.read();
            int version = inputStream.readChar(); //unsigned short

            byte[] payload = new byte[length];
            inputStream.read(payload);
            messaging.onReceive(id, payload);
        } catch (IOException exception) {
            this.close();
        }
    }

    public void send(){

    }

    public Socket getSocket(){
        return this.socket;
    }

    public void close(){
        try {
            this.isConnected = false;
            this.messaging = null;
            this.inputStream.close();
            this.outputStream.close();
            this.socket.close();
            Debugger.print("Client disconnected " + getaddrinfo());
        } catch (IOException x){
            throw new RuntimeException(x);
        }
    }

    public String getaddrinfo(){
        return socket.getRemoteSocketAddress().toString();
    }
}
