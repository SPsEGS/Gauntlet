package model.editor;

/**
 * Defines the different types of tiles.
 */
public enum TileType {
    // Potions
    POTION_LIFE,
    POTION_POISON,
    POTION_SPEED,
    POTION_MAGIC,
    POTION_PHYSICAL,
    POTION_DEFENSE,

    // Items
    KEY,
    CHICKEN,
    BOMB,
    TREASURE_CHEST,

    // Other tiles,
    GROUND,
    WALL,
    DOOR,
    EXIT,

    // Spawners
    GHOST_SPAWNER_1,
    GHOST_SPAWNER_2,
    GHOST_SPAWNER_3,
    OTHER_SPAWNER_1,
    OTHER_SPAWNER_2,
    OTHER_SPAWNER_3,

    // Player starts
    PLAYER_START_1,
    PLAYER_START_2,
    PLAYER_START_3,
    PLAYER_START_4
}
