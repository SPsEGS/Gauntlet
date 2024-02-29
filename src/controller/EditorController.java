package controller;

import components.ZoomableScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import model.editor.*;
import view.TileConnexions;
import view.TileView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;
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
    //@FXML
    private ScrollPane scroll;

    /** The grid on which the game map is shown */
    //@FXML
    private GridPane grid;

    @FXML
    private Pane zoom;

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

    /** The map that is currently being edited. */
    private GameMap map;

    /**
     * Holds a 2D array of every TileView that represents the map.
     * Because there is no way to get a node from x and y coordinates from a GridPane.
     */
    private TileView[][] tileViews;

    /** The name of the selected file to save or load. */
    private String filename = null;

    /**
     * Sets up the editor's main scene.
     * Creates and adds all dynamically created UI elements to the editor (tile selection, map grid).
     */
    @FXML
    public void initialize() {
        root.setOnDragDetected(event -> {
            if (event.getButton() == MouseButton.PRIMARY || event.getButton() == MouseButton.SECONDARY) {
                event.consume();
                root.startFullDrag();
            }
        });

        this.grid = new GridPane();

        this.scroll = new ZoomableScrollPane(this.grid);
        this.scroll.setPannable(true);
        this.scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scroll.setPrefSize(1280,720);
        this.scroll.getStyleClass().add("background");

        this.zoom.getChildren().add(this.scroll);

        initTileGroups();
        setupTileSelect();

        GameMap map = new GameMap(20, 20);

        loadMap(map);

        this.setScrollEventFilters();
    }

    /**
     * Sets up the tile selection pane with a button for each tile type, organized in tabs
     */
    private void setupTileSelect() {
        // Create a tab with an HBox for each tile group
        for(String group : tileGroups.keySet()) {
            Tab t = new Tab();
            t.setText(group);
            HBox hBox = new HBox();

            // For each tile in the group, create a button with the correct texture
            for(TileType tile : tileGroups.get(group)) {
                TileView tileView = new TileView(tile);

                Button button = new Button(TilesHelper.getTileName(tile), tileView);
                button.addEventHandler(ActionEvent.ACTION, event -> selectedTileType = tile);

                hBox.getChildren().add(button);
            }

            t.setContent(hBox);
            tileSelect.getTabs().add(t);
        }
    }

    /**
     * Sets the tile at the given coordinates to the given tile type.
     * @param x The x coordinate of the tile to set.
     * @param y The y coordinate of the tile to set.
     * @param tileView The TileView to update.
     */
    // FIXME: Having to give a reference to the TileView feels wrong.
    private void setTile(int x, int y, TileView tileView) {
        if(this.selectedTileType != null) {
            tileView.setTile(this.selectedTileType);
            map.setTile(x, y, this.selectedTileType);
        }

        updateTileConnexions(x, y);
    }


    /**
     * Sets the tile at the given coordinates to the GROUND tile type.
     * @param x The x coordinate of the tile to set.
     * @param y The y coordinate of the tile to set.
     * @param tileView The TileView to update.
     */
    // FIXME: Code duplication, hard-coded ground value. I just didn't find anything better for now.
    private void removeTile(int x, int y, TileView tileView) {
        tileView.setTile(TileType.GROUND);
        map.setTile(x, y, TileType.GROUND);
    }

    /**
     * Creates a TileView with the given parameters.
     * @param tile The type of tile that this TileView will hold.
     * @param x The x coordinate of the tile.
     * @param y The y coordinate of the tile.
     * @return A new TileView with the given parameters.
     */
    private TileView createTileView(TileType tile, int x, int y) {
        TileView tileView = new TileView(tile);

        // Place / remove one tile when clicking
        tileView.setOnMouseClicked(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                setTile(x, y, tileView);
            }
            else if(event.getButton() == MouseButton.SECONDARY) {
                event.consume();
                removeTile(x, y, tileView);
            }
        });

        // Place / remove tiles when dragging
        tileView.setOnMouseDragEntered(event -> {
            if(event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                setTile(x, y, tileView);
            }
            else if(event.getButton() == MouseButton.SECONDARY) {
                event.consume();
                removeTile(x, y, tileView);
            }
        });

        return tileView;
    }

    /**
     * Loads a GameMap into the editor grid
     * @param map The map to display
     */
    public void loadMap(GameMap map) {
        this.tileViews = new TileView[map.getWidth()][map.getHeight()];

        for(int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
                // Create final variables to be able to access them in the anonymous EventHandler
                final TileType currentTile = map.getTile(x, y);

                TileView tileView = createTileView(currentTile, x, y);

                tileViews[x][y] = tileView;
                grid.add(tileView, x, y);
            }
        }

        this.map = map;

        for(int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                updateTileConnexions(x, y);
            }
        }
    }

    private boolean isConnectedTile(TileType tile) {
        return switch (tile) {
            case WALL, DOOR -> true;
            default -> false;
        };
    }

    private void updateTileConnexions(int x, int y) {
        List<int[]> coordinateList = new ArrayList<>();

        coordinateList.add(new int[] {x, y});

        if(x < this.map.getWidth() - 1)
            coordinateList.add(new int[] {x + 1, y});

        if(x > 0)
            coordinateList.add(new int[] {x - 1, y});

        if(y < this.map.getHeight() - 1)
            coordinateList.add(new int[] {x, y + 1});

        if(y > 0)
            coordinateList.add(new int[] {x, y - 1});

        for(int[] coord : coordinateList) {
            updateTileFrame(coord[0], coord[1]);
        }
    }

    private void updateTileFrame(int x, int y) {
        TileType tile = map.getTile(x, y);

        if (isConnectedTile(this.map.getTile(x, y))) {

            TileConnexions connexions = new TileConnexions();

            if (x > 0 && this.map.getTile(x - 1, y) == tile) {
                connexions.setLeft(true);
            }

            if (x < this.map.getWidth() - 1 && this.map.getTile(x + 1, y) == tile) {
                connexions.setRight(true);
            }

            if (y < this.map.getHeight() - 1 && this.map.getTile(x, y + 1) == tile) {
                connexions.setBottom(true);
            }

            if (y > 0 && this.map.getTile(x, y - 1) == tile) {
                connexions.setTop(true);
            }

            tileViews[x][y].setFrame(connexions.getIndex());
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

    /** Resets the map that is currently being edited.  */
    // FIXME: Add a way to choose the size of the map.
    public void onNew() {
        // FIXME: Add a modal dialog to ask for user confirmation
        this.filename = null;
        this.loadMap(new GameMap(20, 20));
    }

    /**
     * Saves the current map to the file previously selected by the user (during the last Save As).
     * If Save As has never been run (and no file has been chosen), automatically executes Save As instead.
     */
    public void onSave() {
        if (this.filename == null)
            this.onSaveAs();

        FileSaver saver = new FileSaver(this.map, this.filename);
        saver.saveBinary();
    }

     /** Opens a file picker window and saves the current map to the file selected by the user. */
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

    /** Opens a file picker and loads the map from the file selected by the user. */
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

    // FIXME: Add documentation when you know what you're doing.
    public void onShowShortcuts() {
        System.out.println("do something");
    }

    /** Quits the editor. */
    public void onQuit() {
        // FIXME: Maybe replace this with a change of scene (geometry dash reference????????)
        Platform.exit();
    }

    /** Sets the event filters needed to properly handle panning the view with the mouse. */
    private void setScrollEventFilters() {
        // This one is to disable panning the pane with the scroll wheel
        //this.scroll.addEventFilter(ScrollEvent.ANY, Event::consume);

        // This one is to set the panning button to the middle mouse button
        this.scroll.addEventFilter(MouseEvent.MOUSE_DRAGGED, event -> {
            if (!event.isMiddleButtonDown())
                event.consume();
        });

        // FIXME: This literally disables all other mouse drag events so I can't properly place tiles.
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