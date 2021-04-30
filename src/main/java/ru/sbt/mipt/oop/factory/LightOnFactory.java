package ru.sbt.mipt.oop.factory;

import ru.sbt.mipt.oop.events.LightOnEvent;
import ru.sbt.mipt.oop.events.SensorEvent;

public class LightOnFactory implements EventFactory {
    @Override
    public SensorEvent getEvent(String objectId) {
        return new LightOnEvent(objectId);
    }
}
