package ru.sbt.mipt.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerializer implements Serializer {
    @Override
    public String serialize(SmartHome smartHome) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(smartHome);
    }
}
