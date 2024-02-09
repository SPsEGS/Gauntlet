package com.teamf5.gauntlet.Model.Editor;

/**
 * Represents a game map as an array of tiles.
 */
public class GameMap {
    private final TileType[][] TILE_ARRAY;
    private final int WIDTH;
    private final int HEIGHT;

    /**
     * Constructs a new GameMap filled with a certain tile type.
     * @param width The width of the map.
     * @param height The height of the map.
     * @param fillType The tile the map will be filled with.
     */
    public GameMap(int width, int height, TileType fillType) {
        this.TILE_ARRAY = new TileType[width][height];
        this.WIDTH = width;
        this.HEIGHT = height;

        for (int y = 0; y < this.HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                this.TILE_ARRAY[x][y] = fillType;
            }
        }
    }

    /**
     * Constructs a new GameMap filled with a certain tile type.
     * @param width The width of the map.
     * @param height The height of the map.
     */
    public GameMap(int width, int height) {
        this(width, height, TileType.GROUND);
    }

    /**
     * Sets the tile type at a position.
     * @param x The x position of the tile.
     * @param y The y position of the tile.
     * @param tile The tile to set.
     */
    public void setTile(int x, int y, TileType tile) {
        this.TILE_ARRAY[x][y] = tile;
    }

    /**
     * Returns the tile at a position.
     * @param x The x position to get.
     * @param y The y position to get.
     * @return The tile type at position (x, y)
     */
    public TileType getTile(int x, int y) {
        return this.TILE_ARRAY[x][y];
    }

    /**
     * Returns the tile at a position as an int.
     * @param x The x position to get.
     * @param y The y position to get.
     * @return The tile type at position (x, y)
     */
    public int getTileAsInt(int x, int y) {
        return this.TILE_ARRAY[x][y].ordinal();
    }

    /**
     * Returns the width of the map.
     * @return The width of the map.
     */
    public int getWidth() {
        return this.WIDTH;
    }

    /**
     * Returns the height of the map.
     * @return The height of the map.
     */
    public int getHeight() {
        return this.HEIGHT;
    }

    @Override
    public String toString() {
        String str = "";

        for (int y = 0; y < this.HEIGHT; y++) {
            str += "(";
            for (int x = 0; x < this.WIDTH; x++) {
                str += String.valueOf(this.TILE_ARRAY[x][y]);
                if (x < this.WIDTH - 1)
                    str += ", ";
            }
            str += ")\n";
        }

        return str;
    }
}
