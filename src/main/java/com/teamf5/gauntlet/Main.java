package com.teamf5.gauntlet;

import com.teamf5.gauntlet.Model.Editor.FileLoader;
import com.teamf5.gauntlet.Model.Editor.FileSaver;
import com.teamf5.gauntlet.Model.Editor.GameMap;
import com.teamf5.gauntlet.Model.Editor.TileType;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    public void start(Stage stage) throws Exception {
        final Pane myPane = new Pane();

        // Scene dimensions
        double width = 300.0, height = 200.0;

        // Scene = container for all content
        Scene scene = new Scene(myPane, width, height);

        stage.setScene(scene);
        stage.setTitle("Basic Scene");
        stage.show ();
    }

    public static void testGameMap() {
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
