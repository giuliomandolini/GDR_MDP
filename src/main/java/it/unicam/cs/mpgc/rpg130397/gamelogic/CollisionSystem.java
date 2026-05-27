package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.views.BulletView;
import it.unicam.cs.mpgc.rpg130397.views.EnemyView;
import it.unicam.cs.mpgc.rpg130397.views.GameObjectView;
import it.unicam.cs.mpgc.rpg130397.views.PlayerView;

import java.util.*;
import java.util.stream.Collectors;

/// Manages and stores collisions in the scene. in particular: Player and enemies, Bullets and Player, and Bullets and Enemies.
public class CollisionSystem {

    private static Set<GameObject> playerCollisions;
    private static Map<Enemy, List<Bullet>> enemyBulletCollisions;

    public static void resetCollisions()
    {
        playerCollisions = new HashSet<>();
        enemyBulletCollisions = new HashMap<>();
    }

    public static void checkForCollisions(List<BulletView> bullets, List<EnemyView> enemies, PlayerView player) {
        resetCollisions();

        //collisions between player and enemies
        for(EnemyView e : enemies)
        {
            if(collision(e, player)) playerCollisions.add(e.getObject());
        }

        //collisions between bullets and (enemies and player)
        for(BulletView b : bullets)
        {
            //if the bullet is instantiated by an enemy, it has to check if it collides with the player, and vice versa
            if(b.getObject().getSpawner() instanceof Enemy)
            {
                if(collision(b, player)){
                    playerCollisions.add(b.getObject());
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

    public static Set<GameObject> getPlayerCollisions() {
        return playerCollisions;
    }

    public static <T extends GameObject> Set<T> getPlayerCollisions(Class<T> type)
    {
        return playerCollisions.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toSet());
    }

    public static Map<Enemy, List<Bullet>> getEnemyBulletCollisions() {
        return enemyBulletCollisions;
    }
}
