package ru.sbt.mipt.oop.factory;

import ru.sbt.mipt.oop.events.DoorOpenedEvent;
import ru.sbt.mipt.oop.events.SensorEvent;

public class DoorOpenedFactory implements EventFactory {
    @Override
    public SensorEvent getEvent(String objectId) {
        return new DoorOpenedEvent(objectId);
    }
}
