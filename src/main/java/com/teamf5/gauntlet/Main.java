package com.teamf5.gauntlet;

import com.teamf5.gauntlet.Model.Editor.FileLoader;
import com.teamf5.gauntlet.Model.Editor.FileSaver;
import com.teamf5.gauntlet.Model.Editor.GameMap;
import com.teamf5.gauntlet.Model.Editor.TileType;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

// Test imports
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


public class Main extends Application {
    public void start(Stage stage) {
        final VBox myPane = new VBox(5);

        // Scene dimensions
        double width = 300.0, height = 200.0;

        Scene scene = new Scene(myPane, width, height);
        scene.getStylesheets().add(getClass().getResource("/Style/darkTheme.css").toExternalForm());

        myPane.getStyleClass().add("background");

        Button b = new Button("Test");
        b.getStyleClass().add("button");

        // BEGIN : TabPane
        TabPane tabPaneTest = new TabPane();
        Tab tab1 = new Tab("Test tab 1");
        Tab tab2 = new Tab("Test tab 2");

        tabPaneTest.getStyleClass().add("tabPane");
        tab1.getStyleClass().add("tab");
        tab2.getStyleClass().add("tab");

        Label content1 = new Label("Supposed to be a radio thing");
        Label content2 = new Label("Supposed to be a radio thing");
        content1.getStyleClass().add("content");
        content2.getStyleClass().add("content");

        tab1.setContent(content1);
        tab2.setContent(content2);

        tabPaneTest.getTabs().addAll(tab1, tab2);
        // END : TabPane

        myPane.getChildren().addAll(b, tabPaneTest);

        stage.setScene(scene);
        stage.setTitle("Basic Scene");
        stage.show ();
    }

    /*
    public static void main(String[] args) {
        System.out.println("Hello world!");

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
     */
}
