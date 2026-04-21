package it.unicam.cs.mpgc.rpg130397;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GameController {
    private Map<String, Weapon> weaponsMap = new HashMap<>();
    private Map<String, Enemy> enemiesMap = new HashMap<>();

    private List<Enemy> enemies;
    private Enemy closestEnemy;
    private long lastUpdate;
    private final float UPDATE_COOLDOWN = 0.05f;

    public GameController() throws FileNotFoundException {
        enemies = new LinkedList<>();
        weaponsMap = JDeserializer.getWeapons();
        enemiesMap = JDeserializer.getEnemies();
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
