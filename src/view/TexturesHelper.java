package view;

import model.editor.TileType;
import javafx.scene.image.Image;

/**
 * Contains static methods to deal with the game's textures
 */
public final class TexturesHelper {

    /**
     * We explicitly define a private constructor to prevent the class from being instanced since it is never needed, by design.
     */
    private TexturesHelper() {}

    /**
     * Gets the image corresponding to the given tile
     * @param tile The tile to find the texture of
     * @return An image of the chosen tile's texture
     */
    public static Image getTileImage(TileType tile) {
        String address;

        switch(tile) {
            case POTION_LIFE:
                address = "/textures/Tiles/Potions/potion_life.png";
                break;
            case POTION_POISON:
                address = "/textures/Tiles/Potions/potion_poison.png";
                break;
            case POTION_SPEED:
                address = "/textures/Tiles/Potions/potion_speed.png";
                break;
            case POTION_MAGIC:
                address = "/textures/Tiles/Potions/potion_magic.png";
                break;
            case POTION_PHYSICAL:
                address = "/textures/Tiles/Potions/potion_physical.png";
                break;
            case POTION_DEFENSE:
                address = "/textures/Tiles/Potions/potion_defense.png";
                break;
            case KEY:
                address = "/textures/Items/key.png";
                break;
            case CHICKEN:
                address = "/textures/Items/food.png";
                break;
            case BOMB:
                address = "/textures/Items/smart_bomb.png";
                break;
            case TREASURE_CHEST:
                address = "/textures/Items/treasure.png";
                break;
            case GROUND:
                address = "/textures/Tiles/Environment/floor.png";
                break;
            case WALL:
                address = "/textures/Tiles/Environment/wall.png";
                break;
            case DOOR:
                address = "/textures/Tiles/Environment/door.png";
                break;
            case BONES_SPAWNER:
                address = "/textures/Tiles/Spawners/spawner_ghost.png";
                break;
            case BOX_SPAWNER:
                address = "/textures/Tiles/Spawners/spawner_grunt.png";
                break;
            case EXIT:
                address = "/textures/Tiles/Environment/exit.png";
                break;
            default:
                address = "bomb_animation"; // TODO: This is a placeholder, add an error texture?
        }

        return new Image(TexturesHelper.class.getResource(address).toExternalForm());
    }
}
