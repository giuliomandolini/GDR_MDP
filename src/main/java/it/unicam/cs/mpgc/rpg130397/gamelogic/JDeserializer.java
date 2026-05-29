package it.unicam.cs.mpgc.rpg130397.gamelogic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/// Serializes and Deserializes data from jsons
public class JDeserializer {
    /**
     *  Static method that deserializes the data contained in the weapons.json file into the weapons map
     * @return the map of all the weapons
     */
    public static Map<String, WeaponStats> getWeaponsStat() throws FileNotFoundException {
        Gson json = new Gson();
        InputStream f = JDeserializer.class.getClassLoader().getResourceAsStream("json/WeaponStats.json");
        if(f == null) throw new IllegalStateException("Errore nelle risorse del progetto");
        InputStreamReader r = new InputStreamReader(f);
        //Data type definition for the correct deserialization of the json file
        Type weaponMapType = new TypeToken<Map<String, WeaponStats>>() {}.getType();
        Map<String, WeaponStats> temp = json.fromJson(r, weaponMapType);
        if(temp == null || temp.keySet().isEmpty()) throw new IllegalStateException("Stats not found");
        System.out.println("stats = " + temp);
                //todo
        return temp;
    }

    /**
     *  Static method that deserializes the data contained in the enemies.json file into the enemies map
     * @return the map of all the enemies
     */
    public static Map<String, Enemy> getEnemies() {
        Gson json = new Gson();
        InputStream f = JDeserializer.class.getClassLoader().getResourceAsStream("json/Enemies.json");
        if(f == null) throw new IllegalStateException("Errore nelle risorse del progetto");
        InputStreamReader r = new InputStreamReader(f);
        //Data type definition for the correct deserialization of the json file
        Type enemyMapType = new TypeToken<Map<String, Enemy>>() {}.getType();
        return json.fromJson(r, enemyMapType);
    }

    public static void saveInventory() throws IOException {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter("src/main/resources/json/Inventory.json");
        System.out.println("before saving invent " + GameData.getPlayer().getInventory() + " " +  getPreviousInventory());

        g.toJson(GameData.getPlayer().getInventory(), writer);
        writer.close();
        System.out.println("after saving invent " + GameData.getPlayer().getInventory() + " " +  getPreviousInventory());
    }
    public static Map<Characteristics.CharacteristicType, Weapon> getPreviousInventory() throws FileNotFoundException {
        Gson json = new Gson();
        Reader reader = new FileReader("src/main/resources/json/Inventory.json");

        //Data type definition for the correct deserialization of the json file
        Type inventoryMapType = new TypeToken<Map<Characteristics.CharacteristicType, Weapon>>() {}.getType();

        Map<Characteristics.CharacteristicType, Weapon> inv = json.fromJson(reader, inventoryMapType);

        if(inv == null)
        {
            inv = new HashMap<>();
            inv.put(Characteristics.CharacteristicType.DEXTERITY, new Weapon("Dagger"));
        }
        //Only the name and the level of the weapons are saved. Their stats are saved in the GameData class.
        return addWeaponsStats(inv);
    }
    private static Map<Characteristics.CharacteristicType, Weapon> addWeaponsStats(Map<Characteristics.CharacteristicType, Weapon> inv)
    {
        System.out.println("definitivo: inv = " + inv + ", stats = " + GameData.getWeaponStatMap());
        for(Weapon w : inv.values())
        {
            System.out.println(w + ", " + w.getName() + " is getting " + GameData.getWeaponStatMap().get(w.getName()));
            w.setStats(GameData.getWeaponStatMap().get(w.getName()));
            w.setLevel(GameData.getWeaponLevel(w.getName()));
        }
        return inv;
    }


    public static Map<String, Integer> getWeaponLevels() throws FileNotFoundException {
        Gson json = new Gson();
        Reader reader = new FileReader("src/main/resources/json/WeaponLevels.json");
        //Data type definition for the correct deserialization of the json file
        Type weaponLevelsType = new TypeToken<Map<String, Integer>>() {}.getType();
        return json.fromJson(reader, weaponLevelsType);
    }

    public static void saveWeaponLevels() throws IOException {
        System.out.println("before saving level "  + GameData.getWeaponsLevelMap() + " " + getWeaponLevels());
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter("src/main/resources/json/WeaponLevels.json");
        g.toJson(GameData.getWeaponsLevelMap(), writer);
        writer.close();
        System.out.println("after saving level "  + GameData.getWeaponsLevelMap() + " " + getWeaponLevels());
    }

}
