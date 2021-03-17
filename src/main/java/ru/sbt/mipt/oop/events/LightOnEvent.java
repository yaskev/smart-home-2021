package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.SensorEventType;

public class LightOnEvent implements Event {
    private final String objectId;
    private final SensorEventType eventType = SensorEventType.LIGHT_ON;

    public LightOnEvent(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public SensorEventType getEventType() {
        return eventType;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }
}
