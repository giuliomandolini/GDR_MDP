package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

import java.util.*;

/// Updates enemies and bullets for what concerns combat, and every damaging action must pass trough this class.
public class CombatSystem {

    /// Checks for all the bullets collisions and interactions with the player and the enemies and the enemies attacks.
    public static void update()
    {
        updateBullets();
        enemyMeleeAttacks();
        enemiesRangedAttack();
    }

    //Updates all the bullets in the scene and manages their combat and damage
    private static void updateBullets()
    {
        //Enemy bullets
        Set<Bullet> bullets = CollisionSystem.getPlayerCollisions(Bullet.class);

        for(Bullet b : bullets)
        {
            if(b.getSpawner() instanceof Enemy)
            {
                damage(GameData.getPlayer(), (b.getDamage()));
                GameData.removeGameObject(b);
            }
        }

        //Player bullets
        Map<Bullet, Enemy> coll = CollisionSystem.getBulletEnemyCollisions();
        for(Bullet b : coll.keySet())
        {
            if(b.getArea() > 0)
            {
                List<Enemy> toDamage = new LinkedList<>(GameData.getGameObjectsOfType(Enemy.class));
                for(Enemy enemyToDamage : toDamage)
                {
                    if(enemyToDamage.getPosition().distanceFrom(b.getPosition()) <= b.getArea())
                        damage(enemyToDamage, b.getDamage());
                }
            }
            else
            {
                damage(coll.get(b), b.getDamage());
            }
            GameData.removeGameObject(b);

        }
    }

    private static void enemyMeleeAttacks()
    {
        Set<Enemy> collisions = CollisionSystem.getPlayerCollisions(Enemy.class);
        for(Enemy e : collisions)
        {
            if(e.canAttack())
            {
                damage(GameData.getPlayer(), e.getDamage());
                e.setLastAttack();
            }
        }
    }

    private static void enemiesRangedAttack()
    {
        for(Enemy e : GameData.getGameObjectsOfType(Enemy.class))
        {
            if(e.getRange() > 0 && e.canAttack())
            {
                //enemy spawn bullets without area damage firing towards the player
                SpawnSystem.createBullet(e.getBullet(), e.getDamage(), e, GameData.getPlayer().getPosition(), e.getRange(), 0);
                e.setLastAttack();
            }
        }
    }


    public static void damage(Entity target, float amount)
    {
        target.changeHealth(-amount);
    }
}
