package it.unicam.cs.mpgc.rpg130397;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Player;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;
import it.unicam.cs.mpgc.rpg130397.gamelogic.JDeserializer;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class App {
    static void main() throws IOException {


        //TODO controlla i valori passati tra i metodi

        //Map<String, Enemy> enemies = new HashMap<>();
        /*Map<String, Weapon> weapons = new HashMap<>();
        Weapon dagger = new Weapon("Dagger", 10, 2, 10, 0, Characteristics.CharacteristicType.DEXTERITY, null);
        Weapon bow = new Weapon("Bow", 20, 3, 30, 0, Characteristics.CharacteristicType.DEXTERITY, null);
        Weapon fireball = new Weapon("Fireball", 10, 5, 20, 3, Characteristics.CharacteristicType.INTELLIGENCE, null);
        Weapon bolt = new Weapon("Bolt", 8, 0.5f, 5, 0, Characteristics.CharacteristicType.INTELLIGENCE, null);
        Weapon club = new Weapon("Club", 0, 2, 20, 0, Characteristics.CharacteristicType.STRENGTH, null);
        Weapon sword = new Weapon("Sword", 0, 1, 15, 0, Characteristics.CharacteristicType.STRENGTH, null);

        weapons.put("Dagger", dagger);
        weapons.put("Bow", bow);
        weapons.put("Fireball", fireball);
        weapons.put("Bolt", bolt);
        weapons.put("Club", club);
        weapons.put("Sword", sword);
        /*Enemy s = new Enemy("SkeletonWarrior", 50f, 5f, 10, 0, 2, null);
        Enemy a = new Enemy("SkeletonArcher", 20f, 5f, 6, 10, 2, null);
        Enemy m = new Enemy("SkeletonMage", 10f, 4f, 15, 15, 4, null);
        Enemy z = new Enemy("Zombie", 70f, 3f, 3, 0, 1, null);


        enemies.put(s.getName(), s);
        enemies.put(a.getName(), a);
        enemies.put(m.getName(), m);
        enemies.put(z.getName(), z);
*/
        /*Gson j = new GsonBuilder().setPrettyPrinting().create();
        File f = new File("src/main/resources/jsons/WeaponStats.json");

        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        j.toJson(weapons, writer);
        writer.close();

        Reader r = new FileReader(f);
        Type weaponMapType = new TypeToken<Map<String, Weapon>>() {}.getType();
        weapons = j.fromJson(r, weaponMapType);

        weapons = JDeserializer.getWeapons();
        System.out.println(weapons.get("Bow").getName());*/
        Map<Characteristics.CharacteristicType, Weapon> inventory  = new HashMap<>();
        inventory.put(Characteristics.CharacteristicType.STRENGTH, new Weapon(JDeserializer.getWeaponsStat().get("Axe"), 1, null));
        JDeserializer.saveInventory(inventory);
        Player p = new Player("io", 1, 1);
        p.setInventory(JDeserializer.getPreviousInventory());
        System.out.println(p.getInventory().get(Characteristics.CharacteristicType.STRENGTH).getStats().getBaseDamage());
    }
}
