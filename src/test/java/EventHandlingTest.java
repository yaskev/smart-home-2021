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
    private final SmartHome smartHome = new SmartHome();

    private final Door doorWithId1 = new Door(false, "1");
    private final Door doorWithId2 = new Door(true, "2");
    private final Door hallDoor = new Door(true, "3");

    private final Light lightWithId1 = new Light("1", true);
    private final Light lightWithId2 = new Light("2", false);

    private final CommandSender sender = new ConsoleCommandSender();
    Collection<EventHandler> eventHandlers = new ArrayList<>();

    void addHandlers() {
        eventHandlers.add(new DoorEventHandler(smartHome));
        eventHandlers.add(new HallDoorEventHandler(smartHome, sender));
        eventHandlers.add(new LightEventHandler(smartHome));
    }

    void fillSmartHome() {
        Room justRoom = new Room(Collections.singletonList(lightWithId1),
                                Arrays.asList(doorWithId1, doorWithId2),
                                "kitchen");
        Room hall = new Room (Collections.singletonList(lightWithId2),
                                Collections.singletonList(hallDoor),
                                "hall");
        smartHome.addRoom(justRoom);
        smartHome.addRoom(hall);
    }

    @Test
    void testLightOn() {
        assertTrue(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());

        EventGenerator generator = new FixedEventGenerator(Collections.singletonList(new LightOnEvent("2")));
        addHandlers();
        fillSmartHome();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();

        assertTrue(lightWithId1.isOn());
        assertTrue(lightWithId2.isOn());
    }

    @Test
    void testLightOff() {
        assertTrue(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());

        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new LightOffEvent("1"),
                                                                        new LightOffEvent("2")));
        addHandlers();
        fillSmartHome();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();

        assertFalse(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());
    }

    @Test
    void testDoorOpened() {
        assertFalse(doorWithId1.isOpen());
        assertTrue(doorWithId2.isOpen());
        assertTrue(hallDoor.isOpen());

        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new DoorOpenedEvent("1"),
                                                                        new DoorOpenedEvent("3")));
        addHandlers();
        fillSmartHome();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();

        assertTrue(doorWithId1.isOpen());
        assertTrue(doorWithId2.isOpen());
        assertTrue(hallDoor.isOpen());
    }

    @Test
    void testDoorClosed() {
        assertFalse(doorWithId1.isOpen());
        assertTrue(doorWithId2.isOpen());
        assertTrue(hallDoor.isOpen());

        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new DoorClosedEvent("1"),
                                                                        new DoorClosedEvent("3")));
        addHandlers();
        fillSmartHome();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();

        assertFalse(doorWithId1.isOpen());
        assertTrue(doorWithId2.isOpen());
        assertFalse(hallDoor.isOpen());
    }

    @Test
    void testHallDoorClosed() {
        assertFalse(doorWithId1.isOpen());
        assertTrue(doorWithId2.isOpen());
        assertTrue(hallDoor.isOpen());

        EventGenerator generator = new FixedEventGenerator(Collections.singletonList(new DoorClosedEvent("3")));
        addHandlers();
        fillSmartHome();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();

        assertFalse(doorWithId1.isOpen());
        assertTrue(doorWithId2.isOpen());
        assertFalse(hallDoor.isOpen());

        assertFalse(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());
    }

    @Test
    void testNotHallDoorClosed() {
        assertFalse(doorWithId1.isOpen());
        assertTrue(doorWithId2.isOpen());
        assertTrue(hallDoor.isOpen());

        EventGenerator generator = new FixedEventGenerator(Collections.singletonList(new DoorClosedEvent("2")));
        addHandlers();
        fillSmartHome();

        EventLoop loop = new EventLoop(generator, eventHandlers);
        loop.runLoop();

        assertFalse(doorWithId1.isOpen());
        assertFalse(doorWithId2.isOpen());
        assertTrue(hallDoor.isOpen());

        assertTrue(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());
    }
}
