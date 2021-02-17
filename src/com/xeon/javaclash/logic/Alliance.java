package com.xeon.javaclash.logic;

import com.xeon.javaclash.core.Debugger;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Alliance {

    public int id, badge;
    public String name = "TestAlliance", description = " ";
    public ArrayList<Integer> players;
    private static HashMap<Integer, Alliance> alliances = new HashMap<>();

    private Alliance(int id) {
        this.id = id;
        this.players = new ArrayList<>();
    }

    public static Alliance load(int id) {
        if (id == 0) {
            id = 1;
            Debugger.print("Creating new alliance...");
            for (; alliances.containsKey(Integer.valueOf(id)); id++);
            alliances.put(Integer.valueOf(id), new Alliance(id));
        }
        return alliances.get(Integer.valueOf(id));
    }

    public static List<Alliance> getAlliancesList() {
        int count = alliances.size();
        return (new ArrayList<>(alliances.values()));
    }

    public void addPlayer(int id) {
        this.players.add(id);
        Alliance.saveData();
    }

    public void removePlayer(int id) {
        this.players.remove((Integer)id);
        Alliance.saveData();
    }

    public static int count() {
        return alliances.size();
    }

    public static void saveData() {
        try (DataOutputStream d = new DataOutputStream(new FileOutputStream("alliances.dat"))){
            d.writeInt(alliances.size());

            for (Alliance all : alliances.values()){
                d.writeInt(all.id);
                d.writeInt(all.badge);
                d.writeUTF(all.name);
                d.writeUTF(all.description);

                d.writeInt(all.players.size());
                for (int i = 0; i < all.players.size(); i++) {
                    d.writeInt(all.players.get(i));
                }
                d.write(new byte[10]);
            }
        } catch (IOException e) {
            Debugger.error("Error while saving alliances...");
            throw new RuntimeException(e);
        }
    }

    public static void loadSavedData() {
        if (!(new File("alliances.dat")).exists()) {
            return;
        }

        try (DataInputStream d = new DataInputStream(new FileInputStream("alliances.dat"))) {
            int count = d.readInt();

            for (int i = 0; i < count; i++) {
                Alliance all = new Alliance(d.readInt());

                all.badge = d.readInt();
                all.name = d.readUTF();
                all.description = d.readUTF();

                int plcount = d.readInt();
                all.players = new ArrayList<>();
                for (int j = 0; j < plcount; j++) {
                    all.players.add(d.readInt()); //reads player id
                }

                d.skip(10L);
                alliances.put(Integer.valueOf(all.id), all);
            }

            Debugger.print("Loaded data file with " + count + " alliances!");
        } catch (IOException e) {
            Debugger.error("Error while loading alliances data...");
            throw new RuntimeException(e);
        }
    }


}
