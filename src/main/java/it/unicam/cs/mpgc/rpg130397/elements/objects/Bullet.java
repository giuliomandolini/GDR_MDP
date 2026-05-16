package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.BulletStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;

public class Bullet extends GameObject {

    private BulletStats stats;
    //the damage is inherited from the entity that spawns it
    private transient float damage;
    private transient Position target;

    static transient final float LIFESPAN = 5f;
    private transient long spawnTime;

    //Used for definition inside the JSON, the damage is set runtime on the Enemy constructor
    public Bullet(BulletStats stats, Entity spawner, Position target)
    {
        super(stats.getName(), spawner.getPosition());
        this.stats = stats;
        this.target = target;
        spawnTime = System.currentTimeMillis();
    }
    ///has to be called on each update
    public void update()
    {
        move();
        checkForCollision();
        checkForLifespan();
    }

    private void checkForCollision() {
        //TODO controlla le collisioni con il giocatore
    }

    private void checkForLifespan() {
        //TODO
        if(System.currentTimeMillis() > spawnTime + LIFESPAN) return; //distruggi il proiettile
    }

    private void move()
    {
        //TODO
    }

    public void setDamage(float damage)
    {
        this.damage = damage;
    }

}
