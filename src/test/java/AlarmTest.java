import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.equipment.Room;
import ru.sbt.mipt.oop.events.AlarmActivatedEvent;
import ru.sbt.mipt.oop.events.AlarmDeactivatedEvent;
import ru.sbt.mipt.oop.events.LightOffEvent;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.signalling.Alarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class AlarmTest {
    private final SmartHome smartHome = new SmartHome();

    private final Door doorWithId1 = new Door(false, "1");
    private final Door doorWithId2 = new Door(true, "2");
    private final Door hallDoor = new Door(true, "3");

    private final Light lightWithId1 = new Light("1", true);
    private final Light lightWithId2 = new Light("2", false);

    private final CommandSender sender = new ConsoleCommandSender();
    Collection<EventHandler> eventHandlers = new ArrayList<>();

    private final Alarm alarm = new Alarm();

    void addHandlers() {
        eventHandlers.add(new DoorEventHandler(smartHome));
        eventHandlers.add(new HallDoorEventHandler(smartHome, sender));
        eventHandlers.add(new LightEventHandler(smartHome));
        eventHandlers.add(new AlarmEventHandler(smartHome));
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
        smartHome.setAlarm(alarm);
    }

    @Test
    void testAlarmEnables() {
        assertEquals("AlarmDisabledState", alarm.getState());
        EventGenerator generator = new FixedEventGenerator(Collections.singletonList(new AlarmActivatedEvent(1)));
        addHandlers();
        fillSmartHome();

        smartHome.setEventLoop(new EventLoop(generator, eventHandlers));
        smartHome.runLoop();

        assertEquals("AlarmEnabledState", alarm.getState());
    }

    @Test
    void testAlarmDisables() {
        assertEquals("AlarmDisabledState", alarm.getState());
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new AlarmActivatedEvent(1),
                                                                        new AlarmDeactivatedEvent(1)));
        addHandlers();
        fillSmartHome();

        smartHome.setEventLoop(new EventLoop(generator, eventHandlers));
        smartHome.runLoop();

        assertEquals("AlarmDisabledState", alarm.getState());
    }

    @Test
    void testAlarmRingsWhenWrongCode() {
        assertEquals("AlarmDisabledState", alarm.getState());
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new AlarmActivatedEvent(1),
                new AlarmDeactivatedEvent(2)));
        addHandlers();
        fillSmartHome();

        smartHome.setEventLoop(new EventLoop(generator, eventHandlers));
        smartHome.runLoop();

        assertEquals("AlarmRingingState", alarm.getState());
    }

    @Test
    void testSensorEventsIgnoredWhenAlarmRinging() {
        assertTrue(lightWithId1.isOn());
        assertEquals("AlarmDisabledState", alarm.getState());

        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new AlarmActivatedEvent(1),
                new AlarmDeactivatedEvent(2), new LightOffEvent("1")));
        addHandlers();
        fillSmartHome();

        smartHome.setEventLoop(new EventLoop(generator, eventHandlers));
        smartHome.runLoop();

        assertEquals("AlarmRingingState", alarm.getState());
        assertTrue(lightWithId1.isOn());
    }

    @Test
    void testAlarmRingsWhenSensorEventHappens() {
        assertEquals("AlarmDisabledState", alarm.getState());
        assertTrue(lightWithId1.isOn());
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new AlarmActivatedEvent(1),
                new LightOffEvent("1")));
        addHandlers();
        fillSmartHome();

        smartHome.setEventLoop(new EventLoop(generator, eventHandlers));
        smartHome.runLoop();

        assertEquals("AlarmRingingState", alarm.getState());
        assertFalse(lightWithId1.isOn());
    }
}
