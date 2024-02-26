package com.teamf5.gauntlet.View;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Multiplayer extends AnchorPane {
    public Multiplayer() {
        String fxmlFileName = "/View/MultiplayerView.fxml";

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
