package com.teamf5.gauntlet;

import com.teamf5.gauntlet.View.Editor;
import com.teamf5.gauntlet.View.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMain extends Application {
    public void start(Stage stage) {
        int sceneWidth = 700;
        int sceneHeight = 700;

        Menu menu = new Menu();
        Scene scene = new Scene(menu, sceneWidth, sceneHeight);

        // FIXME: find the javafx way for styles
        scene.getStylesheets().add(getClass().getResource("/Style/darkTheme.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
