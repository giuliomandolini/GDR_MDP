package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.EnemyModel;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** Class that contains the game data, such as the hash tables of the weapons and the enemies currently
 * in the game
 */
public class GameData {
    private static Map<String, WeaponStats> weaponStatMap = new HashMap<>();
    private static Map<String, EnemyModel> enemiesMap = new HashMap<>();

    private List<EnemyModel> enemies;

    public GameData() throws FileNotFoundException {
        enemies = new LinkedList<>();
        weaponStatMap = JDeserializer.getWeaponsStat();
        enemiesMap = JDeserializer.getEnemies();
        if(enemiesMap == null) throw new IllegalStateException();
    }

    public static EnemyModel getEnemy(String enemy) {
        return enemiesMap.get(enemy);
    }

    public List<EnemyModel> getEnemies() {
        return enemies;
    }

    public static Map<String, EnemyModel> getEnemiesMap() {
        return enemiesMap;
    }

    public static Map<String, WeaponStats> getWeaponStatMap() {
        return weaponStatMap;
    }
}
