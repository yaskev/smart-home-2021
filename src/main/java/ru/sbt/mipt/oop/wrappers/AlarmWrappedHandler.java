package ru.sbt.mipt.oop.wrappers;

import ru.sbt.mipt.oop.EventType;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.handlers.EventHandler;
import ru.sbt.mipt.oop.notifiers.Notifier;
import ru.sbt.mipt.oop.signalling.Alarm;

import java.util.Collection;

public class AlarmWrappedHandler implements EventHandler {
    private final Alarm alarm;
    private final Collection<EventHandler> handlers;
    private final Notifier notifier;

    public AlarmWrappedHandler(Alarm alarm, Collection<EventHandler> handlers, Notifier notifier) {
        this.alarm = alarm;
        this.handlers = handlers;
        this.notifier = notifier;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == EventType.ALARM_ACTIVATE) {
            alarm.activate(((AlarmEvent)event).getAlarmCode());
            return;
        } else if (event.getEventType() == EventType.ALARM_DEACTIVATE) {
            alarm.deactivate(((AlarmEvent)event).getAlarmCode());
        }
        if (alarm.isEnabled() && event instanceof SensorEvent) {
            alarm.turnAlarmOn();
            for (EventHandler handler : handlers) {
                handler.handleEvent(event);
            }
        }
        if (alarm.isRinging()) {
            notifier.notifyClient("Sending sms");
        } else if (!alarm.isEnabled() && event instanceof SensorEvent){
            for (EventHandler handler : handlers) {
                handler.handleEvent(event);
            }
        }
    }
}
