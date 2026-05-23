package it.unicam.cs.mpgc.rpg130397.views;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;

public class PlayerView extends GameObjectView {
    Player player;

    public PlayerView(Player p) {
        player = p;
        super(p);
        setLayoutX(GameController.SCREENWIDTH / 2);
        setLayoutY(GameController.SCREENHEIGHT / 2);
    }

    public void update()
    {
        //TODO animations, effects, ecc
    }

    public Player getObject() {
        return player;
    }
}
