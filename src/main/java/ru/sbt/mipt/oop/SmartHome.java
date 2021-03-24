package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;

import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.action.Actionable;
import ru.sbt.mipt.oop.equipment.*;
import ru.sbt.mipt.oop.signalling.Alarm;
import ru.sbt.mipt.oop.wrappers.Wrapper;

public class SmartHome implements Actionable {
    Collection<Room> rooms;
    private Alarm alarm = null;
    private EventLoop eventLoop = null;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public void setEventLoop(EventLoop loop) {
        eventLoop = loop;
    }

    public void runLoop() {
        if (eventLoop != null) {
            eventLoop.runLoop();
        }
    }

    public void wrapHandlers(Wrapper wrapper) {
        eventLoop.wrapHandlers(wrapper, this);
    }

    @Override
    public void execute(Action action) {
        action.run(this);

        for (Room room : rooms) {
            room.execute(action);
        }

        if (alarm != null) {
            alarm.execute(action);
        }
    }
}