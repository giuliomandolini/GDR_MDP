package it.unicam.cs.mpgc.rpg130397;

/**
 * This class is the base class of all Entities that are aggressive towards the player.
 */
public abstract class Enemy extends Entity{

    public Enemy(float health, float speed, float basicDamage) {
        super(health, speed, basicDamage);
    }
}
