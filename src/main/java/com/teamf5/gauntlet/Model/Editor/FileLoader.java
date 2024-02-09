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
     * @param name The name of the file containing the map, without the extension.
     */
    public FileLoader(String name) {
        this.FILENAME = name + FileProperties.getFileExtension();
    }

    /**
     * Loads a map in a binary format from a file.
     * @return The map created from the data in the file.
     */
    public GameMap loadBinary() {
        try {
            FileInputStream file = new FileInputStream(this.FILENAME);

            // Read the identification string
            int identificationLength = FileProperties.getIdentificationBytes().length;
            byte[] identificationBytes = new byte[identificationLength];
            int readSize = file.read(identificationBytes);
            if (!Arrays.equals(identificationBytes, FileProperties.getIdentificationBytes()))
                throw new Exception("Failed to read file " + this.FILENAME + "! The identification string is not valid.");

            // Read the begin file tag
            if (file.read() != FileProperties.BEGIN_FILE_TAG)
                throw new Exception("Failed to read file " + this.FILENAME + "! The begin file tag was not found.");

            // Read the header
            if (file.read() != FileProperties.BEGIN_HEADER_TAG)
                throw new Exception("Failed to read file " + this.FILENAME + "! The begin header tag was not found.");

            int mapWidth = file.read();
            int mapHeight = file.read();

            if (file.read() != FileProperties.END_HEADER_TAG)
                throw new Exception("Failed to read file " + this.FILENAME + "! The end header tag was not found.");

            // Read the map data
            TileType[] types = TileType.values();
            GameMap map = new GameMap(mapWidth, mapHeight);
            for (int x = 0; x < mapWidth; x++) {
                for (int y = 0; y < mapHeight; y++) {
                    int tile = file.read();

                    if (tile == -1)
                        throw new Exception("Failed to read file " + this.FILENAME + "! The dimensions of the map are wrong.");

                    map.setTile(x, y, types[tile]);
                }
            }

            // Read the end file tag (don't throw an exception because the map should be properly read)
            if (file.read() != FileProperties.END_FILE_TAG)
                System.out.println("WARNING " + this.FILENAME + ": There is more data after the end of the map; maybe the dimensions are wrong?");

            // Print a warning if there is more data after the end of the file.
            if (file.read() != -1)
                System.out.println("WARNING " + this.FILENAME + ": There is more data after the end file tag.");

            return map;
        } catch (Exception e) {
            System.out.println("Failed to open file " + this.FILENAME + "! " + e.getMessage());
        }

        return null;
    }
}
