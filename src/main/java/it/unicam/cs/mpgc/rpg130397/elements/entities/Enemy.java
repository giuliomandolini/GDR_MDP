package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.BulletStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.EntityStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;
import it.unicam.cs.mpgc.rpg130397.gamelogic.CombatSystem;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

/**
 * This class is the base class of all Entities that are aggressive towards the player.
 * All enemies move towards the player until they are at range, then they attack.
 * If the player leaves their maximum range, the enemy goes back to moving towards the player.
 */
public class Enemy extends Entity{

    private float damage;
    private float cooldown;
    private float range;
    private BulletStats bullet;

    //the field must not be saved in the json so it has to be declared transient
    private transient long lastAttack;
    //id is needed to override equals and hashcode more easily, or else it would be impossible to distinguish an enemy from another
    private transient int id;
    private transient Player player;


    public Enemy(String name, float health, float speed, float damage, float range, float cooldown, BulletStats bullet, Position position, int id) {
        super(name, health, speed, position);
        this.damage = damage;
        this.cooldown = cooldown;
        this.range = range;
        this.bullet = bullet;
        this.id = id;
        lastAttack = System.currentTimeMillis();
        player = GameData.getPlayer();
    }

    ///Has to be called at every tick. Makes the enemy decide wether to move or to attack.
    public void update()
    {
        //if the enemy is ranged, it attacks if the player is in range. Else, it moves.
        if(range > 0)
        {
            if(getPosition().distanceFrom(player.getPosition()) <= range)
                if(canAttack()) attack();
            else move();
        }
        //if the enemy is melee, it moves, then if the player is near enough, it attacks. if it is reloading its attack it remains still.
        else
        {
            if(canAttack())
            {
                if(CombatSystem.getPlayerEnemyCollisions().contains(this)) attack();
                else move();
            }
        }
    }

    private void attack(){
        if(range == 0) CombatSystem.damage(GameData.getPlayer(), damage);
        else GameData.addBullet(new Bullet(bullet, this, GameData.getPlayer().getPosition()));

        lastAttack = System.currentTimeMillis();
    }

    //checks if the cooldown permits the attack
    private boolean canAttack()
    {
        return lastAttack + cooldown < System.currentTimeMillis();
    }

    protected void die()
    {
        if(Math.random() > 0.95f) return;
        //TODO instanzia il baule
    }

    private void move()
    {
        getPosition().moveTowards(player.getPosition(), getStats().get(EntityStats.StatType.SPEED));
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Enemy)) return false;
        if(((Enemy) obj).id == id) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
