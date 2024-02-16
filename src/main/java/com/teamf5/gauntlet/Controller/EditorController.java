package com.teamf5.gauntlet.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import com.teamf5.gauntlet.Model.Editor.TileType;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.*;

/**
 * The controller for the main view of the Editor.
 */
public class EditorController {
    @FXML
    public GridPane grid;
    @FXML
    private TabPane tileSelect;

    private final Map<String, List<TileType>> tileGroups = new HashMap<>();

    /** Constructor (empty on purpose) */
    public EditorController() {
        initTileGroups();
    }

    public void initialize() throws IOException {
        for(String group : tileGroups.keySet()) {
            Tab t = new Tab();
            t.setText(group);

            HBox hBox = new HBox();
            for(TileType tile : tileGroups.get(group)) {
                Button button = new Button("", new ImageView(getImage()));
                hBox.getChildren().add(button);
            }

            t.setContent(hBox);
            tileSelect.getTabs().add(t);
        }
    }

    private Image getImage() {
        return new Image(getClass().getResource("/Textures/Tiles/Potions/potion_magic.png").toExternalForm());
    }

    private void initTileGroups() {
        this.tileGroups.put("Potions", new ArrayList<>());
        this.tileGroups.get("Potions").add(TileType.POTION_LIFE);
        this.tileGroups.get("Potions").add(TileType.POTION_POISON);
        this.tileGroups.get("Potions").add(TileType.POTION_SPEED);
        this.tileGroups.get("Potions").add(TileType.POTION_MAGIC);
        this.tileGroups.get("Potions").add(TileType.POTION_PHYSICAL);
        this.tileGroups.get("Potions").add(TileType.POTION_DEFENSE);

        this.tileGroups.put("Items", new ArrayList<>());
        this.tileGroups.get("Items").add(TileType.KEY);
        this.tileGroups.get("Items").add(TileType.CHICKEN);
        this.tileGroups.get("Items").add(TileType.BOMB);
        this.tileGroups.get("Items").add(TileType.TREASURE_CHEST);

        this.tileGroups.put("Obstacles", new ArrayList<>());
        this.tileGroups.get("Obstacles").add(TileType.GROUND);
        this.tileGroups.get("Obstacles").add(TileType.WALL);
        this.tileGroups.get("Obstacles").add(TileType.DOOR);
        this.tileGroups.get("Obstacles").add(TileType.BOX_SPAWNER);
        this.tileGroups.get("Obstacles").add(TileType.BONES_SPAWNER);
    }
}
