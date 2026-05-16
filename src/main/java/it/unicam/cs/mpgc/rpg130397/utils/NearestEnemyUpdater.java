package it.unicam.cs.mpgc.rpg130397.utils;

import it.unicam.cs.mpgc.rpg130397.elements.entities.EnemyModel;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

public class NearestEnemyUpdater {
    private GameData data;

    private EnemyModel closestEnemyModel;
    private long lastUpdate;
    private final float UPDATE_COOLDOWN = 0.05f;

    public NearestEnemyUpdater(GameData data)
    {
        this.data = data;
    }

    public void updateClosestEnemy()
    {
        if(lastUpdate + UPDATE_COOLDOWN > System.currentTimeMillis()) return;
        closestEnemyModel = data.getEnemies().getFirst();
        for(EnemyModel e : data.getEnemies())
        {
            //TODO distanza tra nemici e giocatore
            if(/*distanza*/ true)
            {
                closestEnemyModel = e;
            }
        }
        lastUpdate = System.currentTimeMillis();
    }

    public EnemyModel getClosestEnemy() {
        return closestEnemyModel;
    }
}
