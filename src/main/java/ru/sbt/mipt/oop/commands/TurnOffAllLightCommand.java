package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Light;

public class TurnOffAllLightCommand implements Command {
    private final SmartHome smartHome;

    public TurnOffAllLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        Action turnOffLight = (object) -> {
            if (object instanceof Light) {
                ((Light)object).setOn(false);
            }
        };
        smartHome.execute(turnOffLight);
    }
}
