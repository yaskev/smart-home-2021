package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.equipment.*;

import java.io.IOException;

public class DoorOpenedEvent implements Event {
    private final SmartHome smartHome;
    private final String objectId;
    private final Logger logger;

    public DoorOpenedEvent(SmartHome smartHome, Logger logger, String objectId) {
        this.logger = logger;
        this.smartHome = smartHome;
        this.objectId = objectId;
    }

    public void handle() throws IOException {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(this.objectId)) {
                    door.setOpen(true);
                    logger.log("Door " + door.getId() + " in room " + room.getName() + " was opened.");
                }
            }
        }
    }
}
