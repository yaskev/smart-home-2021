package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.sbt.mipt.oop.adapters.CCEventHandlerAdapter;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.handlers.*;
import ru.sbt.mipt.oop.notifiers.Notifier;
import ru.sbt.mipt.oop.notifiers.SMSNotifier;
import ru.sbt.mipt.oop.signalling.Alarm;
import ru.sbt.mipt.oop.wrappers.AlarmWrappedHandler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

@Configuration
public class SmartHomeConfiguration {
    @Bean
    SensorEventsManager sensorEventsManager() {
        HashMap<String, String> eventClasses = new HashMap<>();
        eventClasses.put("LightIsOn", LightOnEvent.class.getCanonicalName());
        eventClasses.put("LightIsOff", LightOffEvent.class.getCanonicalName());
        eventClasses.put("DoorIsOpen", DoorOpenedEvent.class.getCanonicalName());
        eventClasses.put("DoorIsClosed", DoorClosedEvent.class.getCanonicalName());

        SensorEventsManager manager = new SensorEventsManager();
        manager.registerEventHandler(new CCEventHandlerAdapter(alarmWrappedHandler(alarm(), Arrays.asList(doorEventHandler(smartHome()),
                hallDoorEventHandler(smartHome(), sender()), lightEventHandler(smartHome())), notifier()), eventClasses));

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

}
