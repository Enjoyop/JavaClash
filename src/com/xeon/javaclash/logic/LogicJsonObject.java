package com.xeon.javaclash.logic;

import com.xeon.javaclash.core.Debugger;

import java.io.*;

public class LogicJsonObject {

    private String path;
    public String data;
    public LogicJsonObject(String path){
        this.path = path;
        this.data = readLine();
    }

    private String readLine() {
        String line;
        try (BufferedReader reader = new BufferedReader(new FileReader((path)))) {
            line = reader.readLine();
            return line;
        } catch (IOException e) {
            if (e instanceof FileNotFoundException){
                Debugger.error("File not found: " + path);
                throw new RuntimeException(e);
            }
            return null;
        }
    }
}
