package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.events.Event;

import java.util.Collection;
import java.util.Iterator;

public class FixedEventGenerator implements EventGenerator {
    private final Iterator<Event> iter;
    private final Collection<Event> events;

    public FixedEventGenerator(Collection<Event> events) {
        this.events = events;
        this.iter = this.events.iterator();
    }

    @Override
    public Event generate() {
        if (iter.hasNext()) {
            return iter.next();
        }
        return null;
    }
}
