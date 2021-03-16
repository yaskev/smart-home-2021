package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.EventHandler;
import ru.sbt.mipt.oop.handlers.HallDoorEventHandler;
import ru.sbt.mipt.oop.handlers.LightEventHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


public class Application {

    public static void main(String... args) throws IOException {
        SmartHome smartHome = new JsonHomeReader("smart-home-1.js").read();
        Logger logger = new ConsoleLogger();
        CommandSender sender = new ConsoleCommandSender();
        EventGenerator generator = new RandomEventGenerator();

        Collection<EventHandler> eventHandlers = new ArrayList<>();
        eventHandlers.add(new DoorEventHandler(smartHome, logger));
        eventHandlers.add(new HallDoorEventHandler(smartHome, sender));
        eventHandlers.add(new LightEventHandler(smartHome, logger));

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();
    }
}
