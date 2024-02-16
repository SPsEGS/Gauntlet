package com.teamf5.gauntlet.View;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

/**
 * The main window of the Editor, allowing the user to build a map with a visual tool, using a set of predefined tiles.
 */
public class Editor extends Pane {

    /**
     * Creates a new Editor and loads its FXML view.
     */
    public Editor() {
        String fxmlFileName = "/View/EditorView.fxml";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            System.err.println("Editor (View): Failed to load FXML file " + fxmlFileName);
            e.printStackTrace();

            Platform.exit(); // TODO: Replace with proper error handling (falling back to the main menu?)
        }
    }
}
