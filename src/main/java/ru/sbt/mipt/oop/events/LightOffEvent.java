package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.SensorEventType;

public class LightOffEvent implements Event {
    private final String objectId;
    private final SensorEventType eventType = SensorEventType.LIGHT_OFF;

    public LightOffEvent(String objectId) {
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
