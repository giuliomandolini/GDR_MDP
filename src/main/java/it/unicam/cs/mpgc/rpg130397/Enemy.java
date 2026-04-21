package it.unicam.cs.mpgc.rpg130397;

/**
 * This class is the base class of all Entities that are aggressive towards the player.
 * All enemies move towards the player until they are at range, then they attack.
 * If the player leaves their maximum range, the enemy goes back to moving towards the player.
 */
public abstract class Enemy extends Entity{

    private float damage;
    private float range;
    private boolean ranged;
    private float cooldown;

    private long lastAttack;

    public Enemy(float health, float speed, float damage, float range, boolean ranged, float cooldown) {
        super(health, speed);
        this.damage = damage;
        this.range = range;
        this.ranged = ranged;
        this.cooldown = cooldown;
    }

    public void Act()
    {
        if(/*posizione del giocatore è in range*/ true)
            Attack();
        else
            Move();
    }

    private void Attack() {
    }

    private void Move()

}
