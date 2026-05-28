package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;

/**
 * This is the base class for each entity that exists in the game.<br>
 * It contains the standard {@link EntityStats} <br>
 * Every entity must have a speed and health, but not all entities can attack (possible friendly npc)
 */
public abstract class Entity extends GameObject{
    private final EntityStats stats;

    public Entity(String name, float health, float speed, Position position) {
        super(name, position);
        stats = new EntityStats(health, speed);
    }

    ///@return true if the health is <= 0, false otherwise
    public void changeHealth(float amount){
        stats.set(EntityStats.StatType.CURRENT_HEALTH, stats.get(EntityStats.StatType.CURRENT_HEALTH) + amount);
        if(stats.get(EntityStats.StatType.CURRENT_HEALTH) <= 0) die();
        if(stats.get(EntityStats.StatType.CURRENT_HEALTH) > stats.get(EntityStats.StatType.MAX_HEALTH))
        {
            stats.set(EntityStats.StatType.CURRENT_HEALTH, stats.get(EntityStats.StatType.MAX_HEALTH));
        }
    }

    protected abstract void die();

    public EntityStats getStats() {
        return stats;
    }
}
