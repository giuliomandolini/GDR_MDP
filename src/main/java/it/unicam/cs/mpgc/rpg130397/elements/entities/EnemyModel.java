package it.unicam.cs.mpgc.rpg130397.elements.entities;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.objects.Bullet;

/**
 * This class is the base class of all Entities that are aggressive towards the playerModel.
 * All enemies move towards the playerModel until they are at range, then they attack.
 * If the playerModel leaves their maximum range, the enemy goes back to moving towards the playerModel.
 */
public class EnemyModel extends Entity{

    private float damage;
    private float cooldown;
    private float range;
    private Bullet bullet;

    //the field must not be saved in the json so it has to be declared transient
    private transient long lastAttack;
    private transient PlayerModel playerModel;


    public EnemyModel(String name, float health, float speed, float damage, float range, float cooldown, Bullet bullet, PlayerModel playerModel, Position position) {
        super(name, health, speed, position);
        this.damage = damage;
        this.cooldown = cooldown;
        this.playerModel = playerModel;
        this.range = range;
        this.bullet = bullet;
        if(bullet != null)
            bullet.setDamage(damage);
    }

    ///Has to be called at every tick. Makes the enemy decide wether to move, to attack, or both.
    public void chooseMoveOrAttack()
    {
        //if the enemy is ranged, it attacks if the playerModel is in range. Else, it moves.
        if(range > 0)
        {
            //TODO
            if(/*distanza tra il giocatore e il nemico < range*/true)
                if(canAttack()) attack();
            else move();
        }
        //if the enemy is melee, it moves, then if the playerModel is near enough, it attacks.
        else
        {
            move();
            //TODO
            if(canAttack() && true/*è in collisione con il giocatore*/) attack();
        }
    }

    private void attack(){
        if(range == 0) playerModel.changeHealth(-damage);
        //TODO
        else return; //altrimenti instanzia il proiettile
    }

    //checks if the cooldown permits the attack
    private boolean canAttack()
    {
        return lastAttack + cooldown < System.currentTimeMillis();
    }

    //makes the entity move towards the playerModel
    private void move() {
        //TODO dipende da javafx
    }

    protected void die()
    {
        if(Math.random() > 0.95f) return;
        //TODO instanzia il baule
    }

}
