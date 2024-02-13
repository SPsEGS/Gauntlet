package com.teamf5.gauntlet.Model.Editor;

/**
 * A class to hold all the tags and properties to put in a map file.
 * The map file should be structured as following:
 * <ul>
 *  <li>The identification string (with getIdentificationBytes).</li>
 *  <li>The begin file tag.</li>
 *  <li>The begin header tag.</li>
 *  <li>The map width.</li>
 *  <li>The map height.</li>
 *  <li>The end header tag.</li>
 *  <li>The map data.</li>
 *  <li>The end file tag.</li>
 * </ul>
 * This structure allows for a flexible way to read the file.
 */
public class FileProperties {
    public final static int BEGIN_FILE_TAG = 0;
    public final static int BEGIN_HEADER_TAG = 1;
    public final static int END_HEADER_TAG = 2;
    public final static int END_FILE_TAG = 3;

    /**
     * @return The identification string of the file as an array of bytes.
     */
    public static byte[] getIdentificationBytes() {
        return "Gauntlet2Map-v0.0.1".getBytes();
    }

    /**
     * @return The file extension of a binary game map file format
     */
    public static String getFileExtensionBinary() {
        return ".gmap";
    }
}
