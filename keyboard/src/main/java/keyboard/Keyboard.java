package keyboard;

import com.github.kwhat.jnativehook.GlobalScreen;
import keyboard.config.SequenceConfig;
import keyboard.listener.GlobalKeyListener;
import keyboard.logic.KeyboardLogic;
import keyboard.config.ConfigLoader;

import java.util.logging.Logger;

public class Keyboard {
    public static void main(String[] args) throws Exception {

        Logger logger = java.util.logging.Logger.getLogger(
                GlobalScreen.class.getPackage().getName()
        );
        logger.setLevel(java.util.logging.Level.OFF);
        logger.setUseParentHandlers(false); // thank you stackoverflowo user

        //runs on jar run/doubleclick lol
        System.out.println("starting app");

        ConfigLoader configLoader = new ConfigLoader();
        configLoader.loadConfigs();
        System.out.println("Config loaded!");

        SequenceConfig config = configLoader.getSequenceConfig();
        System.out.println("Config loaded: " + config);
        System.out.println("Sequences: " + config.sequences);

        KeyboardLogic logic = new KeyboardLogic(
                configLoader.getNormalConfig(),
                configLoader.getSequenceConfig()
        );
        System.out.println("KeyboardLogic created!");

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(logic));
        System.out.println("Hook registered!");

    }
}