package it.unicam.cs.mpgc.rpg130397;

/**
 * This is the base class for each entity that exists in the game.<br>
 * It contains the standard {@link Stats}
 */
public abstract class Entity {
    Stats stats;

    public Entity(float health, float speed, float basicDamage) {
        stats = new Stats(health, speed, basicDamage);
    }
}
