package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.*;

public class RandomEventGenerator implements EventGenerator {
    @Override
    public Event generate() {
        if (Math.random() < 0.05) {
            return null; // null means end of event stream
        }
        String objectId = "" + ((int) (10 * Math.random()));
        final int eventNumber = (int) (4 * Math.random());

        if (eventNumber < 1) {
            return new LightOnEvent(objectId);
        }
        else if (eventNumber < 2) {
            return new LightOffEvent(objectId);
        }
        else if (eventNumber < 3) {
            return new DoorOpenedEvent(objectId);
        }
        else {
            return new DoorClosedEvent(objectId);
        }
    }
}
