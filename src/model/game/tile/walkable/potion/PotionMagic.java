package model.game.tile.walkable.potion;

import model.game.entity.player.Player;

public class PotionMagic extends Potion {
    private static final int DEFAULT_BUFF = 0; /* TODO */

    public PotionMagic () {
        super(DEFAULT_BUFF);
    }

    public void walkEffect (Player p) {
        p.addBuffMagic(this.getBUFF());
    }
}
