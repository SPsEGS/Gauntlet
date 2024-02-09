package com.teamf5.gauntlet.Model.Editor;

public class GameMap {
    private final TileType[][] TILE_ARRAY;
    private final int WIDTH;
    private final int HEIGHT;

    public GameMap(int width, int height, TileType fillType) {
        this.TILE_ARRAY = new TileType[width][height];
        this.WIDTH = width;
        this.HEIGHT = height;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.TILE_ARRAY[x][y] = fillType;
            }
        }
    }

    public GameMap(int width, int height) {
        this(width, height, TileType.GROUND);
    }

    public void setTile(int x, int y, TileType tile) {
        this.TILE_ARRAY[x][y] = tile;
    }

    public TileType getTile(int x, int y) {
        return this.TILE_ARRAY[x][y];
    }

    public int getWidth() {
        return this.WIDTH;
    }

    public int getHeight() {
        return this.HEIGHT;
    }
}
