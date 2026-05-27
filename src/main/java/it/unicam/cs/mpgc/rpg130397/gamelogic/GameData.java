package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
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

    private static Map<Class<? extends GameObject>, List<GameObject>> gameObjects;

    private static List<GameObject> onlyViewElements;
    private static List<GameObject> onlyModelElements;


    public static void start() throws FileNotFoundException {
        weaponStatMap = JDeserializer.getWeaponsStat();
        enemiesMap = JDeserializer.getEnemies();

        onlyViewElements = new ArrayList<>();
        onlyModelElements = new ArrayList<>();

        EnemySpawnSystem.start();
    }

    public static void removeElement(GameObject e)
    {
        if(gameObjects.get(e.getClass()) == null) return;
        gameObjects.get(e.getClass()).remove(e);
        onlyViewElements.add(e);
    }
    public static void addElement(GameObject e)
    {
        if(gameObjects.get(e.getClass()) == null) gameObjects.put(e.getClass(), new LinkedList<>());
        gameObjects.get(e.getClass()).add(e);
        onlyViewElements.add(e);
    }
    public static List<GameObject> getElements(Class<? extends GameObject> type)
    {
        List<GameObject> t = gameObjects.get(type);
        //avoid passing null, but an empty list instead
        if(t == null) t = new LinkedList<>();
        return t;
    }

    public static Map<String, Enemy> getEnemiesMap() {
        return enemiesMap;
    }
    public static Map<String, WeaponStats> getWeaponStatMap() {
        return weaponStatMap;
    }

    public static List<GameObject> getElementsToSpawn()
    {
        List<GameObject> toReturn = new ArrayList<>(onlyModelElements);
        onlyModelElements = new ArrayList<>();
        return toReturn;
    }
    public static List<GameObject> getElementsToDespawn()
    {
        List<GameObject> toReturn = new ArrayList<>(onlyViewElements);
        onlyViewElements = new ArrayList<>();
        return toReturn;
    }

    public static void setPlayer(Player player) {
        GameData.player = player;
    }
    public static Player getPlayer() {
        return player;
    }

}
