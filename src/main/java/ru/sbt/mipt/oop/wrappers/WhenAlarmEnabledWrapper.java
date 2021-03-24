package ru.sbt.mipt.oop.wrappers;

import ru.sbt.mipt.oop.EventType;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.handlers.EventHandler;
import ru.sbt.mipt.oop.signalling.Alarm;

public class WhenAlarmEnabledWrapper implements EventHandler {
    private final EventHandler baseHandler;
    private final SmartHome smartHome;

    public WhenAlarmEnabledWrapper(SmartHome smartHome, EventHandler baseHandler) {
        this.baseHandler = baseHandler;
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(Event event) {
        if (event instanceof SensorEvent) {
            Action ringAlarm = (object) -> {
                if (object instanceof Alarm) {
                    ((Alarm)object).turnAlarmOn();
                }
            };
            smartHome.execute(ringAlarm);

            smartHome.wrapHandlers(Wrapper.ALARM_RINGING);
            System.out.println("Sending sms");
        }

        baseHandler.handleEvent(event);
    }
}
