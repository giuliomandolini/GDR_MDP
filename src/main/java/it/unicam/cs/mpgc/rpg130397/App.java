package it.unicam.cs.mpgc.rpg130397;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class App {
    static void main() throws IOException {
        Map<String, Enemy> enemies = new HashMap<>();
        Enemy s = new Enemy("Swordsman", 10f, 2f, 10, 0, 10, null);
        Enemy a = new Enemy("Archer", 10f, 2f, 10, 10, 10, null);

        enemies.put(s.getName(), s);
        enemies.put(a.getName(), a);

        Gson j = new GsonBuilder().setPrettyPrinting().create();
        File f = new File("src/main/resources/Enemies.json");

        BufferedWriter writer = new BufferedWriter(new FileWriter(f));
        j.toJson(enemies, writer);
        writer.close();

        Reader r = new FileReader(f);
        Type weaponMapType = new TypeToken<Map<String, Enemy>>() {}.getType();
        enemies = j.fromJson(r, weaponMapType);

        System.out.println(enemies.get("Archer").getName());
    }
}
