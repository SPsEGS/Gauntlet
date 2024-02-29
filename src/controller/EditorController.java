package controller;

import model.editor.*;
import components.ZoomableScrollPane;
import view.TileConnexions;
import view.TileView;
import view.TilesHelper;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javafx.fxml.FXML;

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
    private ZoomableScrollPane scroll;

    /** The grid on which the game map is shown */
    //@FXML
    private GridPane grid;

    @FXML
    private AnchorPane anchor;

    @FXML
    private BorderPane zoom;

    /** The TabPane containing the groups of buttons to select a tile */
    @FXML
    private TabPane tileSelect;

    /** The button to change the theme */
    @FXML
    private Button themeButton;

    /** The button to zoom in */
    @FXML
    private Button zoomPlus;

    /** The button to zoom out */
    @FXML
    private Button zoomMinus;

    /** The different values of tiles that can be selected, organized in groups to be shown in different tabs */
    private final Map<String, List<TileType>> tileGroups = new HashMap<>();

    /** The type of tile that has been selected (to be placed upon a click). Ground by default to avoid it being null. */
    private TileType selectedTileType = TileType.GROUND;

    /** The button representing the currently selected tile. */
    private Button selectedTileButton;

    /** The map that is currently being edited. */
    private GameMap map;

    @FXML
    private AnchorPane popUp;

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

        initZoom();

        initTileGroups();
        setupTileSelect();

        GameMap map = new GameMap(20, 20);

        loadMap(map);

        this.setScrollEventFilters();
    }

    /**
     * Increase the scale (zoom factor) of the grid that displays the map.
     */
    @FXML
    public void zoomMore() {
        this.grid.setScaleX(this.grid.getScaleX() * 1.90);
        this.grid.setScaleY(this.grid.getScaleY() * 1.90);
    }

    /**
     * Increase the scale (zoom factor) of the grid that displays the map.
     */
    @FXML
    public void zoomLess() {
        this.grid.setScaleX(this.grid.getScaleX() * 0.53);
        this.grid.setScaleY(this.grid.getScaleY() * 0.53);
    }

    private void initZoom() {
        this.grid = new GridPane();

        BorderPane border = new BorderPane();

        border.setCenter(this.grid);

        border.setBottom(createRegion(200.0,200.0));
        border.setLeft(createRegion(100.0,100.0));
        border.setRight(createRegion(100.0,100.0));
        border.setTop(createRegion(80.0,80.0));

        this.scroll = new ZoomableScrollPane(border);
        this.scroll.setPannable(true);
        this.scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        this.scroll.getStyleClass().add("background");

        this.zoom.setCenter(this.scroll);

        this.scroll.prefWidthProperty().bind(this.root.widthProperty());
        this.scroll.prefHeightProperty().bind(this.root.heightProperty());
    }

    private Region createRegion(double width, double height) {
        Region spring = new Region();
        spring.setMinSize(width, height);
        spring.setMaxSize(width, height);
        return spring;
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

                Button button = new Button();
                button.setGraphic(tileView);

                Tooltip buttonToolTip = new Tooltip(TilesHelper.getTileName(tile));
                buttonToolTip.setShowDelay(new Duration(0));

                button.setTooltip(buttonToolTip);

                button.addEventHandler(ActionEvent.ACTION, event -> {
                    // Remove the styleClass to highlight the selected tile's button.
                    this.selectedTileButton.getStyleClass().remove("selected-tile-button");

                    selectedTileType = tile;

                    // Add the styleClass to highlight the selected tile's button.
                    this.selectedTileButton = button;
                    button.getStyleClass().add("selected-tile-button");
                });

                hBox.getChildren().add(button);

                // Initialize the value of selectedTileButton
                if(tile == TileType.GROUND)
                    selectedTileButton = button;
            }

            t.setContent(hBox);
            tileSelect.getTabs().add(t);
            hBox.getStyleClass().add("tabContent");
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

        updateTileConnexions(x, y);
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
        this.map = map;

        this.tileViews = new TileView[map.getWidth()][map.getHeight()];

        // For every tile in the map
        for(int y = 0; y < map.getHeight(); y++) {
            for(int x = 0; x < map.getWidth(); x++) {
                TileView tileView = createTileView(map.getTile(x, y), x, y);

                // Store the TileView in a 2D array, so we can access it later.
                // Because we cannot grab an element from the GridPane directly.
                // There is no duplication because objects in Java are references.
                tileViews[x][y] = tileView;

                grid.add(tileView, x, y);
            }
        }

        for(int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                // This has to run after all the tiles and TileViews have been initialised.
                updateTileConnexions(x, y);
            }
        }
    }

    /**
     * Finds the four neighbours of a TileView to update in order to show the correct connected textures.
     * Then calls the method to change the frame to the correct one on the selected tile and its neighbours.
     * @param x The x coordinate of the TileView to update.
     * @param y The y coordinate of the TileView to update.
     */
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

    /**
     * Changes the frame of a TileView to connect with its neighbours.
     * @param x The x coordinate of the TileView to update.
     * @param y The y coordinate of the TileView to update.
     */
    private void updateTileFrame(int x, int y) {
        TileType tile = map.getTile(x, y);

        if (TileConnexions.isConnectedTile(this.map.getTile(x, y))) {

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

            if(tile == TileType.DOOR)
                tileViews[x][y].setFrame(connexions.getIndex() - 1);
            if(tile == TileType.WALL)
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
        this.popUp.setVisible(true);
    }

    public void createNew() {
        this.filename = null;
        this.loadMap(new GameMap(20, 20));
        this.popUp.setVisible(false);
    }

    public void onCancel() {
        this.popUp.setVisible(false);
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