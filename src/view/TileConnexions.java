package view;

import model.editor.TileType;

/**
 * Represents all the connexions a tile has with adjacent tiles of the same type.
 * Used to show connected textures for doors and walls.
 */
public class TileConnexions {
    /** Whether the tile to the left is of the same type as the current tile */
    private boolean left = false;

    /** Whether the tile below is of the same type as the current tile */
    private boolean bottom = false;

    /** Whether the tile to the right is of the same type as the current tile */
    private boolean right = false;

    /** Whether the tile above is of the same type as the current tile */
    private boolean top = false;

    /**
     * Calculates the index of the frame to use when displaying the tile, depending on its connexions
     * @return The number of the frame to display
     */
    public int getIndex() {
        int leftInt = booleanToInt(left);
        int bottomInt = booleanToInt(bottom);
        int rightInt = booleanToInt(right);
        int topInt = booleanToInt(top);

        if (left || bottom || top || right) {
            return leftInt * 0b1000 + bottomInt * 0b0100 + rightInt * 0b0010 + topInt;
        }
        else {
            return 15;
        }
    }

    /**
     * Set whether the tile is connected to the left.
     * @param left Whether the tile is connected to the left.
     */
    public void setLeft(boolean left) {
        this.left = left;
    }

    /**
     * Set whether the tile is connected to the bottom.
     * @param bottom Whether the tile is connected to the bottom.
     */
    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    /**
     * Set whether the tile is connected to the right.
     * @param right Whether the tile is connected to the right.
     */
    public void setRight(boolean right) {
        this.right = right;
    }

    /**
     * Set whether the tile is connected to the top.
     * @param top Whether the tile is connected to the top.
     */
    public void setTop(boolean top) {
        this.top = top;
    }

    /**
     * Checks if a tile type has connected textures or not.
     * @param tile The type of tile to check.
     * @return Whether the tile type has connected textures.
     */
    public static boolean isConnectedTile(TileType tile) {
        return switch (tile) {
            case WALL, DOOR -> true;
            default -> false;
        };
    }

    /**
     * Converts a boolean to an int.
     * @param b The boolean to convert.
     * @return 1 if the boolean is true, 0 if it's false.
     */
    private static int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

}
