package com.xeon.javaclash.logic;

import com.xeon.javaclash.core.Debugger;
import com.xeon.javaclash.datastream.Writer;

import java.io.*;
import java.util.HashMap;

public class Player {

    public static HashMap<Integer, Player> players = new HashMap<>(); //data storage

    public int id, gems = 100000, gold = 500, elixir = 500, darkelixir = 500;
    public int trophies, lvl = 8, exp;
    public int allianceID;
    public String token = "Token", name = "Guest";
    public LogicJsonObject home;
    public int nameSet = 0;
    public int lastMessage = 0;
    public int role = 0;

    private Player(int id){
        this.id = id;
        home = new LogicJsonObject("Gamefiles/starting_home.json");
    }

    public static Player load(int id) {
        if (id == 0) {
            id = 1;
            Debugger.print("Creating new account...");
            for (; players.containsKey(Integer.valueOf(id)); id++);
            players.put(Integer.valueOf(id), new Player(id));
        }

        if (!players.containsKey(Integer.valueOf(id))) {
            return null; //LoginFailed
        }

        return players.get(Integer.valueOf(id));
    }

    public static void loadSavedData() {
        if (!(new File("players.dat")).exists()) {
            return;
        }

        try (DataInputStream d = new DataInputStream(new FileInputStream("players.dat"))){
            int count = d.readInt();

            for (int i = 0; i < count; i++) {
                Player pl = new Player(d.readInt());
                pl.name = d.readUTF();
                pl.token = d.readUTF();
                pl.nameSet = d.readInt();
                pl.lvl = d.readInt();
                pl.exp = d.readInt();
                pl.trophies = d.readInt();
                pl.gems = d.readInt();
                pl.gold = d.readInt();
                pl.elixir = d.readInt();
                pl.darkelixir = d.readInt();
                pl.allianceID = d.readInt();
                pl.role = d.readInt();

                d.skip(16L);
                players.put(Integer.valueOf(pl.id), pl);
            }
            Debugger.print("Loaded data file with " + count + " players!");
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public static void saveData() {
        try (DataOutputStream d = new DataOutputStream(new FileOutputStream("players.dat"))) {
            d.writeInt(players.size());
            for (Player pl : players.values()) {
                d.writeInt(pl.id);
                d.writeUTF(pl.name);
                d.writeUTF(pl.token);
                d.writeInt(pl.nameSet);
                d.writeInt(pl.lvl);
                d.writeInt(pl.exp);
                d.writeInt(pl.trophies);
                d.writeInt(pl.gems);
                d.writeInt(pl.gold);
                d.writeInt(pl.elixir);
                d.writeInt(pl.darkelixir);
                d.writeInt(pl.allianceID);
                d.writeInt(pl.role);

                d.write(new byte[16]);
            }
        } catch (IOException e) {throw new RuntimeException(e);}
    }

    public void LogicClientHome(Writer writer) {
        writer.writeInt(1); //timestamp

        writer.writeLongLong(0, this.id); //home id

        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeInt(0);

        //starting_home.json
        writer.writeCompressedString(this.home.data);

        writer.writeCompressedString("{\"events\":[]}");
        writer.writeCompressedString("{\"Village2\":{\"TownHallMaxLevel\":9,\"ScoreChangeForLosing\":[{\"Milestone\":0,\"Percentage\":0},{\"Milestone\":400,\"Percentage\":30},{\"Milestone\":800,\"Percentage\":55},{\"Milestone\":1200,\"Percentage\":70},{\"Milestone\":1600,\"Percentage\":85},{\"Milestone\":2000,\"Percentage\":95},{\"Milestone\":2400,\"Percentage\":100}],\"StrengthRangeForScore\":[{\"Milestone\":0,\"Percentage\":60},{\"Milestone\":200,\"Percentage\":80},{\"Milestone\":400,\"Percentage\":100},{\"Milestone\":600,\"Percentage\":120},{\"Milestone\":800,\"Percentage\":140},{\"Milestone\":1000,\"Percentage\":160},{\"Milestone\":1200,\"Percentage\":180},{\"Milestone\":1400,\"Percentage\":200},{\"Milestone\":1600,\"Percentage\":400},{\"Milestone\":1800,\"Percentage\":600},{\"Milestone\":2000,\"Percentage\":1000}]},\"KillSwitches\":{\"TestValue\":true}}");
    }

    public void LogicClientAvatar(Writer writer) {
        writer.writeLongLong(0, this.id);
        writer.writeLongLong(0, this.id);

        writer.writeBool(this.allianceID != 0); //hasAlliance
        if (this.allianceID != 0) {
            Alliance alliance = Alliance.load(this.allianceID);
            writer.writeLongLong(0, alliance.id); //ClanID

            writer.writeString(alliance.name);

            writer.writeInt(alliance.badge); //badge id

            writer.writeInt(2);
            writer.writeInt(1);

            writer.writeBool(false);

            writer.writeLongLong(0, 1);
        } else {
            writer.writeLongLong(0, 1);
        }


        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(1000);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(1000);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(10);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeString(this.name); //name
        writer.writeString(null);

        writer.writeInt(this.lvl); //level
        writer.writeInt(0); //exp

        writer.writeInt(this.gems); //gems
        writer.writeInt(this.gems); //free gems

        writer.writeInt(1200);
        writer.writeInt(60);

        writer.writeInt(this.trophies);
        writer.writeInt(0);

        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeInt(0);
        writer.writeByte(0);

        writer.writeInt(0);
        writer.writeInt(-1);

        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeByte(0);

        writer.writeInt(6); //resource cap
        writer.writeInt(3000000);
        writer.writeInt(1000000);
        writer.writeInt(3000001);
        writer.writeInt(2000000000);
        writer.writeInt(3000002);
        writer.writeInt(2000000000);
        writer.writeInt(3000003);
        writer.writeInt(2000000000);
        writer.writeInt(3000007);
        writer.writeInt(2000000000);
        writer.writeInt(3000008);
        writer.writeInt(2000000000);

        writer.writeInt(6); //resources array
        {
            writer.writeInt(3000000);
            writer.writeInt(1000000);
            writer.writeInt(3000001);
            writer.writeInt(this.gold);
            writer.writeInt(3000002);
            writer.writeInt(this.elixir);
            writer.writeInt(3000003);
            writer.writeInt(this.darkelixir);
            writer.writeInt(3000007);
            writer.writeInt(100000);
            writer.writeInt(3000008);
            writer.writeInt(100000);
        }

        writer.writeInt(3); //home troops
        {
            writer.writeInt(4000005); //balloon
            writer.writeInt(10);
            writer.writeInt(4000006); //wizard
            writer.writeInt(5);
            writer.writeInt(4000059); //electro dragon
            writer.writeInt(1);
        }

        writer.writeInt(0);

        writer.writeInt(0);

        writer.writeInt(0);

        writer.writeInt(5); //hero levels
        {
            writer.writeInt(28000000);
            writer.writeInt(69);

            writer.writeInt(28000001);
            writer.writeInt(69);

            writer.writeInt(28000002);
            writer.writeInt(49);

            writer.writeInt(28000003);
            writer.writeInt(29);

            writer.writeInt(28000004);
            writer.writeInt(19);
        }

        writer.writeInt(0); //hero health

        writer.writeInt(0);

        writer.writeInt(0);

        writer.writeInt(5); //hero states
        {
            writer.writeInt(28000000);
            writer.writeInt(3);
            writer.writeInt(28000001);
            writer.writeInt(3);
            writer.writeInt(28000002);
            writer.writeInt(3);
            writer.writeInt(28000003);
            writer.writeInt(3);
            writer.writeInt(28000004);
            writer.writeInt(3);
        }

        writer.writeInt(0);

        int steps = this.nameSet == 0 ? 10 : 35;
        writer.writeInt(steps); //tutorial steps
        {
            for (int i = 0; i < steps; i++)
                writer.writeInt(21000000 + i);
        }

        writer.writeInt(0);

        writer.writeInt(0);

        writer.writeInt(94); //NPC completed levels
        for (int i = 0; i < 94; i++){
            writer.writeInt(21000000 + i);
            writer.writeInt(3);
        }

        writer.writeInt(0);

        writer.writeInt(0);

        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeInt(97); //??
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(1);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(1);
        writer.writeInt(1613470277);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(255);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(1);
        writer.writeInt(20);

        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);
        writer.writeInt(0);

        writer.writeByte(0);


    }
}
