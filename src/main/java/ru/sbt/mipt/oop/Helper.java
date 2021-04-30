package ru.sbt.mipt.oop;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class Helper {
    public static String getRandomId(int length) {
        byte[] array = new byte[length];
        new Random().nextBytes(array);
        return new String(array, StandardCharsets.UTF_8);
    }
}
