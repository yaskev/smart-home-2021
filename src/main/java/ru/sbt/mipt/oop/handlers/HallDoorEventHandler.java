package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.equipment.Room;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

public class HallDoorEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final CommandSender sender;

    public HallDoorEventHandler(SmartHome smartHome, CommandSender sender) {
        this.smartHome = smartHome;
        this.sender = sender;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == EventType.DOOR_CLOSED) {
            Action turnOffAllLight = (object) -> {
                if (object instanceof Light) {
                    ((Light)object).setOn(false);
                    SensorCommand command = new LightOffCommand(((Light)object).getId());
                    sender.sendCommand(command);
                }
            };

            Action checkIfHallDoor = (object) -> {
                if (object instanceof Room && ((Room) object).getName().equals("hall")) {
                    Action onHallDoorClosed = (obj) -> {
                        if (obj instanceof Door && ((Door) obj).getId().equals(((SensorEvent)event).getObjectId())) {
                            smartHome.execute(turnOffAllLight);
                        }
                    };
                    ((Room)object).execute(onHallDoorClosed);
                }
            };

            smartHome.execute(checkIfHallDoor);
        }
    }
}
