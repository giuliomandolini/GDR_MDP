package it.unicam.cs.mpgc.rpg130397.elements.stats;

import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the fundamental stats for each entity: <br>
 *  -CurrentHealth <br>
 *  -MaxHealth <br>
 *  -Speed <br>
 */
public class EntityStats {

    public EntityStats(float health, float speed)
    {
        stats.put(StatType.CURRENT_HEALTH, health);
        stats.put(StatType.MAX_HEALTH, health);
        stats.put(StatType.SPEED, speed);
    }

    //Hashmap to memorize the statistics of an entity in the game
    private final Map<StatType, Float> stats = new HashMap<>();

    public enum StatType{
        CURRENT_HEALTH,
        MAX_HEALTH,
        SPEED
    }

    public float get(StatType type) {
        return stats.get(type);
    }

    public void set(StatType type, float value) {
        stats.replace(type, value);
    }
}

