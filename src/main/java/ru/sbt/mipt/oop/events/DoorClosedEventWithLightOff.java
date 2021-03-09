package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.*;

import ru.sbt.mipt.oop.equipment.*;

import java.io.IOException;

public class DoorClosedEventWithLightOff implements Event {
    private final SmartHome smartHome;
    private final String objectId;
    private final Logger logger;
    private final CommandSender sender;

    public DoorClosedEventWithLightOff(SmartHome smartHome, Logger logger, String objectId, CommandSender sender) {
        this.logger = logger;
        this.smartHome = smartHome;
        this.objectId = objectId;
        this.sender = sender;
    }

    public void handle() throws IOException {
        for (Room room : smartHome.getRooms()) {
            for (Door door : room.getDoors()) {
                if (door.getId().equals(this.objectId)) {
                    door.setOpen(false);
                    logger.log("Door " + door.getId() + " in room " + room.getName() + " was closed.");
                    // если мы получили событие о закрытие двери в холле - это значит, что была закрыта входная дверь.
                    // в этом случае мы хотим автоматически выключить свет во всем доме (это же умный дом!)

                    // P.S. Я думал, как вынести это в интерфейс "SpecialAction", но не придумал способа передавать
                    // туда room так, чтобы указывать его только в реализации, а не в интерфейсе
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
