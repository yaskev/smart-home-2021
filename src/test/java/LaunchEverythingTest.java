import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.equipment.Door;

import static org.junit.jupiter.api.Assertions.*;


public class LaunchEverythingTest {
    @Test
    void testAlarmEnables() {
        Door door1 = new Door(true, "1");
        Door door2 = new Door(false, "2");

        door1.setOpen(false);
        assertFalse(door1.isOpen());
        assertFalse(door2.isOpen());
    }
}