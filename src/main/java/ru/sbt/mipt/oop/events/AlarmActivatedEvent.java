package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.EventType;

public class AlarmActivatedEvent extends AlarmEvent {
    private final int alarmCode;

    public AlarmActivatedEvent(int code) {
        alarmCode = code;
    }

    @Override
    public EventType getEventType() {
        return EventType.ALARM_ACTIVATE;
    }

    @Override
    public int getAlarmCode() {
        return alarmCode;
    }
}
