package model.game.tile.walkable.potion;

import model.game.entity.player.Player;

public class PotionLife extends Potion {
    private static final int DEFAULT_BUFF = 0; /* TODO */

    public PotionLife() {
        super(DEFAULT_BUFF);
    }

    public void walkEffect(Player p) {
        p.increaseHp(this.getBUFF());
    }
}
