package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.events.Event;

import java.io.IOException;

public interface EventHandler {
    void handleEvent(Event event) throws IOException;
}
