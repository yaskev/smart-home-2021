package ru.sbt.mipt.oop.factory;

import ru.sbt.mipt.oop.events.LightOffEvent;
import ru.sbt.mipt.oop.events.SensorEvent;

public class LightOffFactory implements EventFactory {
    @Override
    public SensorEvent getEvent(String objectId) {
        return new LightOffEvent(objectId);
    }
}
