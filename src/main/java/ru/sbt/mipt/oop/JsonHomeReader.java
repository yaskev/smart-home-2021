package ru.sbt.mipt.oop;

import com.google.gson.Gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonHomeReader implements SmartHomeReader {
    private final String filename;

    public JsonHomeReader(String filename) {
        this.filename = filename;
    }

    @Override
    public SmartHome read() {
        Gson gson = new Gson();
        try {
            String json = new String(Files.readAllBytes(Paths.get(this.filename)));
            return gson.fromJson(json, SmartHome.class);
        } catch (IOException e) {
            System.err.println(e.toString());
        }
        return null;
    }
}
