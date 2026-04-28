package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/** Class that contains the game data, such as the hash tables of the weapons and the enemies currently
 * in the game
 */
public class GameData {
    private Map<String, WeaponStats> weaponStatMap = new HashMap<>();
    private Map<String, Enemy> enemiesMap = new HashMap<>();

    private List<Enemy> enemies;

    public GameData() throws FileNotFoundException {
        enemies = new LinkedList<>();
        weaponStatMap = JDeserializer.getWeaponsStat();
        enemiesMap = JDeserializer.getEnemies();
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

}
