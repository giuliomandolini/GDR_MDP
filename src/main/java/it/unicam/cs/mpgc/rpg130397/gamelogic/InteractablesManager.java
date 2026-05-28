package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.interactables.Chest;
import it.unicam.cs.mpgc.rpg130397.elements.interactables.Interactable;

import java.util.Set;

public class InteractablesManager {
    public static void update()
    {
        Set<Interactable> collisions = CollisionSystem.getPlayerCollisions(Interactable.class);

        for(Interactable i : collisions)
        {
            i.interact();
        }
    }
}
