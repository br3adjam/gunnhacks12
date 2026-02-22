package keyboard.logic;

import keyboard.config.ConfigLoader;

public class KeyboardLogic {
    private final SequenceManager sequenceManager;
    private final KeybindingManager keybindingManager;
    private boolean sequenceMode = false;

    public KeyboardLogic(ConfigLoader configLoader) {
        this.sequenceManager = new SequenceManager(configLoader.getSequenceConfig());
        this.keybindingManager = new KeybindingManager(configLoader.getNormalConfig());
    }

    public void handleKeyPress(int keyCode) {
        if (keybindingManager.isToggleKey(keyCode)) {
            sequenceMode = !sequenceMode;
            System.out.println("sequence mode: " + sequenceMode);
            return;
        }

        if (sequenceMode) {
            sequenceManager.processKey(keyCode);
        } else {
            keybindingManager.processKey(keyCode);
        }
    }
}