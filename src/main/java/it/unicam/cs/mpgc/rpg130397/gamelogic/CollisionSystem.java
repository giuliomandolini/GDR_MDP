package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.views.BulletView;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;

import java.util.*;

public class CollisionSystem {

    private static Set<Enemy> playerEnemyCollisions;
    private static Set<Bullet> playerBulletCollisions;
    private static Map<Enemy, List<Bullet>> enemyBulletCollisions;

    public static void resetCollisions()
    {
        playerEnemyCollisions = new HashSet<>();
        playerBulletCollisions = new HashSet<>();
        enemyBulletCollisions = new HashMap<>();
    }

    public static void checkForCollisions(List<BulletView> bullets, List<EnemyView> enemies, PlayerView player) {
        resetCollisions();

        //collisions between player and enemies
        for(EnemyView e : enemies)
        {
            if(collision(e, player)) playerEnemyCollisions.add(e.getObject());
        }

        //collisions between bullets and (enemies and player)
        for(BulletView b : bullets)
        {
            //if the bullet is instantiated by an enemy, it has to check if it collides with the player, and vice versa
            if(b.getObject().getSpawner() instanceof Enemy)
            {
                if(collision(b, player)){
                    playerBulletCollisions.add(b.getObject());
                }
            }
            else
            {
                for(EnemyView e : enemies)
                {
                    if(collision(b, e))
                    {
                        if(enemyBulletCollisions.get(e.getObject()) == null)
                            enemyBulletCollisions.put(e.getObject(), new ArrayList<>());
                        enemyBulletCollisions.get(e.getObject()).add(b.getObject());
                    }
                }
            }

        }
    }
    private static boolean collision(GameObjectView i1, GameObjectView i2)
    {
        return i1.getBoundsInParent().intersects(i2.getBoundsInParent());
    }

    public static Set<Enemy> getPlayerEnemyCollisions() {
        return playerEnemyCollisions;
    }

    public static Set<Bullet> getPlayerBulletCollisions() {
        return playerBulletCollisions;
    }

    public static Map<Enemy, List<Bullet>> getEnemyBulletCollisions() {
        return enemyBulletCollisions;
    }
}
