package it.unicam.cs.mpgc.rpg130397;

import java.util.LinkedList;
import java.util.List;

public class GameController {
    private List<Enemy> enemies;
    private Enemy closestEnemy;
    private long lastUpdate;
    private final float UPDATE_COOLDOWN = 0.05f;

    public GameController() {
        enemies = new LinkedList<>();
    }

    public void updateClosestEnemy()
    {
        if(lastUpdate + UPDATE_COOLDOWN > System.currentTimeMillis()) return;
        closestEnemy = enemies.getFirst();
        for(Enemy e : enemies)
        {
            //TODO distanza tra nemici e giocatore
            if(/*distanza*/ true)
            {
                closestEnemy = e;
            }
        }
    }

    public Enemy getClosestEnemy() {
        return closestEnemy;
    }
}
