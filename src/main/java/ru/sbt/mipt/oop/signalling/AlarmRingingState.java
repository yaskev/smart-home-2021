package ru.sbt.mipt.oop.signalling;

public class AlarmRingingState extends AlarmState {
    public AlarmRingingState(Alarm alarm) {
        super(alarm);
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
