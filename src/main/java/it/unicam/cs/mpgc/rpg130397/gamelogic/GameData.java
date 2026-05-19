package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import javafx.scene.layout.Pane;

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
    private static Map<String, Enemy> enemiesMap = new HashMap<>();
    private static Player player;

    private static List<Enemy> enemies;
    private static List<Bullet> bullets;

    public static void start(Player p, Pane gamePane) throws FileNotFoundException {

        enemies = new LinkedList<>();
        bullets = new LinkedList<>();

        weaponStatMap = JDeserializer.getWeaponsStat();
        enemiesMap = JDeserializer.getEnemies();
        if(enemiesMap == null) throw new IllegalStateException();
        player = p;
        SpawnSystem.start(gamePane);
    }

    public static Enemy getEnemy(String enemy) {
        return enemiesMap.get(enemy);
    }
    public static List<Enemy> getEnemies() {
        return enemies;
    }
    public static void addEnemies(List<Enemy> e) { enemies.addAll(e); }
    public static Map<String, Enemy> getEnemiesMap() {
        return enemiesMap;
    }

    public static List<Bullet> getBullets() {
        return bullets;
    }
    public static void addBullet(Bullet b)
    {
        bullets.add(b);
    }
    public static void removeBullet(Bullet b)
    {
        bullets.remove(b);
    }

    public static Map<String, WeaponStats> getWeaponStatMap() {
        return weaponStatMap;
    }

    public static void setPlayer(Player player) {
        GameData.player = player;
    }
    public static Player getPlayer() {
        return player;
    }
}
