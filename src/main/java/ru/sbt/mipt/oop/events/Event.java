package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.SensorEventType;

import java.io.IOException;

public interface Event {
    SensorEventType getEventType();

    String getObjectId();
}
