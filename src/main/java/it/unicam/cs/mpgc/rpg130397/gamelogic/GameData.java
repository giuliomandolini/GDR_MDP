package it.unicam.cs.mpgc.rpg130397.gamelogic;

import it.unicam.cs.mpgc.rpg130397.elements.stats.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.elements.entities.player.Player;

import java.io.FileNotFoundException;
import java.util.*;

/// Class that contains the game data, such as the player reference, hash tables of the weapons and lists of enemies and bullets currently in game;
/// as well as bullets and enemies to delete and add in the scene
public class GameData {
    private static Map<String, WeaponStats> weaponStatMap;
    private static Map<String, Integer> weaponsLevelMap;
    private static Map<String, Enemy> enemiesMap;
    private static Player player;
    public final static float CHEST_SPAWN_RATE = 0.2f;

    private static Map<Class<? extends GameObject>, List<GameObject>> gameObjects;

    private static List<GameObject> onlyViewElements;
    private static List<GameObject> onlyModelElements;

    public static void start() throws FileNotFoundException {
        //controls about stats are made in the JsonManager class
        weaponStatMap = JsonManager.getWeaponsStat();
        enemiesMap = JsonManager.getEnemies();
        weaponsLevelMap = JsonManager.getWeaponLevels();

        //se weaponsLevelMap è null non si è mai avviato il gioco
        if(weaponsLevelMap == null) weaponsLevelMap = new HashMap<>();

        onlyViewElements = new ArrayList<>();
        onlyModelElements = new ArrayList<>();

        gameObjects = new HashMap<>();

        SpawnSystem.start();
    }

    /// Removes a GameObject from the game.
    /// @param gameObject the GameObject to remove.
    public static void removeGameObject(GameObject gameObject)
    {
        if(gameObjects.get(gameObject.getClass()) == null) return;
        gameObjects.get(gameObject.getClass()).remove(gameObject);
        onlyViewElements.add(gameObject);
    }
    /// Adds a GameObject into the game.
    /// @param gameObject the GameObject to add.
    public static void addGameObject(GameObject gameObject)
    {
        if(gameObjects.get(gameObject.getClass()) == null) gameObjects.put(gameObject.getClass(), new LinkedList<>());
        gameObjects.get(gameObject.getClass()).add(gameObject);
        onlyModelElements.add(gameObject);
    }
    /// Gets all the GameObjects of a given type.
    /// @param type The type of the elements to get.
    public static <T extends GameObject> List<T> getGameObjectsOfType(Class<T> type)
    {
        List<T> t = (List<T>) gameObjects.get(type);
        //avoid passing null, but an empty list instead
        if(t == null) t = new LinkedList<>();
        return t;
    }
    public static List<GameObject> getAllGameObjects() {
        return gameObjects.values().stream().flatMap(Collection::stream).toList();
    }

    //methods to get json info
    public static Map<String, Enemy> getEnemiesMap() {
        return enemiesMap;
    }
    public static Map<String, WeaponStats> getWeaponStatMap() {
        return weaponStatMap;
    }
    public static void saveWeaponLevel(String name, int level) {
        if(weaponsLevelMap.putIfAbsent(name, level) != null)
            weaponsLevelMap.put(name, level);
    }

    //if the level has not been saved yet, then the default level is 0 because the weapon has never been used.
    public static int getWeaponLevel(String name)
    {
        return weaponsLevelMap.getOrDefault(name, 0);
    }

    public static Map<String, Integer> getWeaponsLevelMap() {
        return weaponsLevelMap;
    }

    /// Called when the GameController updates the views. It updates the list of the elements that still have to spawn (have their view created)
    public static List<GameObject> getElementsToSpawn()
    {
        List<GameObject> toReturn = new ArrayList<>(onlyModelElements);
        onlyModelElements = new ArrayList<>();
        return toReturn;
    }
    /// Called when the GameController updates the views. It updates the list of the elements that still have to despawn (have their view deleted)
    public static List<GameObject> getElementsToDespawn()
    {
        List<GameObject> toReturn = new ArrayList<>(onlyViewElements);
        onlyViewElements = new ArrayList<>();
        return toReturn;
    }

    public static void setPlayer(Player player) {
        if(player == null) throw new IllegalArgumentException("The player cannot be null");
        GameData.player = player;
        gameObjects.put(Player.class, new LinkedList<>());
        gameObjects.get(Player.class).add(player);
    }
    public static Player getPlayer() {
        return player;
    }

}
