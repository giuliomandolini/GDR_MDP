package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Updatable;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/// Class that contains the instructions for general game functioning.
/// the update gets called directly from GameController and it spreads it to all the remaining logic that
/// needs to be updated.
public class GameManager {

    public static void update()
    {
        SpawnSystem.spawnEnemies();
        SpawnSystem.relocateEnemies();
        CollisionSystem.checkForCollisions();
        updateModels();
        InteractablesManager.update();
        CombatSystem.update();
    }


    public static void updateModels()
    {
        for(List<GameObject> l : GameData.getAllGameObjects().values())
        {
            //needed to avoid ConcurrentModificationException: there might be enemies or bullets that get removed during the update cycle.
            List<GameObject> list = new LinkedList<>(l);
            for(GameObject g : list)
            {
                if(g instanceof Updatable)
                    ((Updatable)g).update();
            }
        }
    }

    public static void lose() throws IOException {
        JDeserializer.saveInventory(GameData.getPlayer().getInventory());
        GameController.lose();
    }

}
