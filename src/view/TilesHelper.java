package view;

import model.editor.TileType;

public class TilesHelper {
    public static String getTileName(TileType tile) {
        return switch (tile) {
            case POTION_LIFE -> "Life Potion";
            case POTION_POISON -> "Poison Potion";
            case POTION_SPEED -> "Speed Potion";
            case POTION_MAGIC -> "Magic Potion";
            case POTION_PHYSICAL -> "Physical Potion";
            case POTION_DEFENSE -> "Defense Potion";

            case KEY -> "Key";
            case CHICKEN -> "Chicken";
            case BOMB -> "Bomb";
            case TREASURE_CHEST -> "Treasure Chest";

            case GROUND -> "Ground";
            case WALL -> "Wall";
            case DOOR -> "Door";
            case EXIT -> "Exit";

            case GHOST_SPAWNER_1 -> "Ghost Spawner (Level 1)";
            case GHOST_SPAWNER_2 -> "Ghost Spawner (Level 2)";
            case GHOST_SPAWNER_3 -> "Ghost Spawner (Level 3)";
            case OTHER_SPAWNER_1 -> "Others Spawner (Level 1)";
            case OTHER_SPAWNER_2 -> "Others Spawner (Level 2)";
            case OTHER_SPAWNER_3 -> "Others Spawner (Level 3)";

            case PLAYER_START_1 -> "Player 1 Start";
            case PLAYER_START_2 -> "Player 2 Start";
            case PLAYER_START_3 -> "Player 3 Start";
            case PLAYER_START_4 -> "Player 4 Start";
        };
    }
}