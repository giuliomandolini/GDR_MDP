package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

import java.io.FileNotFoundException;
import java.util.*;

/// Class that contains the game data, such as the player reference, hash tables of the weapons and lists of enemies and bullets currently in game;
/// as well as bullets and enemies to delete and add in the scene
public class GameData {
    private static Map<String, WeaponStats> weaponStatMap = new HashMap<>();
    private static Map<String, Enemy> enemiesMap = new HashMap<>();
    private static Player player;

    private static List<Enemy> enemies;
    private static List<Bullet> bullets;

    private static List<Enemy> onlyModelEnemies;
    private static List<Bullet> onlyModelBullets;

    private static List<Enemy> onlyViewEnemies;
    private static List<Bullet> onlyViewBullets;


    public static void start() throws FileNotFoundException {

        enemies = new LinkedList<>();
        bullets = new LinkedList<>();

        weaponStatMap = JDeserializer.getWeaponsStat();
        enemiesMap = JDeserializer.getEnemies();

        onlyModelBullets = new ArrayList<>();
        onlyModelEnemies = new ArrayList<>();

        onlyViewBullets = new ArrayList<>();
        onlyViewEnemies = new ArrayList<>();

        EnemySpawnSystem.start();
    }

    public static List<Enemy> getEnemies() {
        return enemies;
    }
    public static void addEnemy(Enemy e) { onlyModelEnemies.add(e); }
    public static Map<String, Enemy> getEnemiesMap() {
        return enemiesMap;
    }
    public static void removeEnemy(Enemy e)
    {
        enemies.remove(e);
        onlyViewEnemies.add(e);
    }
    public static List<Enemy> getEnemiesToSpawn()
    {
        enemies.addAll(onlyModelEnemies);
        List<Enemy> toReturn = new ArrayList<>(onlyModelEnemies);
        onlyModelEnemies = new ArrayList<>();
        return toReturn;
    }
    public static List<Enemy> getEnemiesToDespawn()
    {
        List<Enemy> toReturn = new ArrayList<>(onlyViewEnemies);
        onlyViewEnemies = new ArrayList<>();
        return toReturn;
    }

    public static List<Bullet> getBullets() {
        return bullets;
    }
    public static void addBullet(Bullet b)
    {
        onlyModelBullets.add(b);
    }
    public static void removeBullet(Bullet b)
    {
        bullets.remove(b);
        onlyViewBullets.add(b);
    }
    public static List<Bullet> getBulletsToSpawn()
    {
        bullets.addAll(onlyModelBullets);
        List<Bullet> toReturn = new ArrayList<>(onlyModelBullets);
        onlyModelBullets = new ArrayList<>();
        return toReturn;
    }
    public static List<Bullet> getBulletsToDespawn()
    {
        List<Bullet> toReturn = new ArrayList<>(onlyViewBullets);
        onlyViewBullets = new ArrayList<>();
        return toReturn;
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
