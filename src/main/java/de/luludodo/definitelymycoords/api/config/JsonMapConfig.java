package de.luludodo.definitelymycoords.api.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import de.luludodo.definitelymycoords.api.config.serializer.MapSerializer;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public abstract class JsonMapConfig<K,V> {
    private static final Logger LOG = LoggerFactory.getLogger("Lulu/JsonMapConfig");

    public static class EmptyFileException extends InvalidJsonException {
        public EmptyFileException() {
            super();
        }
        public EmptyFileException(String message) {
            super(message);
        }
        public EmptyFileException(Throwable cause) {
            super(cause);
        }
        public EmptyFileException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    public static class InvalidJsonException extends IOException {
        public InvalidJsonException() {
            super();
        }
        public InvalidJsonException(String message) {
            super(message);
        }
        public InvalidJsonException(Throwable cause) {
            super(cause);
        }
        public InvalidJsonException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    private final Gson gson;
    private final File file;
    private final String filename;
    private final String[] oldFilenames;
    private final Type type;
    protected Map<K, V> content;
    public JsonMapConfig(String filename, MapSerializer<K, V> serializer, String... oldFilenames) {
        this.filename = filename;
        this.oldFilenames = oldFilenames;
        type = new TypeToken<Map<K, V>>(){}.getType();
        gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(type, serializer).create();
        file = FabricLoader.getInstance().getConfigDir().resolve(filename + ".json").toFile();
        reload();
    }

    private void fillWithDefaults(Map<K, V> map) {
        getDefaults().forEach(map::putIfAbsent);
    }

    private void tryRestoreOldSettings() {
        if (file.exists()) return;
        for (String oldFilename : oldFilenames) {
            File oldFile = FabricLoader.getInstance().getConfigDir().resolve(oldFilename + ".json").toFile();
            if (!oldFile.exists()) continue;
            try {
                file.getParentFile().mkdirs();
                FileUtils.moveFile(oldFile, file);
                LOG.info("Restored old settings from {}.json to {}.json!", oldFilename, filename);
                break;
            } catch (IOException e) {
                LOG.error("Cannot restore old settings for {}.json from {}.json!", filename, oldFilename);
            }
        }
    }

    private Map<K, V> read(File file) throws IOException {
        String contentToParse = FileUtils.readFileToString(file, Charset.defaultCharset());
        if (contentToParse.isBlank()) {
            throw new EmptyFileException();
        }
        try {
            return gson.fromJson(contentToParse, type);
        } catch (Exception e) {
            throw new InvalidJsonException(e);
        }
    }

    protected abstract Map<K, V> getDefaults();

    public void reset() {
        content = new HashMap<>(getDefaults());
        save();
    }

    public void reload() {
        tryRestoreOldSettings();
        try {
            content = read(file);
            fillWithDefaults(content);
            LOG.info("Loaded {}.json!", filename);
        } catch (EmptyFileException e) {
            LOG.error("Couldn't read config {}.json, because the file is empty!", filename);
            content = new HashMap<>(getDefaults());
        } catch (InvalidJsonException e) {
            LOG.error("Couldn't parse config " + filename + ".json!", e);
            content = new HashMap<>(getDefaults());
        } catch (NoSuchFileException | FileNotFoundException e) {
            LOG.warn("Couldn't find config {}.json!", filename); // Warning since it could be the first time launching
            content = new HashMap<>(getDefaults());
        } catch (IOException e) {
            LOG.error("Couldn't read config " + filename + ".json!", e);
            content = new HashMap<>(getDefaults());
        }
    }

    public void save() {
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (IOException e) {
            LOG.error("Couldn't create config " + filename + ".json!", e);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
            writer.write(gson.toJson(content, type));
        } catch (IOException e) {
            LOG.error("Couldn't save config " + filename + ".json!", e);
        }
    }

    public V get(K key) {
        return content.get(key);
    }

    public boolean contains(K key) {
        return content.containsKey(key);
    }

    public void set(K key, V value) {
        content.put(key, value);
        save();
    }

    public Set<K> options() {
        return content.keySet();
    }

    public void forEach(BiConsumer<K, V> action) {
        content.forEach(action);
    }
}
