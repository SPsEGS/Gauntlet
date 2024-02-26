package controller;

import model.editor.GameMap;
import model.editor.TileType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

// From https://stackoverflow.com/a/30796829
class NumberOnlyListener implements ChangeListener<String> {
    private final TextField textField;

    public NumberOnlyListener(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (!newValue.matches("\\d*")) {
            this.textField.setText(newValue.replaceAll("\\D", ""));
        }
    }
}

public class MultiplayerMenuController {
    @FXML
    private TextField serverText;

    @FXML
    private TextField hostPortText;

    @FXML
    private TextField serverPortText;

    @FXML
    private Label invalidPortLabel;

    @FXML
    public void initialize() {
        this.hostPortText.textProperty().addListener(new NumberOnlyListener(this.hostPortText));
        this.serverPortText.textProperty().addListener(new NumberOnlyListener(this.serverPortText));
    }

    public void onLocal() {
        System.out.println("playing local");
    }

    public void onJoin() {
        int port = Integer.parseInt(this.serverPortText.getText());
        if (!this.checkPort(port))
            return;

        System.out.println("joining game at " + this.serverText.getText() + "on port " + this.serverPortText.getText());
    }

    public void onHost() {
        int port = Integer.parseInt(this.hostPortText.getText());
        if (!this.checkPort(port))
            return;

        System.out.println("hosting game on port " + this.hostPortText.getText());
    }

    public void onBack() {
        System.out.println("go back");
        Platform.exit();
    }

    private boolean checkPort(int port) {
        boolean isValid = port > 0 && port < 65535;
        if (!isValid)
            this.invalidPortLabel.setVisible(true);
        return isValid;
    }
}
