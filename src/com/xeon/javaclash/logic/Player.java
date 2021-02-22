package com.xeon.javaclash.logic;

import com.xeon.javaclash.core.Debugger;
import com.xeon.javaclash.datastream.Writer;

import java.io.*;
import java.util.HashMap;

public class Player {

    public static HashMap<Integer, Player> players = new HashMap<>(); //data storage

    public int id;
    public int gems = 100000;
    public int gold = 500;
    public int elixir = 500;
    public int darkelixir = 500;
    public int builderGold = 1000000000;
    public int builderElixir = 1000000000;
    public int medals = 2500;

    public int trophies = 1250;
    public int builderTrophies = 1000;
    public int attackWins = 100;
    public int attackLoses = 25;
    public int defenseWins = 50;
    public int defenseLoses = 12;

    public int lvl = 8;
    public int exp = 50;
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

        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream("players.dat"))) {
            int count = dataInputStream.readInt();

            for (int i = 0; i < count; i++) {
                Player player = new Player(dataInputStream.readInt());

                player.name = dataInputStream.readUTF();
                player.token = dataInputStream.readUTF();
                player.nameSet = dataInputStream.readInt();
                player.lvl = dataInputStream.readInt();
                player.exp = dataInputStream.readInt();
                player.trophies = dataInputStream.readInt();
                player.builderTrophies = dataInputStream.readInt();
                player.gems = dataInputStream.readInt();
                player.gold = dataInputStream.readInt();
                player.elixir = dataInputStream.readInt();
                player.darkelixir = dataInputStream.readInt();
                player.builderGold = dataInputStream.readInt();
                player.builderElixir = dataInputStream.readInt();
                player.medals = dataInputStream.readInt();
                player.allianceID = dataInputStream.readInt();
                player.role = dataInputStream.readInt();

                dataInputStream.skip(16L);
                players.put(Integer.valueOf(player.id), player);
            }
            Debugger.print("Loaded data file with " + count + " players!");
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static void saveData() {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream("players.dat"))) {
            dataOutputStream.writeInt(players.size());
            for (Player player : players.values()) {
                dataOutputStream.writeInt(player.id);
                dataOutputStream.writeUTF(player.name);
                dataOutputStream.writeUTF(player.token);
                dataOutputStream.writeInt(player.nameSet);
                dataOutputStream.writeInt(player.lvl);
                dataOutputStream.writeInt(player.exp);
                dataOutputStream.writeInt(player.trophies);
                dataOutputStream.writeInt(player.builderTrophies);
                dataOutputStream.writeInt(player.gems);
                dataOutputStream.writeInt(player.gold);
                dataOutputStream.writeInt(player.elixir);
                dataOutputStream.writeInt(player.darkelixir);
                dataOutputStream.writeInt(player.builderGold);
                dataOutputStream.writeInt(player.builderElixir);
                dataOutputStream.writeInt(player.medals);
                
                dataOutputStream.writeInt(player.allianceID);
                dataOutputStream.writeInt(player.role);

                dataOutputStream.write(new byte[16]);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
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
        writer.writeInt(this.exp); //exp

        writer.writeInt(this.gems); //gems
        writer.writeInt(this.gems); //free gems

        writer.writeInt(1200);
        writer.writeInt(60);

        writer.writeInt(this.trophies);
        writer.writeInt(this.builderTrophies);

        writer.writeInt(this.attackWins);
        writer.writeInt(this.attackLoses);

        writer.writeInt(this.defenseWins);
        writer.writeInt(this.defenseLoses);

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
        {
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
        }

        writer.writeInt(7); //resources array
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
            writer.writeInt(this.builderGold);
            writer.writeInt(3000008);
            writer.writeInt(this.builderElixir);
            writer.writeInt(3000009);
            writer.writeInt(this.medals);
        }

        writer.writeInt(2); //home troops
        {
            writer.writeInt(4000005); //balloon
            writer.writeInt(8);
            writer.writeInt(4000059); //electro dragon
            writer.writeInt(8);
        }

        writer.writeInt(4); //home spells
        {
            writer.writeInt(26000002); //rage
            writer.writeInt(3);
            writer.writeInt(26000005); //freeze
            writer.writeInt(3);
            writer.writeInt(26000009); //poison
            writer.writeInt(1);
            writer.writeInt(26000011); //speed up
            writer.writeInt(1);
        }

        writer.writeInt(22); // home troop levels
        {
            writer.writeInt(4000000); //barbarian
            writer.writeInt(7);
            writer.writeInt(4000001); //archer
            writer.writeInt(7);
            writer.writeInt(4000002); //goblin
            writer.writeInt(6);
            writer.writeInt(4000003); //giant
            writer.writeInt(8);
            writer.writeInt(4000004); //wall brraker
            writer.writeInt(8);
            writer.writeInt(4000005); //balloon
            writer.writeInt(8);
            writer.writeInt(4000006); //wizard
            writer.writeInt(8);
            writer.writeInt(4000007); //healer
            writer.writeInt(5);
            writer.writeInt(4000008); //dragon
            writer.writeInt(7);
            writer.writeInt(4000009); //pekka
            writer.writeInt(7);
            writer.writeInt(4000010); //minion
            writer.writeInt(7);
            writer.writeInt(4000011); //boar rider
            writer.writeInt(9);
            writer.writeInt(4000012); //valkriye
            writer.writeInt(6);
            writer.writeInt(4000013); //golem
            writer.writeInt(8);
            writer.writeInt(4000015); //witch
            writer.writeInt(4);
            writer.writeInt(4000017); //lava hound
            writer.writeInt(4);
            writer.writeInt(4000022); //bowler
            writer.writeInt(4);
            writer.writeInt(4000023); //baby dragon
            writer.writeInt(5);
            writer.writeInt(4000024); //miner
            writer.writeInt(6);
            writer.writeInt(4000053); //yeti
            writer.writeInt(2);
            writer.writeInt(4000058); //ice golem
            writer.writeInt(4);
            writer.writeInt(4000059); //electro dragon
            writer.writeInt(3);
        }

        writer.writeInt(11); //spell levels
        {
            writer.writeInt(26000000); //lightning
            writer.writeInt(6);
            writer.writeInt(26000001); //healing
            writer.writeInt(7);
            writer.writeInt(26000002); //rage
            writer.writeInt(5);
            writer.writeInt(26000003); //jump
            writer.writeInt(3);
            writer.writeInt(26000005); //freeze
            writer.writeInt(6);
            writer.writeInt(26000009); //poison
            writer.writeInt(5);
            writer.writeInt(26000010); //earthquake
            writer.writeInt(4);
            writer.writeInt(26000011); //speed up
            writer.writeInt(4);
            writer.writeInt(26000016); //clone
            writer.writeInt(4);
            writer.writeInt(26000017); //skeleton
            writer.writeInt(6);
            writer.writeInt(26000027); //bat
            writer.writeInt(6);
        }

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

        writer.writeInt(5); //hero health
        for (int i = 28000000; i < 28000005; i++) {
            writer.writeInt(i);
            writer.writeInt(0);
        }

        writer.writeInt(5); //hero states
        for (int i = 28000000; i < 28000005; i++) {
            writer.writeInt(i);
            writer.writeInt(3);
        }

        writer.writeInt(1); //alliance units
        {
            writer.writeInt(4000006);
            writer.writeInt(6);
            writer.writeInt(7);
        }

        int steps = this.nameSet == 0 ? 10 : 35;
        writer.writeInt(steps); //tutorial steps
        {
            for (int i = 0; i < steps; i++)
                writer.writeInt(21000000 + i);
        }

       
        writer.writeInt(7);
        {
            for (int i = 0; i < 7; i++) // achievement rewards
            {
                writer.writeInt(23000000 + i);
            }
        }

        writer.writeInt(7); // achievement progress
        {
            writer.writeInt(23000000);
            writer.writeInt(14);
            writer.writeInt(23000001);
            writer.writeInt(225);
            writer.writeInt(23000002);
            writer.writeInt(12);
            writer.writeInt(23000003);
            writer.writeInt(1000);
            writer.writeInt(23000004);
            writer.writeInt(3);
            writer.writeInt(23000005);
            writer.writeInt(2000000000);
            writer.writeInt(23000006);
            writer.writeInt(2000000000);
        }

        writer.writeInt(94); //NPC completed levels
        for (int i = 17000000; i < 17000094; i++) {
            writer.writeInt(i);
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
