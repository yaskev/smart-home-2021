package ru.sbt.mipt.oop.events;

import java.io.IOException;

public interface Event {
    void handle() throws IOException;
}
