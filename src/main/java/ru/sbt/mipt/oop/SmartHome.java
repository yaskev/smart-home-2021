package ru.sbt.mipt.oop;

import java.util.ArrayList;
import java.util.Collection;

import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.action.Actionable;
import ru.sbt.mipt.oop.equipment.*;
import ru.sbt.mipt.oop.signalling.Alarm;

public class SmartHome implements Actionable {
    Collection<Room> rooms;
    Alarm alarm;

    public SmartHome() {
        rooms = new ArrayList<>();
        alarm = null;
    }

    public SmartHome(Alarm alarm) {
        rooms = new ArrayList<>();
        this.alarm = alarm;
    }

    public SmartHome(Collection<Room> rooms, Alarm alarm) {
        this.rooms = rooms;
        this.alarm = alarm;
    }

    public void addRoom(Room room) {
        rooms.add(room);
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