package keyboard;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import java.util.List;

public class GlobalKeyListener implements NativeKeyListener {
    public List<String> lastPressed;

    public List<String> getLastPressed() {
        return lastPressed;
    }

    public static void main(String[] args) throws Exception {
        System.out.println("starting hook");
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new GlobalKeyListener());
        System.out.println("hook registered");
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        lastPressed.add(NativeKeyEvent.getKeyText(e.getKeyChar()));

    }
}
