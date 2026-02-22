package keyboard.config;

import java.util.List;

public class SequenceConfig {
    public static class SequenceEntry {
        public List<String> sequence;
        public String run;

        public List<String> getSequence() {
            return sequence;
        }

        public String getRun() {
            return run;
        }
    }

    public List<SequenceEntry> sequences;
}