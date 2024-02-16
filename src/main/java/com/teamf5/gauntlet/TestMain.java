package com.teamf5.gauntlet;

import com.teamf5.gauntlet.View.Editor;
import com.teamf5.gauntlet.View.HallOfFlame;
import com.teamf5.gauntlet.View.Menu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TestMain extends Application {
    public void start(Stage stage) {
        int sceneWidth = 700;
        int sceneHeight = 700;

        //Menu mypane = new Menu();
        HallOfFlame mypane = new HallOfFlame();
        Scene scene = new Scene(mypane, sceneWidth, sceneHeight);

        // FIXME: find the javafx way for styles
        scene.getStylesheets().add(getClass().getResource("/Style/darkTheme.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }
}
