package model.game.tile.walkable.potion;

import model.game.entity.player.Player;

public class PotionPoison extends Potion {
    private static final int DEFAULT_BUFF = 0; /* TODO */

    public PotionPoison () {
        super(DEFAULT_BUFF);
    }

    public void walkEffect (Player p) {
        p.decreaseHp(this.getBUFF());
    }
}
