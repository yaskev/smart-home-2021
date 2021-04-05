package ru.sbt.mipt.oop.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import com.coolcompany.smarthome.events.EventHandler;
import ru.sbt.mipt.oop.events.Event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class CCEventHandlerAdapter implements EventHandler {
    private final ru.sbt.mipt.oop.handlers.EventHandler baseHandler;
    private final HashMap<String, String> getEventClass;

    public CCEventHandlerAdapter(ru.sbt.mipt.oop.handlers.EventHandler baseHandler, HashMap<String, String> eventClasses) {
        this.baseHandler = baseHandler;
        this.getEventClass = eventClasses;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        Event smartHomeEvent = getSmartHomeEvent(event);
        if (smartHomeEvent != null) {
            baseHandler.handleEvent(getSmartHomeEvent(event));
        }
    }

    private Event getSmartHomeEvent(CCSensorEvent event) {
        if (getEventClass.containsKey(event.getEventType())) {
            Class<?> eventClass = null;
            try {
                eventClass = Class.forName(getEventClass.get(event.getEventType()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (eventClass != null) {
                try {
                    Constructor<?> constr = eventClass.getConstructor(String.class);
                    return (Event) constr.newInstance(event.getObjectId());
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
