package keyboard.config;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ConfigLoader {
    private NormalConfig config;
    private SequenceConfig sequences;

    private final String configDir =
            System.getProperty("user.home") + "/.config/keyboard/";


    public void loadConfigs() throws Exception {
        Files.createDirectories(Path.of(configDir));

        copyIfMissing("default-config.json");
        copyIfMissing("default-sequences.json");

        Gson gson = new Gson();

        Path defaultConfigPath = Path.of(configDir + "config.json");
        Path path = Path.of(configDir + "sequences.json");

        if (!Files.exists(defaultConfigPath)) {
            Files.writeString(defaultConfigPath, gson.toJson(new NormalConfig()));
        }
        Path sequencesPath = path;
        if (!Files.exists(sequencesPath)) {
            try (InputStream in = getClass().getClassLoader().getResourceAsStream("default-sequences.json")) {
                if (in == null) {
                    throw new RuntimeException("Missing resource: default-sequences.json");
                }
                Files.copy(in, sequencesPath, StandardCopyOption.REPLACE_EXISTING);
            }
        }

        config = gson.fromJson(
                new FileReader(configDir + "config.json"),
                NormalConfig.class
        );

        sequences = gson.fromJson(
                new FileReader(configDir + "sequences.json"),
                SequenceConfig.class
        );
    }

    private void copyIfMissing(String fileName) throws Exception {
        Path target = Path.of(configDir + fileName);

        if (Files.exists(target)) {
            return;
        }

        try (InputStream in = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new RuntimeException(
                        "Missing resource: " + fileName
                );
            }
            Files.copy(in, target);
        }
    }

    public NormalConfig getNormalConfig() {
        return config;
    }

    public SequenceConfig getSequenceConfig() {
        return sequences;
    }
}
