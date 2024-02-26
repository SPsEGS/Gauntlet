package model.game.tile.walkable.potion;

import model.game.entity.player.Player;

public class PotionSpeed extends Potion
{
    private static final int DEFAULT_BUFF = 0; /* TODO */

    public PotionSpeed () {
        super(DEFAULT_BUFF);
    }

    public void walkEffect (Player p) {
        p.addBuffSpeed(this.getBUFF());
    }
}
