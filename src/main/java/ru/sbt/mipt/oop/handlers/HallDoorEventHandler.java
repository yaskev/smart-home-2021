package ru.sbt.mipt.oop.handlers;

import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.equipment.Room;
import ru.sbt.mipt.oop.events.Event;

public class HallDoorEventHandler implements EventHandler {
    private final SmartHome smartHome;
    private final CommandSender sender;

    public HallDoorEventHandler(SmartHome smartHome, CommandSender sender) {
        this.smartHome = smartHome;
        this.sender = sender;
    }

    @Override
    public void handleEvent(Event event) {
        if (event.getEventType() == SensorEventType.DOOR_CLOSED) {
            for (Room room : smartHome.getRooms()) {
                for (Door door : room.getDoors()) {
                    if (door.getId().equals(event.getObjectId())) {
                        // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
                        // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)
                        if (room.getName().equals("hall")) {
                            for (Room homeRoom : smartHome.getRooms()) {
                                for (Light light : homeRoom.getLights()) {
                                    light.setOn(false);
                                    SensorCommand command = new LightOffCommand(light.getId());
                                    sender.sendCommand(command);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
