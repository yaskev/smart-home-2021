package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.*;

public class DoorOpenedEvent implements Event {
    private final String objectId;
    private final SensorEventType eventType = SensorEventType.DOOR_OPEN;

    public DoorOpenedEvent(String objectId) {
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
