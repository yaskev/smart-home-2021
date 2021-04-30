package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import config.RemoteControlConfiguration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String... args) {
        AbstractApplicationContext context = new AnnotationConfigApplicationContext(RemoteControlConfiguration.class);
        SensorEventsManager sensorEventsManager = context.getBean(SensorEventsManager.class);
        sensorEventsManager.start();
    }
}
