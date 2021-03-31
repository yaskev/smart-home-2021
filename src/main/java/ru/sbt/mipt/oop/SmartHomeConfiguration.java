package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.adapters.MakeCoolCompanyEventHandler;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.*;

@Configuration
public class SmartHomeConfiguration {
    @Bean
    SensorEventsManager sensorEventsManager(SmartHome smartHome, CommandSender sender) {
        SensorEventsManager manager = new SensorEventsManager();
        EventHandler doorEventHandler = new DoorEventHandler(smartHome);
        EventHandler hallDoorEventHandler = new HallDoorEventHandler(smartHome, sender);
        EventHandler lightEventHandler = new LightEventHandler(smartHome);
        manager.registerEventHandler(new MakeCoolCompanyEventHandler(doorEventHandler));
        manager.registerEventHandler(new MakeCoolCompanyEventHandler(hallDoorEventHandler));
        manager.registerEventHandler(new MakeCoolCompanyEventHandler(lightEventHandler));

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

}
