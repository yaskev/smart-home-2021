package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

public class DoorEventHandler implements EventHandler {
    private final SmartHome smartHome;

    public DoorEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == EventType.DOOR_OPEN || event.getEventType() == EventType.DOOR_CLOSED) {
            boolean isDoorOpen = event.getEventType() == EventType.DOOR_OPEN;
            String objId = ((SensorEvent)event).getObjectId();

            Action updateDoorState = (object) -> {
                if (object instanceof Door && ((Door) object).getId().equals(objId)) {
                    ((Door)object).setOpen(isDoorOpen);
                }
            };
            smartHome.execute(updateDoorState);
        }
    }
}
