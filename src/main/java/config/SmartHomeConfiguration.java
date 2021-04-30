package config;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.*;
import ru.sbt.mipt.oop.adapters.CCEventHandlerAdapter;
import ru.sbt.mipt.oop.factory.*;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.notifiers.Notifier;
import ru.sbt.mipt.oop.notifiers.SMSNotifier;
import ru.sbt.mipt.oop.signalling.Alarm;
import ru.sbt.mipt.oop.wrappers.AlarmWrappedHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SmartHomeConfiguration {
    @Bean
    SensorEventsManager sensorEventsManager() {
        SensorEventsManager manager = new SensorEventsManager();
        manager.registerEventHandler(ccEventHandlerAdapter());
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

    @Bean
    AlarmWrappedHandler alarmWrappedHandler(Alarm alarm, Collection<EventHandler> handlers, Notifier notifier) {
        return new AlarmWrappedHandler(alarm, handlers, notifier);
    }

    @Bean
    Alarm alarm() {
        return new Alarm();
    }

    @Bean
    Notifier notifier() {
        return new SMSNotifier();
    }

    @Bean
    Map<String, EventFactory> ccTypeToEventFactoryMap() {
        Map<String, EventFactory> eventFactories = new HashMap<>();
        eventFactories.put("LightIsOn", new LightOnFactory());
        eventFactories.put("LightIsOff", new LightOffFactory());
        eventFactories.put("DoorIsOpen", new DoorOpenedFactory());
        eventFactories.put("DoorIsClosed", new DoorClosedFactory());
        return eventFactories;
    }

    @Bean
    CCEventHandlerAdapter ccEventHandlerAdapter() {
        return new CCEventHandlerAdapter(alarmWrappedHandler(), ccTypeToEventFactoryMap());
    }

    @Bean
    Collection<EventHandler> eventHandlers() {
        return Arrays.asList(doorEventHandler(smartHome()),
                hallDoorEventHandler(smartHome(), sender()), lightEventHandler(smartHome()));
    }

    @Bean
    EventHandler alarmWrappedHandler() {
        return new AlarmWrappedHandler(alarm(), eventHandlers(), notifier());
    }

}
