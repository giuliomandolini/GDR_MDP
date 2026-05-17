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
    private transient Entity spawner;

    static transient final float LIFESPAN = 5f;
    private transient int spawnTime;

    //Used for definition inside the JSON, the damage is set runtime on the Enemy constructor
    public Bullet(BulletStats stats, Entity spawner, Position target)
    {
        super(stats.getName(), spawner.getPosition());
        this.stats = stats;
        this.spawner = spawner;
        this.target = target;

        //takes in consideration only the past 6 hours (better override of hashcode)
        //(21600000 is the number of milliseconds in 6 hours)
        spawnTime = (int) (System.currentTimeMillis() % 21600000);
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

    public Entity getSpawner() {
        return spawner;
    }

    public float getDamage() {
        return damage;
    }

    public int getSpawnTime() {
        return spawnTime;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Bullet)) return false;
        return ((Bullet) o).getSpawnTime() == spawnTime && ((Bullet) o).getSpawner() == spawner;
    }

    @Override
    public int hashCode()
    {
        return spawnTime + spawner.hashCode();
    }
}
