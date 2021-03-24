package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.EventType;

public class LightOnEvent extends SensorEvent {
    private final String objectId;
    private final EventType eventType = EventType.LIGHT_ON;

    public LightOnEvent(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public String getObjectId() {
        return objectId;
    }
}
