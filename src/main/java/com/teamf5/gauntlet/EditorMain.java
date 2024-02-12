package com.teamf5.gauntlet;

import com.teamf5.gauntlet.View.Editor;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Class to manually launch the editor, for testing purposes.
 * The editor will be able to be launched from the game, but we don't have a game yet.
 */
public class EditorMain extends Application {
    public void start(Stage stage) {
        int sceneWidth = 1400;
        int sceneHeight = 1000;

        Editor editor = new Editor();
        Scene scene = new Scene(editor, sceneWidth, sceneHeight);
        scene.getStylesheets().add(getClass().getResource("/Style/darkTheme.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
