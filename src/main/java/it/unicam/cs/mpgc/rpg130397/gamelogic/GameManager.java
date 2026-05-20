package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.views.BulletView;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;

import java.util.LinkedList;
import java.util.List;

/// Class that contains the instructions for general game functioning and utility methods
public class GameManager {

    public static void update(List<BulletView> bullets, List<EnemyView> enemies, PlayerView player)
    {
        EnemySpawnSystem.spawnEnemies();
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
}
