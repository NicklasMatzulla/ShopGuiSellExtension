/*
 * Copyright (c) 2024 Nicklas Matzulla.
 */

package de.nicklasmatzulla.shopguisellextension.util;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BaseConfig {

    private final File configFile;
    private final String classpathFile;
    protected FileConfiguration config;

    public BaseConfig(final @NotNull File configFile, final @NotNull String classpathFile) {
        this.configFile = configFile;
        this.classpathFile = classpathFile;
        loadConfig();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void createConfig() {
        if (this.configFile.exists()) {
            return;
        }
        this.configFile.getParentFile().mkdirs();
        try (final InputStream configInputStream = getClass().getClassLoader().getResourceAsStream(this.classpathFile)) {
            if (configInputStream == null) {
                throw new FileNotFoundException("The classpath configuration " + this.classpathFile + " was not found");
            }
            Files.copy(configInputStream, this.configFile.toPath());
        } catch (final @NotNull IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void loadConfig() {
        try {
            createConfig();
            this.config = new YamlConfiguration();
            this.config.load(this.configFile);
        } catch (final @NotNull IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

}
