package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.signalling.Alarm;

public class MakeAlarmRingingCommand implements Command {
    private final SmartHome smartHome;

    public MakeAlarmRingingCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        Action ringAlarm = (object) -> {
            if (object instanceof Alarm) {
                ((Alarm)object).turnAlarmOn();
            }
        };
        smartHome.execute(ringAlarm);
    }
}
