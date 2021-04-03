package ru.sbt.mipt.oop.signalling;

public class AlarmDisabledState implements AlarmState {
    private final Alarm alarm;

    public AlarmDisabledState(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public void activate() {
        alarm.changeState(new AlarmEnabledState(alarm));
    }

    @Override
    public void deactivate() {}

    @Override
    public void turnAlarmOn() {
        alarm.changeState(new AlarmRingingState(alarm));
    }

    @Override
    public String toString() {
        return "AlarmDisabledState";
    }
}
