package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.adapters.CCEventHandlerAdapter;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.*;

@Configuration
public class SmartHomeConfiguration {
    @Bean
    SensorEventsManager sensorEventsManager() {
        SensorEventsManager manager = new SensorEventsManager();
        manager.registerEventHandler(new CCEventHandlerAdapter(doorEventHandler));
        manager.registerEventHandler(new CCEventHandlerAdapter(hallDoorEventHandler));
        manager.registerEventHandler(new CCEventHandlerAdapter(lightEventHandler));

        return manager;
    }

    @Bean
    SmartHome smartHome() {
        return new JsonHomeReader("smart-home-1.js").read();
    }

    @Bean
    CommandSender sender() {
        return new ConsoleCommandSender();
    }

    @Bean
    DoorEventHandler doorEventHandler(SmartHome smartHome) {
        return new DoorEventHandler(smartHome);
    }

    @Bean
    HallDoorEventHandler hallDoorEventHandler(SmartHome smartHome, CommandSender sender) {
        return new HallDoorEventHandler(smartHome, sender);
    }

    @Bean
    LightEventHandler lightEventHandler(SmartHome smartHome) {
        return new LightEventHandler(smartHome);
    }

}
