package ru.sbt.mipt.oop.equipment;

import ru.sbt.mipt.oop.action.Action;
import ru.sbt.mipt.oop.action.Actionable;

import java.util.Collection;

public class Room implements Actionable {
    private final Collection<Light> lights;
    private final Collection<Door> doors;
    private final String name;

    public Room(Collection<Light> lights, Collection<Door> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public Collection<Light> getLights() {
        return lights;
    }

    public Collection<Door> getDoors() {
        return doors;
    }

    public String getName() {
        return name;
    }

    public void execute(Action action) {
        action.run(this);

        for (Light light : lights) {
            light.execute(action);
        }

        for (Door door : doors) {
            door.execute(action);
        }
    }

    public boolean hasDoorWithId(String id) {
        return getDoorWithId(id) != null;
    }

    public Door getDoorWithId(String id) {
        for (Door door : doors) {
            if (door.getId().equals(id)) {
                return door;
            }
        }
        return null;
    }

    public Light getLightWithId(String id) {
        for (Light light : lights) {
            if (light.getId().equals(id)) {
                return light;
            }
        }
        return null;
    }
}
