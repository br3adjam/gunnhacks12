package keyboard.config;

import java.util.Map;

public class NormalConfig {
    public Map<String, String> keys = Map.of(
            "UP", "UP_ARROW",
            "DOWN", "DOWN_ARROW",
            "LEFT", "LEFT_ARROW",
            "RIGHT", "RIGHT_ARROW",
            "MOD", "PAGE_UP",
            "END", "PAGE_DOWN",
            "TOGGLE", "RCTRL"
        );
    
    public int maxComboLength = 8;

    public boolean enableToggleKey = true;
    public boolean minimizeOnClose = true;
    public boolean playErrorSound = false;
}