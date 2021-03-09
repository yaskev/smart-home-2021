package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.*;

public class RandomEventGenerator implements EventGenerator {
    private final SmartHome smartHome;
    private final Logger logger;
    private final CommandSender sender;

    public RandomEventGenerator(SmartHome smartHome, Logger logger, CommandSender sender) {
        this.logger = logger;
        this.smartHome = smartHome;
        this.sender = sender;
    }

    @Override
    public Event generate() {
        if (Math.random() < 0.05) {
            return null; // null means end of event stream
        }
        String objectId = "" + ((int) (10 * Math.random()));
        final int eventNumber = (int) (4 * Math.random());

        if (eventNumber < 1) {
            return new LightOnEvent(smartHome, logger, objectId);
        }
        else if (eventNumber < 2) {
            return new LightOffEvent(smartHome, logger, objectId);
        }
        else if (eventNumber < 3) {
            return new DoorOpenedEvent(smartHome, logger, objectId);
        }
        else {
            return new DoorClosedEventWithLightOff(smartHome, logger, objectId, sender);
        }
    }
}
