package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class HallOfFlame extends Pane {
    public HallOfFlame() {
        String fxmlFileName = "/View/HallOfFlameView.fxml";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFileName));
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException("HallOfFlame: Failed to load " + fxmlFileName);
        }
    }
}
