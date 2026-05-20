package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemySpawnSystem {

    private static long lastSpawn;
    private static long spawnCooldown;
    private static int enemiesToSpawn;
    private static Pane pane;
    //In future developments the enemies will be coming in groups and not just random as it is now,
    //that is why i decided to use a map instead of a set inside GameData
    private static List<Enemy> possibleEnemies;
    private static int idCounter;


    public static void start(Pane gamePane)
    {
        possibleEnemies = GameData.getEnemiesMap().values().stream().toList();
        spawnCooldown = 2000;
        enemiesToSpawn = 5;
        pane = gamePane;
        //lastSpawn = System.currentTimeMillis();
        idCounter = 0;
    }



    public static List<Enemy> spawnEnemies()
    {
        if(lastSpawn + spawnCooldown < System.currentTimeMillis())
        {
            List<Enemy> toAdd = new ArrayList<>();
            for (int i = 0; i < enemiesToSpawn; i++) {
                Position spawnPoint = getRandomPosition();
                Enemy base = /*GameData.getEnemiesMap().get("Skeleton Archer");*/possibleEnemies.get(new Random().nextInt(possibleEnemies.size()));

                //creates a new copy of the object, because otherwise the enemy cannot be added because javafx does not permit duplicates into the scene
                Enemy enemy = new Enemy(base.getName(), base.getStats().get(EntityStats.StatType.MAX_HEALTH), base.getStats().get(EntityStats.StatType.SPEED),
                        base.getDamage(), base.getRange(), base.getCooldown(), base.getBullet(), spawnPoint, idCounter++);

                toAdd.add(enemy);
            }

            lastSpawn = System.currentTimeMillis();
            GameData.addEnemies(toAdd);

            return toAdd;
        }
        return null;
    }
    private static Position getRandomPosition() {
        double rand = Math.random();
        float maxX = (float) pane.getMinWidth();
        float maxY = (float) pane.getMinHeight();

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

        return new Position(x, y);
    }
}
