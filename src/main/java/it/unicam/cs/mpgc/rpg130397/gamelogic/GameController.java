package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;

/// Class that contains the instructions for general game functioning and utility methods
public class GameController {
    private GameData data;

    private Enemy closestEnemy;
    private long lastUpdate;
    private final float UPDATE_COOLDOWN = 0.05f;

    public GameController(GameData data) {
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
    }

    public void destroyEnemy(Enemy e)
    {
        data.getEnemies().remove(e);
    }
    public void spawnEnemy(Enemy e)
    {
        data.getEnemies().add(e);
    }
    public Enemy getClosestEnemy() {
        return closestEnemy;
    }
}
