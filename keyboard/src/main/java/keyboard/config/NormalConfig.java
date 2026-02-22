package keyboard.config;

import java.util.Map;

public class NormalConfig {
    public Map<String, String> keys = Map.of(
                "UP", "UP",
                "DOWN", "DOWN",
                "LEFT", "LEFT",
                "RIGHT", "RIGHT",
                "MOD", "A",
                "END", "B",
                "TOGGLE", "CONTROL"
        );
    
    public int maxComboLength = 8;

    public boolean enableToggleKey = true;
    public boolean minimizeOnClose = true;
    public boolean playErrorSound = false;
}