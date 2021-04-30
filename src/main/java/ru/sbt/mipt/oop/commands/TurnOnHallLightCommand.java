package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.equipment.Room;

public class TurnOnHallLightCommand implements Command {
    private final SmartHome smartHome;

    public TurnOnHallLightCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        Action turnOnLight = (object) -> {
            if (object instanceof Room && ((Room) object).getName().equals("hall")) {
                Action turnOn = (obj) -> {
                    if (obj instanceof Light) {
                        ((Light)obj).setOn(true);
                    }
                };
                ((Room)object).execute(turnOn);
            }
        };
        smartHome.execute(turnOnLight);
    }
}
