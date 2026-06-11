package it.unicam.cs.mpgc.rpg130397.utils;

import it.unicam.cs.mpgc.rpg130397.gamelogic.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

import java.util.List;

public class NearestEnemyUpdater {
    private static Enemy closestEnemy;
    private static long lastUpdate;
    private static final int UPDATE_COOLDOWN = 50;

    /// Updates and return the enemy that is closest to the player. <br>
    /// If it was updated in the last 0.05 seconds, it doesn't update again
    /// @return The closest enemy relative to the player.
    // in 0.05 seconds the closest enemy doesn't change much, but if there is a considerable amount of enemies the method could
    // become somewhat heavy if done each frame. Updating it 20 times per second is still more than enough.
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
