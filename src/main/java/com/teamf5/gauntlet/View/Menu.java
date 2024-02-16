package com.teamf5.gauntlet.View;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class Menu extends Pane {
    public Menu() {
        String fxmlFileName = "/View/MenuView.fxml";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("Menu: Failed to load " + fxmlFileName);
        }
    }


}
