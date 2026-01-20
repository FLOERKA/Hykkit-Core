package ru.floerka.utils;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hypixel.hytale.logger.HytaleLogger;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;

public class PlayerFinderUtil {


    public static String findUIDByName(String playerName) {
        Path universeFolder = Path.of("universe");
        if (!Files.exists(universeFolder) || !Files.isDirectory(universeFolder)) {
            HytaleLogger.getLogger().at(Level.INFO).log("folder not found");
            return null;
        }
        Path playersFolder = universeFolder.resolve("players");
        if (!Files.exists(playersFolder) || !Files.isDirectory(playersFolder)) {
            HytaleLogger.getLogger().at(Level.INFO).log("folder2 not found");
            return null;
        }
        File folderAsFile = playersFolder.toFile();
        for (File playerFile : folderAsFile.listFiles(file -> file.getName().endsWith(".json"))) {
            try (FileReader reader = new FileReader(playerFile)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                if (json.has("Components")) {
                    JsonObject components = json.getAsJsonObject("Components");
                    if (components.has("Nameplate")) {
                        JsonObject nameplate = components.getAsJsonObject("Nameplate");
                        String textPlayerName = nameplate.get("Text").getAsString();
                        if (!textPlayerName.equals(playerName)) continue;
                        return playerFile.getName().replace(".json", "");
                    }

                }
            } catch (IOException e) {
                HytaleLogger.getLogger().at(Level.INFO).log("Exception: " + e.getMessage());
                return null;
            }
        }
        HytaleLogger.getLogger().at(Level.INFO).log("Just null))");
        return null;
    }
}
