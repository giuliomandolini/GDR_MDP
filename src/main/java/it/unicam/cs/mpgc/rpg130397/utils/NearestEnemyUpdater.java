package it.unicam.cs.mpgc.rpg130397.utils;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

import java.util.List;

public class NearestEnemyUpdater {
    private static Enemy closestEnemy;
    private static long lastUpdate;
    private static final int UPDATE_COOLDOWN = 10;

    public static Enemy updateAndGetClosestEnemy()
    {
        List<Enemy> enemies = GameData.getGameObjectsOfType(Enemy.class);
        if(enemies.isEmpty()) return null;
        //avoids calculating the nearest enemy too frequently as it probably remains the same between attacks with an update rate of 100 times per second
        if(lastUpdate + UPDATE_COOLDOWN > System.currentTimeMillis()) return closestEnemy;

        closestEnemy = enemies.getFirst();
        Position playerPosition = GameData.getPlayer().getPosition();

        float minDistance = closestEnemy.getPosition().distanceFrom(playerPosition);

        for(int i = 1; i < enemies.size(); i++)
        {
            float distance = enemies.get(i).getPosition().distanceFrom(playerPosition);
            if(distance < minDistance)
            {
                closestEnemy = enemies.get(i);
                minDistance = distance;
            }
        }
        lastUpdate = System.currentTimeMillis();
        return closestEnemy;
    }

}
