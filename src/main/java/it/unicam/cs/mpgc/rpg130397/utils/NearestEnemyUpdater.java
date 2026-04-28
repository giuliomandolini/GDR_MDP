package it.unicam.cs.mpgc.rpg130397.utils;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

public class NearestEnemyUpdater {
    private GameData data;

    private Enemy closestEnemy;
    private long lastUpdate;
    private final float UPDATE_COOLDOWN = 0.05f;

    public NearestEnemyUpdater(GameData data)
    {
        this.data = data;
    }

    public void updateClosestEnemy()
    {
        if(lastUpdate + UPDATE_COOLDOWN > System.currentTimeMillis()) return;
        closestEnemy = data.getEnemies().getFirst();
        for(Enemy e : data.getEnemies())
        {
            //TODO distanza tra nemici e giocatore
            if(/*distanza*/ true)
            {
                closestEnemy = e;
            }
        }
        lastUpdate = System.currentTimeMillis();
    }

    public Enemy getClosestEnemy() {
        return closestEnemy;
    }
}
