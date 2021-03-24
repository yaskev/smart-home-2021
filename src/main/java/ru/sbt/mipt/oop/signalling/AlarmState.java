package ru.sbt.mipt.oop.signalling;

public abstract class AlarmState {
    protected final Alarm alarm;

    public AlarmState(Alarm alarm) {
        this.alarm = alarm;
    }

    public abstract void activate();
    public abstract void deactivate();
    public abstract void turnAlarmOn();
}
