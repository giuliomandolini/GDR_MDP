package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.interactables.Interactable;

import java.util.Set;

/// This class determine how the interactable objects interact with the player (under witch circumstances)
/// and makes them interact if needed. <br>
/// An interactable interacts with the player if it collides with it.
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
