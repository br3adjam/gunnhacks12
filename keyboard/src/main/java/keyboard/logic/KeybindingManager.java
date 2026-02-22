package keyboard.logic;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import keyboard.config.ConfigLoader;
import keyboard.config.NormalConfig;
import keyboard.config.SequenceConfig;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeybindingManager {
    private final int maxComboLength;

    private final boolean enableToggleKey;
    private final boolean playErrorSound;

    private final List<Integer> comboBuffer = new ArrayList<>();

    private final Map<String, Integer> keyCodes = new HashMap<>();

    ObjectMapper mapper = new ObjectMapper(); // stackoverflow my beloved
//    List<List<String>> arrayCollection = mapper.readValue(ConfigLoader, List.class); //rip doesnt work figure out
    // todo)) find fast way to compare sequences from getSequenceConfig with comboBuffer (hashmaps lol)

    ConfigLoader loader = new ConfigLoader();
    SequenceConfig config = loader.getSequenceConfig();
    List<SequenceConfig.SequenceEntry> seq = config.sequences;
    List<String> seqs = (List<String>) seq.stream();

    public KeybindingManager(NormalConfig config) {
        keyCodes.put("MOD", keyFromString(config.keys.get("MOD")));
        keyCodes.put("END", keyFromString(config.keys.get("END")));
        keyCodes.put("TOGGLE", keyFromString(config.keys.get("TOGGLE")));
        keyCodes.put("UP", keyFromString(config.keys.get("UP")));
        keyCodes.put("DOWN", keyFromString(config.keys.get("DOWN")));
        keyCodes.put("LEFT", keyFromString(config.keys.get("LEFT")));
        keyCodes.put("RIGHT", keyFromString(config.keys.get("RIGHT")));

        this.maxComboLength = config.maxComboLength;
        this.playErrorSound = config.playErrorSound;
        this.enableToggleKey = config.enableToggleKey;
    }

    public void processKey(int keyCode) {
        if (keyCode == keyCodes.get("END")) {
            executeCombo();
            comboBuffer.clear();
            return;
        }

        comboBuffer.add(keyCode);

        if (comboBuffer.size() > maxComboLength) {
            resetWithError();
        }
        System.out.println("key: " + keyCode);
    }

    private void reset() {
        comboBuffer.clear();
    }

    private void resetWithError() {
        reset();

        if (playErrorSound) {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

    public void executeCombo() {

        if (comboBuffer.getLast() == keyCodes.get("MOD")) { // mod end always clears buffer, make cleaner when coding the "matching" code as well
            comboBuffer.clear();
        }

        for (int keyCode : comboBuffer) {
            String keyText = KeyEvent.getKeyText(keyCode);
        }

        // todo)) lol make logic based off buffer(ringbuffer might be funny to use) and sequences
    }

    public boolean isToggleKey(int keyCode) {
        return enableToggleKey && keyCode == keyCodes.get("TOGGLE");
    }

    public int keyFromString(String key) { // lol
        try {
            return NativeKeyEvent.class
                    .getField("VC_" + key.toUpperCase())
                    .getInt(null);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Invalid key: " + key
            );
        }
    }
}