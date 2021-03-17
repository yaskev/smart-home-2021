package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.events.Event;

public class DoorEventHandler implements EventHandler {
    private final SmartHome smartHome;

    public DoorEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == SensorEventType.DOOR_OPEN || event.getEventType() == SensorEventType.DOOR_CLOSED) {
            boolean isDoorOpen = event.getEventType() == SensorEventType.DOOR_OPEN;
            String objId = event.getObjectId();

            Action updateDoorState = (object) -> {
                if (object instanceof Door && ((Door) object).getId().equals(objId)) {
                    ((Door)object).setOpen(isDoorOpen);
                }
            };
            smartHome.execute(updateDoorState);
        }
    }
}
