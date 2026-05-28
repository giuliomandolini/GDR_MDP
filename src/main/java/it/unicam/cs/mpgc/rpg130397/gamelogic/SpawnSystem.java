package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.controllers.GameController;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.BulletStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.utils.ScreenToWorldPoint;

import java.util.List;
import java.util.Random;

/// Manages the spawn of the enemies and their relocation: in case the player moves too far away from an enemy, it respawns it nearer.
/// It should be the only way to comunicate with GameData.addGameObject().
public class SpawnSystem {

    private static long lastSpawn;
    private static long spawnCooldown;
    private static int enemiesToSpawn;
    //In future developments the enemies will be coming in groups and not just random as it is now,
    //that is why I decided to use a map instead of a set inside GameData
    private static List<Enemy> possibleEnemies;
    private static int idCounter;


    public static void start()
    {
        possibleEnemies = GameData.getEnemiesMap().values().stream().toList();
        enemiesToSpawn = 2;
        idCounter = 0;
    }

    public static void spawnEnemies()
    {
        if(GameData.getGameObjectsOfType(Enemy.class).size() > 60)
        {
            spawnCooldown = 600;
        }
        else {
            spawnCooldown = 100;
        }
        if(lastSpawn + spawnCooldown < System.currentTimeMillis())
        {
            for (int i = 0; i < enemiesToSpawn; i++) {
                Position spawnPoint = getRandomPosition();
                Enemy base = possibleEnemies.get(new Random().nextInt(possibleEnemies.size()));

                //creates a new copy of the object, because otherwise the enemy cannot be added because javafx does not permit duplicates into the scene
                Enemy enemy = new Enemy(base.getName(), base.getStats().get(EntityStats.StatType.MAX_HEALTH), base.getStats().get(EntityStats.StatType.SPEED),
                        base.getDamage(), base.getRange(), base.getCooldown(), base.getBullet(), spawnPoint, getNewId());

                spawn(enemy);
            }
            lastSpawn = System.currentTimeMillis();
        }
    }

    public static void relocateEnemies()
    {
        for(Enemy e : GameData.getGameObjectsOfType(Enemy.class))
        {
            if(e.getPosition().distanceFrom(GameData.getPlayer().getPosition()) > GameController.SCREENWIDTH * 0.7f)
                e.setPosition(getRandomPosition());
        }
    }

    private static Position getRandomPosition() {
        double rand = Math.random();
        float maxX = (float) GameController.SCREENWIDTH;
        float maxY = (float) GameController.SCREENHEIGHT;

        float x;
        float y;
        if(rand < 0.25)
        {
            x = (float) Math.random() * maxX;
            y = 0;
        } else if(rand < 0.5)
        {
            x = maxX;
            y = (float) Math.random() * maxY;
        }
        else if(rand < 0.75)
        {
            x = (float) Math.random() * maxX;
            y = maxY;
        }
        else {
            x = 0;
            y = (float) Math.random() * maxY;
        }

        return ScreenToWorldPoint.screenToWorld(new Position(x, y));
    }

    public static void createBullet(BulletStats stats, float damage, Entity spawner, Position target, float range, float area)
    {
        Bullet b = new Bullet(stats, damage, spawner, target, range, area);
        spawn(b);
    }

    public static void spawn(GameObject o, Position p)
    {
        o.setPosition(p);
        spawn(o);
    }
    public static void spawn(GameObject o)
    {
        GameData.addGameObject(o);
    }

    public static int getNewId()
    {
        return idCounter++;
    }

}
