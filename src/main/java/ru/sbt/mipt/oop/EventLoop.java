package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.handlers.EventHandler;

public class EventLoop {
    private final EventGenerator generator;
    private final EventHandler handler;

    public EventLoop(EventGenerator generator, EventHandler handler) {
        this.handler = handler;
        this.generator = generator;
    }

    public void runLoop() {
        Event event = generator.generate();

        while (event != null) {
            handler.handleEvent(event);
            event = generator.generate();
        }
    }
}
