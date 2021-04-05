package ru.sbt.mipt.oop.wrappers;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.handlers.EventHandler;

import java.util.Collection;

public class NotWrappedHandler implements EventHandler {
    private final Collection<EventHandler> handlers;

    public NotWrappedHandler(Collection<EventHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handleEvent(Event event) {
        for (EventHandler handler : handlers) {
            handler.handleEvent(event);
        }
    }
}
