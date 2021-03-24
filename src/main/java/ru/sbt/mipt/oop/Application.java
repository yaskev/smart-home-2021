package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.EventHandler;
import ru.sbt.mipt.oop.handlers.HallDoorEventHandler;
import ru.sbt.mipt.oop.handlers.LightEventHandler;

import java.util.ArrayList;
import java.util.Collection;


public class Application {

    public static void main(String... args) {
        SmartHome smartHome = new JsonHomeReader("smart-home-1.js").read();
        CommandSender sender = new ConsoleCommandSender();
        EventGenerator generator = new RandomEventGenerator();

        Collection<EventHandler> eventHandlers = new ArrayList<>();
        eventHandlers.add(new DoorEventHandler(smartHome));
        eventHandlers.add(new HallDoorEventHandler(smartHome, sender));
        eventHandlers.add(new LightEventHandler(smartHome));

        smartHome.setEventLoop(new EventLoop(generator, eventHandlers));
        smartHome.runLoop();
    }
}
