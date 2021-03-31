package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.*;

public class MakeCoolCompanyEventHandler implements EventHandler {
    private final ru.sbt.mipt.oop.handlers.EventHandler baseHandler;

    public MakeCoolCompanyEventHandler(ru.sbt.mipt.oop.handlers.EventHandler baseHandler) {
        this.baseHandler = baseHandler;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        Event smartHomeEvent = getSmartHomeEvent(event);
        if (smartHomeEvent != null) {
            baseHandler.handleEvent(getSmartHomeEvent(event));
        }
    }

    private Event getSmartHomeEvent(CCSensorEvent event) {
        switch (event.getEventType()) {
            case "LightIsOn":
                return new LightOnEvent(event.getObjectId());
            case "LightIsOff":
                return new LightOffEvent(event.getObjectId());
            case "DoorIsOpen":
                return new DoorOpenedEvent(event.getObjectId());
            case "DoorIsClosed":
                return new DoorClosedEvent(event.getObjectId());
            default:
                return null;
        }
    }
}
