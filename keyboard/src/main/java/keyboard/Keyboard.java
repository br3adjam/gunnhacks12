package keyboard;

import com.github.kwhat.jnativehook.GlobalScreen;
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

        KeyboardLogic logic = new KeyboardLogic(configLoader);

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener(logic));

        System.out.println("hook registered!");
    }
}