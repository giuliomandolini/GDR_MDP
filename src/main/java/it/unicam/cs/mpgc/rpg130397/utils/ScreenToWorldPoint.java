package it.unicam.cs.mpgc.rpg130397.utils;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

public class ScreenToWorldPoint {
    public static Position screenToWorld(Position coordinate)
    {
        return new Position((float) (coordinate.getX() + GameData.getPlayer().getPosition().getX() - GameController.SCREENWIDTH / 2),
                (float) (coordinate.getY() + GameData.getPlayer().getPosition().getY() - GameController.SCREENHEIGHT / 2));
    }
    public static Position worldToScreen(Position coordinate)
    {
        return new Position((float) (coordinate.getX() - GameData.getPlayer().getPosition().getX() + GameController.SCREENWIDTH / 2),
                (float) (coordinate.getY() - GameData.getPlayer().getPosition().getY() + GameController.SCREENHEIGHT / 2));
    }

}
