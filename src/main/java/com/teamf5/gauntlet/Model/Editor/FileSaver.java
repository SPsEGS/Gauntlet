package com.teamf5.gauntlet.Model.Editor;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 *  A class to save a GameMap to a file.
 */
public class FileSaver {
    private final String FILENAME;
    private final GameMap MAP;

    /**
     * Creates a new FileSaver.
     * @param map The map you want to save.
     * @param name The name of the file containing the map, without the extension.
     */
    public FileSaver(GameMap map, String name) {
        this.MAP = map;
        this.FILENAME = name + FileProperties.getFileExtension();
    }

    /**
     * Saves the map into a file in a binary, non-readable format.
     */
    public void saveBinary() {
        try {
            FileOutputStream file = new FileOutputStream(this.FILENAME);

            // Write the identification string
            file.write(FileProperties.getIdentificationBytes());

            file.write(FileProperties.BEGIN_FILE_TAG);

            // Write the header
            file.write(FileProperties.BEGIN_HEADER_TAG);
            // Write the map dimensions
            file.write(this.MAP.getWidth());
            file.write(this.MAP.getHeight());
            file.write(FileProperties.END_HEADER_TAG);

            // Write the actual map
            for (int x = 0; x < this.MAP.getWidth(); x++) {
                for (int y = 0; y < this.MAP.getHeight(); y++) {
                    file.write(this.MAP.getTileAsInt(x, y));
                }
            }

            file.write(FileProperties.END_FILE_TAG);
        } catch (IOException e) {
            System.out.println("Failed to save to " + this.FILENAME + "! " + e.getMessage());
        }
    }
}
