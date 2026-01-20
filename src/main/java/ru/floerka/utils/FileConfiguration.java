package ru.floerka.utils;

import com.hypixel.hytale.server.core.HytaleServer;
import org.simpleyaml.configuration.file.YamlFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileConfiguration extends YamlFile {

    public FileConfiguration(Path path) {
        try {
            if(Files.notExists(path)) {
                InputStream resource = HytaleServer.get().getResource(path.toString());
                if(resource != null) {
                    Files.copy(resource, path);
                    resource.close();
                } else Files.createFile(path);
            }
            setConfigurationFile(path.toFile());
            load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public FileConfiguration(File file) {
        this(file.toPath());
    }
    public FileConfiguration(String fileName) {
        this(Path.of(fileName));
    }


    public void save() {
        try {
            super.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(File file) {
        try {
            super.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void save(String file) {
        try {
            super.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
