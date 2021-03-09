package ru.sbt.mipt.oop;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RewriteFileLogger implements Logger {
    private final Path path;

    public RewriteFileLogger(String filename) {
        path = Paths.get(filename);
    }

    @Override
    public void log(String msg) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(msg);
        }
    }
}
