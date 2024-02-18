package com.teamf5.gauntlet.View;

import com.teamf5.gauntlet.Model.Editor.TileType;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

// FIXME: Should this class be used for the game as well, or only the editor?
/**
 * A class to display a tile in the editor
 */
public class TileDisplay extends StackPane {
    /** The ImageView displaying the foreground tile (item, wall, etc.). Only initialized if a foreground tile is present. */
    private ImageView foreground;

    /** The ImageView displaying the background tile (ground / floor). Always initialized with an image.*/
    private ImageView background;

    /** The width of the texture (used to only show the first frame on animated / dynamic textures) */
    private final int textureWidth = 79;

    /** The height of the texture (used to only show the first frame on animated / dynamic textures) */
    private final int textureHeight = textureWidth;

    /**
     * Constructor to create a TileDisplay with a background and a foreground.
     * @param foregroundTile The type of tile in the foreground
     * @param backgroundTile The type of tile in the background
     */
    public TileDisplay(TileType foregroundTile, TileType backgroundTile) {
        createForeground(foregroundTile);
        createBackground(backgroundTile);

        this.getChildren().addAll(this.background, this.foreground);
    }

    /**
     * Constructor to create a TileDisplay with only a background
     * @param backgroundTile The type of tile in the background
     */
    public TileDisplay(TileType backgroundTile) {
        createBackground(backgroundTile);

        this.getChildren().add(this.background);
    }

    /**
     * Initialize the foreground ImageView with the chosen TileType's texture.
     * @param tile The type of tile to put in the foreground
     */
    private void createForeground(TileType tile) {
        Image foregroundImage = TexturesHelper.getTileImage(tile);
        this.foreground = new ImageView(foregroundImage);
        // Only show the first frame of the texture
        this.foreground.setViewport(new Rectangle2D(0, 0, this.textureWidth, this.textureHeight));
    }

    /**
     * Initialize the background ImageView with the chosen TileType's texture.
     * @param tile The type of tile to put in the background
     */
    private void createBackground(TileType tile) {
        Image backgroundImage = TexturesHelper.getTileImage(tile);
        this.background = new ImageView(backgroundImage);
        // Only show the first frame of the texture
        this.foreground.setViewport(new Rectangle2D(0, 0, textureWidth, textureHeight));
    }
}
