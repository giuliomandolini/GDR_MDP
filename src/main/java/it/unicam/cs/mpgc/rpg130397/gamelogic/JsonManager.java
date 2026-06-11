package it.unicam.cs.mpgc.rpg130397.gamelogic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg130397.elements.entities.player.Inventory;
import it.unicam.cs.mpgc.rpg130397.elements.stats.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.io.*;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/// This class serializes and deserializes data from jsons.
public class JsonManager {
    /**
     *  Static method that deserializes the data contained in the weapons.json file into the weapons map
     * @return the map of all the weapons
     */
    public static Map<String, WeaponStats> getWeaponsStat() {
        Gson json = new Gson();
        InputStream f = JsonManager.class.getClassLoader().getResourceAsStream("json/WeaponStats.json");
        if(f == null) throw new IllegalStateException("Errore nelle risorse del progetto");
        InputStreamReader r = new InputStreamReader(f);
        //Data type definition for the correct deserialization of the json file
        Type weaponMapType = new TypeToken<Map<String, WeaponStats>>() {}.getType();
        Map<String, WeaponStats> temp = json.fromJson(r, weaponMapType);
        if(temp == null || temp.isEmpty()) throw new IllegalStateException("Stats not found");
        return temp;
    }

    /**
     *  Static method that deserializes the data contained in the enemies.json file into the enemies map
     * @return the map of all the enemies
     */
    public static Map<String, Enemy> getEnemies() {
        Gson json = new Gson();
        InputStream f = JsonManager.class.getClassLoader().getResourceAsStream("json/Enemies.json");
        if(f == null) throw new IllegalStateException("Errore nelle risorse del progetto");
        InputStreamReader r = new InputStreamReader(f);
        //Data type definition for the correct deserialization of the json file
        Type enemyMapType = new TypeToken<Map<String, Enemy>>() {}.getType();
        return json.fromJson(r, enemyMapType);
    }

    public static void saveInventory() throws IOException {
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter("src/main/resources/json/Inventory.json");

        g.toJson(GameData.getPlayer().getInventory(), writer);
        writer.close();
    }
    //if there is no previous inventory, the first one is composed by the dagger weapon
    public static Inventory getPreviousInventory() throws FileNotFoundException {
        Gson json = new Gson();
        Reader reader = new FileReader("src/main/resources/json/Inventory.json");

        //Data type definition for the correct deserialization of the json file
        Type inventoryMapType = new TypeToken<Inventory>() {}.getType();

        Inventory inv = json.fromJson(reader, inventoryMapType);

        if(inv == null)
        {
            inv = new Inventory(List.of(new Weapon("Dagger")));
        }
        //Only the name and the level of the weapons are saved. Their stats are saved in the GameData class.
        return addWeaponsStats(inv);
    }
    private static Inventory addWeaponsStats(Inventory inv)
    {
        for(Weapon w : inv.getWeapons())
        {
            w.setStatsAndLevel(GameData.getWeaponStatMap().get(w.getName()), GameData.getWeaponLevel(w.getName()));
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
        Gson g = new GsonBuilder().setPrettyPrinting().create();
        Writer writer = new FileWriter("src/main/resources/json/WeaponLevels.json");
        g.toJson(GameData.getWeaponsLevelMap(), writer);
        writer.close();
    }

}
