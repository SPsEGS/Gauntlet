package model.editor;

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
     * @param name The name of the file containing the map, with or without the extension.
     */
    public FileSaver(GameMap map, String name) {
        this.MAP = map;
        if (name.endsWith(FileProperties.getFileExtensionBinary()))
            this.FILENAME = name;
        else
            this.FILENAME = name + FileProperties.getFileExtensionBinary();
    }

    /**
     * Saves the map into a file in a binary, non-readable format.
     */
    public void saveBinary() {
        try {
            // We store all the information related to the map in a byte array, then we write it only once to the file.
            // This is faster than writing in the file byte by byte.

            int fileSize = 0;
            fileSize += FileProperties.getIdentificationBytes().length + 1;
            fileSize++; // Begin file tag
            fileSize++; // Begin header tag
            fileSize += 2; // Dimensions of the map
            fileSize++; // End header tag
            fileSize += this.MAP.getWidth() * this.MAP.getHeight(); // Map data
            fileSize++; // End file tag

            byte[] byteArray = new byte[fileSize];
            int currentIndex = 0;

            // Write the identification string
            byte[] idBytes = FileProperties.getIdentificationBytes();
            for (int i = currentIndex; i < currentIndex + idBytes.length; i++)
                byteArray[i] = idBytes[i];
            currentIndex += idBytes.length;

            byteArray[currentIndex] = FileProperties.BEGIN_FILE_TAG;
            currentIndex++;

            // Write the header
            byteArray[currentIndex] = FileProperties.BEGIN_HEADER_TAG;
            currentIndex++;
            // Write the map dimensions
            byteArray[currentIndex] = (byte)this.MAP.getWidth();
            currentIndex++;
            byteArray[currentIndex] = (byte)this.MAP.getHeight();
            currentIndex++;
            byteArray[currentIndex] = FileProperties.END_HEADER_TAG;
            currentIndex++;

            // Write the actual map
            for (int x = 0; x < this.MAP.getWidth(); x++) {
                for (int y = 0; y < this.MAP.getHeight(); y++) {
                    byteArray[currentIndex] = (byte)this.MAP.getTileAsInt(x, y);
                    currentIndex++;
                }
            }

            byteArray[currentIndex] = FileProperties.END_FILE_TAG;

            FileOutputStream file = new FileOutputStream(this.FILENAME);
            file.write(byteArray);
        } catch (IOException e) {
            System.out.println("Failed to save to " + this.FILENAME + "! " + e.getMessage());
        }
    }
}
