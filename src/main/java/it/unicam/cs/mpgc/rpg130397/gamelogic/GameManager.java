package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Updatable;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/// Class that contains the instructions for general game functioning.
/// the update gets called directly from GameController and it spreads it to all the logic that
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
        //needed to avoid ConcurrentModificationException: there might be enemies or bullets that get removed during the update cycle.
        List<GameObject> allGameObjects = new LinkedList<>(GameData.getAllGameObjects().values().stream().flatMap(Collection::stream).toList());
        for(GameObject g : allGameObjects)
        {
            if(g instanceof Updatable)
                ((Updatable)g).update();
        }
    }

    public static void lose() throws IOException {

        JDeserializer.saveInventory();
        JDeserializer.saveWeaponLevels();

        GameController.lose();
    }

}
