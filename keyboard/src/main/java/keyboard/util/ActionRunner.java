package keyboard.util;

/**
 * Conveniently runs files lol
 * <iframe src="tttm.us/badapple"></iframe>
 */
public class ActionRunner {
    public static void run(String path) {
        try {
            new ProcessBuilder(path).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}