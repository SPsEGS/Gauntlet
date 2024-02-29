package view;

public class TileConnexions {
    private boolean left = false;
    private boolean bottom = false;
    private boolean right = false;
    private boolean top = false;

    public TileConnexions() {}

    private int booleanToInt(boolean b) {
        return b ? 1 : 0;
    }

    public int getIndex() {
        int leftInt = booleanToInt(left);
        int bottomInt = booleanToInt(bottom);
        int rightInt = booleanToInt(right);
        int topInt = booleanToInt(top);

        if (left || bottom || top || right) {
            int index = leftInt * 0b1000 + bottomInt * 0b0100 + rightInt * 0b0010 + topInt;

            // Our frame numbers (the only thing that uses this value) start at 0, not 1.
            return index - 1;
        }
        else {
            return 0;
        }
    }

    public boolean getLeft() {
        return this.left;
    }

    public boolean getBottom() {
        return this.bottom;
    }

    public boolean getRight() {
        return this.right;
    }

    public boolean getTop() {
        return this.top;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setBottom(boolean bottom) {
        this.bottom = bottom;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setTop(boolean top) {
        this.top = top;
    }
}
