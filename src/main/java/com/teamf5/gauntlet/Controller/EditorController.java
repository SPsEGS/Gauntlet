package com.teamf5.gauntlet.Controller;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import com.teamf5.gauntlet.Model.Editor.TileType;
import javafx.scene.layout.HBox;

import java.util.*;

/**
 * The controller for the main view of the Editor.
 */
public class EditorController {
    /** The grid on which the game map is shown */
    @FXML
    private GridPane grid;

    /** The TabPane containing the groups of buttons to select a tile */
    @FXML
    private TabPane tileSelect;

    /** The different values of tiles that can be selected, organized in groups to be shown in different tabs */
    private final Map<String, List<TileType>> tileGroups = new HashMap<>();

    /** Constructor (initializes all tile groups). */
    public EditorController() {
        initTileGroups();
    }

    /**
     * Creates and adds all dynamically created UI elements to the editor (tile selection, map grid)
     */
    public void initialize() {
        // Create a tab with an HBox for each tile group
        for(String group : tileGroups.keySet()) {
            Tab t = new Tab();
            t.setText(group);
            HBox hBox = new HBox();

            // For each tile in the group, create a button with the correct texture
            for(TileType tile : tileGroups.get(group)) {
                ImageView imageView = new ImageView(getImage(tile));
                // Crop the image to only show the first 80x80 pixels (some textures have several frames, only show the first)
                imageView.setViewport(new Rectangle2D(0, 0, 79, 79));

                Button button = new Button("", imageView);
                hBox.getChildren().add(button);
            }

            t.setContent(hBox);
            tileSelect.getTabs().add(t);
        }
    }

    /**
     * Gets the image corresponding to the given tile
     * @param tile The tile to find the texture of
     * @return An image of the chosen tile's texture
     */
    private Image getImage(TileType tile) {
        String address;

        switch(tile) {
            case POTION_LIFE:
                address = "/Textures/Tiles/Potions/potion_life.png";
                break;
            case POTION_POISON:
                address = "/Textures/Tiles/Potions/potion_poison.png";
                break;
            case POTION_SPEED:
                address = "/Textures/Tiles/Potions/potion_speed.png";
                break;
            case POTION_MAGIC:
                address = "/Textures/Tiles/Potions/potion_magic.png";
                break;
            case POTION_PHYSICAL:
                address = "/Textures/Tiles/Potions/potion_physical.png";
                break;
            case POTION_DEFENSE:
                address = "/Textures/Tiles/Potions/potion_defense.png";
                break;
            case KEY:
                address = "/Textures/Items/key.png";
                break;
            case CHICKEN:
                address = "/Textures/Items/food.png";
                break;
            case BOMB:
                address = "/Textures/Items/smart_bomb.png";
                break;
            case TREASURE_CHEST:
                address = "/Textures/Items/treasure.png";
                break;
            case GROUND:
                address = "/Textures/Tiles/Environment/floor.png";
                break;
            case WALL:
                address = "/Textures/Tiles/Environment/wall.png";
                break;
            case DOOR:
                address = "/Textures/Tiles/Environment/door.png";
                break;
            case BONES_SPAWNER:
                address = "/Textures/Tiles/Spawners/spawner_ghost.png";
                break;
            case BOX_SPAWNER:
                address = "/Textures/Tiles/Spawners/spawner_grunt.png";
                break;
            case EXIT:
                address = "/Textures/Tiles/Environment/exit.png";
                break;
            default:
                address = "bomb_animation"; // TODO: This is a placeholder, add an error texture?
        }

        return new Image(getClass().getResource(address).toExternalForm());
    }

    /**
     * Sort all tile types into groups and store them in the tileGroups HashMap.
     */
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
