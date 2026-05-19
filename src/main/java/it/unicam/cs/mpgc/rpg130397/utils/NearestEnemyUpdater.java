package it.unicam.cs.mpgc.rpg130397.utils;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

import java.util.List;

public class NearestEnemyUpdater {
    private static Enemy closestEnemy;
    private static float minDistance;
    private static long lastUpdate;
    private static final float UPDATE_COOLDOWN = 0.05f;

    public static Enemy updateAndGetClosestEnemy()
    {
        List<Enemy> enemies = GameData.getEnemies();
        if(lastUpdate + UPDATE_COOLDOWN > System.currentTimeMillis() || enemies.isEmpty()) return null;

        closestEnemy = enemies.getFirst();
        Position playerPosition = GameData.getPlayer().getPosition();

        minDistance = closestEnemy.getPosition().distanceFrom(playerPosition);

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
