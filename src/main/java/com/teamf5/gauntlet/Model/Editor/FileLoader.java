package com.teamf5.gauntlet.Model.Editor;

import java.io.FileInputStream;
import java.util.Arrays;

/**
 * A class to load a GameMap from a file.
 */
public class FileLoader {
    private final String FILENAME;

    /**
     * Creates a new FileLoader.
     * @param name The name of the file containing the map, with or without the extension.
     */
    public FileLoader(String name) {
        if (name.endsWith(FileProperties.getFileExtensionBinary()))
            this.FILENAME = name;
        else
            this.FILENAME = name + FileProperties.getFileExtensionBinary();
    }

    /**
     * Loads a map in a binary format from a file.
     * @return The map created from the data in the file.
     */
    public GameMap loadBinary() {
        try {
            // We put the entire file data into a byte array, that way we only read the file once, and we read from the byte array instead.
            // This is way faster than reading the file byte by byte.
            // Every time we read from the byte array, we increment the currentIndex variable to keep track of where we are in the array.

            FileInputStream file = new FileInputStream(this.FILENAME);
            byte[] bytes = file.readAllBytes();
            int currentIndex = 0;

            // Read the identification string
            int identificationLength = FileProperties.getIdentificationBytes().length;
            byte[] identificationBytes = new byte[identificationLength];
            for (int i = currentIndex; i < currentIndex + identificationLength; i++) {
                identificationBytes[i] = bytes[i];
            }
            if (!Arrays.equals(identificationBytes, FileProperties.getIdentificationBytes()))
                throw new Exception("Failed to read file " + this.FILENAME + "! The identification string is not valid.");
            currentIndex += identificationLength;

            // Read the begin file tag
            if (bytes[currentIndex] != FileProperties.BEGIN_FILE_TAG)
                throw new Exception("Failed to read file " + this.FILENAME + "! The begin file tag was not found.");
            currentIndex++;

            // Read the header
            if (bytes[currentIndex] != FileProperties.BEGIN_HEADER_TAG)
                throw new Exception("Failed to read file " + this.FILENAME + "! The begin header tag was not found.");
            currentIndex++;

            int mapWidth = bytes[currentIndex];
            currentIndex++;
            int mapHeight = bytes[currentIndex];
            currentIndex++;

            if (bytes[currentIndex] != FileProperties.END_HEADER_TAG)
                throw new Exception("Failed to read file " + this.FILENAME + "! The end header tag was not found.");
            currentIndex++;

            // Read the map data
            TileType[] types = TileType.values();
            GameMap map = new GameMap(mapWidth, mapHeight);
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
                    int tile = bytes[currentIndex];

                    if (tile == -1)
                        throw new Exception("Failed to read file " + this.FILENAME + "! The dimensions of the map are wrong.");

                    map.setTile(x, y, types[tile]);
                    currentIndex++;
                }
            }

            // Read the end file tag (don't throw an exception because the map should be properly read)
            if (bytes[currentIndex] != FileProperties.END_FILE_TAG)
                System.out.println("WARNING " + this.FILENAME + ": There is more data after the end of the map; maybe the dimensions are wrong?");

            return map;
        } catch (Exception e) {
            System.out.println("Failed to open file " + this.FILENAME + "! " + e.getMessage());
        }

        return null;
    }
}
