package it.unicam.cs.mpgc.rpg130397;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class App {
    static void main() throws IOException {
        Map<String, Weapon> weapons = new HashMap<>();

        Weapon w = new Weapon("Dagger", 10f, 2f, 10, 0, Characteristics.CharacteristicType.INTELLIGENCE, null);
        Weapon w1 = new Weapon("Dagger 1", 10f, 2f, 10, 0, Characteristics.CharacteristicType.INTELLIGENCE, null);

        weapons.put(w.getName(), w);
        weapons.put(w1.getName(), w1);

        Gson j = new GsonBuilder().setPrettyPrinting().create();
        File f = new File("src/main/resources/Weapons.json");

        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        j.toJson(weapons, writer);
        writer.close();

        Reader r = new FileReader(f);
        Type weaponMapType = new TypeToken<Map<String, Weapon>>() {}.getType();
        weapons = j.fromJson(r, weaponMapType);

        System.out.println(weapons/*.get("Dagger").getName()*/);
    }
}
