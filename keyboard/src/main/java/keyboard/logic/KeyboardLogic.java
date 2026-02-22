package keyboard.logic;

import keyboard.config.ConfigLoader;
import keyboard.config.NormalConfig;
import keyboard.config.SequenceConfig;

public class KeyboardLogic {
    private final SequenceManager sequenceManager;
    private final KeybindingManager keybindingManager;
    private boolean sequenceMode = false;

    public KeyboardLogic(NormalConfig config, SequenceConfig sequence) {
        this.sequenceManager = new SequenceManager(sequence);
        this.keybindingManager = new KeybindingManager(config, sequence);
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