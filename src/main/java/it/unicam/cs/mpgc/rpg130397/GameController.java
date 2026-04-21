package it.unicam.cs.mpgc.rpg130397;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class GameController {
    private Map<String, Weapon> weapons = new HashMap<>();

    private List<Enemy> enemies;
    private Enemy closestEnemy;
    private long lastUpdate;
    private final float UPDATE_COOLDOWN = 0.05f;

    public GameController() {
        enemies = new LinkedList<>();
        getWeapons();
    }

    //Private method to transform the data contained in the weapons.json file into the weapons hashmap
    private void getWeapons() throws FileNotFoundException {
        File f = new File("src/main/resources/Weapons.json");
        Gson json = new Gson();
        Reader r = new FileReader(f);
        //Data type definition for the correct deserialization of the json file
        Type weaponMapType = new TypeToken<Map<String, Weapon>>() {}.getType();
        weapons = json.fromJson(r, weaponMapType);
    }

    public void updateClosestEnemy()
    {
        if(lastUpdate + UPDATE_COOLDOWN > System.currentTimeMillis()) return;
        closestEnemy = enemies.getFirst();
        for(Enemy e : enemies)
        {
            //TODO distanza tra nemici e giocatore
            if(/*distanza*/ true)
            {
                closestEnemy = e;
            }
        }
    }



    public Enemy getClosestEnemy() {
        return closestEnemy;
    }
}
