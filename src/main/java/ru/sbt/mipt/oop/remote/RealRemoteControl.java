package ru.sbt.mipt.oop.remote;

import rc.RemoteControl;
import ru.sbt.mipt.oop.commands.Command;

import java.util.*;

public class RealRemoteControl implements RemoteControl {
    private final Map<String, Command> buttonCodeToCommandMap = new HashMap<>();
    private final Set<String> possibleCodes = new HashSet<>(Arrays.asList("A", "B", "C", "D", "1", "2", "3", "4"));

    @Override
    public void onButtonPressed(String buttonCode, String rcId) {
        if (buttonCodeToCommandMap.containsKey(buttonCode)) {
            buttonCodeToCommandMap.get(buttonCode).execute();
        }
    }

    public void assignCommandToButton(String buttonCode, Command command) {
        if (possibleCodes.contains(buttonCode)) {
            buttonCodeToCommandMap.put(buttonCode, command);
        } else {
            throw new IllegalArgumentException("Invalid button code: " + buttonCode);
        }
    }
}
