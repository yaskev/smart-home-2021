package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.EventType;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.events.Event;
import ru.sbt.mipt.oop.events.SensorEvent;

public class LightEventHandler implements EventHandler {
    private final SmartHome smartHome;

    public LightEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == EventType.LIGHT_OFF || event.getEventType() == EventType.LIGHT_ON) {
            boolean isLightOn = event.getEventType() == EventType.LIGHT_ON;
            String objId = ((SensorEvent)event).getObjectId();

            Action updateLightState = (object) -> {
                if (object instanceof Light && ((Light) object).getId().equals(objId)) {
                    ((Light)object).setOn(isLightOn);
                }
            };
            smartHome.execute(updateLightState);
        }
    }
}
