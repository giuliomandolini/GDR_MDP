package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.Updatable;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/// Class that contains the instructions for general game functioning.
/// the update gets called directly from GameController and it spreads it to all the logic that
/// needs to be updated.
public class GameManager {

    public static void setupGameLogic() throws FileNotFoundException {

        GameData.start();
        InputManager.start();
    }

    /// Updates all the game logic
    public static void update()
    {
        SpawnSystem.update();
        CollisionSystem.checkForCollisions();
        ModelManager.update();
        CombatSystem.update();
    }

    /// Happens when the player hp get <= 0
    public static void lose() throws IOException {
        JsonManager.saveInventory();
        JsonManager.saveWeaponLevels();

        GameController.lose();
    }

}
