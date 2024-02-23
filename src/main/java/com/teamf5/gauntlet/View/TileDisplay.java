package com.teamf5.gauntlet.View;

import com.teamf5.gauntlet.Model.Editor.TileType;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

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
    @SuppressWarnings({"SuspiciousNameCombination", "FieldCanBeLocal"})
    private final int textureHeight = textureWidth;

    /**
     * Constructor to create a TileDisplay with a background and a foreground.
     * @param foregroundTile The type of tile in the foreground
     */
    public TileDisplay(TileType foregroundTile) {
        createForeground(foregroundTile);
        createBackground();

        this.getChildren().addAll(this.background, this.foreground);
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
     * Initialize the background ImageView with the ground texture
     */
    private void createBackground() {
        Image backgroundImage = TexturesHelper.getTileImage(TileType.GROUND);
        this.background = new ImageView(backgroundImage);
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
