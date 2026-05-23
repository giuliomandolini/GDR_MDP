package it.unicam.cs.mpgc.rpg130397.utils;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

/// Needed to convert a world point into a screen point and vice versa, because the fact that the player has to remain at the center of the screen
/// makes the other entities rendered different
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
