package view;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.editor.TileType;

/**
 * An ImageView wrapper with specialized functions to display the game's textures.
 */
public class TextureView extends ImageView {
    /** The width of one frame of the texture */
    public final int FRAME_WIDTH = 80;

    /** The height of one frame of the texture */
    public final int FRAME_HEIGHT = 80;

    /**
     * Constructs a TextureView for a tile's texture, with no frame set.
     * @param tile The tile to display.
     * @apiNote This will display the entire texture image, even if it contains several frames.
     */
    public TextureView(TileType tile) {
        this.setImage(getTileImage(tile));
    }

    /**
     * Constructs a TextureView for a tile's texture, showing only the selected frame.
     * @param tile The tile to display.
     * @param frame The frame to display.
     */
    public TextureView(TileType tile, int frame) {
        this.setImage(getTileImage(tile));
        this.setFrame(frame);
    }

    /**
     * Changes the viewport to show the selected frame.
     * @param frame The number of the frame to display.
     */
    public void setFrame(int frame) {
        this.setViewport(new Rectangle2D(frame * FRAME_WIDTH, 0, FRAME_WIDTH, FRAME_HEIGHT));
    }

    /**
     * Gets the image corresponding to the given tile
     * @param tile The tile to find the texture of
     * @return An image of the chosen tile's texture
     */
    public static Image getTileImage(TileType tile) {
        String address = switch (tile) {
            case POTION_LIFE -> "/textures/Tiles/Potions/potion_life.png";
            case POTION_POISON -> "/textures/Tiles/Potions/potion_poison.png";
            case POTION_SPEED -> "/textures/Tiles/Potions/potion_speed.png";
            case POTION_MAGIC -> "/textures/Tiles/Potions/potion_magic.png";
            case POTION_PHYSICAL -> "/textures/Tiles/Potions/potion_physical.png";
            case POTION_DEFENSE -> "/textures/Tiles/Potions/potion_defense.png";
            case KEY -> "/textures/Items/key.png";
            case CHICKEN -> "/textures/Items/food.png";
            case BOMB -> "/textures/Items/smart_bomb.png";
            case TREASURE_CHEST -> "/textures/Items/treasure.png";
            case GROUND -> "/textures/Tiles/Environment/floor.png";
            case WALL -> "/textures/Tiles/Environment/wall.png";
            case DOOR -> "/textures/Tiles/Environment/door.png";
            case GHOST_SPAWNER_1, GHOST_SPAWNER_2, GHOST_SPAWNER_3 -> "/textures/Tiles/Spawners/spawner_ghost.png";
            case OTHER_SPAWNER_1, OTHER_SPAWNER_2, OTHER_SPAWNER_3 -> "/textures/Tiles/Spawners/spawner_grunt.png";
            case EXIT -> "/textures/Tiles/Environment/exit.png";
            // TODO: Add textures for player starts
            default -> "/textures/Effects/bomb_animation.gif"; // TODO: This is a placeholder, add an error texture?
        };

        return new Image(TextureView.class.getResource(address).toExternalForm());
    }
}
