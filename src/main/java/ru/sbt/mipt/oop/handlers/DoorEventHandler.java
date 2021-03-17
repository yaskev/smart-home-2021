package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.equipment.Room;
import ru.sbt.mipt.oop.events.Event;

import java.io.IOException;

public class DoorEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final Logger logger;

    public DoorEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == SensorEventType.DOOR_OPEN) {
            for (Room room : smartHome.getRooms()) {
                for (Door door : room.getDoors()) {
                    if (door.getId().equals(event.getObjectId())) {
                        door.setOpen(true);
                        try {
                            logger.log("Door " + door.getId() + " in room " + room.getName() + " was opened.");
                        } catch (IOException e) {
                            System.err.println(e.toString());
                        }
                    }
                }
            }
        } else if (event.getEventType() == SensorEventType.DOOR_CLOSED) {
            for (Room room : smartHome.getRooms()) {
                for (Door door : room.getDoors()) {
                    if (door.getId().equals(event.getObjectId())) {
                        door.setOpen(false);
                        try {
                            logger.log("Door " + door.getId() + " in room " + room.getName() + " was closed.");
                        } catch (IOException e) {
                            System.err.println(e.toString());
                        }
                    }
                }
            }
        }
    }
}
