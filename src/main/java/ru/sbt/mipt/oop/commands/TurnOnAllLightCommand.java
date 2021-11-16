package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Light;

public class TurnOnAllLightCommand implements Command {
    private final SmartHome smartHome;

    public TurnOnAllLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        Action turnOnLight = (object) -> {
            if (object instanceof Light) {
                ((Light)object).setOn(true);
            }
        };
        smartHome.execute(turnOnLight);
    }
}
