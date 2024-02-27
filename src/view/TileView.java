package view;

import model.editor.TileType;
import javafx.scene.layout.StackPane;

/**
 * A class to display a tile
 */
public class TileView extends StackPane {
    /** The ImageView displaying the foreground tile (item, wall, etc.). Only initialized if a foreground tile is present. */
    private TextureView foreground;

    /** The ImageView displaying the background tile (ground / floor). Always initialized with an image.*/
    private TextureView background;

    /**
     * Constructor to create a TileDisplay with a background and a foreground.
     * @param foregroundTile The type of tile in the foreground
     */
    public TileView(TileType foregroundTile) {
        createForeground(foregroundTile);
        createBackground();

        this.getChildren().addAll(this.background, this.foreground);
    }

    /**
     * Initialize the foreground ImageView with the chosen TileType's texture.
     * @param tile The type of tile to put in the foreground
     */
    private void createForeground(TileType tile) {
        this.foreground = new TextureView(tile, 0);
    }

    /**
     * Initialize the background ImageView with the ground texture
     */
    private void createBackground() {
        // FIXME: Hard-coded ground value.
        this.background = new TextureView(TileType.GROUND);
    }

    /**
     * Sets the tile to display
     * @param tile The type of tile to display
     */
    public void setTile(TileType tile) {
        this.getChildren().remove(this.foreground);
        createForeground(tile);
        this.getChildren().add(this.foreground);
    }
}
