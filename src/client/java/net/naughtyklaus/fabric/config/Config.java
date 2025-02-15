package net.naughtyklaus.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.naughtyklaus.fabric.client.SoundmasterClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

import static net.naughtyklaus.fabric.config.Soundmaster.DEFAULT_ALLOWED_MUSIC_FILES;

public class Config {
    private static final Path DIR_PATH = Path.of("config");
    private static final String FILE_NAME = SoundmasterClient.NAMESPACE + ".json";
    private static final String BACKUP_FILE_NAME = SoundmasterClient.NAMESPACE + ".unreadable.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Config instance = null;
    public boolean muteCopyrightedAudio;
    public ArrayList<String> allowedMusicFiles = new ArrayList<>();

    public Config() {
        this.muteCopyrightedAudio = false;
        allowedMusicFiles.addAll(Arrays.asList(DEFAULT_ALLOWED_MUSIC_FILES));
    }

    public static boolean doesMuteCopyrightedAudio() {
        return instance.muteCopyrightedAudio;
    }

    public static void setMuteCopyrightedAudio(boolean enabled) {
        instance.muteCopyrightedAudio = enabled;
        Config.save();
    }

    public static void addAllowedMusicFile(String s) {
        instance.allowedMusicFiles.add(s);
        Config.save();
    }

    public static void removeAllowedMusicFile(String s) {
        instance.allowedMusicFiles.remove(s);
        Config.save();
    }

    public static Config get() {
        if (instance == null) {
            instance = Config.load();
        }
        return instance;
    }

    public static Config getAndSave() {
        get();
        save();
        return instance;
    }

    public static void resetAndSave() {
        instance = new Config();
        save();
    }

    public static @NotNull Config load() {
        Path file = DIR_PATH.resolve(FILE_NAME);
        Config config = null;
        if (Files.exists(file)) {
            config = load(file, GSON);
            if (config == null) {
                backup();
            }
        }
        return config != null ? config : new Config();
    }

    private static @Nullable Config load(Path file, Gson gson) {
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(file.toFile()), StandardCharsets.UTF_8)) {
            return gson.fromJson(reader, Config.class);
        } catch (Exception e) {
            return null;
        }
    }

    private static void backup() {
        try {
            if (!Files.isDirectory(DIR_PATH)) Files.createDirectories(DIR_PATH);
            Path file = DIR_PATH.resolve(FILE_NAME);
            Path backupFile = file.resolveSibling(BACKUP_FILE_NAME);
            Files.move(file, backupFile, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {

        }
    }

    public static void save() {
        if (instance == null) return;
        try {
            if (!Files.isDirectory(DIR_PATH)) Files.createDirectories(DIR_PATH);
            Path file = DIR_PATH.resolve(FILE_NAME);
            Path tempFile = file.resolveSibling(file.getFileName() + ".tmp");
            try (OutputStreamWriter writer = new OutputStreamWriter(
                    new FileOutputStream(tempFile.toFile()), StandardCharsets.UTF_8)) {
                writer.write(GSON.toJson(instance));
            } catch (IOException e) {
                throw new IOException(e);
            }
            Files.move(tempFile, file, StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

