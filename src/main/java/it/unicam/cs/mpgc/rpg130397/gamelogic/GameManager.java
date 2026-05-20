package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.views.BulletView;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;

import java.util.List;

/// Class that contains the instructions for general game functioning and utility methods
public class GameManager {

    public static void update(List<BulletView> bullets, List<EnemyView> enemies, PlayerView player)
    {
        EnemySpawnSystem.spawnEnemies();
        updateModels();

        CollisionSystem.checkForCollisions(bullets, enemies, player);
        CombatSystem.update();
    }


    public static void updateModels()
    {
        for(Enemy e : GameData.getEnemies())
        {
            e.update();
        }
        for(Bullet b : GameData.getBullets())
        {
            b.update();
        }
        GameData.getPlayer().update();
    }
}
