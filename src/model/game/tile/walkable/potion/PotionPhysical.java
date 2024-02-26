package model.game.tile.walkable.potion;

import model.game.entity.player.Player;

public class PotionPhysical extends Potion {
    private static final int DEFAULT_BUFF = 0; /* TODO */

    public PotionPhysical () {
        super(DEFAULT_BUFF);
    }

    public void walkEffect (Player p) {
        p.addBuffPhysic(this.getBUFF());
    }
}
