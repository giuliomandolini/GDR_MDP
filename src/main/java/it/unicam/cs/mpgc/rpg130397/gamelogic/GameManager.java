package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.views.BulletView;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/// Class that contains the instructions for general game functioning.
/// the update gets called directly from GameController and it spreads it to all the remaining logic that
/// needs to be updated.
public class GameManager {

    public static void update(List<BulletView> bullets, List<EnemyView> enemies, PlayerView player)
    {
        EnemySpawnSystem.spawnEnemies();
        EnemySpawnSystem.relocateEnemies();
        CollisionSystem.checkForCollisions(bullets, enemies, player);
        updateModels();
        CombatSystem.update();
    }


    public static void updateModels()
    {
        for(Enemy e : GameData.getEnemies())
        {
            e.update();
        }
        //another list is necessary to avoid concurrentModificationException
        List<Bullet> toUpdate = new LinkedList<>(GameData.getBullets());
        for(Bullet b : toUpdate)
        {
            b.update();
        }
        GameData.getPlayer().update();
    }

    public static void lose() throws IOException {
        JDeserializer.saveInventory(GameData.getPlayer().getInventory());
        GameController.lose();

    }

}
