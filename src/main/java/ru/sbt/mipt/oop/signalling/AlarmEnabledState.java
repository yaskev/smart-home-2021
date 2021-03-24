package ru.sbt.mipt.oop.signalling;

public class AlarmEnabledState extends AlarmState {
    public AlarmEnabledState(Alarm alarm) {
        super(alarm);
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
