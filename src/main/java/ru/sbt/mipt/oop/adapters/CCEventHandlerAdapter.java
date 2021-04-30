package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.factory.EventFactory;

import java.util.Map;

public class CCEventHandlerAdapter implements EventHandler {
    private final ru.sbt.mipt.oop.handlers.EventHandler baseHandler;
    private final Map<String, EventFactory> ccEventToFactoryMap;

    public CCEventHandlerAdapter(ru.sbt.mipt.oop.handlers.EventHandler baseHandler, Map<String, EventFactory> eventFactories) {
        this.baseHandler = baseHandler;
        this.ccEventToFactoryMap = eventFactories;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        Event smartHomeEvent = getSmartHomeEvent(event);
        if (smartHomeEvent != null) {
            baseHandler.handleEvent(getSmartHomeEvent(event));
        }
    }

    private Event getSmartHomeEvent(CCSensorEvent event) {
        if (ccEventToFactoryMap.containsKey(event.getEventType())) {
           return ccEventToFactoryMap.get(event.getEventType()).getEvent(event.getObjectId());
        }
        return null;
    }
}
