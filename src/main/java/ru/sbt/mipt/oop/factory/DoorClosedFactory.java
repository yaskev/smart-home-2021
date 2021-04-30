package ru.sbt.mipt.oop.factory;

import ru.sbt.mipt.oop.events.DoorClosedEvent;
import ru.sbt.mipt.oop.events.SensorEvent;

public class DoorClosedFactory implements EventFactory {
    @Override
    public SensorEvent getEvent(String objectId) {
        return new DoorClosedEvent(objectId);
    }
}
