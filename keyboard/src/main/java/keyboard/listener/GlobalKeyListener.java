package keyboard.listener;

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import keyboard.logic.KeyboardLogic;

public record GlobalKeyListener(KeyboardLogic logic) implements NativeKeyListener {
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        logic.handleKeyPress(e.getKeyCode());
    }
}