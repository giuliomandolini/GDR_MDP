package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

/// Class that contains the instructions for general game functioning and utility methods
public class GameManager {

    public static void update()
    {
        updateModels();
        SpawnSystem.spawnEnemies();
    }


    public static void updateModels()
    {
        for(Enemy e : GameData.getEnemies())
        {
            e.update();
        }
        System.out.println(GameData.getBullets() + ", ");
        for(Bullet b : GameData.getBullets())
        {
            b.update();
        }
        GameData.getPlayer().update();
    }
}
