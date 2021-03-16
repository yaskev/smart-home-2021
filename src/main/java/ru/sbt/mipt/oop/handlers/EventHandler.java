package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.events.Event;

public interface EventHandler {
    void handleEvent(Event event);
}
