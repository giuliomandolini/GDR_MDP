package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.util.*;
import java.util.stream.Collectors;

/// Updates enemies and bullets for what concerns combat, and every damaging action must pass trough this class.
public class CombatSystem {

    public static void update()
    {
        updateBullets();
        meleeAttacks();
        enemiesRangedAttack();
    }

    private static void updateBullets()
    {
        //Enemy bullets
        Set<Bullet> bullets = CollisionSystem.getPlayerCollisions(Bullet.class);

        for(Bullet b : bullets)
        {
            damage(GameData.getPlayer(), (b.getDamage()));
            GameData.removeBullet(b);
        }

        //Player bullets
        Map<Enemy, List<Bullet>> coll = CollisionSystem.getEnemyBulletCollisions();
        for(Enemy e : coll.keySet())
        {
            for(Bullet b : coll.get(e))
            {
                if(b.getArea() > 0)
                {
                    List<Enemy> toDamage = new LinkedList<>(GameData.getEnemies());
                    for(Enemy enemyToDamage : toDamage)
                    {
                        if(enemyToDamage.getPosition().distanceFrom(b.getPosition()) <= b.getArea())
                            damage(enemyToDamage, b.getDamage());
                    }
                }
                else
                {
                    damage(e, b.getDamage());
                }
                GameData.removeBullet(b);
            }
        }
    }

    private static void meleeAttacks()
    {
        //Player melee
        Set<Enemy> collisions = CollisionSystem.getPlayerCollisions(Enemy.class);

        if(collisions.isEmpty()) return;

        Enemy first = (Enemy) collisions.toArray()[0];

        Weapon playerStrenghtWeapon = GameData.getPlayer().getInventory().get(Characteristics.CharacteristicType.STRENGTH);

        if(playerStrenghtWeapon != null && playerStrenghtWeapon.meleeAttack())
        {
            damage(first, playerStrenghtWeapon.getDamage());
        }

        //Enemy melee
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
        for(Enemy e : GameData.getEnemies())
        {
            if(e.getRange() > 0 && e.canAttack())
            {
                //enemy spawn bullets without area damage firing towards the player
                Bullet newBullet = new Bullet(e.getBullet(), e.getDamage(), e, GameData.getPlayer().getPosition(), e.getRange(), 0);
                GameData.addBullet(newBullet);
                e.setLastAttack();
            }
        }
    }


    public static void damage(Entity target, float amount)
    {
        target.changeHealth(-amount);
    }
}
