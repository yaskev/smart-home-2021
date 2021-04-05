package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.EventType;

public abstract class SensorEvent implements Event {

    @Override
    public abstract EventType getEventType();

    public abstract String getObjectId();
}
