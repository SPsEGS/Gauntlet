package com.teamf5.gauntlet;

import com.teamf5.gauntlet.Model.Editor.FileLoader;
import com.teamf5.gauntlet.Model.Editor.FileSaver;
import com.teamf5.gauntlet.Model.Editor.GameMap;
import com.teamf5.gauntlet.Model.Editor.TileType;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        GameMap map = new GameMap(8, 8);
        map.setTile(1, 0, TileType.CHICKEN);
        System.out.println("Original map:");
        System.out.println(map);

        FileSaver saver = new FileSaver(map, "test_map");
        saver.saveBinary();

        FileLoader loader = new FileLoader("test_map");
        GameMap loadedMap = loader.loadBinary();

        System.out.println("Loaded map:");
        System.out.println(loadedMap);
    }
}
