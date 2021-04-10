import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.commands.*;
import ru.sbt.mipt.oop.equipment.Door;
import ru.sbt.mipt.oop.equipment.Light;
import ru.sbt.mipt.oop.equipment.Room;
import ru.sbt.mipt.oop.remote.RealRemoteControl;
import ru.sbt.mipt.oop.signalling.Alarm;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RemoteControlTest {
    private final Alarm alarm = new Alarm();
    private final SmartHome smartHome = new SmartHome(alarm);

    private final Door doorWithId1 = new Door(false, "1");
    private final Door doorWithId2 = new Door(true, "2");
    private final Door hallDoor = new Door(true, "3");

    private final Light lightWithId1 = new Light("1", true);
    private final Light lightWithId2 = new Light("2", false);

    private final RealRemoteControl rc1 = new RealRemoteControl();
    private final RealRemoteControl rc2 = new RealRemoteControl();

    private final Command cmd1 = new TurnOffAllLightCommand(smartHome);
    private final Command cmd2 = new CloseHallDoorCommand(smartHome);
    private final Command cmd3 = new EnableAlarmCommand(smartHome);
    private final Command cmd4 = new MakeAlarmRingingCommand(smartHome);
    private final Command cmd5 = new TurnOnAllLightCommand(smartHome);
    private final Command cmd6 = new TurnOnHallLightCommand(smartHome);

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
    void testLightTurningOff() {
        assertTrue(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());
        fillSmartHome();

        rc1.assignCommandToButton("A", cmd1);
        rc1.onButtonPressed("A", "");

        assertFalse(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());
    }

    @Test
    void testLightTurningOn() {
        assertTrue(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());
        fillSmartHome();

        rc1.assignCommandToButton("B", cmd5);
        rc1.onButtonPressed("B", "");

        assertTrue(lightWithId1.isOn());
        assertTrue(lightWithId2.isOn());
    }

    @Test
    void testLightTurningOnInHallOnly() {
        lightWithId1.setOn(false);
        lightWithId2.setOn(false);

        fillSmartHome();
        rc1.assignCommandToButton("A", cmd6);
        rc1.onButtonPressed("A", "");

        assertFalse(lightWithId1.isOn());
        assertTrue(lightWithId2.isOn());
    }

    @Test
    void testHallDoorClosed() {
        doorWithId1.setOpen(true);
        doorWithId2.setOpen(true);
        hallDoor.setOpen(true);

        fillSmartHome();
        rc1.assignCommandToButton("A", cmd2);
        rc1.onButtonPressed("A", "");

        assertFalse(hallDoor.isOpen());
        assertTrue(doorWithId1.isOpen());
        assertTrue(doorWithId2.isOpen());
    }

    @Test
    void testAlarmEnabled() {
        assertFalse(alarm.isEnabled());
        assertFalse(alarm.isRinging());

        rc1.assignCommandToButton("A", cmd3);
        rc1.onButtonPressed("A", "");

        assertTrue(alarm.isEnabled());
        assertFalse(alarm.isRinging());
    }

    @Test
    void testAlarmRinging() {
        assertFalse(alarm.isEnabled());
        assertFalse(alarm.isRinging());

        rc1.assignCommandToButton("A", cmd4);
        rc1.onButtonPressed("A", "");

        assertFalse(alarm.isEnabled());
        assertTrue(alarm.isRinging());
    }

    @Test
    void testMultipleRCWork() {
        lightWithId1.setOn(false);
        lightWithId2.setOn(false);

        fillSmartHome();
        rc1.assignCommandToButton("A", cmd5);
        rc2.assignCommandToButton("A", cmd1);
        rc1.onButtonPressed("A", "");

        assertTrue(lightWithId1.isOn());
        assertTrue(lightWithId2.isOn());

        rc2.onButtonPressed("A", "");

        assertFalse(lightWithId1.isOn());
        assertFalse(lightWithId2.isOn());
    }
}
