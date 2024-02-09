package com.teamf5.gauntlet;

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

//    public static void main(String[] args) {
//        System.out.println("Hello world!");
//    }
}
