package ru.sbt.mipt.oop.wrappers;

import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.handlers.EventHandler;

public class WhenAlarmRingingWrapper implements EventHandler {
    @Override
    public void handleEvent(Event event) {
        System.out.println("Sending sms");
    }
}
