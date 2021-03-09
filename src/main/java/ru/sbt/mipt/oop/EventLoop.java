package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.Event;

import java.io.IOException;

public class EventLoop {
    private final EventGenerator generator;

    public EventLoop(EventGenerator generator) {
        this.generator = generator;
    }

    public void runLoop() throws IOException {
        Event event = this.generator.generate();

        while (event != null) {
            event.handle();
            event = this.generator.generate();
        }
    }
}
