package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.SensorEventType;
import ru.sbt.mipt.oop.SmartHome;
import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.events.Event;

public class LightEventHandler implements EventHandler {
    private final SmartHome smartHome;

    public LightEventHandler(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == SensorEventType.LIGHT_OFF || event.getEventType() == SensorEventType.LIGHT_ON) {
            boolean isLightOn = event.getEventType() == SensorEventType.LIGHT_ON;
            String objId = event.getObjectId();

            Action updateLightState = (object) -> {
                if (object instanceof Light && ((Light) object).getId().equals(objId)) {
                    ((Light)object).setOn(isLightOn);
                }
            };
            smartHome.execute(updateLightState);
        }
    }
}
