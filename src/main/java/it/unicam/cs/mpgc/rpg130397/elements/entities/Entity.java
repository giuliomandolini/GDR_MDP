package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;

/**
 * This is the base class for each entity that exists in the game.<br>
 * It contains the standard {@link EntityStats}
 */
public abstract class Entity {
    private EntityStats stats;
    private final String name;

    public Entity(String name, float health, float speed) {
        stats = new EntityStats(health, speed);
        this.name = name;
    }
    public EntityStats getStats() {
        return stats;
    }
    public String getName() {
        return name;
    }
}
