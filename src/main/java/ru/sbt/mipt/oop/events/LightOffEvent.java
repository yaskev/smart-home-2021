package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.EventType;

public class LightOffEvent extends SensorEvent {
    private final String objectId;
    private final EventType eventType = EventType.LIGHT_OFF;

    public LightOffEvent(String objectId) {
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
