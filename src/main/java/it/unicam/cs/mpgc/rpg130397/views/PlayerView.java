package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;

public class PlayerView extends GameObjectView {
    Player player;

    public PlayerView(Player p) {
        player = p;
        super(p);
    }

    public void update()
    {
        setLayoutX(player.getPosition().getX());
        setLayoutY(player.getPosition().getY());
    }

    public Player getPlayer() {
        return player;
    }
}
