package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.entities.player.Player;
import it.unicam.cs.mpgc.rpg130397.elements.stats.BulletStats;
import it.unicam.cs.mpgc.rpg130397.elements.stats.EntityStats;
import it.unicam.cs.mpgc.rpg130397.gamelogic.Position;
import it.unicam.cs.mpgc.rpg130397.elements.Updatable;
import it.unicam.cs.mpgc.rpg130397.elements.interactables.Chest;
import it.unicam.cs.mpgc.rpg130397.gamelogic.CollisionSystem;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;
import it.unicam.cs.mpgc.rpg130397.gamelogic.SpawnSystem;

/**
 * This class is the base class of all Entities that are aggressive towards the player.
 * All enemies move towards the player until they are at range, then they attack.
 * If the player leaves their maximum range, the enemy goes back to moving towards the player.
 */
public class Enemy extends Entity implements Updatable {

    private final float damage;
    private final long cooldown;
    private final float range;
    private final BulletStats bullet;

    //the field must not be saved in the json so it has to be declared transient
    private transient long lastAttack;
    //id is needed to override equals and hashcode more easily, or else it would be impossible to distinguish an enemy from another
    private final transient Player player;

    public Enemy(String name, float health, float speed, float damage, float range, long cooldown, BulletStats bullet, Position position) {
        super(name, health, speed, position);
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.bullet = bullet;
        this.setPosition(position);
        if(range > 0) lastAttack = System.currentTimeMillis();
        player = GameData.getPlayer();
    }

    ///Has to be called at every tick. Manages its movement. Combat is managed in {@link it.unicam.cs.mpgc.rpg130397.gamelogic.CombatSystem}
    public void update()
    {
        //if the enemy is ranged, it attacks if the player is in range. Else, it moves.
        if(range > 0)
        {
            if(getPosition().distanceFrom(player.getPosition()) > range)
                move();
        }
        //if the enemy is melee, it moves, then if the player is near enough, it attacks.
        else
        {
            if(!CollisionSystem.getPlayerCollisions().contains(this))
                move();
        }
    }


    //checks if the cooldown and eventually range permits the attack
    public boolean canAttack()
    {
        if(lastAttack + cooldown > System.currentTimeMillis()) return false;
        if(range > 0)
        {
            return !(getPosition().distanceFrom(GameData.getPlayer().getPosition()) > range);
        }
        return true;
    }
    public void setLastAttack() {
        lastAttack = System.currentTimeMillis();
    }

    protected void die()
    {
        if(Math.random() < GameData.CHEST_SPAWN_RATE)
        {
            SpawnSystem.spawn(new Chest(getPosition()));
        }
        GameData.removeGameObject(this);
    }

    private void move()
    {
        getPosition().moveTowards(player.getPosition(), getStats().get(EntityStats.StatType.SPEED));
    }

    public float getDamage() {
        return damage;
    }

    public long getCooldown() {
        return cooldown;
    }

    public float getRange() {
        return range;
    }

    public BulletStats getBullet() {
        return bullet;
    }

    public Player getPlayer() {
        return player;
    }
}
