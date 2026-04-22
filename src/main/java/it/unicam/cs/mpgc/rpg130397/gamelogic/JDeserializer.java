package it.unicam.cs.mpgc.rpg130397.gamelogic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Enemy;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Weapon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class JDeserializer {
    /**
     *  Static method that deserializes the data contained in the weapons.json file into the weapons map
     * @return the map of all the weapons
     */
    public static Map<String, Weapon> getWeapons() throws FileNotFoundException {
        File f = new File("src/main/resources/jsons/Weapons.json");
        Gson json = new Gson();
        Reader r = new FileReader(f);
        //Data type definition for the correct deserialization of the json file
        Type weaponMapType = new TypeToken<Map<String, Weapon>>() {}.getType();
        return json.fromJson(r, weaponMapType);
    }

    /**
     *  Static method that deserializes the data contained in the enemies.json file into the enemies map
     * @return the map of all the enemies
     */
    public static Map<String, Enemy> getEnemies() throws FileNotFoundException {
        File f = new File("src/main/resources/jsons/Enemies.json");
        Gson json = new Gson();
        Reader r = new FileReader(f);
        //Data type definition for the correct deserialization of the json file
        Type enemyMapType = new TypeToken<Map<String, Enemy>>() {}.getType();
        return json.fromJson(r, enemyMapType);
    }
}
