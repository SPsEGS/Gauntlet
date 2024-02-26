package model.game.tile.walkable.potion;

import model.game.entity.player.Player;
import model.game.tile.walkable.WalkableTile;

public abstract class Potion extends WalkableTile {
    private final int BUFF;

    public Potion (int buff) {
        this.BUFF = buff;
    }

    public int getBUFF () {
        return this.BUFF;
    }

    public abstract void walkEffect (Player p);
}
