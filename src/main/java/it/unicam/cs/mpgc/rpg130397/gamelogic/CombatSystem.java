package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

import java.util.*;

public class CombatSystem {

    private static Set<Enemy> playerEnemyCollisions;
    private static Set<Bullet> playerBulletCollisions;
    private static Map<Enemy, Bullet> enemyBulletCollisions;


    public static void resetCollisions()
    {
        playerEnemyCollisions = new HashSet<>();
        playerBulletCollisions = new HashSet<>();
        enemyBulletCollisions = new HashMap<>();
    }

    ///@return true if the health is <= 0, false otherwise
    public static boolean damage(Entity e, float damage)
    {
        return e.changeHealth(damage);
    }

    public static void addCollision(Enemy e)
    {
        playerEnemyCollisions.add(e);
    }
    public static void addCollision(Bullet b)
    {
        playerBulletCollisions.add(b);
    }
    public static void addCollision(Enemy e, Bullet b)
    {
        enemyBulletCollisions.put(e, b);
    }

    public static Set<Enemy> getPlayerEnemyCollisions() {
        return playerEnemyCollisions;
    }

    public static Set<Bullet> getPlayerBulletCollisions() {
        return playerBulletCollisions;
    }

    public static Map<Enemy, Bullet> getEnemyBulletCollisions() {
        return enemyBulletCollisions;
    }
}
