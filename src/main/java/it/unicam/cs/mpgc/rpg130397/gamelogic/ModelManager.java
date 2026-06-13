package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.Updatable;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.interactables.Interactable;

import java.util.LinkedList;
import java.util.List;

/// This class updates the GameObjects that are inside the views in the scene, stored in GameData.
//It is necessary to maintain the responsabilities clean for GameManager
public class ModelManager {
    /// Updates all the models inside the game scene. <br>
    /// If they are updatable, they update
    /// If they are interactable, they try to interact
    public static void update()
    {
        //needed to avoid ConcurrentModificationException: there might be enemies or bullets that get removed during the update cycle.
        List<GameObject> allGameObjects = new LinkedList<>(GameData.getAllGameObjects());
        for(GameObject g : allGameObjects)
        {
            if(g instanceof Updatable u) //The use of pattern variables was an IntelliJ suggestion
                u.update();
            if(g instanceof Interactable i)
            {
                if (i.canInteract()) i.interact();
            }
        }
    }



}
