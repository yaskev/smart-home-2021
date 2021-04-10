import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.equipment.Room;
import ru.sbt.mipt.oop.events.AlarmActivatedEvent;
import ru.sbt.mipt.oop.events.AlarmDeactivatedEvent;
import ru.sbt.mipt.oop.events.LightOffEvent;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.notifiers.*;
import ru.sbt.mipt.oop.signalling.Alarm;
import ru.sbt.mipt.oop.wrappers.AlarmWrappedHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;


public class AlarmTest {
    private final Alarm alarm = new Alarm();
    private final SmartHome smartHome = new SmartHome(alarm);

    private final Door doorWithId1 = new Door(false, "1");
    private final Door doorWithId2 = new Door(true, "2");
    private final Door hallDoor = new Door(true, "3");

    private final Light lightWithId1 = new Light("1", true);
    private final Light lightWithId2 = new Light("2", false);

    private final CommandSender sender = new ConsoleCommandSender();
    Collection<EventHandler> eventHandlers = new ArrayList<>();

    private final Notifier notifier = new SMSNotifier();

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
    void testAlarmEnables() {
        assertFalse(alarm.isEnabled());
        EventGenerator generator = new FixedEventGenerator(Collections.singletonList(new AlarmActivatedEvent(1)));
        addHandlers();
        fillSmartHome();
        EventHandler handlerWrapper = new AlarmWrappedHandler(alarm, eventHandlers, notifier);

        EventLoop eventLoop = new EventLoop(generator, handlerWrapper);
        eventLoop.runLoop();

        assertTrue(alarm.isEnabled());
        assertFalse(alarm.isRinging());
    }

    @Test
    void testAlarmDisables() {
        assertFalse(alarm.isEnabled());
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new AlarmActivatedEvent(1),
                                                                        new AlarmDeactivatedEvent(1)));
        addHandlers();
        fillSmartHome();
        EventHandler handlerWrapper = new AlarmWrappedHandler(alarm, eventHandlers, notifier);
        EventLoop eventLoop = new EventLoop(generator, handlerWrapper);
        eventLoop.runLoop();

        assertFalse(alarm.isEnabled());
    }

    @Test
    void testAlarmRingsWhenWrongCode() {
        assertFalse(alarm.isEnabled());
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new AlarmActivatedEvent(1),
                new AlarmDeactivatedEvent(2)));
        addHandlers();
        fillSmartHome();
        EventHandler handlerWrapper = new AlarmWrappedHandler(alarm, eventHandlers, notifier);
        EventLoop eventLoop = new EventLoop(generator, handlerWrapper);
        eventLoop.runLoop();

        assertTrue(alarm.isRinging());
    }

    @Test
    void testSensorEventsIgnoredWhenAlarmRinging() {
        assertTrue(lightWithId1.isOn());
        assertFalse(alarm.isEnabled());

        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new AlarmActivatedEvent(1),
                new AlarmDeactivatedEvent(2), new LightOffEvent("1")));
        addHandlers();
        fillSmartHome();
        EventHandler handlerWrapper = new AlarmWrappedHandler(alarm, eventHandlers, notifier);
        EventLoop eventLoop = new EventLoop(generator, handlerWrapper);
        eventLoop.runLoop();

        assertTrue(alarm.isRinging());
        assertTrue(lightWithId1.isOn());
    }

    @Test
    void testAlarmRingsWhenSensorEventHappens() {
        assertFalse(alarm.isEnabled());
        assertTrue(lightWithId1.isOn());
        EventGenerator generator = new FixedEventGenerator(Arrays.asList(new AlarmActivatedEvent(1),
                new LightOffEvent("1")));
        addHandlers();
        fillSmartHome();
        EventHandler handlerWrapper = new AlarmWrappedHandler(alarm, eventHandlers, notifier);
        EventLoop eventLoop = new EventLoop(generator, handlerWrapper);
        eventLoop.runLoop();

        assertTrue(alarm.isRinging());
        assertFalse(lightWithId1.isOn());
    }
}
