package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.handlers.EventHandler;

import java.util.Collection;

public class EventLoop {
    private final EventGenerator generator;
    private final Collection<EventHandler> handlers;

    public EventLoop(EventGenerator generator, Collection<EventHandler> handlers) {
        this.handlers = handlers;
        this.generator = generator;
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