package it.unicam.cs.mpgc.rpg130397;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.BulletStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.utils.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

    public int width = 800;
    public int height = 800;
    @Override
    public void start(Stage stage) throws IOException {
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);

        SceneManager.setStage(stage);
        SceneManager.loadScene("menu");



        stage.show();
    }

    static void main() throws IOException {
        //loadEnemies();
        //        loadWeapons();

        //launch();
        //TODO controlla i valori passati tra i metodi

        //System.out.println(weapons.get("Bow").getName());
        //List<GameObject> l = new ArrayList<>() ;
        //l.stream();
        /*saveInventory();

        GameData data = new GameData();
        Player p = new Player("io", 1, 1, JDeserializer.getPreviousInventory(data), null);
        System.out.println(p.getInventory().get(Characteristics.CharacteristicType.STRENGTH).getStats().getBaseDamage());
*/
        //System.out.println(GetSpriteByName.getResourcePath("SkeletonArcher"));
        //loadEnemies();
    }

//    private static void saveInventory() throws IOException {
//        Map<Characteristics.CharacteristicType, Weapon> inventory  = new HashMap<>();
//        inventory.put(Characteristics.CharacteristicType.STRENGTH, new Weapon("Axe", JDeserializer.getWeaponsStat().get("Axe")));
//        JDeserializer.saveInventory(inventory);
//    }

    private static void loadEnemies() throws IOException {
        Gson j = new GsonBuilder().setPrettyPrinting().create();
        File f = new File("src/main/resources/json/Enemies.json");
        Type mapType = new TypeToken<Map<String, Enemy>>() {}.getType();

        //Map<String, Enemy> enemies = new HashMap<>();
        Map<String, Enemy> enemies = new HashMap<>();
//        Enemy s = new Enemy("Skeleton Warrior", 50f, 5f, 10, 0, 2, null, null, 1);
//        Enemy a = new Enemy("Skeleton Archer", 20f, 5f, 6, 10, 2, new BulletStats("Arrow", 10f), null, 2);
//        Enemy m = new Enemy("Skeleton Mage", 10f, 4f, 15, 15, 4, new BulletStats("Fire Bolt", 7f),null, 3);
//        //Enemy z = new Enemy("Zombie", 70f, 3f, 3, 0, 1, null, null);


//        enemies.put(s.getName(), s);
//        enemies.put(a.getName(), a);
//        enemies.put(m.getName(), m);
        //enemies.put(z.getName(), z);


        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        j.toJson(enemies, writer);
        writer.close();


    }
    private static void loadWeapons() throws IOException {
        Gson j = new GsonBuilder().setPrettyPrinting().create();
        File f = new File("src/main/resources/json/WeaponStats.json");
        Type mapType = new TypeToken<Map<String, WeaponStats>>() {}.getType();

        //Map<String, Enemy> enemies = new HashMap<>();
        Map<String, WeaponStats> weapons = new HashMap<>();
        WeaponStats dagger = new WeaponStats(100, 2000, 10, 0, new BulletStats("Dagger", 10), Characteristics.CharacteristicType.DEXTERITY);
        WeaponStats bow = new WeaponStats( 200, 3000, 30, 0, new BulletStats("Arrow", 20), Characteristics.CharacteristicType.DEXTERITY);
        WeaponStats fireball = new WeaponStats( 150, 5000, 20, 50, new BulletStats("Fireball", 8), Characteristics.CharacteristicType.INTELLIGENCE);
        WeaponStats bolt = new WeaponStats(80, 500, 5, 0, new BulletStats("Fire Bolt", 15), Characteristics.CharacteristicType.INTELLIGENCE);
        WeaponStats club = new WeaponStats( 0, 2000, 20, 0, null, Characteristics.CharacteristicType.STRENGTH);
        WeaponStats sword = new WeaponStats( 0, 1000, 15, 0, null, Characteristics.CharacteristicType.STRENGTH);

        weapons.put("Dagger", dagger);
        weapons.put("Bow", bow);
        weapons.put("Fireball", fireball);
        weapons.put("Fire Bolt", bolt);
        weapons.put("Axe", club);
        weapons.put("Sword", sword);

        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        j.toJson(weapons, writer);
        writer.close();
    }
}
