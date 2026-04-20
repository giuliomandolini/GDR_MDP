package it.unicam.cs.mpgc.rpg130397;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the fundamental stats for each entity: <br>
 *  -Health <br>
 *  -Speed <br>
 *  -BasicDamage <br>
 */
public class Stats {

    public Stats(float health, float speed, float basicDamage)
    {
        stats.put(StatType.HEALTH, health);
        stats.put(StatType.SPEED, speed);
        stats.put(StatType.BASICDAMAGE, basicDamage);
    }

    //Hashmap per memorizzare le statistiche di una entità nel gioco
    private final Map<StatType, Float> stats = new HashMap<>();

    public enum StatType{
        HEALTH,
        SPEED,
        BASICDAMAGE
    }

    public float getStats(StatType type) {
        return stats.get(type);
    }

    public void setStats(StatType type, float value) {
        stats.replace(type, value);
    }
}

