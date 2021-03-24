package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.EventType;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.events.AlarmEvent;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.signalling.Alarm;
import ru.sbt.mipt.oop.wrappers.Wrapper;

public class AlarmEventHandler implements EventHandler {
    private final SmartHome smartHome;

    public AlarmEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == EventType.ALARM_ACTIVATE || event.getEventType() == EventType.ALARM_DEACTIVATE) {
            Action alarmOnOff = (object) -> {
                if (object instanceof Alarm) {
                    if (event.getEventType() == EventType.ALARM_ACTIVATE) {
                        ((Alarm)object).activate(((AlarmEvent)event).getAlarmCode());
                        smartHome.wrapHandlers(Wrapper.ALARM_ENABLED);
                    } else {
                        boolean ring = ((Alarm)object).deactivate(((AlarmEvent)event).getAlarmCode());
                        if (ring) {
                            smartHome.wrapHandlers((Wrapper.ALARM_RINGING));
                        } else {
                            smartHome.wrapHandlers((Wrapper.NONE));
                        }
                    }
                }
            };
            smartHome.execute(alarmOnOff);
        }
    }
}
