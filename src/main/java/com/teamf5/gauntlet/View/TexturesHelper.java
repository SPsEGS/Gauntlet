package com.teamf5.gauntlet.View;

import com.teamf5.gauntlet.Model.Editor.TileType;
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

        return new Image(TexturesHelper.class.getResource(address).toExternalForm());
    }
}
