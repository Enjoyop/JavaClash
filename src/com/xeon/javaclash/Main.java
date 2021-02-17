package com.xeon.javaclash;

import com.xeon.javaclash.core.Debugger;
import com.xeon.javaclash.core.ServerConnection;
import com.xeon.javaclash.logic.Alliance;
import com.xeon.javaclash.logic.Player;

public class Main {

    public static void main(String[] args) {
        Debugger.print("Preparing server...");
        Player.loadSavedData();
        Alliance.loadSavedData();

        ServerConnection server = new ServerConnection(9339);
    }
}
