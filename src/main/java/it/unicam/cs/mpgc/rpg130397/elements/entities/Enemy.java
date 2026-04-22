package it.unicam.cs.mpgc.rpg130397.elements.entities;

/**
 * This class is the base class of all Entities that are aggressive towards the player.
 * All enemies move towards the player until they are at range, then they attack.
 * If the player leaves their maximum range, the enemy goes back to moving towards the player.
 */
public class Enemy extends Entity{

    private float damage;
    private float cooldown;
    private Player player;
    private float range;

    private long lastAttack;

    public Enemy(String name, float health, float speed, float damage, float range, float cooldown, Player player) {
        super(name, health, speed);
        this.damage = damage;
        this.cooldown = cooldown;
        this.player = player;
        this.range = range;
    }

    ///Has to be called at every tick. Makes the enemy decide wether to move, to attack, or both.
    public void chooseMoveOrAttack()
    {
        //if the enemy is ranged, it attacks if the player is in range. Else, it moves.
        if(range > 0)
        {
            //TODO
            if(/*distanza tra il giocatore e il nemico < range*/true)
                if(canAttack()) attack();
            else move();
        }
        //if the enemy is melee, it moves, then if the player is near enough, it attacks.
        else
        {
            move();
            //TODO
            if(canAttack() && true/*è in collisione con il giocatore*/) attack();
        }
    }

    private void attack(){
        if(range == 0) player.changeHealth(-damage);
        //TODO
        else return; //altrimenti instanzia il proiettile
    }

    //checks if the cooldown permits the attack
    private boolean canAttack()
    {
        return lastAttack + cooldown < System.currentTimeMillis();
    }

    //makes the entity move towards the player
    private void move() {
        //TODO dipende da javafx
    }

}
