package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.SensorEventType;

public interface Event {
    SensorEventType getEventType();

    String getObjectId();
}
