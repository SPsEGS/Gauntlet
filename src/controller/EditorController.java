package controller;

import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import model.editor.*;
import view.TexturesHelper;
import view.TileDisplay;
import javafx.application.Platform;
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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import view.TilesHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The controller for the main view of the Editor.
 */
public class EditorController {
    @FXML
    private Pane root;

    /** The scroll pane used to navigate the map's. Contains the map's grid pane. */
    @FXML
    private ScrollPane scroll;

    /** The grid on which the game map is shown */
    @FXML
    private GridPane grid;

    /** The TabPane containing the groups of buttons to select a tile */
    @FXML
    private TabPane tileSelect;

    /** The button to change the theme */
    @FXML
    private Button themeButton;

    /** The different values of tiles that can be selected, organized in groups to be shown in different tabs */
    private final Map<String, List<TileType>> tileGroups = new HashMap<>();

    /** The type of tile that has been selected (to be placed upon a click). Ground by default to avoid it being null. */
    private TileType selectedTileType = TileType.GROUND;

    private GameMap map;
    private String filename = null;

    /**
     * Sets up the editor's main scene.
     * Creates and adds all dynamically created UI elements to the editor (tile selection, map grid).
     */
    @FXML
    public void initialize() {
        root.setOnDragDetected(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                root.startFullDrag();
            }
        });
        initTileGroups();
        setupTileSelect();

        GameMap map = new GameMap(20, 20);

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

                Button button = new Button(TilesHelper.getTileName(tile), imageView);
                button.addEventHandler(ActionEvent.ACTION, event -> selectedTileType = tile);

                hBox.getChildren().add(button);
            }

            t.setContent(hBox);
            tileSelect.getTabs().add(t);
        }
    }

    private void setTile(int x, int y, TileDisplay tileDisplay) {
        if(this.selectedTileType != null) {
            tileDisplay.setTile(this.selectedTileType);
            map.setTile(x, y, this.selectedTileType);
        }
    }

    private TileDisplay createTileDisplay(TileType currentTile, int x, int y) {
        TileDisplay tileDisplay = new TileDisplay(currentTile);

        // Place one tile when clicking
        tileDisplay.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                setTile(x, y, tileDisplay);
            }
        });

        // Place several tiles when dragging
        tileDisplay.setOnMouseDragEntered( event -> {
            event.consume();
            setTile(x, y, tileDisplay);
        });

        return tileDisplay;
    }

    /**
     * Loads a GameMap into the editor grid
     * @param map The map to display
     */
    public void loadMap(GameMap map) {
        for(int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
                // Create final variables to be able to access them in the anonymous EventHandler
                final TileType currentTile = map.getTile(x, y);

                TileDisplay tileDisplay = createTileDisplay(currentTile, x, y);

                grid.add(tileDisplay, x, y);
            }
        }

        this.map = map;
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

        this.tileGroups.put("Spawners", new ArrayList<>());
        this.tileGroups.get("Spawners").add(TileType.GHOST_SPAWNER_1);
        this.tileGroups.get("Spawners").add(TileType.GHOST_SPAWNER_2);
        this.tileGroups.get("Spawners").add(TileType.GHOST_SPAWNER_3);
        this.tileGroups.get("Spawners").add(TileType.OTHER_SPAWNER_1);
        this.tileGroups.get("Spawners").add(TileType.OTHER_SPAWNER_2);
        this.tileGroups.get("Spawners").add(TileType.OTHER_SPAWNER_3);

        this.tileGroups.put("Players", new ArrayList<>());
        this.tileGroups.get("Players").add(TileType.PLAYER_START_1);
        this.tileGroups.get("Players").add(TileType.PLAYER_START_2);
        this.tileGroups.get("Players").add(TileType.PLAYER_START_3);
        this.tileGroups.get("Players").add(TileType.PLAYER_START_4);
    }

    public void onNew() {
        // FIXME: Add a modal dialog to ask for user confirmation
        this.filename = null;
        this.loadMap(new GameMap(20, 20));
    }

    public void onSave() {
        if (this.filename == null)
            this.onSaveAs();

        FileSaver saver = new FileSaver(this.map, this.filename);
        saver.saveBinary();
    }

    public void onSaveAs() {
        FileChooser dialog = new FileChooser();
        dialog.setTitle("Save map as");
        dialog.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Gauntlet binary map file", "*" + FileProperties.getFileExtensionBinary())
        );

        File file = dialog.showSaveDialog(null);
        if (file == null)
            return;

        this.filename = file.getName();
        this.onSave();
    }

    public void onOpen() {
        FileChooser dialog = new FileChooser();
        dialog.setTitle("Open a map file");
        dialog.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Gauntlet binary map file", "*" + FileProperties.getFileExtensionBinary())
        );

        File file = dialog.showOpenDialog(null);
        if (file == null)
            return;

        this.filename = file.getName();

        FileLoader loader = new FileLoader(this.filename);
        GameMap map = loader.loadBinary();
        this.loadMap(map);
    }

    public void onShowShortcuts() {
        System.out.println("do something");
    }

    public void onQuit() {
        // FIXME: Maybe replace this with a change of scene (geometry dash reference????????)
        Platform.exit();
    }

    private void setScrollEventFilters() {
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

        // FIXME: This literally disables all other mouse events so I can't properly place tiles.
        // This one is to disable the "moving" mouse icon when not clicking the middle mouse button
        /*this.scroll.addEventFilter(MouseEvent.DRAG_DETECTED, new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (!event.isMiddleButtonDown())
                    event.consume();
            }
        });*/
    }
}