package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;

/**
 * This is the base class for each entity that exists in the game.<br>
 * It contains the standard {@link EntityStats} <br>
 * Every entity must have a speed and health, but not all entities can attack (possible friendly npc)
 */
public abstract class Entity extends GameObject{
    private EntityStats stats;

    public Entity(String name, float health, float speed) {
        super(name);
        stats = new EntityStats(health, speed);
    }

    public void changeHealth(float amount){
        stats.set(EntityStats.StatType.CURRENT_HEALTH, amount);
    }
    protected abstract void die();

    public EntityStats getStats() {
        return stats;
    }
}
