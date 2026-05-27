package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Interactable;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;

import java.util.List;
import java.util.Set;

public class InteractablesManager {
    public static void update()
    {
        List<Interactable> interactableSet = GameData.getInteractables();
        Set<GameObject> collisions = CollisionSystem.getPlayerCollisions();

        for(Interactable i : interactableSet)
        {
            if(collisions.contains())
        }
    }
}
