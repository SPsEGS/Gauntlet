import model.editor.FileLoader;
import model.editor.FileSaver;
import model.editor.GameMap;
import model.editor.TileType;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

// Test imports
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import view.Multiplayer;


public class Main extends Application {
    public void start(Stage stage) {
        this.testGameMap();

        final Multiplayer myPane = new Multiplayer();

        // Scene dimensions
        double width = 300.0, height = 200.0;

        Scene scene = new Scene(myPane, width, height);
        scene.getStylesheets().add(getClass().getResource("/style/darkTheme.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Basic Scene");
        stage.show ();
    }
    
    public static void testGameMap() {
        GameMap map = new GameMap(8, 8);
        map.setTile(1, 0, TileType.CHICKEN);
        map.setTile(6, 4, TileType.BOMB);
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
