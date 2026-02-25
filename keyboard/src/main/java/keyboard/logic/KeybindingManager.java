package keyboard.logic;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import keyboard.config.NormalConfig;
import keyboard.config.SequenceConfig;
import keyboard.util.ActionRunner;

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

    private final Map<List<Integer>, String> sequenceMap = new HashMap<>();

    public void registerSequence(List<String> seq, String output) {
        List<Integer> key = seq.stream()
                .map(this::keyFromString)
                .toList();
        sequenceMap.put(key, output);
        // debug
        System.out.println("Registered sequence: " + key + " -> " + output);
    }

//    ConfigLoader loader = new ConfigLoader();
//    SequenceConfig config = loader.getSequenceConfig();

    SequenceConfig config;

    public KeybindingManager(NormalConfig normalConfig, SequenceConfig sequenceConfig) {
        this.config = sequenceConfig;

        keyCodes.put("MOD", keyFromString(normalConfig.keys.get("MOD")));
        keyCodes.put("END", keyFromString(normalConfig.keys.get("END")));
        keyCodes.put("TOGGLE", keyFromString(normalConfig.keys.get("TOGGLE")));
        keyCodes.put("UP", keyFromString(normalConfig.keys.get("UP")));
        keyCodes.put("DOWN", keyFromString(normalConfig.keys.get("DOWN")));
        keyCodes.put("LEFT", keyFromString(normalConfig.keys.get("LEFT")));
        keyCodes.put("RIGHT", keyFromString(normalConfig.keys.get("RIGHT")));

        this.maxComboLength = normalConfig.maxComboLength;
        this.playErrorSound = normalConfig.playErrorSound;
        this.enableToggleKey = normalConfig.enableToggleKey;

        if (sequenceConfig != null && sequenceConfig.sequences != null) {
            for (SequenceConfig.SequenceEntry entry : sequenceConfig.sequences) {
                registerSequence(entry.getSequence(), entry.getRun());
            }
        }
    }

//    List<SequenceConfig.SequenceEntry> seq = config.sequences;

    public void processKey(int keyCode) {
        if (keyCode == keyCodes.get("END")) {
            executeCombo();
            reset();
            return;
        }

        comboBuffer.add(keyCode);

        if (comboBuffer.size() > maxComboLength) {
            resetWithError();
        }
        System.out.println("key: " + keyCode + " buffer: " + comboBuffer);
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

    public String checkCombo() {
        // Use an immutable copy of comboBuffer to avoid any lookup issues with different List implementations
        List<Integer> key = List.copyOf(comboBuffer);
        return sequenceMap.getOrDefault(key, "INVALID");
    }

    public void executeCombo() {
        System.out.println("Executing combo. buffer=" + comboBuffer + " registeredKeys=" + sequenceMap.keySet());

        int size = comboBuffer.size();
        if (size >= 2 && comboBuffer.get(size-1).equals(keyCodes.get("MOD")) && comboBuffer.get(size-2).equals(keyCodes.get("MOD"))) {
            comboBuffer.clear();
            return;
        }

        // Detailed debug: compare comboBuffer to each registered key
        int comboHash = comboBuffer.hashCode();
        System.out.println("comboBuffer.hash=" + comboHash + " comboBuffer.equalsAnyRegistered?");
        for (List<Integer> registered : sequenceMap.keySet()) {
            System.out.println(" registered=" + registered + " hash=" + registered.hashCode() + " equals=" + registered.equals(comboBuffer));
        }

        if (checkCombo().equals("INVALID")) {
            System.out.println("Invalid sequence!");
        } else {
            ActionRunner.run(checkCombo());
        }
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

    public SequenceConfig getConfig() {
        return config;
    }
}