package ru.sbt.mipt.oop;

import java.io.IOException;


public class Application {

    public static void main(String... args) throws IOException {
        SmartHome smartHome = new JsonHomeReader("smart-home-1.js").read();
        Logger logger = new ConsoleLogger();
        CommandSender sender = new ConsoleCommandSender();
        EventGenerator generator = new RandomEventGenerator(smartHome, logger, sender);
        EventLoop loop = new EventLoop(generator);
        loop.runLoop();
    }
}
