package ru.sbt.mipt.oop.signalling;

import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.action.Actionable;

public class Alarm implements Actionable {
    private AlarmState state;
    private int alarmCode;

    public Alarm() {
        this.state = new AlarmDisabledState(this);
    }

    public void activate(int code) {
        alarmCode = code;
        state.activate();
    }

    public boolean deactivate(int code) {
        if (code == alarmCode) {
            state.deactivate();
        } else {
            state.turnAlarmOn();
        }

        return code != alarmCode;
    }

    public void turnAlarmOn() {
        state.turnAlarmOn();
    }

    void changeState(AlarmState state) {
        this.state = state;
    }

    @Override
    public void execute(Action action) {
        action.run(this);
    }

    public boolean isEnabled() {
        return state instanceof AlarmEnabledState;
    }

    public boolean isRinging() {
        return state instanceof AlarmRingingState;
    }
}
