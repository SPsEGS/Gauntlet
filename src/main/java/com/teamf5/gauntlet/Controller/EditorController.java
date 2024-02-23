package com.teamf5.gauntlet.Controller;

import com.teamf5.gauntlet.Model.Editor.GameMap;
import com.teamf5.gauntlet.View.TexturesHelper;
import com.teamf5.gauntlet.View.TileDisplay;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;

import com.teamf5.gauntlet.Model.Editor.TileType;
import javafx.scene.layout.HBox;

import java.util.*;

/**
 * The controller for the main view of the Editor.
 */
public class EditorController {
    /** The scroll pane used to navigate the map's. Contains the map's grid pane. */
    @FXML
    private ScrollPane scroll;

    /** The grid on which the game map is shown */
    @FXML
    private GridPane grid;

    /** The TabPane containing the groups of buttons to select a tile */
    @FXML
    private TabPane tileSelect;

    /** The button to open the menu */
    @FXML
    private Button menuButton;

    /** The button to change the theme */
    @FXML
    private Button themeButton;

    /** The different values of tiles that can be selected, organized in groups to be shown in different tabs */
    private final Map<String, List<TileType>> tileGroups = new HashMap<>();

    /** The type of tile that has been selected (to be placed upon a click) */
    private TileType selectedTileType;

    /**
     * Creates and adds all dynamically created UI elements to the editor (tile selection, map grid)
     */
    public void initialize() {
        initTileGroups();
        setupTileSelect();

        GameMap map = new GameMap(20, 20);
        map.setTile(1, 2, TileType.WALL);
        map.setTile(1, 3, TileType.WALL);

        loadMap(map);

        this.setScrollEventFilters();
    }

    /**
     * Sets up the tile selection pane with a button for each tile type, organized in tabs
     */
    // TODO: Add a label to identify the tiles (two potions have the same texture)
    private void setupTileSelect() {
        // Create a tab with an HBox for each tile group
        for(String group : tileGroups.keySet()) {
            Tab t = new Tab();
            t.setText(group);
            HBox hBox = new HBox();

            // For each tile in the group, create a button with the correct texture
            for(TileType tile : tileGroups.get(group)) {
                ImageView imageView = new ImageView(TexturesHelper.getTileImage(tile));
                // Crop the image to only show the first 80x80 pixels (some textures have several frames, only show the first)
                imageView.setViewport(new Rectangle2D(0, 0, 79, 79));

                Button button = new Button("", imageView);
                button.addEventHandler(ActionEvent.ACTION, new EventHandler<>() {
                    @Override
                    public void handle(ActionEvent event) {
                        selectedTileType = tile;
                    }
                });

                hBox.getChildren().add(button);
            }

            t.setContent(hBox);
            tileSelect.getTabs().add(t);
        }
    }

    /**
     * Loads a GameMap into the editor grid
     * @param map The map to display
     */
    public void loadMap(GameMap map) {
        for(int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
                final TileType currentTile = map.getTile(x, y);
                TileDisplay tileDisplay = new TileDisplay(currentTile);

                tileDisplay.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent event) {
                        System.out.println("Current Tile: " + selectedTileType);
                        tileDisplay.setTile(currentTile);
                    }
                });

                grid.add(tileDisplay, x, y);
            }
        }
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

    public void setScrollEventFilters() {
        // This one is to disable panning the pane with the scroll wheel
        this.scroll.addEventFilter(ScrollEvent.ANY, new EventHandler<>() {
            @Override
            public void handle(ScrollEvent event) {
                event.consume();
            }
        });
        // This one is to set the panning button to the middle mouse button
        this.scroll.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isMiddleButtonDown())
                    event.consume();
            }
        });
        // This one is to disable the "moving" mouse icon when not clicking the middle mouse button
        this.scroll.addEventFilter(MouseEvent.DRAG_DETECTED, new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isMiddleButtonDown())
                    event.consume();
            }
        });
    }
}
