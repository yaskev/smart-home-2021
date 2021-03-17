import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.equipment.Room;
import ru.sbt.mipt.oop.events.DoorClosedEvent;
import ru.sbt.mipt.oop.events.DoorOpenedEvent;
import ru.sbt.mipt.oop.events.LightOffEvent;
import ru.sbt.mipt.oop.events.LightOnEvent;
import ru.sbt.mipt.oop.handlers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class EventHandlingTest {
    private final SmartHome smartHome = new JsonHomeReader("smart-home-1.js").read();
    private final CommandSender sender = new ConsoleCommandSender();
    Collection<EventHandler> eventHandlers = new ArrayList<>();

    void addHandlers() {
        eventHandlers.add(new DoorEventHandler(smartHome));
        eventHandlers.add(new HallDoorEventHandler(smartHome, sender));
        eventHandlers.add(new LightEventHandler(smartHome));
    }

    @Test
    void testLightOn() {
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new LightOnEvent("1"),
                                                                        new LightOnEvent("3")));
        addHandlers();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();
        for (Room room : smartHome.getRooms()) {
            Light light = room.getLightWithId("1");
            if (light != null) {
                assertTrue(light.isOn());
            }
            light = room.getLightWithId("3");
            if (light != null) {
                assertTrue(light.isOn());
            }
        }
    }

    @Test
    void testLightOff() {
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new LightOffEvent("4"),
                                                                        new LightOffEvent("2")));
        addHandlers();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();
        for (Room room : smartHome.getRooms()) {
            Light light = room.getLightWithId("2");
            if (light != null) {
                assertFalse(light.isOn());
            }
            light = room.getLightWithId("4");
            if (light != null) {
                assertFalse(light.isOn());
            }
        }
    }

    @Test
    void testDoorOpened() {
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new DoorOpenedEvent("1"),
                                                                        new DoorOpenedEvent("3")));
        addHandlers();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();
        for (Room room : smartHome.getRooms()) {
            Door door = room.getDoorWithId("1");
            if (door != null) {
                assertTrue(door.isOpen());
            }
            door = room.getDoorWithId("3");
            if (door != null) {
                assertTrue(door.isOpen());
            }
        }
    }

    @Test
    void testDoorClosed() {
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new DoorClosedEvent("1"),
                                                                        new DoorClosedEvent("3")));
        addHandlers();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();
        for (Room room : smartHome.getRooms()) {
            Door door = room.getDoorWithId("1");
            if (door != null) {
                assertFalse(door.isOpen());
            }
            door = room.getDoorWithId("3");
            if (door != null) {
                assertFalse(door.isOpen());
            }
        }
    }

    @Test
    void testHallDoorClosed() {
        EventGenerator generator = new FixedEventGenerator(Collections.singletonList(new DoorClosedEvent("4")));
        addHandlers();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();
        for (Room room : smartHome.getRooms()) {
            Door door = room.getDoorWithId("4");
            if (door != null) {
                assertFalse(door.isOpen());
            }
            for (Light light : room.getLights()) {
                assertFalse(light.isOn());
            }
        }
    }

    @Test
    void testNotHallDoorClosed() {
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new LightOnEvent("1"),
                                                                        new DoorClosedEvent("3")));
        addHandlers();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();
        for (Room room : smartHome.getRooms()) {
            Door door = room.getDoorWithId("3");
            if (door != null) {
                assertFalse(door.isOpen());
            }
            Light light = room.getLightWithId("1");
            if (light != null) {
                assertTrue(light.isOn());
            }
        }
    }
}
