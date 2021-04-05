package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.EventType;

public abstract class AlarmEvent implements Event {
    @Override
    public abstract EventType getEventType();

    public abstract int getAlarmCode();
}
