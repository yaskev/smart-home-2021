package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.Logger;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.equipment.*;

import java.io.IOException;

public class LightOffEvent implements Event {
    private final SmartHome smartHome;
    private final String objectId;
    private final Logger logger;

    public LightOffEvent(SmartHome smartHome, Logger logger, String objectId) {
        this.logger = logger;
        this.smartHome = smartHome;
        this.objectId = objectId;
    }

    public void handle() throws IOException {
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                if (light.getId().equals(this.objectId)) {
                    light.setOn(false);
                    logger.log("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
                }
            }
        }
    }
}
