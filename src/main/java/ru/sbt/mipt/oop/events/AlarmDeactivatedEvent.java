package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.EventType;

public class AlarmDeactivatedEvent extends AlarmEvent {
    private final int alarmCode;

    public AlarmDeactivatedEvent(int code) {
        alarmCode = code;
    }

    @Override
    public EventType getEventType() {
        return EventType.ALARM_DEACTIVATE;
    }

    @Override
    public int getAlarmCode() {
        return alarmCode;
    }
}
