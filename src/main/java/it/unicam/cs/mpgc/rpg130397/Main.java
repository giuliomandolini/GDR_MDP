package it.unicam.cs.mpgc.rpg130397;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.JDeserializer;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static void main() throws IOException {


        //TODO controlla i valori passati tra i metodi

        //System.out.println(weapons.get("Bow").getName());

        saveInventory();

        GameData data = new GameData();
        Player p = new Player("io", 1, 1, JDeserializer.getPreviousInventory(data));
        System.out.println(p.getInventory().get(Characteristics.CharacteristicType.STRENGTH).getStats().getBaseDamage());


    }

    private static void saveInventory() throws IOException {
        Map<Characteristics.CharacteristicType, Weapon> inventory  = new HashMap<>();
        inventory.put(Characteristics.CharacteristicType.STRENGTH, new Weapon("Axe", JDeserializer.getWeaponsStat().get("Axe"), null));
        JDeserializer.saveInventory(inventory);
    }

    private void loadEnemies() throws IOException {
        Gson j = new GsonBuilder().setPrettyPrinting().create();
        File f = new File("src/main/resources/Enemies.json");
        Type mapType = new TypeToken<Map<String, Enemy>>() {}.getType();

        //Map<String, Enemy> enemies = new HashMap<>();
        Map<String, Enemy> enemies = new HashMap<>();
        Enemy s = new Enemy("SkeletonWarrior", 50f, 5f, 10, 0, 2, null);
        Enemy a = new Enemy("SkeletonArcher", 20f, 5f, 6, 10, 2, null);
        Enemy m = new Enemy("SkeletonMage", 10f, 4f, 15, 15, 4, null);
        Enemy z = new Enemy("Zombie", 70f, 3f, 3, 0, 1, null);


        enemies.put(s.getName(), s);
        enemies.put(a.getName(), a);
        enemies.put(m.getName(), m);
        enemies.put(z.getName(), z);

        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        j.toJson(enemies, writer);
        writer.close();
    }
    private void loadWeapons() throws IOException {
        Gson j = new GsonBuilder().setPrettyPrinting().create();
        File f = new File("src/main/resources/WeaponStats.json");
        Type mapType = new TypeToken<Map<String, WeaponStats>>() {}.getType();

        //Map<String, Enemy> enemies = new HashMap<>();
        Map<String, WeaponStats> weapons = new HashMap<>();
        WeaponStats dagger = new WeaponStats(10, 2, 10, 0, Characteristics.CharacteristicType.DEXTERITY);
        WeaponStats bow = new WeaponStats( 20, 3, 30, 0, Characteristics.CharacteristicType.DEXTERITY);
        WeaponStats fireball = new WeaponStats( 10, 5, 20, 3, Characteristics.CharacteristicType.INTELLIGENCE);
        WeaponStats bolt = new WeaponStats(8, 0.5f, 5, 0, Characteristics.CharacteristicType.INTELLIGENCE);
        WeaponStats club = new WeaponStats( 0, 2, 20, 0, Characteristics.CharacteristicType.STRENGTH);
        WeaponStats sword = new WeaponStats( 0, 1, 15, 0, Characteristics.CharacteristicType.STRENGTH);

        weapons.put("Dagger", dagger);
        weapons.put("Bow", bow);
        weapons.put("Fireball", fireball);
        weapons.put("Bolt", bolt);
        weapons.put("Axe", club);
        weapons.put("Sword", sword);

        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        j.toJson(weapons, writer);
        writer.close();
    }
}
