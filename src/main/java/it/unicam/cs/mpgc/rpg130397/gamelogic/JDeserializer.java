package it.unicam.cs.mpgc.rpg130397.gamelogic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg130397.Main;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Characteristics;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.WeaponStats;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Map;

public class JDeserializer {
    /**
     *  Static method that deserializes the data contained in the weapons.json file into the weapons map
     * @return the map of all the weapons
     */
    public static Map<String, WeaponStats> getWeaponsStat() throws FileNotFoundException {
        Gson json = new Gson();
        InputStream f = JDeserializer.class.getClassLoader().getResourceAsStream("WeaponStats.json");
        if(f == null) throw new IllegalStateException("Errore nelle risorse del progetto");
        InputStreamReader r = new InputStreamReader(f);
        //Data type definition for the correct deserialization of the json file
        Type weaponMapType = new TypeToken<Map<String, WeaponStats>>() {}.getType();
        return json.fromJson(r, weaponMapType);
    }

    /**
     *  Static method that deserializes the data contained in the enemies.json file into the enemies map
     * @return the map of all the enemies
     */
    public static Map<String, Enemy> getEnemies() throws FileNotFoundException {
        Gson json = new Gson();
        InputStream f = JDeserializer.class.getClassLoader().getResourceAsStream("Enemies.json");
        if(f == null) throw new IllegalStateException("Errore nelle risorse del progetto");
        InputStreamReader r = new InputStreamReader(f);
        //Data type definition for the correct deserialization of the json file
        Type enemyMapType = new TypeToken<Map<String, Enemy>>() {}.getType();
        return json.fromJson(r, enemyMapType);
    }

    public static void saveInventory(Map<Characteristics.CharacteristicType, Weapon> inventory) throws IOException {
        File f = new File("src/main/resources/Inventory.json");
        Gson j = new GsonBuilder().setPrettyPrinting().create();
        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        j.toJson(inventory, writer);
        writer.close();
    }
    public static Map<Characteristics.CharacteristicType, Weapon> getPreviousInventory(GameData data) throws FileNotFoundException {
        File f = new File("src/main/resources/Inventory.json");
        Gson json = new Gson();
        Reader r = new FileReader(f);

        //Data type definition for the correct deserialization of the json file
        Type inventoryMapType = new TypeToken<Map<Characteristics.CharacteristicType, Weapon>>() {}.getType();

        Map<Characteristics.CharacteristicType, Weapon> inv = json.fromJson(r, inventoryMapType);

        //Only the name and the level of the weapons are saved. Their stats are saved in the GameData class.
        return addWeaponsStats(inv, data);
    }
    private static Map<Characteristics.CharacteristicType, Weapon> addWeaponsStats(Map<Characteristics.CharacteristicType, Weapon> inv, GameData data)
    {
        for(Weapon w : inv.values())
        {
            w.setStats(data.getWeaponStatMap().get(w.getName()));
        }
        return inv;
    }

}
