package ru.sbt.mipt.oop.signalling;

public class AlarmEnabledState implements AlarmState {
    private final Alarm alarm;

    public AlarmEnabledState(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void activate() {}

    @Override
    public void deactivate() {
        alarm.changeState(new AlarmDisabledState(alarm));
    }

    @Override
    public void turnAlarmOn() {
        alarm.changeState(new AlarmRingingState(alarm));
    }

    @Override
    public String toString() {
        return "AlarmEnabledState";
    }
}
