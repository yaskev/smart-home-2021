package ru.sbt.mipt.oop.factory;

import ru.sbt.mipt.oop.events.SensorEvent;

public interface EventFactory {
    SensorEvent getEvent(String objectId);
}
