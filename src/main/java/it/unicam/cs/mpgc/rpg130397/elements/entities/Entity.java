package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Stats;

/**
 * This is the base class for each entity that exists in the game.<br>
 * It contains the standard {@link Stats}
 */
public abstract class Entity {
    private Stats stats;
    private final String name;

    public Entity(String name, float health, float speed) {
        stats = new Stats(health, speed);
        this.name = name;
    }
    public Stats getStats() {
        return stats;
    }
    public String getName() {
        return name;
    }
}
