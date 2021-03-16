package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.*;

public class DoorClosedEvent implements Event {
    private final String objectId;
    private final SensorEventType eventType = SensorEventType.DOOR_CLOSED;

    public DoorClosedEvent(String objectId) {
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
