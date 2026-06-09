package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.Main;
import it.unicam.cs.mpgc.rpg130397.elements.stats.BulletStats;
import it.unicam.cs.mpgc.rpg130397.elements.stats.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.utils.ScreenToWorldPoint;

import java.util.Random;

/// Manages the spawn of the enemies and their relocation: in case the player moves too far away from an enemy, it respawns it nearer.
/// It should be the only way to comunicate with GameData.addGameObject().
public class SpawnSystem {

    private static long lastSpawn;
    private static long spawnCooldown;
    private static int enemiesToSpawn;
    //In future developments the enemies will be coming in groups and not just random as it is now,
    //that is why I decided to use a map instead of a set inside GameData
    private static int idCounter;
    //Memorizes an array of the possible enemies by their name, so there is no need to extract the keySet from
    //GameData.enemiesMap at each iteration and the get from an hashmap is O(1) so it is not convenient to store
    //the entire values of the map
    private static String[] enemyNames;


    public static void start()
    {
        enemiesToSpawn = 2;
        idCounter = 0;
        enemyNames = GameData.getEnemiesMap().keySet().toArray(new String[0]); //IntelliJ suggestion
    }

    public static void spawnEnemies()
    {
        updateEnemySpawnRate();

        if(lastSpawn + spawnCooldown < System.currentTimeMillis())
        {
            for (int i = 0; i < enemiesToSpawn; i++) {
                Position spawnPoint = getRandomPosition();
                String randomEnemy = enemyNames[new Random().nextInt(enemyNames.length)];
                Enemy base = GameData.getEnemiesMap().get(randomEnemy); //possibleEnemies.get(new Random().nextInt(possibleEnemies.size()));

                //creates a new copy of the object, because otherwise the enemy cannot be added because javafx does not permit duplicates into the scene
                Enemy enemy = new Enemy(base.getName(), base.getStats().get(EntityStats.StatType.MAX_HEALTH), base.getStats().get(EntityStats.StatType.SPEED),
                        base.getDamage(), base.getRange(), base.getCooldown(), base.getBullet(), spawnPoint);

                spawn(enemy);
            }
            lastSpawn = System.currentTimeMillis();
        }
    }

    private static void updateEnemySpawnRate()
    {
        int numberOfEnemies = GameData.getGameObjectsOfType(Enemy.class).size();

        System.out.println(numberOfEnemies);

        if(numberOfEnemies < 50)
        {
            spawnCooldown = 150;
            enemiesToSpawn = 2;
        }
        else if(numberOfEnemies < 80){
            enemiesToSpawn = 2;
            spawnCooldown = 500;
        }
        else {
            enemiesToSpawn = 1;
            spawnCooldown = 400;
        }
    }

    public static void relocateEnemies()
    {
        for(Enemy e : GameData.getGameObjectsOfType(Enemy.class))
        {
            if(e.getPosition().distanceFrom(GameData.getPlayer().getPosition()) > Main.SCREEN_WIDTH * 0.7f)
                e.setPosition(getRandomPosition());
        }
    }

    private static Position getRandomPosition() {
        double rand = Math.random();
        float maxX = (float) Main.SCREEN_WIDTH;
        float maxY = (float) Main.SCREEN_HEIGHT;

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

    public static void spawn(GameObject o)
    {
        GameData.addGameObject(o);
    }

    public static int getNewId()
    {
        return idCounter++;
    }

}
