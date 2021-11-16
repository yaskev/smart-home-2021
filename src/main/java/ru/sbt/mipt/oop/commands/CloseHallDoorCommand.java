package ru.sbt.mipt.oop.commands;

import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.equipment.Room;

public class CloseHallDoorCommand implements Command {
    private final SmartHome smartHome;

    public CloseHallDoorCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        Action closeHallDoor = (object) -> {
            if (object instanceof Room && ((Room) object).getName().equals("hall")) {
                Action close = (obj) -> {
                    if (obj instanceof Door) {
                        ((Door)obj).setOpen(false);
                    }
                };
                ((Room)object).execute(close);
            }
        };
        smartHome.execute(closeHallDoor);
    }
}
