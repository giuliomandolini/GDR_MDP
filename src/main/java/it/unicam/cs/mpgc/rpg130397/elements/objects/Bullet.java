package it.unicam.cs.mpgc.rpg130397.elements.objects;

import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.BulletStats;
import it.unicam.cs.mpgc.rpg130397.elements.abstractelements.Position;
import it.unicam.cs.mpgc.rpg130397.elements.entities.Entity;
import it.unicam.cs.mpgc.rpg130397.elements.entities.GameObject;
import it.unicam.cs.mpgc.rpg130397.gamelogic.GameData;

public class Bullet extends GameObject {

    private BulletStats stats;
    //the damage is inherited from the entity that spawns it
    private transient float damage;
    private transient Position target;
    private transient Position bulletPosition;
    private transient Entity spawner;

    static transient final float LIFESPAN = 7000f;
    private transient long spawnTime;

    //Used for definition inside the JSON, the damage is set runtime on the Enemy constructor
    public Bullet(BulletStats stats, float damage, Entity spawner, Position target)
    {
        Position spawnPosition = new Position(spawner.getPosition().getX(), spawner.getPosition().getY());
        super(stats.getName(), spawnPosition);

        this.stats = stats;
        this.spawner = spawner;
        this.target = target;
        this.damage = damage;

        //takes in consideration only the past 6 hours (better override of hashcode)
        //(21600000 is the number of milliseconds in 6 hours)
        spawnTime = System.currentTimeMillis();
    }
    ///has to be called on each update
    public void update()
    {
        move();
        checkForLifespan();
    }

    private void checkForLifespan() {
        if(System.currentTimeMillis() > spawnTime + LIFESPAN) GameData.removeBullet(this);
    }

    private void move()
    {
        getPosition().moveTowards(target, stats.getSpeed());
    }

    public Entity getSpawner() {
        return spawner;
    }

    public float getDamage() {
        return damage;
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Bullet)) return false;
        return ((Bullet) o).getSpawnTime() == spawnTime && ((Bullet) o).getSpawner().equals(spawner);
    }

    @Override
    public int hashCode()
    {
        return (int) spawnTime + spawner.hashCode();
    }

}
