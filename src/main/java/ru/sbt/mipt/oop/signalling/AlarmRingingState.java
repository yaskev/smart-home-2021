package ru.sbt.mipt.oop.signalling;

public class AlarmRingingState implements AlarmState {
    private final Alarm alarm;

    public AlarmRingingState(Alarm alarm) {
       this.alarm = alarm;
    }

    @Override
    public void activate() {
        alarm.changeState(new AlarmEnabledState(alarm));
    }

    @Override
    public void deactivate() {
        alarm.changeState(new AlarmDisabledState(alarm));
    }

    @Override
    public void turnAlarmOn() {}

    @Override
    public String toString() {
        return "AlarmRingingState";
    }
}
