package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.Logger;
import ru.sbt.mipt.oop.SensorEventType;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.equipment.Room;
import ru.sbt.mipt.oop.events.Event;

import java.io.IOException;

public class LightEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final Logger logger;

    public LightEventHandler(SmartHome smartHome, Logger logger) {
        this.smartHome = smartHome;
        this.logger = logger;
    }

    @Override
    public void handleEvent(Event event) throws IOException {
        if (event.getEventType() == SensorEventType.LIGHT_ON) {
            for (Room room : smartHome.getRooms()) {
                for (Light light : room.getLights()) {
                    if (light.getId().equals(event.getObjectId())) {
                        light.setOn(true);
                        logger.log("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
                    }
                }
            }
        } else if (event.getEventType() == SensorEventType.LIGHT_OFF) {
            for (Room room : smartHome.getRooms()) {
                for (Light light : room.getLights()) {
                    if (light.getId().equals(event.getObjectId())) {
                        light.setOn(false);
                        logger.log("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
                    }
                }
            }
        }
    }
}
