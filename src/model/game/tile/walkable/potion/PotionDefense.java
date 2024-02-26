package model.game.tile.walkable.potion;

import model.game.entity.player.Player;

public class PotionDefense extends Potion {
    private static final int DEFAULT_BUFF = 0; /* TODO */

    public PotionDefense () {
        super(DEFAULT_BUFF);
    }

    public void walkEffect (Player p) {
        p.addBuffDefense(this.getBUFF());
    }
}
