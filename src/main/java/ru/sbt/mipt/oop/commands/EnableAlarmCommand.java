package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.signalling.Alarm;

public class EnableAlarmCommand implements Command {
    private final SmartHome smartHome;

    public EnableAlarmCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        Action enableAlarm = (object) -> {
            if (object instanceof Alarm) {
                ((Alarm)object).activate(1);
            }
        };
        smartHome.execute(enableAlarm);
    }
}
