package com.teamf5.gauntlet.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Editor extends Pane {

    public Editor() {
        String fxmlFileName = "/View/EditorView.fxml";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Editor: Failed to load " + fxmlFileName);
        }
    }
}
