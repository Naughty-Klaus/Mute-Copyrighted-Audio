package net.naughtyklaus.fabric.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.naughtyklaus.fabric.client.Constants;
import net.naughtyklaus.fabric.client.SoundmasterClient;
import net.naughtyklaus.fabric.client.music.MusicEnumerator;
import net.naughtyklaus.fabric.client.music.MusicEnumeratorPresets;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

public class Config {
    private static final Path DIR_PATH = Path.of("config");
    private static final String FILE_NAME = SoundmasterClient.NAMESPACE + ".json";
    private static final String BACKUP_FILE_NAME = SoundmasterClient.NAMESPACE + ".unreadable.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static Config instance = null;
    private MusicEnumeratorPresets preset = MusicEnumeratorPresets.C418_ONLY;
    private static Integer nextPreset = 1;
    public boolean muteCopyrightedAudio;
    public ArrayList<MusicEnumerator> allowedMusic = new ArrayList<>();

    public final String modVersion;

    private static boolean needsUpdate = false;

    public static boolean isUpdateNeeded() {
        return Config.needsUpdate;
    }

    public static void shouldUpdate(boolean update) {
        Config.needsUpdate = update;
    }

    public Config() {
        this.muteCopyrightedAudio = false;
        this.modVersion = Constants.MOD_VERSION;
        allowedMusic.addAll(Arrays.asList(Soundmaster.DEFAULT_ALLOWED_MUSIC));
    }

    public void cyclePreset() {
        preset = MusicEnumeratorPresets.values()[nextPreset];

        this.allowedMusic.clear();
        this.allowedMusic.addAll(Arrays.asList(preset.getPresetMusic()));

        shouldUpdate(true);

        if(Config.nextPreset == MusicEnumeratorPresets.values().length - 1)
            Config.nextPreset = 0;
        else
            Config.nextPreset++;
    }

    public static boolean doesMuteCopyrightedAudio() {
        return instance.muteCopyrightedAudio;
    }

    public static void setMuteCopyrightedAudio(boolean enabled) {
        instance.muteCopyrightedAudio = enabled;
        Config.save();
    }

    public static void addAllowedMusicByFile(String s, String namespace, String author, String title) {
        MusicEnumerator e = MusicEnumerator.findByFile(s);
        instance.allowedMusic.add(e != null ? e : new MusicEnumerator(namespace, author, title, s));
        Config.save();
    }

    public static void removeAllowedMusic(MusicEnumerator e) {
        instance.allowedMusic.remove(e);
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
        } catch (IOException e) {}
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

    public boolean isMusicAllowed(String namespace, String path) {
        for (MusicEnumerator e : allowedMusic) {
            if (e.getNamespace().equalsIgnoreCase(namespace) &&
                    Arrays.asList(e.getFiles()).contains(path))
                return true;
        }
        return false;
    }
}

