package keyboard.logic;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import keyboard.config.SequenceConfig;
import keyboard.util.ActionRunner;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class SequenceManager {
    private final SequenceConfig config;
    private final Deque<Integer> buffer = new ArrayDeque<>(); // i learned this from robotics wow
    private final int maxLength;

    private long lastPressed = 0;
    private final long timeoutMs = 500;

    public SequenceManager(SequenceConfig config) {
        this.config = config;

        int longest = 0;
        for (SequenceConfig.SequenceEntry entry : config.sequences) {
            longest = Math.max(longest, entry.sequence.size());
        }
        this.maxLength = longest;
    }

    public void processKey(int keyCode) {
        long now = System.currentTimeMillis();

        if (now - lastPressed > timeoutMs) {
            buffer.clear();
        }

        lastPressed = now;

        buffer.addLast(keyCode);

        if (buffer.size() > maxLength) {
            buffer.removeFirst();
        }

        checkSequences();
    }

    private void checkSequences() {
        for (SequenceConfig.SequenceEntry entry : config.sequences) {
            if (matches(entry.sequence)) {
                ActionRunner.run(entry. run);
                buffer.clear();
                return;
            }
        }
    }

    private boolean matches(List<String> sequence) {
        if (sequence.size() != buffer.size()) {
            return false;
        }

        int i = 0;
        for (int key : buffer) {
            int expected = keyFromString(sequence.get(i));
            if (key != expected) {
                return false;
            }
            i++;
        }

        return true;
    }

    private int keyFromString(String key) {
        try {
            return NativeKeyEvent.class
                    .getField("VC_" + key.toUpperCase())
                    .getInt(null);
        } catch (Exception e) {
            throw new RuntimeException("Invalid key: " + key);
        }
    }
}