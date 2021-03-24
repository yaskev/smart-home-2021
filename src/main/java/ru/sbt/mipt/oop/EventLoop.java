package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.EventHandler;
import ru.sbt.mipt.oop.handlers.HallDoorEventHandler;
import ru.sbt.mipt.oop.handlers.LightEventHandler;
import ru.sbt.mipt.oop.wrappers.WhenAlarmEnabledWrapper;
import ru.sbt.mipt.oop.wrappers.WhenAlarmRingingWrapper;
import ru.sbt.mipt.oop.wrappers.Wrapper;

import java.util.ArrayList;
import java.util.Collection;

public class EventLoop {
    private final EventGenerator generator;
    private final Collection<EventHandler> basicHandlers;
    private Collection<EventHandler> handlers;

    public EventLoop(EventGenerator generator, Collection<EventHandler> handlers) {
        this.basicHandlers = handlers;
        this.handlers = handlers;
        this.generator = generator;
    }

    public void wrapHandlers(Wrapper wrapper, SmartHome smartHome) {
        if (wrapper == Wrapper.NONE) {
            handlers = basicHandlers;
        } else {
            ArrayList<EventHandler> newHandlers = new ArrayList<>();
            for (EventHandler handler : basicHandlers) {
                if (handler instanceof DoorEventHandler || handler instanceof HallDoorEventHandler
                        || handler instanceof LightEventHandler) {
                    if (wrapper == Wrapper.ALARM_ENABLED) {
                        newHandlers.add(new WhenAlarmEnabledWrapper(smartHome, handler));
                    } else if (wrapper == Wrapper.ALARM_RINGING) {
                        newHandlers.add(new WhenAlarmRingingWrapper());
                    }
                } else {
                    newHandlers.add(handler);
                }
            }
            handlers = newHandlers;
        }
    }

    public void runLoop() {
        Event event = generator.generate();

        while (event != null) {
            for (EventHandler handler : handlers) {
                handler.handleEvent(event);
            }
            event = generator.generate();
        }
    }
}
