package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

import java.util.*;

/// Updates enemies and bullets for what concerns combat, and every damaging action must pass trough this class
public class CombatSystem {

    public static void update()
    {
        //Enemy bullets
        for(Bullet b : CollisionSystem.getPlayerBulletCollisions())
        {
            damage(GameData.getPlayer(), b.getDamage());
            GameData.removeBullet(b);
        }
        //Player bullets
        Map<Enemy, List<Bullet>> coll = CollisionSystem.getEnemyBulletCollisions();
        for(Enemy e : coll.keySet())
        {
            for(Bullet b : coll.get(e))
            {
                System.out.println(b.getName() + ", " + b.getArea() + ", " + b.getDamage());
                System.out.println(b.getStats().getName());
                if(b.getArea() > 0)
                {
                    System.out.println("AREA + " + b.getArea());
                    List<Enemy> toDamage = new LinkedList<>(GameData.getEnemies());
                    for(Enemy enemyToDamage : toDamage)
                    {
                        if(enemyToDamage.getPosition().distanceFrom(b.getPosition()) <= b.getArea())
                            damage(enemyToDamage, b.getDamage());
                    }
                }
                else
                {
                    System.out.println("NO AREA");
                    damage(e, b.getDamage());
                }

                System.out.println("OK");

                GameData.removeBullet(b);
            }
        }
        //Enemy melee
        for(Enemy e : CollisionSystem.getPlayerEnemyCollisions())
        {
            if(e.canAttack())
            {
                damage(GameData.getPlayer(), e.getDamage());
                e.setLastAttack();
            }
        }
    }
    public static void damage(Entity target, float amount)
    {
        target.changeHealth(-amount);
    }
}
