package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;

/// Class that contains the instructions for general game functioning and utility methods
public class GameController {
    private GameData data;


    public GameController(GameData data) {
        this.data = data;
    }


    public void destroyEnemy(Enemy e)
    {
        data.getEnemies().remove(e);
    }
    public void spawnEnemy(Enemy e)
    {
        data.getEnemies().add(e);
    }
}
