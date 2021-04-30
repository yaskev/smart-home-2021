package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import rc.RemoteControlRegistry;
import ru.sbt.mipt.oop.Helper;
import ru.sbt.mipt.oop.commands.*;
import ru.sbt.mipt.oop.remote.RemoteControlImpl;

@Configuration
@Import(SmartHomeConfiguration.class)
public class RemoteControlConfiguration {
    @Autowired SmartHomeConfiguration shConfig;

    @Bean
    RemoteControlRegistry remoteControlRegistry() {
        return new RemoteControlRegistry();
    }

    @Bean
    RemoteControlImpl remoteControlImpl(RemoteControlRegistry registry) {
        RemoteControlImpl rc = new RemoteControlImpl();
        registry.registerRemoteControl(rc, Helper.getRandomId(8));
        return rc;
    }

    @Bean
    Command closeHallDoorCommand(RemoteControlImpl rc) {
        Command cmd = new CloseHallDoorCommand(shConfig.smartHome());
        rc.assignCommandToButton("A", cmd);
        return cmd;
    }

    @Bean
    Command enableAlarmCommand(RemoteControlImpl rc) {
        Command cmd = new EnableAlarmCommand(shConfig.smartHome());
        rc.assignCommandToButton("D", cmd);
        return cmd;
    }

    @Bean
    Command makeAlarmRingingCommand() {
        return new MakeAlarmRingingCommand(shConfig.smartHome());
    }

    @Bean
    Command turnOffAllLightCommand(RemoteControlImpl rc) {
        Command cmd = new TurnOffAllLightCommand(shConfig.smartHome());
        rc.assignCommandToButton("B", cmd);
        return cmd;
    }

    @Bean
    Command turnOnAllLightCommand(RemoteControlImpl rc) {
        Command cmd = new TurnOnAllLightCommand(shConfig.smartHome());
        rc.assignCommandToButton("C", cmd);
        return cmd;
    }

    @Bean
    Command turnOnHallLightCommand() {
        return new TurnOnHallLightCommand(shConfig.smartHome());
    }
}
