package ru.sbt.mipt.oop;

public class LightOffCommand implements SensorCommand {
    private final String objectId;

    public LightOffCommand(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return "SensorCommand{" +
                "type=LightOffCommand" +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
